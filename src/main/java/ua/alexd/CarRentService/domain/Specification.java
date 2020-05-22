package ua.alexd.CarRentService.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

    @NotNull(message = "Потужність двигуна не може бути відсутньою")
    @NotEmpty(message = "Потужність двигуна не може бути пустою")
    @DecimalMin(value = "0.8", message = "Об'єм двигуна не може бути меншим ніж 0.8л.")
    @DecimalMax(value = "6.0", message = "Об'єм двигуна не може бути більшим ніж 6л.")
    @Pattern(regexp = "^[0-6][.][0-9]$", message = "Об'єм потрібно подати у вигляді 'X.X'")
    private String engineCapacity;

    @Min(value = 50, message = "Потужність двигуна не може бути меншою ніж 50к.с.")
    @Max(value = 2000, message = "Потужність двигуна не може бути більшою ніж 2000к.с.")
    private int horsepowers;

    @NotNull(message = "Тип трансмісії не може бути відсутнім")
    @NotEmpty(message = "Тип трансмісії не може бути пустим")
    @Size(min = 1, max = 150)
    private String transmissionType;

    @NotNull(message = "Тип палива не може бути відсутнім")
    @NotEmpty(message = "Тип палива не може бути пустим")
    @Size(min = 1, max = 150)
    private String fuelType;

    @NotNull(message = "Споживання пального не може бути відсутнім")
    @NotEmpty(message = "Споживання пального не може бути пустим")
    @DecimalMin(value = "3.0", message = "Споживання пального не може бути меншим ніж 3л./100км.")
    @DecimalMax(value = "50.0", message = "Споживання пального не може бути більшим ніж 50л./100км.")
    @Pattern(regexp = "^[0-9]{1,2}[.][0-9]$", message = "Споживання пального потрібно подати у вигляді 'xX.X'")
    private String fuelConsumption;
}