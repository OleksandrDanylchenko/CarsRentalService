package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.RentCenter;
import ua.alexd.CarRentService.repository.RentCenterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RentCenterService {
    private final RentCenterRepository rentCenterRepository;

    public RentCenterService(RentCenterRepository rentCenterRepository) {
        this.rentCenterRepository = rentCenterRepository;
    }

    public List<RentCenter> getAllRentCenters() {
        return (List<RentCenter>) rentCenterRepository.findAll();
    }

    public Optional<RentCenter> getRentCenterById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return rentCenterRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewRentCenter(RentCenter newRentCenter) {
        return saveRentCenter(newRentCenter);
    }

    public boolean updateRentCenter(@NotNull RentCenter updRentCenter) {
        var rentCenterFromDB = getRentCenterById(String.valueOf(updRentCenter.getId()));
        if (rentCenterFromDB.isPresent()) {
            BeanUtils.copyProperties(updRentCenter, rentCenterFromDB.get(), "id");
            return saveRentCenter(rentCenterFromDB.get());
        }
        return false;
    }

    private boolean saveRentCenter(RentCenter saveRentCenter) {
        try {
            rentCenterRepository.save(saveRentCenter);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteRentCenter(String id) {
        var deletionRentCenter = getRentCenterById(id);
        if (deletionRentCenter.isPresent()) {
            rentCenterRepository.delete(deletionRentCenter.get());
            return true;
        }
        return false;
    }
}