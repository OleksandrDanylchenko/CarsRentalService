package ua.alexd.CarRentService.repository;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.CarRentService.domain.Complaint;

public interface ComplaintRepository extends CrudRepository<Complaint, Long> {
}