package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.ModelClass;
import ua.alexd.CarRentService.repository.ModelClassRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ModelClassService {
    private final ModelClassRepository modelClassRepository;

    public ModelClassService(ModelClassRepository modelClassRepository) {
        this.modelClassRepository = modelClassRepository;
    }

    public List<ModelClass> getAllClasses() {
        return (List<ModelClass>) modelClassRepository.findAll();
    }

    public Optional<ModelClass> getClassById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return modelClassRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewClass(ModelClass newClass) {
        return saveClass(newClass);
    }

    public boolean updateClass(@NotNull ModelClass updClass) {
        var classFromDB = getClassById(String.valueOf(updClass.getId()));
        if (classFromDB.isEmpty())
            return false;
        BeanUtils.copyProperties(updClass, classFromDB.get(), "id");
        return saveClass(classFromDB.get());
    }

    private boolean saveClass(ModelClass saveClass) {
        try {
            modelClassRepository.save(saveClass);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteClass(String id) {
        var deletionClass = getClassById(id);
        if (deletionClass.isEmpty())
            return false;
        modelClassRepository.delete(deletionClass.get());
        return true;
    }
}