package ua.alexd.CarRentService.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
public class Complaint {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "rent_id")
    private Rent rent;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 200)
    private String theme;

    @NotNull
    @NotEmpty
    private String text;
}