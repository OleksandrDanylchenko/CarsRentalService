package ua.alexd.CarRentService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.alexd.CarRentService.domain.ModelClass;

@Repository
public interface ModelClassRepository extends CrudRepository<ModelClass, Long> {
}