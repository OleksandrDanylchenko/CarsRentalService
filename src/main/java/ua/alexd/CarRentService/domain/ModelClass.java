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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "classes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModelClass {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    @NotNull(message = "Назва не може бути відсутньою")
    @NotEmpty(message = "Назва не може бути пустою")
    @Size(min = 1, max = 150)
    private String name;

    @Column(unique = true)
    @Min(1)
    @Max(200000)
    private int startPrice;
}