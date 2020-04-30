package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.CarRentService.domain.Model;
import ua.alexd.CarRentService.photoService.ModelsPhotoService;
import ua.alexd.CarRentService.repository.ModelRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ModelService {
    private final ModelRepository modelRepository;
    private final ModelsPhotoService modelsImageService;

    public ModelService(ModelRepository modelRepository, ModelsPhotoService modelsImageService) {
        this.modelRepository = modelRepository;
        this.modelsImageService = modelsImageService;
    }

    public List<Model> getAllModels() {
        return (List<Model>) modelRepository.findAll();
    }

    public Optional<Model> getModelById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return modelRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewModel(Model newModel, MultipartFile newModelImage) {
        return saveImagePhoto(newModel, newModelImage) && saveModel(newModel);
    }

    public boolean updateModel(@NotNull Model updModel, MultipartFile updModelImage) {
        var modelFromDB = getModelById(String.valueOf(updModel.getId()));
        if (modelFromDB.isPresent()) {
            BeanUtils.copyProperties(updModel, modelFromDB.get(), "id");
            if (updModelImage == null) // image was not modified -> save only text data
                return saveModel(modelFromDB.get());
            return saveImagePhoto(updModel, updModelImage) && saveModel(modelFromDB.get());
        }
        return false;
    }

    private boolean saveImagePhoto(@NotNull Model model, MultipartFile image) {
        try {
            var modelPhotoName = modelsImageService.saveUploadingImage(image);
            model.setImageName(modelPhotoName);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            return false;
        }
    }

    private boolean saveModel(Model saveModel) {
        try {
            modelRepository.save(saveModel);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteModel(String id) {
        var deletionModel = getModelById(id);
        if (deletionModel.isPresent()) {
            modelRepository.delete(deletionModel.get());
            return true;
        }
        return false;
    }
}