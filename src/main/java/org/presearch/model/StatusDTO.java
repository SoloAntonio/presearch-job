package org.presearch.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {

    private Boolean connected;
    private Boolean blocked;
    private LocalDateTime inCurrentStateSince;
    private Long minutesInCurrentState;
    
}
