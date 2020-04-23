package ua.alexd.CarRentService.repository;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.CarRentService.domain.RentCenter;

public interface RentCenterRepository extends CrudRepository<RentCenter, Long> {
}