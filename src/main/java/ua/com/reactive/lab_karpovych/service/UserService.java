package ua.com.reactive.lab_karpovych.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.lab_karpovych.entity.User;
import ua.com.reactive.lab_karpovych.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> create(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    public Mono<User> update(Long id, User user) {
        return userRepository.findById(id)
                .flatMap(existing -> {
                    existing.setFirstName(user.getFirstName());
                    existing.setLastName(user.getLastName());
                    existing.setBalance(user.getBalance());
                    return userRepository.save(existing);
                });
    }

    public Mono<Boolean> delete(Long id) {
        return userRepository.findById(id)
                .flatMap(existing -> userRepository.delete(existing).thenReturn(true))
                .defaultIfEmpty(false);
    }
}