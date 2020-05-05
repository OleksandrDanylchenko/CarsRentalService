package ua.alexd.CarRentService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.alexd.CarRentService.domain.Complaint;
import ua.alexd.CarRentService.service.ComplaintService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/complaints")
public class ComplaintResource {
    private final ComplaintService complaintService;

    public ComplaintResource(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping
    public ResponseEntity<List<Complaint>> getComplains() {
        var complains = complaintService.getAllComplains();
        return complains.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(complains, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplainById(@PathVariable String id) {
        var foundedComplain = complaintService.getComplainById(id);
        return foundedComplain
                .map(complaint -> new ResponseEntity<>(complaint, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Complaint> addComplain(@Valid @RequestBody Complaint newComplaint) {
        return complaintService.addNewComplain(newComplaint)
                ? new ResponseEntity<>(newComplaint, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<Complaint> updateComplain(@Valid @RequestBody Complaint updComplaint) {
        return complaintService.updateComplain(updComplaint)
                ? new ResponseEntity<>(updComplaint, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplain(@PathVariable String id) {
        return complaintService.deleteComplain(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}