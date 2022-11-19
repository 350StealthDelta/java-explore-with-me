package yandex.practicum.stealth.explore.stat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yandex.practicum.stealth.explore.stat.dao.StatRepository;
import yandex.practicum.stealth.explore.stat.dto.StatDtoMapper;
import yandex.practicum.stealth.explore.stat.dto.EndpointHit;
import yandex.practicum.stealth.explore.stat.dto.ViewStats;
import yandex.practicum.stealth.explore.stat.model.StatEntity;
import yandex.practicum.stealth.explore.stat.model.ViewEntity;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static yandex.practicum.stealth.explore.stat.dto.StatDtoMapper.*;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository repository;

    @Override
    @Transactional
    public ViewStats addEventStatistic(EndpointHit inputDto) {
        StatEntity statEntity = inputDtoToStat(inputDto);
        System.out.println(statEntity);
        StatEntity result = repository.save(statEntity);
        System.out.println(result);
        System.out.println(repository.findById(result.getId()).orElseThrow());
        return viewToOutDto(statToView(result));
    }

    @Override
    public List<ViewStats> getEvents(String start, String end, List<String> uris, Boolean unique) {
        DateTimeFormatter formatter = getFormatter();
        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);
        List<ViewEntity> viewEntities;
        if (unique) {
            viewEntities = uris.stream()
                    .map(s -> repository.findAllHitsDistinct(s, startTime, endTime))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } else {
            viewEntities = uris.stream()
                    .map(s -> repository.findAllHit(s, startTime, endTime))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        return viewEntities.stream()
                .map(StatDtoMapper::viewToOutDto)
                .collect(Collectors.toList());
    }
}
