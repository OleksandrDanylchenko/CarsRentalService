package ua.alexd.CarRentService.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "complaints")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Complain {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @ManyToOne
    @JoinColumn(name = "rent_id")
    private Rent rent;
}