package ua.alexd.CarRentService.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Discount {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Basic
    @Column
    @Min(1)
    private int rentsAmount;

    @Basic
    @Column
    @Min(1)
    private int discountPercents;
}