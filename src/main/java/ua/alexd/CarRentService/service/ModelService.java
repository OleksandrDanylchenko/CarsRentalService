package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Model;
import ua.alexd.CarRentService.repository.ModelRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ModelService {
    private final ModelRepository modelRepository;

    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }

    public Optional<Model> getModelById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return modelRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewModel(Model newModel) {
        return saveRecord(newModel);
    }

    public boolean updateModel(@NotNull Model updModel) {
        var modelFromDB = getModelById(String.valueOf(updModel.getId()));
        if (modelFromDB.isEmpty())
            return false;
        BeanUtils.copyProperties(updModel, modelFromDB.get(), "id");
        return saveRecord(modelFromDB.get());
    }

    private boolean saveRecord(Model saveModel) {
        try {
            modelRepository.save(saveModel);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteModel(String id) {
        var deletionModel = getModelById(id);
        if (deletionModel.isEmpty())
            return false;
        modelRepository.delete(deletionModel.get());
        return true;
    }
}