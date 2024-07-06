package org.presearch.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodDTO {

    private LocalDateTime periodStartDate;
    private LocalDateTime periodEndDate;
    private Integer periodSeconds;
    @Embedded
    private Connections connections;
    @Embedded
    private Disconnections disconnections;
    private Integer totalUptimeSeconds;
    private Integer uptimePercentage;
    private Float avgUptimeScore;
    private Float avgLatencyMs;
    private Float avgLatencyScore;
    private Integer totalRequests;
    private Integer successfulRequests;
    private Float avgSuccessRate;
    private Float avgSuccessRateScore;
    private Float avgReliabilityScore;
    private Float avgStakedCapacityPercent;
    private Float avgUtilizationPercent;
    private BigDecimal totalPreEarned;
    private Long rewardableRequests;

    @Data
    @Embeddable
    private static class Connections{
        private Integer numConnections;
        private LocalDateTime mostRecentConnection;
    }

    @Data
    @Embeddable
    private static class Disconnections{
        private Integer numDisconnections;
        private LocalDateTime mostRecentDisconnection;
    }

}
