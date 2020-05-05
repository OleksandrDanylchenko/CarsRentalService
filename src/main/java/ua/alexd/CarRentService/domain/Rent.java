package ua.alexd.CarRentService.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate rentStart;

    @Basic
    @Column
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate rentEnd;

    @Basic
    @Column
    private int daysDelta;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Basic
    @Column
    private int totalPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "rent")
    private List<Complain> complains = new ArrayList<>();
}