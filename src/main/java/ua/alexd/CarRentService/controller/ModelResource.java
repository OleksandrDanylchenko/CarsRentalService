package ua.alexd.CarRentService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.CarRentService.domain.Model;
import ua.alexd.CarRentService.service.ModelService;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/models")
public class ModelResource {
    private final ModelService modelService;
    private final SmartValidator smartValidator;

    public ModelResource(ModelService modelService, SmartValidator smartValidator) {
        this.modelService = modelService;
        this.smartValidator = smartValidator;
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
    public ResponseEntity<Model> addModel(@RequestParam(value = "model") String newModelStr,
                                          @RequestParam(value = "modelImage") MultipartFile newModelImage) {
        var objectMapper = new ObjectMapper();
        Model newModel;
        try {
            newModel = objectMapper.readValue(newModelStr, Model.class);
            validateModel(newModel);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return modelService.addNewModel(newModel, newModelImage)
                ? new ResponseEntity<>(newModel, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<Model> updateModel(@RequestParam(value = "model") String updModelStr,
                                             @RequestParam(value = "modelImage", required = false) MultipartFile updModelImage) {
        var objectMapper = new ObjectMapper();
        Model updModel;
        try {
            updModel = objectMapper.readValue(updModelStr, Model.class);
            validateModel(updModel);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

    private void validateModel(Model model) {
        Errors errors = new BeanPropertyBindingResult(model, "validateModel");
        smartValidator.validate(model, errors);
        if (errors.hasErrors())
            throw new IllegalArgumentException();
    }
}