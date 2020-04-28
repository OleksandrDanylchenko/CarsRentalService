package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Car;
import ua.alexd.CarRentService.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return (List<Car>) carRepository.findAll();
    }

    public Optional<Car> getCarById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return carRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewCar(Car newCar) {
        return saveCar(newCar);
    }

    public boolean updateCar(@NotNull Car updCar) {
        var carFromDB = getCarById(String.valueOf(updCar.getId()));
        if (carFromDB.isPresent()) {
            BeanUtils.copyProperties(updCar, carFromDB.get(), "id");
            return saveCar(carFromDB.get());
        }
        return false;
    }

    private boolean saveCar(Car saveCar) {
        try {
            carRepository.save(saveCar);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteCar(String id) {
        var deletionCar = getCarById(id);
        if (deletionCar.isPresent()) {
            carRepository.delete(deletionCar.get());
            return true;
        }
        return false;
    }

    public boolean bookCar(@NotNull Car car) {
        var rentCar = getCarById(String.valueOf(car.getId()));
        if (rentCar.isPresent() && rentCar.get().isRentable()) {
            rentCar.get().setRentable(false);
            return updateCar(rentCar.get());
        }
        return false;
    }

    public boolean releaseCar(@NotNull Car car) {
        var rentCar = getCarById(String.valueOf(car.getId()));
        if (rentCar.isPresent() && !rentCar.get().isRentable()) {
            rentCar.get().setRentable(true);
            return updateCar(rentCar.get());
        }
        return false;
    }
}