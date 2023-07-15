package ru.cringules.moodtool.backend.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class MoodEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private User user;

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

    @OneToMany(cascade = CascadeType.ALL)
    private List<MoodEntryTag> tags;
}
