package com.liliya.eventpulse.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * View-model for dashboard analytics. Not a JPA entity.
 */
public class DashboardAnalytics {

    private long totalEvents;
    private long upcomingAndActive;
    private long todaysEvents;
    private Double averageAttendance;
    private Double totalNetworkUsage;
    private final List<NamedNumber> attendanceByEvent = new ArrayList<>();
    private final List<NamedNumber> eventsPerMonth = new ArrayList<>();
    private final List<CapacityComparison> attendanceVsWifi = new ArrayList<>();

    public long getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(long totalEvents) {
        this.totalEvents = totalEvents;
    }

    public long getUpcomingAndActive() {
        return upcomingAndActive;
    }

    public void setUpcomingAndActive(long upcomingAndActive) {
        this.upcomingAndActive = upcomingAndActive;
    }

    public long getTodaysEvents() {
        return todaysEvents;
    }

    public void setTodaysEvents(long todaysEvents) {
        this.todaysEvents = todaysEvents;
    }

    public Double getAverageAttendance() {
        return averageAttendance;
    }

    public void setAverageAttendance(Double averageAttendance) {
        this.averageAttendance = averageAttendance;
    }

    public Double getTotalNetworkUsage() {
        return totalNetworkUsage;
    }

    public void setTotalNetworkUsage(Double totalNetworkUsage) {
        this.totalNetworkUsage = totalNetworkUsage;
    }

    public List<NamedNumber> getAttendanceByEvent() {
        return attendanceByEvent;
    }

    public List<NamedNumber> getEventsPerMonth() {
        return eventsPerMonth;
    }

    public List<CapacityComparison> getAttendanceVsWifi() {
        return attendanceVsWifi;
    }

    public boolean isAverageAttendancePresent() {
        return averageAttendance != null;
    }

    public boolean isTotalNetworkUsagePresent() {
        return totalNetworkUsage != null;
    }

    public boolean hasAttendanceByEvent() {
        return !attendanceByEvent.isEmpty();
    }

    public boolean hasEventsPerMonth() {
        return !eventsPerMonth.isEmpty();
    }

    public boolean hasAttendanceVsWifi() {
        return !attendanceVsWifi.isEmpty();
    }

    public static class NamedNumber {
        private String label;
        private double value;

        public NamedNumber() {
        }

        public NamedNumber(String label, double value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

    public static class CapacityComparison {
        private String label;
        private int attendance;
        private int wifiEstimate;

        public CapacityComparison() {
        }

        public CapacityComparison(String label, int attendance, int wifiEstimate) {
            this.label = label;
            this.attendance = attendance;
            this.wifiEstimate = wifiEstimate;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getAttendance() {
            return attendance;
        }

        public void setAttendance(int attendance) {
            this.attendance = attendance;
        }

        public int getWifiEstimate() {
            return wifiEstimate;
        }

        public void setWifiEstimate(int wifiEstimate) {
            this.wifiEstimate = wifiEstimate;
        }
    }
}
