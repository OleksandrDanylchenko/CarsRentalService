package ua.alexd.CarRentService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.alexd.CarRentService.domain.RentCenter;
import ua.alexd.CarRentService.service.RentCenterService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/rent_center")
public class RentCenterResource {
    private final RentCenterService rentCenterService;

    public RentCenterResource(RentCenterService rentCenterService) {
        this.rentCenterService = rentCenterService;
    }

    @GetMapping
    public ResponseEntity<List<RentCenter>> getRentCenters() {
        var models = rentCenterService.getAllRentCenters();
        return models.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(models, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentCenter> getRentCenterById(@PathVariable String id) {
        var foundedModel = rentCenterService.getRentCenterById(id);
        return foundedModel
                .map(model -> new ResponseEntity<>(model, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<RentCenter> addRentCenter(@Valid @RequestBody RentCenter newRentCenter) {
        return rentCenterService.addNewRentCenter(newRentCenter)
                ? new ResponseEntity<>(newRentCenter, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<RentCenter> updateRentCenter(@Valid @RequestBody RentCenter updRentCenter) {
        return rentCenterService.updateRentCenter(updRentCenter)
                ? new ResponseEntity<>(updRentCenter, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentCenter(@PathVariable String id) {
        return rentCenterService.deleteRentCenter(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}