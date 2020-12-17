package rs.ac.uns.ftn.education.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import rs.ac.uns.ftn.education.model.Course;
import rs.ac.uns.ftn.education.payload.CourseRequest;
import rs.ac.uns.ftn.education.service.CourseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class CourseController {

  @Autowired
  private CourseService courseService;

  @GetMapping("/courses")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<Course> getAll(@PageableDefault(size = 10) Pageable pageable) {
    return courseService.getAll(pageable);
  }

  @GetMapping("/courses/{courseId}")
  @PreAuthorize("hasRole('ADMIN')")
  public Course getOne(@PathVariable("courseId") Long courseId) {
    return courseService.getOne(courseId);
  }

  @PostMapping("/courses")
  @PreAuthorize("hasRole('ADMIN')")
  public Course save(@Valid @RequestBody CourseRequest courseRequest) {
      return courseService.save(courseRequest);
  }

  @DeleteMapping("/courses/{courseId}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable("courseId") Long courseId) {
    courseService.delete(courseId);
  }
}
