package ua.alexd.CarRentService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.alexd.CarRentService.domain.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
}