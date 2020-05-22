package ua.alexd.CarRentService.logic.rent;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Rent;
import ua.alexd.CarRentService.service.CarService;
import ua.alexd.CarRentService.service.ClientService;

@Service
public class RentTriggers {
    private final CarService carService;
    private final ClientService clientService;

    public RentTriggers(CarService carService, ClientService clientService) {
        this.carService = carService;
        this.clientService = clientService;
    }

    public boolean bookCar(@NotNull Rent rent) {
        var rentCar = rent.getCar();
        return carService.bookCar(rentCar);
    }

    public boolean releaseCar(@NotNull Rent rent) {
        var rentCar = rent.getCar();
        if (rentCar != null)
            return carService.releaseCar(rentCar);
        return true;
    }

    public boolean changeCar(@NotNull Rent previousRent, @NotNull Rent updateRent) {
        if (previousRent.getCar().getId() != updateRent.getCar().getId()) {
            var prevCar = previousRent.getCar();
            var newCar = updateRent.getCar();
            return !prevCar.isRentable() && newCar.isRentable() &&
                    carService.bookCar(prevCar) && carService.bookCar(newCar);
        }
        return true;
    }

    public boolean addClientRide(@NotNull Rent rent) {
        var rentClient = rent.getClient();
        return clientService.changeRidesAmount(rentClient, true);
    }

    public boolean changeClients(@NotNull Rent previousRent, @NotNull Rent updateRent) {
        if (previousRent.getClient().getId() != updateRent.getClient().getId()) {
            var prevClient = previousRent.getClient();
            var newClient = updateRent.getClient();
            return clientService.changeRidesAmount(prevClient, false) &&
                    clientService.changeRidesAmount(newClient, true);
        }
        return true;
    }
}