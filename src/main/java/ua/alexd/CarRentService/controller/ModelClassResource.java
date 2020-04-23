package ua.alexd.CarRentService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.alexd.CarRentService.domain.ModelClass;
import ua.alexd.CarRentService.service.ModelClassService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/class")
public class ModelClassResource {
    private final ModelClassService modelClassService;

    public ModelClassResource(ModelClassService modelClassService) {
        this.modelClassService = modelClassService;
    }

    @GetMapping
    public ResponseEntity<List<ModelClass>> getClasses() {
        var classes = modelClassService.getAllClasses();
        return classes.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelClass> getClassById(@PathVariable String id) {
        var foundedClass = modelClassService.getClassById(id);
        return foundedClass
                .map(type -> new ResponseEntity<>(type, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ModelClass> addClass(@Valid @RequestBody ModelClass newClass) {
        return modelClassService.addNewClass(newClass)
                ? new ResponseEntity<>(newClass, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<ModelClass> updateClass(@Valid @RequestBody ModelClass updClass) {
        return modelClassService.updateClass(updClass)
                ? new ResponseEntity<>(updClass, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable String id) {
        return modelClassService.deleteClass(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}