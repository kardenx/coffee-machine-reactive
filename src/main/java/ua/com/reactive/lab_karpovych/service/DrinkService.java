package ua.com.reactive.lab_karpovych.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.lab_karpovych.entity.Drink;
import ua.com.reactive.lab_karpovych.repository.DrinkRepository;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public Flux<Drink> findAll() {
        return drinkRepository.findAll();
    }

    public Mono<Drink> findById(Long id) {
        return drinkRepository.findById(id);
    }

    public Mono<Drink> create(Drink drink) {
        drink.setId(null);
        return drinkRepository.save(drink);
    }

    public Mono<Drink> update(Long id, Drink drink) {
        return drinkRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(drink.getName());
                    existing.setPrice(drink.getPrice());
                    existing.setPortions(drink.getPortions());
                    return drinkRepository.save(existing);
                });
    }

    public Mono<Boolean> delete(Long id) {
        return drinkRepository.findById(id)
                .flatMap(existing -> drinkRepository.delete(existing).thenReturn(true))
                .defaultIfEmpty(false);
    }
}