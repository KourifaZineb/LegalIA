package com.chatbot.lawyerconnectorservice.repository;

import com.chatbot.commonlibrary.enums.ConnectionStatus;
import com.chatbot.lawyerconnectorservice.model.Connection;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    List<Connection> findByUserIdOrderByRequestDateDesc(String userId);

    List<Connection> findByLawyerIdOrderByRequestDateDesc(Long lawyerId);

    List<Connection> findByStatusOrderByRequestDateDesc(ConnectionStatus status);

    List<Connection> findByLawyerIdAndStatusOrderByRequestDateDesc(Long lawyerId, ConnectionStatus status);

    List<Connection> findByUserIdAndStatusOrderByRequestDateDesc(String userId, ConnectionStatus status);

    Optional<Connection> findByUserIdAndLawyerIdAndStatus(String userId, Long lawyerId, ConnectionStatus status);

    @Query("SELECT COUNT(c) FROM Connection c WHERE c.lawyerId = :lawyerId AND c.status = :status")
    Long countByLawyerIdAndStatus(@Param("lawyerId") Long lawyerId, @Param("status") ConnectionStatus status);

    @Query("SELECT COUNT(c) FROM Connection c WHERE c.userId = :userId AND c.status = :status")
    Long countByUserIdAndStatus(@Param("lawyerId") String userId, @Param("status") ConnectionStatus status);

    @Query("SELECT c FROM Connection c WHERE c.requestDate >= :startDate AND c.requestDate <= :endDate ORDER BY c.requestDate DESC")
    List<Connection> findConnectionsInPeriod(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
}