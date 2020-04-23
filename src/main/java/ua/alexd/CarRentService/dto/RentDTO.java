package ua.alexd.CarRentService.dto;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import ua.alexd.CarRentService.domain.Car;
import ua.alexd.CarRentService.domain.Client;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentDTO {
    private long id;

    @NotNull
    @PastOrPresent
    private LocalDateTime rentStart;

    @NotNull
    @Future
    private LocalDateTime rentEnd;

    @Min(1)
    private int daysDelta;

    @PositiveOrZero
    private long clientId;

    @PositiveOrZero
    private long carId;

    @Min(1)
    private int totalPrice;
}