package ua.alexd.CarRentService.repository;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.CarRentService.domain.Discount;

public interface DiscountRepository extends CrudRepository<Discount, Long> {
}
