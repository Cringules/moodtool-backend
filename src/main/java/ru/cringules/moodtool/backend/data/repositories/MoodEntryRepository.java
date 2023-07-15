package ru.cringules.moodtool.backend.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cringules.moodtool.backend.data.MoodEntry;
import ru.cringules.moodtool.backend.data.User;

import java.util.List;

public interface MoodEntryRepository extends JpaRepository<MoodEntry, String> {
    List<MoodEntry> findAllByUser(User user);
}
