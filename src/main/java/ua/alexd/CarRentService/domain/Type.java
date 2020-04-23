package ua.alexd.CarRentService.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "types")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Type {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Basic
    @Column(unique = true)
    @NotNull(message = "Назва типу не може бути відсутньою")
    @NotEmpty(message = "Назва типу не може бути пустою")
    @Size(min = 1, max = 150)
    private String name;
}