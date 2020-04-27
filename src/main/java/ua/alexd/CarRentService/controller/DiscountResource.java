package ua.alexd.CarRentService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.alexd.CarRentService.domain.Discount;
import ua.alexd.CarRentService.service.DiscountService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/discounts")
public class DiscountResource {
    private final DiscountService discountService;

    public DiscountResource(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public ResponseEntity<List<Discount>> getDiscounts() {
        var discounts = discountService.getAllDiscounts();
        return discounts.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(discounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable String id) {
        var foundedDiscount = discountService.getDiscountById(id);
        return foundedDiscount
                .map(discount -> new ResponseEntity<>(discount, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Discount> addDiscount(@Valid @RequestBody Discount newDiscount) {
        return discountService.addNewDiscount(newDiscount)
                ? new ResponseEntity<>(newDiscount, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<Discount> updateDiscount(@Valid @RequestBody Discount updDiscount) {
        return discountService.updateDiscount(updDiscount)
                ? new ResponseEntity<>(updDiscount, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Discount> deleteDiscount(@PathVariable String id) {
        return discountService.deleteDiscount(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}