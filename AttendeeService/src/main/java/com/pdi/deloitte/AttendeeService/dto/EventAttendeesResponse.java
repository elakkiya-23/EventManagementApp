package com.pdi.deloitte.AttendeeService.dto;

import java.util.List;

public class EventAttendeesResponse {

    private String eventName;
    private List<AttendeeSummary> attendees;

    public static class AttendeeSummary {
        private Long id;
        private String name;

        public AttendeeSummary(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<AttendeeSummary> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<AttendeeSummary> attendees) {
        this.attendees = attendees;
    }
}
