package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Specification;
import ua.alexd.CarRentService.repository.SpecificationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SpecificationService {
    private final SpecificationRepository specificationRepository;

    public SpecificationService(SpecificationRepository specificationRepository) {
        this.specificationRepository = specificationRepository;
    }

    public List<Specification> getAllSpecifications() {
        return (List<Specification>) specificationRepository.findAll();
    }

    public Optional<Specification> getSpecificationById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return specificationRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewSpecification(Specification newSpecification) {
        return saveSpecification(newSpecification);
    }

    public boolean updateSpecification(@NotNull Specification updSpecification) {
        var specificationFromDB = getSpecificationById(String.valueOf(updSpecification.getId()));
        if (specificationFromDB.isEmpty())
            return false;
        BeanUtils.copyProperties(updSpecification, specificationFromDB.get(), "id");
        return saveSpecification(specificationFromDB.get());
    }

    private boolean saveSpecification(Specification newSpecification) {
        try {
            specificationRepository.save(newSpecification);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteSpecification(String id) {
        var deletionModel = getSpecificationById(id);
        if (deletionModel.isEmpty())
            return false;
        specificationRepository.delete(deletionModel.get());
        return true;
    }
}