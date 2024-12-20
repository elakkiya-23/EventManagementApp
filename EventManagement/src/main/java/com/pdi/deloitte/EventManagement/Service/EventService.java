package com.pdi.deloitte.EventManagement.Service;

import com.pdi.deloitte.EventManagement.Exception.ResourceNotFoundException;
import com.pdi.deloitte.EventManagement.model.Category;
import com.pdi.deloitte.EventManagement.model.Event;
import com.pdi.deloitte.EventManagement.model.Venue;
import com.pdi.deloitte.EventManagement.repository.CategoryRepository;
import com.pdi.deloitte.EventManagement.repository.EventRepository;
import com.pdi.deloitte.EventManagement.Exception.VenueAlreadyBookedException;
import com.pdi.deloitte.EventManagement.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public EventService(EventRepository eventRepository,VenueRepository venueRepository,CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.categoryRepository = categoryRepository;
    }
    // Check if the venue is available for the given date and time
    public boolean isVenueAvailable(Long venueId, LocalDate date, String startTime, String endTime) {
        List<Event> conflictingEvents = eventRepository.findConflictingEvents(venueId, date, startTime, endTime);
        return conflictingEvents.isEmpty(); // If no conflicts, the venue is available
    }
    public boolean isVenueAvailableU(Long venueId, LocalDate date, String startTime, String endTime, Long currentEventId) {
        List<Event> conflictingEvents = eventRepository.findConflictingEventsExcludingCurrent(venueId, date, startTime, endTime, currentEventId);
        return conflictingEvents.isEmpty(); // If no conflicts, the venue is available
    }


    // Create
    public Event createOrUpdateEvent(Event event)
    {
        // Validate the venue
        Venue venue = venueRepository.findById(event.getVenue().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue with ID " + event.getVenue().getId() + " not found"));

        // Validate the category
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + event.getCategory().getId() + " not found"));
        // Set the venue object in the event
        event.setVenue(venue);

        // Set the category object in the event
        event.setCategory(category);

        boolean isAvailable = isVenueAvailable(
                event.getVenue().getId(),
                event.getDate(),
                event.getStart_time(),
                event.getEnd_time()
        );

        if (!isAvailable) {
            throw new VenueAlreadyBookedException("The venue is already booked for this date and time slot.");
        }

        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, Event updatedEvent) {
        // Fetch the existing event by ID
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with ID " + eventId + " not found."));

        // Validate the venue
        Venue venue = venueRepository.findById(updatedEvent.getVenue().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue with ID " + updatedEvent.getVenue().getId() + " not found"));

        // Set the venue object in the updated event
        updatedEvent.setVenue(venue);

        // Validate the category
        Category category = categoryRepository.findById(updatedEvent.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + updatedEvent.getCategory().getId() + " not found"));
        // Set the venue object in the event
        updatedEvent.setCategory(category);

        // Check if the venue is available for the updated date and time
        boolean isAvailable = isVenueAvailableU(
                updatedEvent.getVenue().getId(),
                updatedEvent.getDate(),
                updatedEvent.getStart_time(),
                updatedEvent.getEnd_time(),
                eventId
        );

        if (!isAvailable) {
            throw new VenueAlreadyBookedException("The venue is already booked for this date and time slot.");
        }

        // Update event details
        existingEvent.setName(updatedEvent.getName());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setDate(updatedEvent.getDate());
        existingEvent.setStart_time(updatedEvent.getStart_time());
        existingEvent.setEnd_time(updatedEvent.getEnd_time());
        existingEvent.setOrganizer(updatedEvent.getOrganizer());
        existingEvent.setType(updatedEvent.getType());
        existingEvent.setTicketPrice(updatedEvent.getTicketPrice());
        existingEvent.setCategory(updatedEvent.getCategory());
        existingEvent.setVenue(updatedEvent.getVenue());

        return eventRepository.save(existingEvent);
    }

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get event by ID
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with ID " + eventId + " not found."));
    }

    // Delete event by ID
    public void deleteEventById(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("Event with ID " + eventId + " not found.");
        }
        eventRepository.deleteById(eventId);
    }


}
