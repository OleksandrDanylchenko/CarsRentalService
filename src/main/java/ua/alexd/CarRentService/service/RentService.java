package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Rent;
import ua.alexd.CarRentService.repository.CarRepository;
import ua.alexd.CarRentService.repository.ClientRepository;
import ua.alexd.CarRentService.repository.DiscountRepository;
import ua.alexd.CarRentService.repository.RentRepository;

import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class RentService {
    private final RentRepository rentRepository;
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final DiscountRepository discountRepository;

    public RentService(RentRepository rentRepository, ClientRepository clientRepository,
                       CarRepository carRepository, DiscountRepository discountRepository) {
        this.rentRepository = rentRepository;
        this.clientRepository = clientRepository;
        this.carRepository = carRepository;
        this.discountRepository = discountRepository;
    }

    public List<Rent> getAllRents() {
        return (List<Rent>) rentRepository.findAll();
    }

    public Optional<Rent> getRentById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return rentRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewRent(@NotNull Rent newRent) {
        setDaysDelta(newRent);
        return setTotalPrice(newRent) && saveRent(newRent);
    }

    public boolean updateRent(@NotNull Rent updRent) {
        var rentFromDB = getRentById(String.valueOf(updRent.getId()));
        if (rentFromDB.isPresent()) {
            setDaysDelta(updRent);
            if (!setTotalPrice(updRent))
                return false;
            BeanUtils.copyProperties(updRent, rentFromDB.get(), "id");
            return saveRent(rentFromDB.get());
        }
        return false;
    }

    private boolean saveRent(Rent saveRent) {
        try {
            rentRepository.save(saveRent);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteRent(String id) {
        var deletionRent = getRentById(id);
        if (deletionRent.isPresent()) {
            rentRepository.delete(deletionRent.get());
            return true;
        }
        return false;
    }

    private void setDaysDelta(@NotNull Rent processingRent) {
        var rentStartDate = processingRent.getRentStart().toLocalDate();
        var rentEndDate = processingRent.getRentEnd().toLocalDate();
        var daysDelta = (int) DAYS.between(rentStartDate, rentEndDate);
        processingRent.setDaysDelta(daysDelta);
    }

    private boolean setTotalPrice(@NotNull Rent processingRent) {
        var carId = processingRent.getCar().getId();
        var rentCar = carRepository.findById(carId);
        var clientId = processingRent.getClient().getId();
        var rentClient = clientRepository.findById(clientId);
        if (rentCar.isPresent() && rentClient.isPresent()) {
            var tariff = rentCar.get().getDayPrice();
            var daysDelta = processingRent.getDaysDelta();
            // TODO Add discount
            var totalPrice = tariff * daysDelta;
            processingRent.setTotalPrice(totalPrice);
            return true;
        }
        return false;
    }

    // TODO Create discount percent
//    private int getDiscountPercent(Client rentClient) {
//
//    }
}