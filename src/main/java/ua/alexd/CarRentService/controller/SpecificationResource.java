package ua.alexd.CarRentService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.alexd.CarRentService.domain.Specification;
import ua.alexd.CarRentService.service.SpecificationService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/specification")
public class SpecificationResource {
    private final SpecificationService specificationService;

    public SpecificationResource(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }

    @GetMapping
    public ResponseEntity<List<Specification>> getSpecifications() {
        var models = specificationService.getAllSpecifications();
        return models.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(models, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specification> getSpecificationById(@PathVariable String id) {
        var foundedModel = specificationService.getSpecificationById(id);
        return foundedModel
                .map(specification -> new ResponseEntity<>(specification, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Specification> addSpecification(@Valid @RequestBody Specification newSpecification) {
        return specificationService.addNewSpecification(newSpecification)
                ? new ResponseEntity<>(newSpecification, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<Specification> updateSpecification(@Valid @RequestBody Specification updSpecification) {
        return specificationService.updateSpecification(updSpecification)
                ? new ResponseEntity<>(updSpecification, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable String id) {
        return specificationService.deleteSpecification(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}