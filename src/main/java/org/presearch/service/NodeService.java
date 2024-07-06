package org.presearch.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.presearch.domain.Node;
import org.presearch.repository.NodeRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class NodeService {

    private final NodeRepository repository;

    public Node findById(String key) {
        return this.repository.findByPublicKey(key);
    }

    public List<Node> findByPeriodsByPeriodStartDateGreaterThan(LocalDateTime startDate) {
        List<Node> nodes = new ArrayList<>(); 
        Iterable<Node> iterable = this.repository.findNodesByPeriodsStartDate(startDate);
        iterable.forEach(nodes::add);
        return nodes;
    }

    public Node save(Node node){
        return this.repository.save(node);
    }
    
    public List<Node> list() {
        List<Node> nodes = new ArrayList<>(); 
        Iterable<Node> iterable = this.repository.findAll();
        iterable.forEach(nodes::add);
        return nodes;
    }

}
