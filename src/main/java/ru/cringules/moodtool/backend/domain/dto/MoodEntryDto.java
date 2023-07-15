package ru.cringules.moodtool.backend.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class MoodEntryDto {
    private String id;

    @NotNull
    @Range(min = -5, max = 5)
    private Integer angryAfraid;

    @NotNull
    @Range(min = -5, max = 5)
    private Integer cheerfulDepressed;

    @NotNull
    @Range(min = -5, max = 5)
    private Integer willfulYielding;

    @NotNull
    @Range(min = -5, max = 5)
    private Integer pressuredLonely;

    @NotNull
    private Instant timestamp;

    @NotNull
    private List<String> tags;
}
