package ua.alexd.CarRentService.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ua.alexd.CarRentService.domain.Model;
import ua.alexd.CarRentService.service.ModelService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/model")
public class ModelResource {
    private final ModelService modelService;

    public ModelResource(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping
    public ResponseEntity<List<Model>> getModels() {
        var models = modelService.getAllModels();
        return models.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(models);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable String id) {
        var foundedModel = modelService.getModelById(id);
        return foundedModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Model> addModel(@Valid @RequestBody Model newModel, @NotNull Errors errors) {
        if (!errors.hasErrors() && modelService.addNewModel(newModel))
            return new ResponseEntity<>(newModel, HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Model> updateModel(@Valid @RequestBody Model updModel, @NotNull Errors errors) {
        if (!errors.hasErrors() && modelService.updateModel(updModel))
            return new ResponseEntity<>(updModel, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable String id) {
        return modelService.deleteModel(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}