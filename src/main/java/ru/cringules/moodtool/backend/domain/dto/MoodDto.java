package ru.cringules.moodtool.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class MoodDto {
    @Range(min = -5, max = 5)
    private Integer angryAfraid;

    @Range(min = -5, max = 5)
    private Integer cheerfulDepressed;

    @Range(min = -5, max = 5)
    private Integer willfulYielding;

    @Range(min = -5, max = 5)
    private Integer pressuredLonely;
}
