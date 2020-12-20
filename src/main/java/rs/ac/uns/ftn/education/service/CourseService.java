package rs.ac.uns.ftn.education.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.education.repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rs.ac.uns.ftn.education.exception.AppException;
import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.Course;
import rs.ac.uns.ftn.education.model.Engagement;

@Service
public class CourseService {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private EngagementService engagementService;
  
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

  public Course removeEngagement(Long courseId, Long engagementId) {
    Course course = getOne(courseId);
    Engagement existingEngagement = engagementService.getOne(engagementId);

    Optional<Engagement> courseEngagement = course
      .getEngagements()
      .stream()
      .filter(engagement -> engagement.equals(existingEngagement))
      .findFirst();

    if (courseEngagement.isPresent()) {
      throw new AppException("Course doesen't have that engagement!");
    }

    course.getEngagements().remove(existingEngagement);
    Course savedCourse = save(course);
    courseRepository.refresh(course);

    return savedCourse;
  }

  public void delete(Long courseId) {
    courseRepository.deleteById(courseId);
  }
}
