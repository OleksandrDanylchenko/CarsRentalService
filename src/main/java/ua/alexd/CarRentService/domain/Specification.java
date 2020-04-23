package ua.alexd.CarRentService.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "specifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Specification {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Basic
    @Column
    @NotNull(message = "Потужність двигуна не може бути відсутньою")
    @NotEmpty(message = "Потужність двигуна не може бути пустою")
    @DecimalMin(value = "0.8", message = "Об'єм двигуна не може бути меншим ніж 0.8л.")
    @DecimalMax(value = "6.0", message = "Об'єм двигуна не може бути більшим ніж 6л.")
    @Pattern(regexp = "^[0-6][.][0-9]$", message = "Об'єм потрібно подати у вигляді 'X.X'")
    private String engineCapacity;

    @Basic
    @Column
    @Min(value = 50, message = "Потужність двигуна не може бути меншою ніж 56к.с.")
    @Max(value = 2000, message = "Потужність двигуна не може бути більшою ніж 2000к.с.")
    private int horsepowers;

    @Basic
    @Column
    @NotNull(message = "Тип трансмісії не може бути відсутнім")
    @NotEmpty(message = "Тип трансмісії не може бути пустим")
    @Size(min = 1, max = 150)
    private String transmissionType;

    @Basic
    @Column
    @NotNull(message = "Тип палива не може бути відсутнім")
    @NotEmpty(message = "Тип палива не може бути пустим")
    @Size(min = 1, max = 150)
    private String fuelType;

    @Basic
    @Column
    @NotNull(message = "Споживання пального не може бути відсутнім")
    @NotEmpty(message = "Споживання пального не може бути пустим")
    @DecimalMin(value = "3.0", message = "Споживання пального не може бути меншим ніж 3л./100км.")
    @DecimalMax(value = "50.0", message = "Споживання пального не може бути більшим ніж 50л./100км.")
    @Pattern(regexp = "^[0-9]{1,2}[.][0-9]$", message = "Споживання пального потрібно подати у вигляді 'xX.X'")
    private String fuelConsumption;
}