package ua.alexd.CarRentService.domain;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

    @Basic
    @Column
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 200)
    private String theme;

    @Basic
    @Column
    @NotNull
    @NotEmpty
    private String text;
}