package com.pdi.deloitte.AttendeeService.repository;

import com.pdi.deloitte.AttendeeService.model.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long>
{

}
