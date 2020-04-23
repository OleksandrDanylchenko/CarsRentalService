package ua.alexd.CarRentService.repository;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.CarRentService.domain.Complain;

public interface ComplainRepository extends CrudRepository<Complain, Long> {
}