package ua.alexd.CarRentService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.alexd.CarRentService.domain.Course;
import ua.alexd.CarRentService.service.CoursesHardcodedService;

import java.net.URI;
import java.util.List;

//3000 React, 8081 Vue JS, 4200 Angular
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
public class CourseResource {
    private final CoursesHardcodedService courseManagementService;

    public CourseResource(CoursesHardcodedService courseManagementService) {
        this.courseManagementService = courseManagementService;
    }

    @GetMapping("/instructors/{username}/courses")
    public List<Course> getAllCourses(@PathVariable String username) {
        return courseManagementService.findAll();
    }

    @GetMapping("/instructors/{username}/courses/{id}")
    public Course getCourse(@PathVariable String username, @PathVariable long id) {
        return courseManagementService.findById(id);
    }

    @PostMapping("/instructors/{username}/courses")
    public ResponseEntity<Void> createCourse(@PathVariable String username, @RequestBody Course course) {
        Course createdCourse = courseManagementService.save(course);
        // Location
        // Get current resource url
        /// {id}
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdCourse.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/instructors/{username}/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable String username, @PathVariable long id,
                                               @RequestBody Course course) {
        Course courseUpdated = courseManagementService.save(course);
        return new ResponseEntity<>(courseUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/instructors/{username}/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String username, @PathVariable long id) {
        Course course = courseManagementService.deleteById(id);
        return course != null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}