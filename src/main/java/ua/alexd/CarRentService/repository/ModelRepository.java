package ua.alexd.CarRentService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.alexd.CarRentService.domain.Model;

@Repository
public interface ModelRepository extends CrudRepository<Model, Long> {
}