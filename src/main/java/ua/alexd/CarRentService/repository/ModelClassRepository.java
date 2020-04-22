package ua.alexd.CarRentService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.alexd.CarRentService.domain.ModelClass;

@Repository
public interface ModelClassRepository extends JpaRepository<ModelClass, Long> {
}