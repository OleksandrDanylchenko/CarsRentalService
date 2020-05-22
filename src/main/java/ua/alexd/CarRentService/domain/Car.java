package ua.alexd.CarRentService.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
    private long id;

    @OneToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "spec_id")
    private Specification specification;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ModelClass modelClass;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private RentCenter rentCenter;

    @Min(1)
    @Max(40000)
    private int dayPrice;

    private boolean rentable;
}