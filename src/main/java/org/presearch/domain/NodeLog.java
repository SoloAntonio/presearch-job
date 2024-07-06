package org.presearch.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NodeLog {

    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "node_log_sequence")
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "node_log_sequence", sequenceName = "node_log_sequence")
    @Id
    private Integer id;
    private Boolean success;
    private Integer nodesReturned;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
