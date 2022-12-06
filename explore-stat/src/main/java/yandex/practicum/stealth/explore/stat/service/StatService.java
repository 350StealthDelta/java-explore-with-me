package yandex.practicum.stealth.explore.stat.service;

import yandex.practicum.stealth.explore.stat.dto.EndpointHit;
import yandex.practicum.stealth.explore.stat.dto.ViewStats;

import java.util.List;

public interface StatService {

    ViewStats addEventStatistic(EndpointHit inputDto);

    List<ViewStats> getEvents(String start, String end, List<String> uris, Boolean unique);
}
