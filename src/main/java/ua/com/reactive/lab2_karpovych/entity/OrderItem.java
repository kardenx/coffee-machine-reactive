package ua.com.reactive.lab2_karpovych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Long drinkId;
    private int quantity;
    private List<Long> ingredientIds;
}