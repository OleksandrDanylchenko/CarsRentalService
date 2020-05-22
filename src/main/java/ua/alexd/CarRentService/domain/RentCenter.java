package ua.alexd.CarRentService.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    private long id;

    @Column(unique = true)
    @NotNull(message = "Адреса не може бути відсутньою")
    @NotEmpty(message = "Адреса не може бути пустою")
    @Size(min = 1, max = 150)
    private String address;

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