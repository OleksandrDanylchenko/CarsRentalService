package ua.alexd.CarRentService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.CarRentService.domain.Model;
import ua.alexd.CarRentService.service.ModelService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/models")
public class ModelResource {
    private final ModelService modelService;

    public ModelResource(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping
    public ResponseEntity<List<Model>> getModels() {
        var models = modelService.getAllModels();
        return models.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(models, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable String id) {
        var foundedModel = modelService.getModelById(id);
        return foundedModel
                .map(model -> new ResponseEntity<>(model, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Model> addModel(@RequestParam(value = "newModel") String newModelStr,
                                          @RequestParam(value = "newModelImage") MultipartFile newModelImage) {
        var objectMapper = new ObjectMapper();
        Model newModel = null;
        try {
            newModel = objectMapper.readValue(newModelStr, Model.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return modelService.addNewModel(newModel, newModelImage)
                ? new ResponseEntity<>(newModel, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<Model> updateModel(@Valid @RequestBody Model updModel,
                                             @RequestBody MultipartFile updModelImage) {
        return modelService.updateModel(updModel, updModelImage)
                ? new ResponseEntity<>(updModel, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable String id) {
        return modelService.deleteModel(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}