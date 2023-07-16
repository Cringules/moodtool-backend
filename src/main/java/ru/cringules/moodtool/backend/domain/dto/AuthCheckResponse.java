package ru.cringules.moodtool.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCheckResponse {
    private String username;
}
