package org.presearch.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Node {

    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "node_sequence")
    @SequenceGenerator(initialValue = 1, allocationSize  = 1, name = "node_sequence", sequenceName = "node_sequence")
    @Id
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="node", fetch=FetchType.LAZY)
    private List<Period> periods = new ArrayList<>();

    @Column(length=500, nullable=false)
    private String publicKey;

    @Embedded
    private Meta meta;
    @Embedded
    private Status status;

    public void addPeriod(Period period){
        this.periods.add(period);
        period.setNode(this);
    }

    public void removePeriod(Period period){
        this.periods.remove(period);
        period.setNode(this);
    }

}
