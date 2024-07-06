package org.presearch.service;

import java.util.ArrayList;
import java.util.List;

import org.presearch.domain.Period;
import org.presearch.repository.PeriodRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class PeriodService {

    private final PeriodRepository repository;

    public Period save(Period period){
        return this.repository.save(period);
    }
    
    public List<Period> list() {
        List<Period> periods = new ArrayList<>(); 
        Iterable<Period> iterable = this.repository.findAll();
        iterable.forEach(periods::add);
        return periods;
    }

}
