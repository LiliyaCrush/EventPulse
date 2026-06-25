package com.liliya.eventpulse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event title is required.")
    private String title;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate eventDate;
    @Min(value = 0, message = "Attendance must be 0 or greater.")
    private Integer attendance;
    @Min(value = 0, message = "WiFi Estimate must be 0 or greater.")
    private Integer wifiEstimate;
    @Min(value = 0, message = "Network Usage must be 0 or greater.")
    private Double networkUsage;
    private String notes;

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public Integer getWifiEstimate() {
        return wifiEstimate;
    }

    public void setWifiEstimate(Integer wifiEstimate) {
        this.wifiEstimate = wifiEstimate;
    }

    public Double getNetworkUsage() {
        return networkUsage;
    }

    public void setNetworkUsage(Double networkUsage) {
        this.networkUsage = networkUsage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
