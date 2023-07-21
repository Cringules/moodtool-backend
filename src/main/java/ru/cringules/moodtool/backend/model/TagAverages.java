package ru.cringules.moodtool.backend.model;

import org.springframework.stereotype.Service;
import ru.cringules.moodtool.backend.data.MoodEntry;
import ru.cringules.moodtool.backend.data.repositories.MoodEntryRepository;

import java.util.List;

@Service
public class TagAverages {

  // как получить??
  private final MoodEntryRepository moodEntryRepository;

  public TagAverages(MoodEntryRepository moodEntryRepository) {
    this.moodEntryRepository = moodEntryRepository;
  }

  public Mood GetTagCurrentAverage(String tag) {
    double angryAfraid = 0;
    double cheerfulDepressed = 0;
    double willfulYielding = 0;
    double pressuredLonely = 0;

    List<MoodEntry> mood = moodEntryRepository.findAll();
    List<MoodEntry> filteredMood = mood.stream()
        .filter(m -> m.getTags().contains(tag)).toList();

    if (filteredMood.isEmpty()) {
      return new Mood(0, 0, 0, 0);
    }

    int n = mood.size();
    for (MoodEntry curMood : mood) {
      angryAfraid += (double) curMood.getAngryAfraid() / n;
      cheerfulDepressed = (double) curMood.getCheerfulDepressed() / n;
      willfulYielding = (double) curMood.getWillfulYielding() / n;
      pressuredLonely = (double) curMood.getPressuredLonely() / n;
    }

    return new Mood((int) angryAfraid, (int) cheerfulDepressed, (int) willfulYielding, (int) pressuredLonely);
  }
}
