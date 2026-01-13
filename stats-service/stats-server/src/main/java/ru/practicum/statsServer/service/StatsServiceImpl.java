package ru.practicum.statsServer.service;

import lombok.RequiredArgsConstructor;
import ru.practicum.statsServer.mapper.EndpointHitMapper;
import ru.practicum.statsServer.model.EndpointHit;
import org.springframework.stereotype.Service;
import ru.practicum.statsServer.repository.StatsRepository;
import ru.practicum.statsDto.dto.EndpointHitDto;
import ru.practicum.statsDto.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    private final EndpointHitMapper endpointHitMapper;

    @Override
    public void saveHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = endpointHitMapper.toEndpointHit(endpointHitDto);
        statsRepository.save(endpointHit);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (unique) {
            return statsRepository.getUniqueStats(start, end, uris);
        } else {
            return statsRepository.getStats(start, end, uris);
        }
    }
}
