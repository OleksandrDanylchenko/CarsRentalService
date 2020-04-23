package ua.alexd.CarRentService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.alexd.CarRentService.domain.Car;
import ua.alexd.CarRentService.service.CarService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/car")
public class CarResource {
    private final CarService carService;

    public CarResource(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        var cars = carService.getAllCars();
        return cars.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable String id) {
        var foundedModel = carService.getCarById(id);
        return foundedModel
                .map(model -> new ResponseEntity<>(model, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@Valid @RequestBody Car newCar) {
        return carService.addNewCar(newCar)
                ? new ResponseEntity<>(newCar, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<Car> updateModel(@Valid @RequestBody Car updCar) {
        return carService.updateCar(updCar)
                ? new ResponseEntity<>(updCar, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable String id) {
        return carService.deleteCar(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}