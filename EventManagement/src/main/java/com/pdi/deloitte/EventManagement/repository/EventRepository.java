package com.pdi.deloitte.EventManagement.repository;

import com.pdi.deloitte.EventManagement.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>
{
    // Find conflicting events by venue, date, and overlapping time
    @Query("SELECT e FROM Event e WHERE e.venue.id = :venueId AND e.date = :date " +
            "AND ((:startTime BETWEEN e.start_time AND e.end_time) " +
            "OR (:endTime BETWEEN e.start_time AND e.end_time) " +
            "OR (e.start_time BETWEEN :startTime AND :endTime))")
    List<Event> findConflictingEvents(
            @Param("venueId") Long venueId,
            @Param("date") LocalDate date,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );
    // Custom queries can go here if needed
    // Custom query to find conflicting events excluding the current event
    @Query("SELECT e FROM Event e WHERE e.venue.id = :venueId AND e.date = :date " +
            "AND ((e.start_time >= :startTime AND e.start_time < :endTime) OR " +
            "(e.end_time > :startTime AND e.end_time <= :endTime)) " +
            "AND e.id != :currentEventId")
    List<Event> findConflictingEventsExcludingCurrent(Long venueId, LocalDate date, String startTime, String endTime, Long currentEventId);

}
