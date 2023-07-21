package ru.cringules.moodtool.backend.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cringules.moodtool.backend.data.repositories.MoodEntryRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictModel {
    private final IntervalAverages intervalAverages;
    private final TagAverages tagAverages;

    public Mood PredictMood(Instant curDate, List<String> tags) {
        int timeAngryAfraid = 0;
        int timeCheerfulDepressed = 0;
        int timeWillfulYielding = 0;
        int timePressuredLonely = 0;

        Timestamp date = Timestamp.from(curDate);

        List<Mood> timeAverages = new ArrayList<Mood>(Arrays.asList(
                intervalAverages.GetHourAverage(date.getHours()),
                intervalAverages.GetWeekdayAverage(date.getDay()),
                intervalAverages.GetMonthAverage(date.getMonth()),
                intervalAverages.GetWeekdayAverage(date.getDay())));

        for (Mood mood : timeAverages) {
            timeAngryAfraid += mood.angryAfraid();
            timeCheerfulDepressed += mood.cheerfulDepressed();
            timeWillfulYielding += mood.willfulYielding();
            timePressuredLonely += mood.pressuredLonely();
        }

        timeAngryAfraid /= 4;
        timeCheerfulDepressed /= 4;
        timeWillfulYielding /= 4;
        timePressuredLonely /= 4;

        int tagAngryAfraid = 0;
        int tagCheerfulDepressed = 0;
        int tagWillfulYielding = 0;
        int tagPressuredLonely = 0;

        for (String tag : tags) {
            Mood mood = tagAverages.GetTagCurrentAverage(tag);
            tagAngryAfraid += mood.angryAfraid();
            tagCheerfulDepressed += mood.cheerfulDepressed();
            tagWillfulYielding += mood.willfulYielding();
            tagPressuredLonely += mood.pressuredLonely();
        }

        int n = tags.size();

        tagAngryAfraid /= n;
        tagCheerfulDepressed /= n;
        tagWillfulYielding /= n;
        tagPressuredLonely /= n;

        return new Mood((timeAngryAfraid + tagAngryAfraid) / 2,
                (timeCheerfulDepressed + tagCheerfulDepressed) / 2,
                (timeWillfulYielding + tagWillfulYielding) / 2,
                (timePressuredLonely + tagPressuredLonely) / 2);
    }
}
