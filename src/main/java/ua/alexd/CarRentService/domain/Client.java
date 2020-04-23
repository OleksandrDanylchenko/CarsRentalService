package ua.alexd.CarRentService.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

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
    @Column
    private long id;

    @Basic
    @Column
    @NotNull(message = "Ім'я не може бути відсутнім")
    @NotEmpty(message = "Ім'я не може бути пустим")
    @Size(min = 1, max = 150)
    private String firstName;

    @Basic
    @Column
    @NotNull(message = "Прізвище не може бути відсутнім")
    @NotEmpty(message = "Прізвище не може бути пустим")
    @Size(min = 1, max = 150)
    private String secondName;

    @Basic
    @Column
    @NotNull(message = "Адреса не може бути відсутньою")
    @NotEmpty(message = "Адреса не може бути пустою")
    @Size(min = 1, max = 150)
    private String address;

    @Basic
    @Column
    @Min(value = 1, message = "Кількість поїздок не можу буде меншою ніж 1")
    private int ridesAmount;
}