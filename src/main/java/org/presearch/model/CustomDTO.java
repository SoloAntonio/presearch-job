package org.presearch.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomDTO {

    private Integer id;
    private MetaDTO meta;
    private StatusDTO status;
    private List<PeriodDTO> periods;
    
}
