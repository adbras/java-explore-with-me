package ru.practicum.ewm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.StatsRequestDto;
import ru.practicum.ewm.dto.ViewStatsDto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class StatsClient {
    private final RestClient restClient;

    public StatsClient(@Value("${stats-server.url}") String statsServerUrl) {
        this.restClient = RestClient.builder().baseUrl(statsServerUrl).build();
    }

    public void sendHit(EndpointHitDto endPointHitDto) {
        restClient.post()
                .uri("/hit")
                .body(endPointHitDto)
                .retrieve()
                .toBodilessEntity();
    }

    public List<ViewStatsDto> getStats(List<StatsRequestDto> statsRequestDtos) {
        List<ViewStatsDto> allStats = new ArrayList<>();
        for (StatsRequestDto statsRequestDto : statsRequestDtos) {
            try {
                List<ViewStatsDto> stats = restClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/stats")
                                .queryParam("start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                        .format(statsRequestDto.getStart()))
                                .queryParam("end", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                        .format(statsRequestDto.getEnd()))
                                .queryParam("uris", statsRequestDto.getUris())
                                .queryParam("unique", statsRequestDto.isUnique())
                                .build())
                        .retrieve()
                        .body(new ParameterizedTypeReference<>() {
                        });
                assert stats != null;
                allStats.addAll(stats);
            } catch (HttpStatusCodeException e) {
                log.error("Ошибка получения статистики: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            } catch (Exception e) {
                log.error("Ошибка при запросе статистики: {}", e.getMessage(), e);
            }
        }
        return allStats;
    }
}
