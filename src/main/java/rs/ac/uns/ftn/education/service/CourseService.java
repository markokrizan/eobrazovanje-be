package rs.ac.uns.ftn.education.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.education.repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.Course;

@Service
public class CourseService {

  @Autowired
  private CourseRepository courseRepository;
  
  public Page<Course> getAll(Pageable pageable) {
    return courseRepository.findAll(pageable);
  }

  public Page<Course> getStudyProgramCourses(Long studyProgramId, Pageable pageable) {
    return courseRepository.findByStudyPrograms_Id(studyProgramId, pageable);
  }

  public Course getOne(Long courseId) {    
    return courseRepository.findById(courseId)
      .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
  }

  public Course save(Course course) {
    return courseRepository.save(course);
  }

  public void delete(Long courseId) {
    courseRepository.deleteById(courseId);
  }
}
