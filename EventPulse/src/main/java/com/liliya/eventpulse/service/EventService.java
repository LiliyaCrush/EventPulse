package com.liliya.eventpulse.service;

import com.liliya.eventpulse.dto.DashboardAnalytics;
import com.liliya.eventpulse.dto.DashboardAnalytics.CapacityComparison;
import com.liliya.eventpulse.dto.DashboardAnalytics.NamedNumber;
import com.liliya.eventpulse.entity.Event;
import com.liliya.eventpulse.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class EventService {

    private static final int ATTENDANCE_CHART_LIMIT = 8;
    private static final int CAPACITY_CHART_LIMIT = 8;
    private static final int MONTHS_IN_TREND = 12;
    private static final DateTimeFormatter MONTH_LABEL =
            DateTimeFormatter.ofPattern("MMM yyyy", Locale.US);

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public List<Event> searchEvents(String keyword) {
        return eventRepository.searchByKeyword(keyword);
    }

    /**
     * Builds dashboard analytics from the given events. Null numeric fields are skipped
     * (never treated as zero measurements).
     */
    public DashboardAnalytics buildDashboardAnalytics(List<Event> events) {
        LocalDate today = LocalDate.now();
        DashboardAnalytics analytics = new DashboardAnalytics();

        if (events == null || events.isEmpty()) {
            analytics.setTotalEvents(0);
            analytics.setUpcomingAndActive(0);
            analytics.setTodaysEvents(0);
            return analytics;
        }

        analytics.setTotalEvents(events.size());

        long upcomingAndActive = 0;
        long todaysEvents = 0;
        double attendanceSum = 0;
        int attendanceCount = 0;
        double networkSum = 0;
        int networkCount = 0;

        for (Event event : events) {
            if (event.getStartDate() == null) {
                continue;
            }
            LocalDate effectiveEnd = effectiveEnd(event);

            if (!effectiveEnd.isBefore(today)) {
                upcomingAndActive++;
            }
            if (!event.getStartDate().isAfter(today) && !effectiveEnd.isBefore(today)) {
                todaysEvents++;
            }
            if (event.getAttendance() != null) {
                attendanceSum += event.getAttendance();
                attendanceCount++;
            }
            if (event.getNetworkUsage() != null) {
                networkSum += event.getNetworkUsage();
                networkCount++;
            }
        }

        analytics.setUpcomingAndActive(upcomingAndActive);
        analytics.setTodaysEvents(todaysEvents);
        if (attendanceCount > 0) {
            analytics.setAverageAttendance(roundOneDecimal(attendanceSum / attendanceCount));
        }
        if (networkCount > 0) {
            analytics.setTotalNetworkUsage(roundOneDecimal(networkSum));
        }

        events.stream()
                .filter(e -> e.getAttendance() != null && e.getTitle() != null)
                .sorted(Comparator.comparing(Event::getAttendance).reversed()
                        .thenComparing(e -> Objects.toString(e.getTitle(), "")))
                .limit(ATTENDANCE_CHART_LIMIT)
                .forEach(e -> analytics.getAttendanceByEvent()
                        .add(new NamedNumber(e.getTitle(), e.getAttendance())));

        YearMonth currentMonth = YearMonth.from(today);
        YearMonth startMonth = currentMonth.minusMonths(MONTHS_IN_TREND - 1L);
        for (YearMonth month = startMonth; !month.isAfter(currentMonth); month = month.plusMonths(1)) {
            YearMonth m = month;
            long count = events.stream()
                    .filter(e -> e.getStartDate() != null)
                    .filter(e -> YearMonth.from(e.getStartDate()).equals(m))
                    .count();
            analytics.getEventsPerMonth()
                    .add(new NamedNumber(m.format(MONTH_LABEL), count));
        }

        events.stream()
                .filter(e -> e.getAttendance() != null && e.getWifiEstimate() != null && e.getTitle() != null)
                .sorted(Comparator.comparing(Event::getAttendance).reversed()
                        .thenComparing(e -> Objects.toString(e.getTitle(), "")))
                .limit(CAPACITY_CHART_LIMIT)
                .forEach(e -> analytics.getAttendanceVsWifi()
                        .add(new CapacityComparison(e.getTitle(), e.getAttendance(), e.getWifiEstimate())));

        return analytics;
    }

    private static LocalDate effectiveEnd(Event event) {
        return event.getEndDate() != null ? event.getEndDate() : event.getStartDate();
    }

    private static double roundOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
