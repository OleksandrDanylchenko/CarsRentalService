package ua.alexd.CarRentService.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "availabilities")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Availability {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private RentCenter rentCenter;

    @Basic
    @Column
    private boolean isRentable;
}