package ua.alexd.CarRentService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.alexd.CarRentService.domain.Type;

@Repository
public interface TypeRepository extends CrudRepository<Type, Long> {
}