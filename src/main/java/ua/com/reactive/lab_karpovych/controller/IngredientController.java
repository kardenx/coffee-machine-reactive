package ua.com.reactive.lab_karpovych.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.lab_karpovych.entity.Ingredient;
import ua.com.reactive.lab_karpovych.service.IngredientService;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public Flux<Ingredient> getAll() {
        return ingredientService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Ingredient>> getById(@PathVariable Long id) {
        return ingredientService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Ingredient> create(@RequestBody Ingredient ingredient) {
        return ingredientService.create(ingredient);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Ingredient>> update(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        return ingredientService.update(id, ingredient)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return ingredientService.delete(id)
                .map(deleted -> deleted
                        ? ResponseEntity.noContent().<Void>build()
                        : ResponseEntity.notFound().<Void>build());
    }
}