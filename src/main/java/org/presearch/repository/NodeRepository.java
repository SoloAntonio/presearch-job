package org.presearch.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.presearch.domain.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NodeRepository extends JpaRepository<Node,Integer>{

    @Query("SELECT n FROM Node n JOIN FETCH n.periods p WHERE p.periodStartDate > :startDate")
    public List<Node> findNodesByPeriodsStartDate(@Param("startDate") LocalDateTime startDate);

    public Node findByPublicKey(@Param("publicKey") String key);
}
