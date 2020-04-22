package ua.alexd.CarRentService.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.alexd.CarRentService.domain.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
}