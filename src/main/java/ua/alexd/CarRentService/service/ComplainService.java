package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Complain;
import ua.alexd.CarRentService.repository.ComplainRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ComplainService {
    private final ComplainRepository complainRepository;

    public ComplainService(ComplainRepository complainRepository) {
        this.complainRepository = complainRepository;
    }

    public List<Complain> getAllComplains() {
        return (List<Complain>) complainRepository.findAll();
    }

    public Optional<Complain> getComplainById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return complainRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewComplain(Complain newComplain) {
        return saveComplain(newComplain);
    }

    public boolean updateComplain(@NotNull Complain updComplain) {
        var complainFromDB = getComplainById(String.valueOf(updComplain.getId()));
        if (complainFromDB.isPresent()) {
            BeanUtils.copyProperties(updComplain, complainFromDB.get(), "id");
            return saveComplain(complainFromDB.get());
        }
        return false;
    }

    private boolean saveComplain(Complain saveComplain) {
        try {
            complainRepository.save(saveComplain);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteComplain(String id) {
        var deletionComplain = getComplainById(id);
        if (deletionComplain.isPresent()) {
            complainRepository.delete(deletionComplain.get());
            return true;
        }
        return false;
    }
}