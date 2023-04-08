package rs.ac.uns.ftn.education.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.education.repository.CourseRepository;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rs.ac.uns.ftn.education.exception.AppException;
import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.Course;
import rs.ac.uns.ftn.education.model.Student;
import rs.ac.uns.ftn.education.model.StudyProgram;
import rs.ac.uns.ftn.education.model.Engagement;

@Service
public class CourseService {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private StudentService studentService;

  @Autowired
  private StudyProgramService studyProgramService;

  public Page<Course> getAll(Pageable pageable) {
    return courseRepository.findAll(pageable);
  }

  public Page<Course> getStudyProgramCourses(Long studyProgramId, Pageable pageable) {
    return courseRepository.findByStudyPrograms_Id(studyProgramId, pageable);
  }

  public Page<Course> getStudentCourses(Long studentId, Pageable pageable) {
    Student student = studentService.getOne(studentId);

    return getStudyProgramCourses(student.getStudyProgram().getId(), pageable);
  }

  public Page<Course> getTeacherCourses(Long teacherId, Pageable pageable) {
    return courseRepository.findByEngagements_Teacher_Id(teacherId, pageable);
  }

  public Course getOne(Long courseId) {
    return courseRepository.findById(courseId)
        .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
  }

  public Course save(Course course) {
    if (course.getId() != null) {
      return updateCourse(course);
    }

    return createCourse(course);
  }

  private Course createCourse(Course course) {
    if (courseRepository.findByName(course.getName()) != null) {
      throw new AppException("Course name already exists");
    }

    Course courseToSave = new Course();
    courseToSave.setName(course.getName());
    courseToSave.setYear(course.getYear());
    courseToSave.setSemester(course.getSemester());
    courseToSave.setEspbPoints(course.getEspbPoints());

    Course savedCourse = courseRepository.save(courseToSave);

    Set<Engagement> engagements = course.getEngagements()
      .stream()
      .map(engagement -> {
        engagement.setCourse(savedCourse);
        return engagement;
      })
      .collect(Collectors.toSet());

    savedCourse.setEngagements(engagements);

    course.getStudyPrograms()
      .stream()
      .map(studyProgram -> {
        StudyProgram savedProgram = studyProgramService.getOne(studyProgram.getId());
        savedProgram.getCourses().add(savedCourse);
        return savedProgram;
      }).forEach(studyProgram -> {
        studyProgramService.save(studyProgram);
      });

    courseRepository.save(savedCourse);
    courseRepository.refresh(savedCourse);
    return savedCourse;
  }

  private Course updateCourse(Course course) {
    Course existingCourse = courseRepository.findByName(course.getName());

    if (existingCourse != null && existingCourse.getId() != course.getId()) {
      throw new AppException("Course name already exists");
    }

    // Add to provided study programs
    course.getStudyPrograms()
        .stream()
        .map(studyProgram -> {
          StudyProgram savedProgram = studyProgramService.getOne(studyProgram.getId());
          savedProgram.getCourses().add(course);
          return savedProgram;
        }).forEach(studyProgram -> {
          studyProgramService.save(studyProgram);
        });

    // Remove from others
    studyProgramService.getAll(Pageable.unpaged())
        .get()
        .filter(studyProgram -> course.getStudyPrograms().contains(studyProgram) == false)
        .forEach(studyProgram -> {
          studyProgram.getCourses().remove(course);
          studyProgramService.save(studyProgram);
        });

    return courseRepository.save(course);
  }

  public void delete(Long courseId) {
    courseRepository.deleteById(courseId);
  }
}
