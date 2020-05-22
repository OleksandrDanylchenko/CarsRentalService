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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clients")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client {
    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "Ім'я не може бути відсутнім")
    @NotEmpty(message = "Ім'я не може бути пустим")
    @Size(min = 1, max = 150)
    private String firstName;

    @NotNull(message = "Прізвище не може бути відсутнім")
    @NotEmpty(message = "Прізвище не може бути пустим")
    @Size(min = 1, max = 150)
    private String secondName;

    @NotNull(message = "Адреса не може бути відсутньою")
    @NotEmpty(message = "Адреса не може бути пустою")
    @Size(min = 1, max = 150)
    private String address;

    @Min(value = 1, message = "Кількість поїздок не можу буде меншою ніж 1")
    private int ridesAmount;
}