package com.pdi.deloitte.AttendeeService.service;

import com.pdi.deloitte.AttendeeService.client.EventManagementClient;
import com.pdi.deloitte.AttendeeService.model.Attendee;
import com.pdi.deloitte.AttendeeService.repository.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final EventManagementClient eventManagementClient;


    @Autowired
    public AttendeeService(AttendeeRepository attendeeRepository,EventManagementClient eventManagementClient) {
        this.attendeeRepository = attendeeRepository;
        this.eventManagementClient = eventManagementClient;

    }

    public Attendee registerForEvent(Attendee attendee, Long eventId) {
        // Fetch event details using Feign Client
        try {
            Event eventDetails = eventManagementClient.getEventDetails(eventId);
            if (eventDetails != null) {
                attendee.setEventId(eventId); // Associate the event ID with the attendee
                return attendeeRepository.save(attendee);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to fetch event details. Event ID: " + eventId);
        }

        throw new RuntimeException("Event not found with ID: " + eventId);
    }


    public Attendee addAttendee(Attendee attendee)
    {
        return attendeeRepository.save(attendee);
    }

    public Attendee updateAttendee(Long id, Attendee updatedAttendee) {
        Attendee existingAttendee = attendeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendee not found with ID: " + id));
        existingAttendee.setName(updatedAttendee.getName());
        existingAttendee.setEmail(updatedAttendee.getEmail());
        existingAttendee.setPhone(updatedAttendee.getPhone());
        existingAttendee.setRegistrationDate(updatedAttendee.getRegistrationDate());
        return attendeeRepository.save(existingAttendee);
    }

    public void deleteAttendee(Long id) {
        attendeeRepository.deleteById(id);
    }

    public List<Attendee> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    public Attendee getAttendeeById(Long id) {
        return attendeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendee not found with ID: " + id));
    }
}
