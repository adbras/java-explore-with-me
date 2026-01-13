package ru.practicum.statsServer.service;

import ru.practicum.statsDto.dto.EndpointHitDto;
import ru.practicum.statsDto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;


public interface StatsService {
    void saveHit(EndpointHitDto hitDto);

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
