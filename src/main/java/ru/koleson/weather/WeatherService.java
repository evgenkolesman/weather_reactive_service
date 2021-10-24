package ru.koleson.weather;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Collections.max;

@Service
public class WeatherService {
    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();

    {
        weathers.put(1, new Weather(1, "Msc", 20));
        weathers.put(2, new Weather(2, "SPb", 15));
        weathers.put(3, new Weather(3, "Bryansk", 15));
        weathers.put(4, new Weather(4, "Smolensk", 15));
        weathers.put(5, new Weather(5, "Kiev", 15));
        weathers.put(6, new Weather(6, "Minsk", 15));
    }

    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    public String findHottest() {
       Integer i = Math.toIntExact(weathers.values().stream()
                .mapToInt(x -> x.getTemperature()).asLongStream().max().getAsLong());
        return weathers.values().stream().filter(x -> x.getTemperature() == i).findFirst().get().getCity();
    }


    public List<String> findHotterThan (Integer temperature) {
        return new LinkedList<>(weathers.values().stream().filter(x -> x.getTemperature() > temperature)
                .map(x -> x.getCity()).collect(Collectors.toList()));
    }
}
