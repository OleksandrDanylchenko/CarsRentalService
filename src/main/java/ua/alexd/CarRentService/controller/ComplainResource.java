package ua.alexd.CarRentService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.alexd.CarRentService.domain.Complain;
import ua.alexd.CarRentService.service.ComplainService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/complain")
public class ComplainResource {
    private final ComplainService complainService;

    public ComplainResource(ComplainService complainService) {
        this.complainService = complainService;
    }

    @GetMapping
    public ResponseEntity<List<Complain>> getComplains() {
        var complains = complainService.getAllComplains();
        return complains.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(complains, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Complain> getComplainById(@PathVariable String id) {
        var foundedComplain = complainService.getComplainById(id);
        return foundedComplain
                .map(complain -> new ResponseEntity<>(complain, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Complain> addComplain(@Valid @RequestBody Complain newComplain) {
        return complainService.addNewComplain(newComplain)
                ? new ResponseEntity<>(newComplain, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<Complain> updateComplain(@Valid @RequestBody Complain updComplain) {
        return complainService.updateComplain(updComplain)
                ? new ResponseEntity<>(updComplain, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplain(@PathVariable String id) {
        return complainService.deleteComplain(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}