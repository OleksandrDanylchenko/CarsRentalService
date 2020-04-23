package ua.alexd.CarRentService.repository;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.CarRentService.domain.Car;

public interface CarRepository extends CrudRepository<Car, Long> {
}