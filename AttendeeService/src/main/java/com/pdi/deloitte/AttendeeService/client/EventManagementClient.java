package com.pdi.deloitte.AttendeeService.client;

import com.pdi.deloitte.AttendeeService.model.Attendee;
import com.pdi.deloitte.AttendeeService.model.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "EVENT-SERVICE",url="http://localhost:8080/api/events")
public interface EventManagementClient
{
    @GetMapping("/by-date")
    List<Event> getEventsByDate(@RequestParam("date") String date);
    @GetMapping
    List<Event> getAllEvents();
    @GetMapping("/{id}")
    Event getEventDetails(@PathVariable("id") Long eventId);
}
