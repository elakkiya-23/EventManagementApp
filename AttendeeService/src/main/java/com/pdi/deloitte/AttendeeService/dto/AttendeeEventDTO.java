package com.pdi.deloitte.AttendeeService.dto;

public class AttendeeEventDTO {

    private Long attendeeId;
    private String attendeeName;
    private String eventName;
    private String venueName;

    public AttendeeEventDTO() {}

    public AttendeeEventDTO(Long attendeeId, String attendeeName, String eventName, String venueName) {
        this.attendeeId = attendeeId;
        this.attendeeName = attendeeName;
        this.eventName = eventName;
        this.venueName = venueName;
    }

    public Long getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(Long attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }
}
