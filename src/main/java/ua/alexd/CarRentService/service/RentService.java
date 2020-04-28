package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Rent;
import ua.alexd.CarRentService.logic.RentCalculator;
import ua.alexd.CarRentService.logic.RentTriggers;
import ua.alexd.CarRentService.repository.RentRepository;

import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class RentService {
    private final RentRepository rentRepository;
    private final RentCalculator rentCalculator;
    private final RentTriggers rentTriggers;

    public RentService(RentRepository rentRepository, RentCalculator rentCalculator, RentTriggers rentTriggers) {
        this.rentRepository = rentRepository;
        this.rentCalculator = rentCalculator;
        this.rentTriggers = rentTriggers;
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
        return setDaysDelta(newRent) && setTotalPrice(newRent) &&
                rentTriggers.addClientRide(newRent) &&
                rentTriggers.bookCar(newRent) &&
                saveRent(newRent);
    }

    public boolean updateRent(@NotNull Rent updRent) {
        var rentFromDB = getRentById(String.valueOf(updRent.getId()));
        if (rentFromDB.isPresent() && prepareUpdateRent(rentFromDB.get(), updRent)) {
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
            rentTriggers.releaseCar(deletionRent.get());
            rentRepository.delete(deletionRent.get());
            return true;
        }
        return false;
    }

    private boolean setDaysDelta(@NotNull Rent processingRent) {
        var rentStartDate = processingRent.getRentStart().toLocalDate();
        var rentEndDate = processingRent.getRentEnd().toLocalDate();
        var daysDelta = (int) DAYS.between(rentStartDate, rentEndDate);
        if (daysDelta > 1) {
            processingRent.setDaysDelta(daysDelta);
            return true;
        }
        return false;
    }

    private boolean setTotalPrice(@NotNull Rent processingRent) {
        var totalPrice = rentCalculator.calculateTotalPrice(processingRent);
        if (totalPrice.isPresent()) {
            processingRent.setTotalPrice(totalPrice.get());
            return true;
        }
        return false;
    }

    private boolean prepareUpdateRent(Rent prevRent, Rent updRent) {
        return setDaysDelta(updRent) && setTotalPrice(updRent) &&
                rentTriggers.changeCar(prevRent, updRent) && rentTriggers.changeClients(prevRent, updRent);

    }
}