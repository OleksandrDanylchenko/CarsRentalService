package ua.alexd.CarRentService.repository;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.CarRentService.domain.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}