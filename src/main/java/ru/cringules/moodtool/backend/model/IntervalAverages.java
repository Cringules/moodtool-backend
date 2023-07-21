package ru.cringules.moodtool.backend.model;

import org.springframework.stereotype.Service;
import ru.cringules.moodtool.backend.data.MoodEntry;
import ru.cringules.moodtool.backend.data.repositories.MoodEntryRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class IntervalAverages {

    // как получить??
    private final MoodEntryRepository moodEntryRepository;

    public IntervalAverages(MoodEntryRepository moodEntryRepository) {
        this.moodEntryRepository = moodEntryRepository;
    }

    private Mood findAverage(List<MoodEntry> mood) {
        double angryAfraid = 0;
        double cheerfulDepressed = 0;
        double willfulYielding = 0;
        double pressuredLonely = 0;

        int n = mood.size();
        for (MoodEntry curMood : mood) {
            angryAfraid += (double) curMood.getAngryAfraid() / n;
            cheerfulDepressed = (double) curMood.getCheerfulDepressed() / n;
            willfulYielding = (double) curMood.getWillfulYielding() / n;
            pressuredLonely = (double) curMood.getPressuredLonely() / n;
        }

        return new Mood((int) angryAfraid, (int) cheerfulDepressed, (int) willfulYielding, (int) pressuredLonely);
    }

    public Mood GetHourAverage(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Incorrect hour!");
        }

        List<MoodEntry> mood = moodEntryRepository.findAll();
        List<MoodEntry> filteredMood = mood.stream()
                .filter(m -> Timestamp.from(m.getTimestamp()).getHours() == hour).toList();

        return findAverage(filteredMood);
    }

    public Mood GetDayPeriodAverage(int dayPeriod) {
        if (dayPeriod < 0 || dayPeriod > 3) {
            throw new IllegalArgumentException("Incorrect day period!");
        }

        List<MoodEntry> mood = moodEntryRepository.findAll();
        List<MoodEntry> filteredMood = new ArrayList<>();

        switch (dayPeriod) {
            case 0 ->
                    filteredMood = mood.stream().filter(m -> Timestamp.from(m.getTimestamp()).getHours() > 4 && Timestamp.from(m.getTimestamp()).getHours() < 12).toList();
            case 1 ->
                    filteredMood = mood.stream().filter(m -> Timestamp.from(m.getTimestamp()).getHours() > 11 && Timestamp.from(m.getTimestamp()).getHours() < 18).toList();
            case 2 ->
                    filteredMood = mood.stream().filter(m -> Timestamp.from(m.getTimestamp()).getHours() > 17 && Timestamp.from(m.getTimestamp()).getHours() < 23).toList();
            case 3 ->
                    filteredMood = mood.stream().filter(m -> Timestamp.from(m.getTimestamp()).getHours() == 23 || Timestamp.from(m.getTimestamp()).getHours() < 5).toList();
        }

        return findAverage(filteredMood);
    }

    public Mood GetWeekdayAverage(int weekday) {
        if (weekday < 0 || weekday > 6) {
            throw new IllegalArgumentException("Incorrect weekday!");
        }

        List<MoodEntry> mood = moodEntryRepository.findAll();
        List<MoodEntry> filteredMood = mood.stream().filter(m -> Timestamp.from(m.getTimestamp()).getDay() == weekday).toList();

        return findAverage(filteredMood);
    }

    public Mood GetMonthAverage(int month) {
        if (month < 0 || month > 11) {
            throw new IllegalArgumentException("Incorrect month!");
        }

        List<MoodEntry> mood = moodEntryRepository.findAll();
        List<MoodEntry> filteredMood = mood.stream().filter(m -> Timestamp.from(m.getTimestamp()).getMonth() == month).toList();

        return findAverage(filteredMood);
    }

    public Mood GetYearPeriodAverage(int yearPeriod) {
        if (yearPeriod < 0 || yearPeriod > 3) {
            throw new IllegalArgumentException("Incorrect year period!");
        }

        List<MoodEntry> mood = moodEntryRepository.findAll();
        List<MoodEntry> filteredMood = new ArrayList<>();

        switch (yearPeriod) {
            case 0 ->
                    filteredMood = mood.stream().filter(m -> Timestamp.from(m.getTimestamp()).getMonth() > 1 && Timestamp.from(m.getTimestamp()).getMonth() < 5).toList();
            case 1 ->
                    filteredMood = mood.stream().filter(m -> Timestamp.from(m.getTimestamp()).getMonth() > 4 && Timestamp.from(m.getTimestamp()).getMonth() < 8).toList();
            case 2 ->
                    filteredMood = mood.stream().filter(m -> Timestamp.from(m.getTimestamp()).getMonth() > 7 && Timestamp.from(m.getTimestamp()).getMonth() < 11).toList();
            case 3 ->
                    filteredMood = mood.stream().filter(m -> Timestamp.from(m.getTimestamp()).getMonth() == 11 || Timestamp.from(m.getTimestamp()).getMonth() < 2).toList();
        }

        return findAverage(filteredMood);
    }
}
