package ru.cringules.moodtool.backend.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cringules.moodtool.backend.data.MoodEntry;

public interface MoodEntryRepository extends JpaRepository<MoodEntry, String> {
}
