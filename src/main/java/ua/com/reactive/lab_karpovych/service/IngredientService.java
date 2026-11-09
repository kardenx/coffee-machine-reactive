package ua.com.reactive.lab_karpovych.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.lab_karpovych.entity.Ingredient;
import ua.com.reactive.lab_karpovych.repository.IngredientRepository;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Flux<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Mono<Ingredient> findById(Long id) {
        return ingredientRepository.findById(id);
    }

    public Mono<Ingredient> create(Ingredient ingredient) {
        ingredient.setId(null);
        return ingredientRepository.save(ingredient);
    }

    public Mono<Ingredient> update(Long id, Ingredient ingredient) {
        return ingredientRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(ingredient.getName());
                    existing.setPrice(ingredient.getPrice());
                    existing.setPortions(ingredient.getPortions());
                    return ingredientRepository.save(existing);
                });
    }

    public Mono<Boolean> delete(Long id) {
        return ingredientRepository.findById(id)
                .flatMap(existing -> ingredientRepository.delete(existing).thenReturn(true))
                .defaultIfEmpty(false);
    }
}