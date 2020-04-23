package ua.alexd.CarRentService.repository;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.CarRentService.domain.Rent;

public interface RentRepository extends CrudRepository<Rent, Long> {
}