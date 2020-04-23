package ua.alexd.CarRentService.dto;

import lombok.*;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AvailabilityDTO {
    private long id;

    @PositiveOrZero
    private long carId;

    @PositiveOrZero
    private long centerId;

    private boolean isRentable;
}