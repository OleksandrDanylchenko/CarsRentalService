package ua.alexd.CarRentService.logic.car;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Car;
import ua.alexd.CarRentService.domain.ModelClass;
import ua.alexd.CarRentService.repository.ModelClassRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Lazy
public class ModelClassResolver {
    private final List<ModelClass> classes;

    public ModelClassResolver(@NotNull ModelClassRepository modelClassRepository) {
        classes = (List<ModelClass>) modelClassRepository.findAll();
    }

    public Optional<ModelClass> resolve(Car car) {
        classes.sort(Comparator.comparingInt(ModelClass::getStartPrice).reversed());
        for (var modelClass : classes)
            if (car.getDayPrice() >= modelClass.getStartPrice())
                return Optional.of(modelClass);
        return Optional.empty();
    }
}