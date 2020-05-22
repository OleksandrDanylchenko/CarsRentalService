package ua.alexd.CarRentService.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private long id;

    @NotNull(message = "Бренд не може бути відсутнім")
    @NotEmpty(message = "Бренд не може бути пустим")
    @Size(min = 1, max = 150)
    private String brand;

    @NotNull(message = "Модель не може бути відсутньою")
    @NotEmpty(message = "Модель не може бути пустою")
    @Size(min = 1, max = 150)
    private String model;

    @Min(value = 2008, message = "Модельний ряд повинен бути пізнішим ніж 2008 рік")
    @Max(value = 2020, message = "Модельний ряд не повинен бути пізнішим ніж поточний рік")
    private int year;

    @NotNull(message = "Тип не може бути відсутнім")
    @NotEmpty(message = "Тип не може бути пустим")
    @Size(min = 1, max = 150)
    private String type;

    private String imageName;
}