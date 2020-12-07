package rs.ac.uns.ftn.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.StudyProgram;

import rs.ac.uns.ftn.education.repository.StudyProgramRepository;

@Service
public class StudyProgramService {
  
  @Autowired
  private StudyProgramRepository studyProgramRepository;

  
  public Page<StudyProgram> getAll(Pageable pageable) {
      return studyProgramRepository.findAll(pageable);
  }

  public StudyProgram getOne(Long studyProgramId) {    
      return studyProgramRepository.findById(studyProgramId)
        .orElseThrow(() -> new ResourceNotFoundException("StudyProgram", "id", studyProgramId));
  }

  public StudyProgram save(StudyProgram studyProgram) {
      return studyProgramRepository.save(studyProgram);
  }

  public void delete(Long studyProgramId) {
    studyProgramRepository.deleteById(studyProgramId);
  }
}
