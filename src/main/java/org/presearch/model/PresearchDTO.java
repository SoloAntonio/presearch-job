package org.presearch.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresearchDTO {
    
    private Boolean success;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer nodesReturned;
    private List<NodeDTO> nodes;

}
