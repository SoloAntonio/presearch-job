package org.presearch.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Period {

    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "period_sequence")
    @SequenceGenerator(initialValue = 1,allocationSize = 1, name = "period_sequence", sequenceName = "period_sequence")
    @Id
    private Integer id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="node_id",nullable = false, foreignKey = @ForeignKey(name="fk_node_id"))
    private Node node;

    private LocalDateTime periodStartDate;
    private LocalDateTime periodEndDate;
    private Integer periodSeconds;
    @Embedded
    private Connections connections;
    @Embedded
    private Disconnections disconnections;
    private Integer totalUptimeSeconds;
    private Short uptimePercentage;
    @Column(columnDefinition="numeric(9,6)")
    private BigDecimal avgUptimeScore;
    @Column(columnDefinition="numeric(9,4)")
    private BigDecimal avgLatencyMs;
    @Column(columnDefinition="numeric(9,6)")
    private BigDecimal avgLatencyScore;
    private Integer totalRequests;
    private Integer successfulRequests;
    @Column(columnDefinition="numeric(5,2)")
    private BigDecimal avgSuccessRate;
    @Column(columnDefinition="numeric(5,2)")
    private BigDecimal avgSuccessRateScore;
    @Column(columnDefinition="numeric(9,6)")
    private BigDecimal avgReliabilityScore;
    @Column(columnDefinition="numeric(11,10)")
    private BigDecimal avgStakedCapacityPercent;
    @Column(columnDefinition="numeric(11,10)")
    private BigDecimal avgUtilizationPercent;
    @Column(columnDefinition="numeric(25,16)")
    private BigDecimal totalPreEarned;
    private Long rewardableRequests;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    private static class Connections{
        @Column
        private Integer numConnections;
        @Column
        private LocalDateTime mostRecentConnection;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    private static class Disconnections{
        @Column
        private Integer numDisconnections;
        @Column
        private LocalDateTime mostRecentDisconnection;
    }

}
