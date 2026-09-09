package com.liliya.eventpulse.service;

import com.liliya.eventpulse.dto.DashboardAnalytics;
import com.liliya.eventpulse.entity.Event;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceAnalyticsTest {

    private final EventService eventService = new EventService(null);

    @Test
    void buildDashboardAnalytics_emptyList_returnsZeroKpisAndNoSeries() {
        DashboardAnalytics analytics = eventService.buildDashboardAnalytics(List.of());

        assertEquals(0, analytics.getTotalEvents());
        assertEquals(0, analytics.getUpcomingAndActive());
        assertEquals(0, analytics.getTodaysEvents());
        assertNull(analytics.getAverageAttendance());
        assertNull(analytics.getTotalNetworkUsage());
        assertFalse(analytics.hasAttendanceByEvent());
        assertFalse(analytics.hasEventsPerMonth());
        assertFalse(analytics.hasAttendanceVsWifi());
    }

    @Test
    void buildDashboardAnalytics_computesKpisAndChartsFromRealFields() {
        LocalDate today = LocalDate.now();

        Event past = event("Past Summit", today.minusMonths(2), null, 100, 120, 12.5);
        Event todayEvent = event("Today Expo", today, today, 80, 70, 8.0);
        Event upcoming = event("Future Forum", today.plusDays(10), today.plusDays(12), 200, null, null);
        Event noAttendance = event("Planning Only", today.plusDays(30), null, null, 50, 3.5);

        DashboardAnalytics analytics = eventService.buildDashboardAnalytics(
                List.of(past, todayEvent, upcoming, noAttendance));

        assertEquals(4, analytics.getTotalEvents());
        assertEquals(3, analytics.getUpcomingAndActive());
        assertEquals(1, analytics.getTodaysEvents());
        assertEquals(126.7, analytics.getAverageAttendance());
        assertEquals(24.0, analytics.getTotalNetworkUsage());
        assertEquals(3, analytics.getAttendanceByEvent().size());
        assertEquals("Future Forum", analytics.getAttendanceByEvent().get(0).getLabel());
        assertEquals(12, analytics.getEventsPerMonth().size());
        assertEquals(2, analytics.getAttendanceVsWifi().size());
        assertEquals("Past Summit", analytics.getAttendanceVsWifi().get(0).getLabel());
        assertEquals(100, analytics.getAttendanceVsWifi().get(0).getAttendance());
        assertEquals(120, analytics.getAttendanceVsWifi().get(0).getWifiEstimate());
        assertEquals("Today Expo", analytics.getAttendanceVsWifi().get(1).getLabel());
        assertEquals(80, analytics.getAttendanceVsWifi().get(1).getAttendance());
        assertEquals(70, analytics.getAttendanceVsWifi().get(1).getWifiEstimate());
    }

    private static Event event(String title, LocalDate start, LocalDate end,
                               Integer attendance, Integer wifi, Double network) {
        Event e = new Event();
        e.setTitle(title);
        e.setStartDate(start);
        e.setEndDate(end);
        e.setAttendance(attendance);
        e.setWifiEstimate(wifi);
        e.setNetworkUsage(network);
        return e;
    }
}
