package org.presearch.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Status {

    private Boolean connected;
    private Boolean blocked;
    private LocalDateTime inCurrentStateSince;
    private Long minutesInCurrentState;
    
}
