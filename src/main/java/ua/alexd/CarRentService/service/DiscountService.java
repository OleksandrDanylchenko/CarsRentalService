package ua.alexd.CarRentService.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Discount;
import ua.alexd.CarRentService.repository.DiscountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public List<Discount> getAllDiscounts() {
        return (List<Discount>) discountRepository.findAll();
    }

    public Optional<Discount> getDiscountById(String id) {
        try {
            var longId = NumberUtils.createLong(id);
            return discountRepository.findById(longId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean addNewDiscount(Discount newDiscount) {
        return saveDiscount(newDiscount);
    }

    public boolean updateDiscount(@NotNull Discount updDiscount) {
        var discountFromDB = getDiscountById(String.valueOf(updDiscount.getId()));
        if (discountFromDB.isPresent()) {
            BeanUtils.copyProperties(updDiscount, discountFromDB.get(), "id");
            return saveDiscount(discountFromDB.get());
        }
        return false;
    }

    private boolean saveDiscount(Discount saveDiscount) {
        try {
            discountRepository.save(saveDiscount);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public boolean deleteDiscount(String id) {
        var deletionDiscount = getDiscountById(id);
        if (deletionDiscount.isPresent()) {
            discountRepository.delete(deletionDiscount.get());
            return true;
        }
        return false;
    }
}