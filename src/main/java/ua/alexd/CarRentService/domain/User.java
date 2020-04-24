package ua.alexd.CarRentService.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Basic
    @Column
    @NotNull(message = "Логін може бути відсутнім")
    @NotEmpty(message = "Логін не може бути пустим")
    @Size(min = 1, max = 150)
    private String username;

    @Basic
    @Column
    @NotNull(message = "Пароль може бути відсутнім")
    @NotEmpty(message = "Пароль не може бути пустим")
    @Size(min = 1)
    private String password;

    @Basic
    @Column
    private boolean active;

//TODO Add authorities

    @Basic
    @Column
    @NotNull(message = "Email може бути відсутнім")
    @NotEmpty(message = "Email не може бути пустим")
    @Email
    private String email;

    @Basic
    @Column
    private String activationCode;

    @Basic
    @Column
    private boolean isClient;
}