package ua.alexd.CarRentService.repository;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.CarRentService.domain.Specification;

public interface SpecificationRepository extends CrudRepository<Specification, Long> {
}