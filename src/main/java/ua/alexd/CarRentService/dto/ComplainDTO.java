package ua.alexd.CarRentService.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ComplainDTO {
    private long id;

    private long rentId;
}