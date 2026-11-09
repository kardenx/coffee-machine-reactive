package ua.com.reactive.lab_karpovych.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.lab_karpovych.entity.User;
import ua.com.reactive.lab_karpovych.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> create(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> update(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return userService.delete(id)
                .map(deleted -> deleted
                        ? ResponseEntity.noContent().<Void>build()
                        : ResponseEntity.notFound().<Void>build());
    }
}