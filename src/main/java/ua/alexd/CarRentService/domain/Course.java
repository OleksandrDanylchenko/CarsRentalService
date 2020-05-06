package ua.alexd.CarRentService.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course {
    private Long id;
    private String username;
    private String description;
}
