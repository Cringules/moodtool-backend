package ru.cringules.moodtool.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.cringules.moodtool.backend.data.User;
import ru.cringules.moodtool.backend.domain.dto.MoodConditions;
import ru.cringules.moodtool.backend.domain.dto.MoodDto;
import ru.cringules.moodtool.backend.domain.dto.MoodEntryDto;
import ru.cringules.moodtool.backend.services.MoodEntryService;

import java.util.List;

@RestController
@RequestMapping("api/mood")
@RequiredArgsConstructor
public class MoodController {
    private final MoodEntryService moodEntryService;

    @PostMapping("entries")
    public ResponseEntity<Void> postMoodEntry(@RequestBody @Valid MoodEntryDto moodEntryDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        moodEntryService.createMoodEntry(moodEntryDto, user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("entries")
    public ResponseEntity<List<MoodEntryDto>> getMoodEntries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(moodEntryService.getMoodEntries(user));
    }

    @GetMapping("predict")
    public ResponseEntity<MoodDto> getMoodPrediction(@Valid MoodConditions moodConditions) {
        // TODO: implement
        return ResponseEntity.ok(new MoodDto(0, 0, 0, 0));
    }
}
