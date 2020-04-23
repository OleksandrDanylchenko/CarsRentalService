package ua.alexd.CarRentService.domain;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
@Table(name = "rents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rent {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Basic
    @Column
    @NotNull
    @PastOrPresent
    private LocalDateTime rentStart;

    @Basic
    @Column
    @NotNull
    @Future
    private LocalDateTime rentEnd;

    @Basic
    @Column
    @Min(value = 1, message = "Машину не можна орендувати менше ніж на 1 день")
    private int daysDelta;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Basic
    @Column
    @Min(value = 1)
    private int totalPrice;
}