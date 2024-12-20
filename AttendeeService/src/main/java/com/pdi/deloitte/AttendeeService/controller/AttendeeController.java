package com.pdi.deloitte.AttendeeService.controller;

import com.pdi.deloitte.AttendeeService.model.Attendee;
import com.pdi.deloitte.AttendeeService.service.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

