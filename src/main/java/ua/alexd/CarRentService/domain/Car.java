package ua.alexd.CarRentService.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "cars")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @OneToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "spec_id")
    private Specification specification;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ModelClass modelClass;

    @Basic
    @Column
    @Min(1)
    @Max(40000)
    private int dayPrice;
}