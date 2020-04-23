package ua.alexd.CarRentService.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "models")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Model {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Basic
    @Column
    @NotNull(message = "Бренд не може бути відсутнім")
    @NotEmpty(message = "Бренд не може бути пустим")
    @Size(min = 1, max = 150)
    private String brand;

    @Basic
    @Column(unique = true)
    @NotNull(message = "Модель не може бути відсутньою")
    @NotEmpty(message = "Модель не може бути пустою")
    @Size(min = 1, max = 150)
    private String model;

    @Basic
    @Column
    @Min(value = 2008, message = "Модельний ряд повинен бути пізнішим ніж 2008 рік")
    @Max(value = 2020, message = "Модельний ряд не повинен бути пізнішим ніж поточний рік")
    private int year;
}