package org.presearch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.presearch.domain.NodeLog;
import org.presearch.repository.NodeLogRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class NodeLogService {

    private final NodeLogRepository repository;

    public NodeLog save(@NonNull NodeLog nodeLog){
        return this.repository.save(nodeLog);
    }

    public Optional<NodeLog> findFirstByOrderByIdDesc(){
        return this.repository.findFirstByOrderByIdDesc();
    }

    public List<NodeLog> list() {
        List<NodeLog> nodes = new ArrayList<>(); 
        Iterable<NodeLog> iterable = this.repository.findAll();
        iterable.forEach(nodes::add);
        return nodes;
    }

}
