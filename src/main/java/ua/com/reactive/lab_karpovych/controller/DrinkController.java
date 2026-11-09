package ua.com.reactive.lab_karpovych.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.lab_karpovych.entity.Drink;
import ua.com.reactive.lab_karpovych.service.DrinkService;

@RestController
@RequestMapping("/api/drinks")
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;

    @GetMapping
    public Flux<Drink> getAll() {
        return drinkService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Drink>> getById(@PathVariable Long id) {
        return drinkService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Drink> create(@RequestBody Drink drink) {
        return drinkService.create(drink);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Drink>> update(@PathVariable Long id, @RequestBody Drink drink) {
        return drinkService.update(id, drink)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return drinkService.delete(id)
                .map(deleted -> deleted
                        ? ResponseEntity.noContent().<Void>build()
                        : ResponseEntity.notFound().<Void>build());
    }
}