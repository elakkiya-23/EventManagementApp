package com.pdi.deloitte.AttendeeService.controller;

import com.pdi.deloitte.AttendeeService.dto.EventAttendeesResponse;
import com.pdi.deloitte.AttendeeService.model.Attendee;
import com.pdi.deloitte.AttendeeService.model.Event;
import com.pdi.deloitte.AttendeeService.service.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendees")
public class AttendeeController {

    private final AttendeeService attendeeService;


    @Autowired
    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    @PostMapping("/register/{eventId}")
    public ResponseEntity<?> registerForEvent(@RequestBody Attendee attendee, @PathVariable Long eventId) {
        try {
            Attendee registeredAttendee = attendeeService.registerForEvent(attendee, eventId);
            return new ResponseEntity<>(registeredAttendee, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/event-details/{eventId}")
    public ResponseEntity<Event> getEventDetails(@PathVariable Long eventId) {
        try {
            Event eventDetails = attendeeService.getEventDetails(eventId);
            return new ResponseEntity<>(eventDetails, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{attendeeId}/details")
    public ResponseEntity<Map<String, Object>> getAttendeeWithEvent(@PathVariable Long attendeeId) {
        Map<String, Object> response = attendeeService.getAttendeeWithEvent(attendeeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return attendeeService.getAllEvents();
    }

    @GetMapping("/with-events")
    public ResponseEntity<List<Attendee>> getAllAttendeesWithEvents() {
        List<Attendee> attendeesWithEvents = attendeeService.getAllAttendeesWithEventDetails();
        return ResponseEntity.ok(attendeesWithEvents);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<EventAttendeesResponse> getAttendeesForEvent(@PathVariable Long eventId) {
        EventAttendeesResponse response = attendeeService.getEventAttendees(eventId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-date")
    public Map<String, Object> getAttendeesByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return attendeeService.getAttendeesByDate(date);
    }

    @PostMapping
    public ResponseEntity<Attendee> addAttendee(@RequestBody Attendee attendee) {
        Attendee createdAttendee = attendeeService.addAttendee(attendee);
        return new ResponseEntity<>(createdAttendee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendee> updateAttendee(@PathVariable Long id, @RequestBody Attendee attendee) {
        Attendee updatedAttendee = attendeeService.updateAttendee(id, attendee);
        return new ResponseEntity<>(updatedAttendee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendee(@PathVariable Long id) {
        attendeeService.deleteAttendee(id);
        return new ResponseEntity<>("Attendee deleted successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Attendee>> getAllAttendees() {
        List<Attendee> attendees = attendeeService.getAllAttendees();
        return new ResponseEntity<>(attendees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendee> getAttendeeById(@PathVariable Long id) {
        Attendee attendee = attendeeService.getAttendeeById(id);
        return new ResponseEntity<>(attendee, HttpStatus.OK);
    }
}

