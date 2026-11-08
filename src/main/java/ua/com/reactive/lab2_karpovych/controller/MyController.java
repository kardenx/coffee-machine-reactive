package ua.com.reactive.lab2_karpovych.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import reactor.core.publisher.Flux;
import ua.com.reactive.lab2_karpovych.entity.Client;
import ua.com.reactive.lab2_karpovych.entity.Drink;

@RestController
public class MyController {

    @GetMapping("/clients")
    public Flux<Client> getClients() {
        Flux<Client> clients = Flux.just(
                        new Client(1L, "Vasya", "Pypkin", 18),
                        new Client(2L, "Iva", "Pypkina", 19),
                        new Client(3L, "Inna", "Pypkina", 20)
                )
                .skip(0)
                .take(2);

        return clients;
    }

    @GetMapping("/drinks")
    public Flux<Drink> getDrinks() {
        return Flux.just(
                new Drink(1L, "Espresso", new BigDecimal("40"), 10),
                new Drink(2L, "Latte", new BigDecimal("60"), 8),
                new Drink(3L, "Cappuccino", new BigDecimal("55"), 5)
        );
    }

}