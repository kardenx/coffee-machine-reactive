package ua.com.reactive.lab_karpovych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("ingredients")
public class Ingredient {
    @Id
    private Long id;
    private String name;
    private BigDecimal price;
    private int portions;
}