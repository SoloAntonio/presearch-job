package org.presearch.repository;

import java.util.Optional;

import org.presearch.domain.NodeLog;
import org.springframework.data.repository.CrudRepository;

public interface NodeLogRepository extends CrudRepository<NodeLog,Integer>{

    public Optional<NodeLog> findFirstByOrderByIdDesc();

}
