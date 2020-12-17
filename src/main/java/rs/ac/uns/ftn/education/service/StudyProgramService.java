package rs.ac.uns.ftn.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.Course;
import rs.ac.uns.ftn.education.model.StudyProgram;

import rs.ac.uns.ftn.education.repository.StudyProgramRepository;

@Service
public class StudyProgramService {
  
  @Autowired
  private StudyProgramRepository studyProgramRepository;

  @Autowired
  private CourseService courseService;

  public Page<StudyProgram> getAll(Pageable pageable) {
      return studyProgramRepository.findAll(pageable);
  }

  public StudyProgram getOne(Long studyProgramId) {    
      return studyProgramRepository.findById(studyProgramId)
        .orElseThrow(() -> new ResourceNotFoundException("StudyProgram", "id", studyProgramId));
  }

  public StudyProgram save(StudyProgram studyProgram) {
      StudyProgram savedStudyProgram = studyProgramRepository.save(studyProgram);
      studyProgramRepository.refresh(savedStudyProgram);

      return savedStudyProgram;
  }

  public StudyProgram addCourse(Long studyProgramId, Long courseId) {
    StudyProgram studyProgram = getOne(studyProgramId);
    Course course = courseService.getOne(courseId);

    studyProgram.getCourses().add(course);

    StudyProgram savedStudyProgram = studyProgramRepository.save(studyProgram);
    studyProgramRepository.refresh(savedStudyProgram);

    return savedStudyProgram;
  }

  public void delete(Long studyProgramId) {
    studyProgramRepository.deleteById(studyProgramId);
  }
}
