package org.presearch.task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.presearch.domain.Node;
import org.presearch.domain.NodeKey;
import org.presearch.domain.NodeLog;
import org.presearch.domain.Period;
import org.presearch.model.NodeDTO;
import org.presearch.model.PeriodDTO;
import org.presearch.model.PresearchDTO;
import org.presearch.service.NodeLogService;
import org.presearch.service.NodeService;
import org.presearch.service.PeriodService;
import org.presearch.service.PresearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.PostConstruct;

import lombok.Data;

@Data
@Component
public class ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private static final ModelMapper modelMapper = new ModelMapper();

    @Value("${presearch.initial_date}")
    private String initialDate;
    @Value("${presearch.max_request_hour}")
    private Integer maxRequestHour;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private final PresearchService presearchService;
    private final NodeService nodeService;
    private final PeriodService periodService;
    private final NodeLogService nodeLogService;

    public ScheduledTask(PresearchService presearchService,NodeService nodeService, PeriodService periodService, NodeLogService nodeLogService){
        this.presearchService = presearchService;
        this.nodeService = nodeService;
        this.periodService = periodService;
        this.nodeLogService = nodeLogService;
    }

    @PostConstruct
    private void setDates(){
        Optional<NodeLog> nodeLog = nodeLogService.findFirstByOrderByIdDesc();
        startDate = nodeLog.isPresent()?nodeLog.get().getStartDate():LocalDateTime.parse(initialDate);
        endDate = startDate.plusSeconds(86399);
        plusDay();
    }

    private void plusDay(){
        if(startDate.equals(LocalDateTime.parse(initialDate)))
            return;
        startDate = startDate.plusDays(1);
        endDate = endDate.plusDays(1);
    }

    @Scheduled(initialDelay = 1, fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void getPresearchStats() {
        LocalTime startTime = LocalTime.now();
        log.info("The start time is:{}", startTime);
        PresearchDTO presearchDTO = presearchService.presearch(startDate, endDate, false);
        LocalTime endTime = LocalTime.now();
        log.info("Node: {} {}", presearchDTO.getNodes().get(0).getPublicKey().getStatus().getConnected(), presearchDTO.getNodes().get(0).getPublicKey().getStatus().getInCurrentStateSince());
        log.info("The end time is: {} Time Duration: {}", endTime, Duration.between(startTime, endTime).toSeconds());
    }

    @Scheduled(cron = "@hourly")
    public void getPresearchData() {
        for(int i = 0; i < maxRequestHour; i++){
            // If is current date finish
            if(startDate.toLocalDate().compareTo(LocalDate.now(ZoneId.of("America/Monterrey")))>-1)
                return;
            LocalTime startTime = LocalTime.now(ZoneId.of("America/Monterrey"));
            log.info("The start time is: {}", startTime);
            log.info("Start Date: {} - End Date: {}",startDate, endDate);
            
            PresearchDTO presearchDTO = presearchService.presearch(startDate, endDate, true);
            if(Boolean.FALSE.equals(presearchDTO.getSuccess()))
                return;
            
            nodeLogService.save(convertToEntity(presearchDTO));
            plusDay();
            if(presearchDTO.getNodes().isEmpty())
                continue;

            if(startDate.equals(LocalDateTime.parse(initialDate)))
                nodeService.save(convertToEntity(presearchDTO.getNodes().get(0)));
            else
                saveNode(presearchDTO);
                
            if (log.isInfoEnabled()) {
                log.info(presearchDTO.toString());
            }            
            LocalTime endTime = LocalTime.now();
            log.info("The end time is: {} - Time Duration: {}s" ,endTime, Duration.between(startTime, endTime).toSeconds());
        }    
    }
    
    private Node convertToEntity(NodeDTO nodeDTO){
    	modelMapper.typeMap(NodeDTO.class, Node.class).
			addMappings(mapper -> {
				mapper.map(src -> src.getPublicKey().getMeta(),Node::setMeta);
				mapper.map(src -> src.getPublicKey().getStatus(),Node::setStatus);
                mapper.map(src -> src.getPublicKey().getPeriod(),Node::addPeriod);
			});
		Node node = modelMapper.map(nodeDTO, Node.class);
		node.setPublicKey(NodeKey.NODE_1);	  
        return node;
    }

    private Period convertToEntity(PeriodDTO periodDTO){
        return modelMapper.map(periodDTO, Period.class);
    }

    private NodeLog convertToEntity(PresearchDTO presearchDTO){
        return modelMapper.map(presearchDTO, NodeLog.class);
    }

    private void saveNode(PresearchDTO presearchDTO){
        Node node = new Node();
        node.setId(1);
        Period period = convertToEntity(presearchDTO.getNodes().get(0).getPublicKey().getPeriod());
        period.setNode(node);
        periodService.save(period);
    }
}
