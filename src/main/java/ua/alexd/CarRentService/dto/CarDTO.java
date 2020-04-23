package ua.alexd.CarRentService.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarDTO {
    private long id;

    @PositiveOrZero
    private long modelId;

    @PositiveOrZero
    private long typeId;

    @PositiveOrZero
    private long specId;

    @PositiveOrZero
    private long classId;

    @Min(value = 1)
    @Max(value = 40000)
    private int dayPrice;
}