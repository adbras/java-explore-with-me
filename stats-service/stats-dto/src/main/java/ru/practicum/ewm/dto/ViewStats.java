package ru.practicum.statsDto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
