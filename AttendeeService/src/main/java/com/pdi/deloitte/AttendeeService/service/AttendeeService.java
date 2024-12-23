package com.pdi.deloitte.AttendeeService.service;

import com.pdi.deloitte.AttendeeService.client.EventManagementClient;
import com.pdi.deloitte.AttendeeService.dto.AttendeeEventDTO;
import com.pdi.deloitte.AttendeeService.dto.EventAttendeesResponse;
import com.pdi.deloitte.AttendeeService.exception.ResourceNotFoundException;
import com.pdi.deloitte.AttendeeService.model.Attendee;
import com.pdi.deloitte.AttendeeService.model.Event;
import com.pdi.deloitte.AttendeeService.repository.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            throw new RuntimeException("Event not found with ID: " + eventId);
        }
        throw new RuntimeException("Unable to fetch event ID!");
    }

    public Event getEventDetails(Long eventId) {
        try {
            return eventManagementClient.getEventDetails(eventId);
        } catch (Exception e) {
            throw new RuntimeException("Unable to fetch event details. Event ID: " + eventId, e);
        }
    }

    public Map<String, Object> getAttendeeWithEvent(Long attendeeId) {
        // Fetch attendee details
        Attendee attendee = attendeeRepository.findById(attendeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendee with ID " + attendeeId + " not found."));

        // Fetch event details using Feign client
        Event eventDetails = eventManagementClient.getEventDetails(attendee.getEventId());

        // Combine attendee and event details into a map
        Map<String, Object> response = new HashMap<>();
        response.put("attendee", attendee);
        response.put("eventDetails", eventDetails);

        return response;
    }

    // Method to get all events
    public List<Event> getAllEvents() {
        return eventManagementClient.getAllEvents();
    }

    public List<Attendee> getAllAttendeesWithEventDetails() {
        List<Attendee> attendees = attendeeRepository.findAll();

        for (Attendee attendee : attendees) {
            try {
                // Fetch event details using Feign Client
                Event eventDetails = eventManagementClient.getEventDetails(attendee.getEventId());
                attendee.setEventDetails(eventDetails);
            } catch (Exception e) {
                // Handle exceptions (e.g., log the error and set eventDetails to null)
                attendee.setEventDetails(null);
            }
        }

        return attendees;
    }

    public EventAttendeesResponse getEventAttendees(Long eventId) {
        // Fetch attendees for the eventId
        List<Attendee> attendees = attendeeRepository.findByEventId(eventId);

        // Fetch event details
        Event eventDetails = null;
        try {
            eventDetails = eventManagementClient.getEventDetails(eventId);
        } catch (Exception e) {
            System.out.println("Error fetching event details: " + e.getMessage());
        }

        // Transform to DTO
        EventAttendeesResponse response = new EventAttendeesResponse();
        response.setEventName(eventDetails != null ? eventDetails.getName() : "Unknown Event");

        List<EventAttendeesResponse.AttendeeSummary> attendeeSummaries = attendees.stream()
                .map(attendee -> new EventAttendeesResponse.AttendeeSummary(attendee.getId(), attendee.getName()))
                .toList();

        response.setAttendees(attendeeSummaries);
        return response;
    }

    public Map<String, Object> getAttendeesByDate(LocalDate date) {
        String formattedDate = date.toString();

        List<Event> events = eventManagementClient.getEventsByDate(formattedDate);
        List<AttendeeEventDTO> attendeeDetails = new ArrayList<>();

        for (Event event : events) {
            List<Attendee> attendees = attendeeRepository.findByEventId(event.getId());

            for (Attendee attendee : attendees) {
                attendeeDetails.add(new AttendeeEventDTO(
                        attendee.getId(),
                        attendee.getName(),
                        event.getName(),
                        event.getVenue().getName()
                ));
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("date", formattedDate);
        response.put("attendees", attendeeDetails);

        return response;
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
