package com.pdi.deloitte.AttendeeService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "EVENT-SERVICE",url="http://localhost:8080/api/events")
public interface EventManagementClient
{
    @GetMapping("/api/events/{id}")
    Event getEventDetails(@PathVariable("id") Long eventId);
}
