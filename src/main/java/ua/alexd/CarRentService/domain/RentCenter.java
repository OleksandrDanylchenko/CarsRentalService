package ua.alexd.CarRentService.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rent_centers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentCenter {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Basic
    @Column(unique = true)
    @NotNull(message = "Адреса не може бути відсутньою")
    @NotEmpty(message = "Адреса не може бути пустою")
    @Size(min = 1, max = 150)
    private String address;

    @Basic
    @Column(unique = true)
    @NotNull(message = "Мобільний телефон не може бути відсутнім")
    @NotEmpty(message = "Мобільний телефон не може бути пустим")
    @Pattern(regexp = "^\\+?3?8?(0\\d{9})$", message = "Телефон повинен відповідати стандартам України")
    @Size(min = 1, max = 15)
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "rentCenter")
    private List<Car> centerCars = new ArrayList<>();
}