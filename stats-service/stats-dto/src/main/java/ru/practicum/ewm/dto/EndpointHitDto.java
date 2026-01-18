package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EndpointHitDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
