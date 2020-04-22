package ua.alexd.CarRentService.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "classes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModelClass {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Basic
    @Column(unique = true)
    @NotNull(message = "Назва не може бути відсутньою")
    @NotEmpty(message = "Назва не може бути пустою")
    @Size(min=1, max=150)
    private String name;

    @Basic
    @Column
    @Min(1)
    @Max(value = 40000)
    private int minPrice;

    @Basic
    @Column
    @Min(1)
    @Max(40000)
    private int maxPrice;
}