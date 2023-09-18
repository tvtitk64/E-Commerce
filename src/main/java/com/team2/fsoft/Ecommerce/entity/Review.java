package com.team2.fsoft.Ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = {
        @Index(name = "rate_name_index",columnList = "rate")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review  extends  BaseEntity{
    @Min(1)
    @Max(5)
    private int rate;

    private String content;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name= "product_id")
    private Product product;




}
