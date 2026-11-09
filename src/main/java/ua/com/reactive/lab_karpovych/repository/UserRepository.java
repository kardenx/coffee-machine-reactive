package ua.com.reactive.lab_karpovych.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.com.reactive.lab_karpovych.entity.User;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}