package ua.com.reactive.lab_karpovych.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ua.com.reactive.lab_karpovych.entity.Message;

public interface MessageRepository extends ReactiveCrudRepository<Message, Long> {
}