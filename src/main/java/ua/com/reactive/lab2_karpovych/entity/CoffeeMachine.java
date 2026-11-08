package ua.com.reactive.lab2_karpovych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeMachine {
    private Long id;
    private List<Drink> drinks;
    private List<Ingredient> ingredients;
}