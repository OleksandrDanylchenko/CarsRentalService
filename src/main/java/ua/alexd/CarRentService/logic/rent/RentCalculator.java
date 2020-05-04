package ua.alexd.CarRentService.logic.rent;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.alexd.CarRentService.domain.Car;
import ua.alexd.CarRentService.domain.Client;
import ua.alexd.CarRentService.domain.Discount;
import ua.alexd.CarRentService.domain.Rent;
import ua.alexd.CarRentService.repository.CarRepository;
import ua.alexd.CarRentService.repository.ClientRepository;
import ua.alexd.CarRentService.repository.DiscountRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RentCalculator {
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final DiscountRepository discountRepository;

    public RentCalculator(ClientRepository clientRepository, CarRepository carRepository,
                          DiscountRepository discountRepository) {
        this.clientRepository = clientRepository;
        this.carRepository = carRepository;
        this.discountRepository = discountRepository;
    }

    public Optional<Integer> calculateTotalPrice(@NotNull Rent rent) {
        var daysDelta = rent.getDaysDelta();
        var dayPrice = getRentCarDayPrice(rent);
        var ridesAmount = getRentClientRides(rent);
        var discounts = getDiscounts();
        if (dayPrice.isPresent() && ridesAmount.isPresent() && !discounts.isEmpty()) {
            var discountPercent = getDiscount(ridesAmount.get(), discounts).orElse(null);
            return Optional.of(calculatePrice(dayPrice.get(), daysDelta, discountPercent));
        }
        return Optional.empty();
    }

    private @NotNull Optional<Integer> getRentCarDayPrice(@NotNull Rent rent) {
        var carId = rent.getCar().getId();
        var rentCar = carRepository.findById(carId);
        return rentCar.map(Car::getDayPrice);
    }

    private @NotNull Optional<Integer> getRentClientRides(@NotNull Rent rent) {
        var clientId = rent.getClient().getId();
        var client = clientRepository.findById(clientId);
        return client.map(Client::getRidesAmount);
    }

    private @NotNull List<Discount> getDiscounts() {
        return (List<Discount>) discountRepository.findAll();
    }

    // Finds the nearest rides amount
    private Optional<Integer> getDiscount(int clientRidesAmount, @NotNull List<Discount> discounts) {
        discounts.sort(Comparator.comparingInt(Discount::getRentsAmount).reversed());
        for (var discount : discounts)
            if (discount.getRentsAmount() <= clientRidesAmount)
                return Optional.of(discount.getDiscountPercents());
        return Optional.empty();
    }

    @Contract(pure = true)
    private int calculatePrice(int dayPrice, int daysDelta, Integer discountOrNull) {
        var corePrice = dayPrice * daysDelta;
        return discountOrNull == null ? corePrice : corePrice - (corePrice * discountOrNull / 100);
    }
}