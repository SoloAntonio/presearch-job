package org.presearch.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicKeyDTO {

    private MetaDTO meta;
    private StatusDTO status;
    private PeriodDTO period;
}
