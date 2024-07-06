package org.presearch.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.presearch.model.PresearchDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;


@Service
public class PresearchService {

    private static final Logger log = LoggerFactory.getLogger(PresearchService.class);

    @Value("${presearch.url}${presearch.api_key}")
    private String url;

    private WebClient webClient;

    private static final ExchangeStrategies strategies = ExchangeStrategies
    .builder()
    .codecs(configurer -> {
        ObjectMapper mapper = JsonMapper.builder() 
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .serializationInclusion(JsonInclude.Include.NON_EMPTY)
            .build();

        configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(mapper, MediaType.APPLICATION_JSON));
        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON));
    }).build();

    @PostConstruct
    private void init() {
        this.webClient = WebClient.builder()
                        .clientConnector(new ReactorClientHttpConnector(
                                            HttpClient.create().compress(true).followRedirect(true)
                        ))
                        .baseUrl(url)
                        .exchangeStrategies(strategies)
                        .build();
    }    
    
    public PresearchDTO presearch(LocalDateTime startDate, LocalDateTime endDate, boolean stats) {
        return  webClient.get().uri(uriBuilder -> uriBuilder
                                .queryParam("stats", stats)
                                .queryParam("start_date", convertDateUTC(startDate))
                                .queryParam("end_date", convertDateUTC(endDate))
                                .build())
            .retrieve()
            .onStatus(HttpStatus.TOO_MANY_REQUESTS::isSameCodeAs, response -> {
                log.error("{} Next try in 1 hour.", response.statusCode()); 
                return Mono.empty();
                })
            .bodyToMono(PresearchDTO.class).block();
    }
    
    private ZonedDateTime convertDateUTC(LocalDateTime dateTime){
        return dateTime.atZone(ZoneId.of("Z"));
    }
}
