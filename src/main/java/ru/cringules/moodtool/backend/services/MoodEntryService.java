package ru.cringules.moodtool.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cringules.moodtool.backend.data.MoodEntry;
import ru.cringules.moodtool.backend.data.MoodEntryTag;
import ru.cringules.moodtool.backend.data.User;
import ru.cringules.moodtool.backend.data.repositories.MoodEntryRepository;
import ru.cringules.moodtool.backend.domain.dto.MoodEntryDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MoodEntryService {
    private final MoodEntryRepository moodEntryRepository;

    public void createMoodEntry(MoodEntryDto dto, User user) {
        MoodEntry moodEntry = new MoodEntry();
        moodEntry.setUser(user);
        moodEntry.setAngryAfraid(dto.getAngryAfraid());
        moodEntry.setCheerfulDepressed(dto.getCheerfulDepressed());
        moodEntry.setWillfulYielding(dto.getWillfulYielding());
        moodEntry.setPressuredLonely(dto.getPressuredLonely());
        moodEntry.setTimestamp(dto.getTimestamp());
        List<MoodEntryTag> tags = new ArrayList<>();
        for (String tagString : dto.getTags()) {
            MoodEntryTag tag = new MoodEntryTag();
            tag.setEntry(moodEntry);
            tag.setTag(tagString);

            tags.add(tag);
        }
        moodEntry.setTags(tags);

        moodEntryRepository.save(moodEntry);
    }

    public List<MoodEntryDto> getMoodEntries(User user) {
        return moodEntryRepository.findAllByUser(user)
                .stream().map(moodEntry -> {
                    MoodEntryDto dto = new MoodEntryDto();
                    dto.setId(moodEntry.getId());
                    dto.setAngryAfraid(moodEntry.getAngryAfraid());
                    dto.setCheerfulDepressed(moodEntry.getCheerfulDepressed());
                    dto.setWillfulYielding(moodEntry.getWillfulYielding());
                    dto.setPressuredLonely(moodEntry.getPressuredLonely());
                    dto.setTimestamp(moodEntry.getTimestamp());
                    dto.setTags(moodEntry.getTags()
                            .stream().map(MoodEntryTag::getTag)
                            .toList());

                    return dto;
                }).toList();
    }
}
