package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Type;
import ua.alexd.CarRentService.repository.TypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> getAllTypes() {
        return (List<Type>) typeRepository.findAll();
    }

    public Optional<Type> getTypeById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return typeRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewType(Type newType) {
        return saveType(newType);
    }

    public boolean updateType(@NotNull Type updType) {
        var typeFromDB = getTypeById(String.valueOf(updType.getId()));
        if (typeFromDB.isPresent()) {
            BeanUtils.copyProperties(updType, typeFromDB.get(), "id");
            return saveType(typeFromDB.get());
        }
        return false;
    }

    private boolean saveType(Type saveType) {
        try {
            typeRepository.save(saveType);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteType(String id) {
        var deletionType = getTypeById(id);
        if (deletionType.isPresent()) {
            typeRepository.delete(deletionType.get());
            return true;
        }
        return false;
    }
}