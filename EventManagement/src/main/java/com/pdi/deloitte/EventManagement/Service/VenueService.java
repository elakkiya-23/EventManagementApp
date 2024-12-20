package com.pdi.deloitte.EventManagement.Service;


import com.pdi.deloitte.EventManagement.model.Venue;
import com.pdi.deloitte.EventManagement.repository.VenueRepository;
import com.pdi.deloitte.EventManagement.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    // Get all venues
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    // Get venue by ID
    public Venue getVenueById(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));
    }

    // Create new venue
    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    // Update venue by ID
    public Venue updateVenue(Long id, Venue venueDetails) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));

        venue.setName(venueDetails.getName());
        venue.setLocation(venueDetails.getLocation());
        venue.setCapacity(venueDetails.getCapacity());

        return venueRepository.save(venue);
    }

    // Delete venue by ID
    public void deleteVenue(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id " + id));
        venueRepository.delete(venue);
    }
}
