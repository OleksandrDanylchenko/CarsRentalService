package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Complaint;
import ua.alexd.CarRentService.repository.ComplaintRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {
    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public List<Complaint> getAllComplains() {
        return (List<Complaint>) complaintRepository.findAll();
    }

    public Optional<Complaint> getComplainById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return complaintRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewComplain(Complaint newComplaint) {
        return saveComplain(newComplaint);
    }

    public boolean updateComplain(@NotNull Complaint updComplaint) {
        var complainFromDB = getComplainById(String.valueOf(updComplaint.getId()));
        if (complainFromDB.isPresent()) {
            BeanUtils.copyProperties(updComplaint, complainFromDB.get(), "id");
            return saveComplain(complainFromDB.get());
        }
        return false;
    }

    private boolean saveComplain(Complaint saveComplaint) {
        try {
            complaintRepository.save(saveComplaint);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteComplain(String id) {
        var deletionComplain = getComplainById(id);
        if (deletionComplain.isPresent()) {
            complaintRepository.delete(deletionComplain.get());
            return true;
        }
        return false;
    }
}