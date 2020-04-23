package ua.alexd.CarRentService.repository;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.CarRentService.domain.Availability;

public interface AvailabilityRepository extends CrudRepository<Availability, Long> {
}