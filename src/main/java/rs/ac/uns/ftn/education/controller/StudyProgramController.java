package rs.ac.uns.ftn.education.controller;

import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.education.model.StudyProgram;
import rs.ac.uns.ftn.education.payload.StudyProgramRequest;
import rs.ac.uns.ftn.education.service.StudyProgramService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.modelmapper.ModelMapper;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class StudyProgramController {

  @Autowired
  private StudyProgramService studyProgramService;

  @Autowired
  private ModelMapper modelMapper;
  
  @GetMapping("/study-programs")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<StudyProgram> getAll(@PageableDefault(size = 10) Pageable pageable) {
      return studyProgramService.getAll(pageable);
  }

  @GetMapping("/study-programs/{studyProgramId}")
  @PreAuthorize("hasRole('ADMIN')")
  public StudyProgram getOne(@PathVariable("studyProgramId") Long studyProgramId) {    
      return studyProgramService.getOne(studyProgramId);
  }

  @PostMapping("/study-programs")
  @PreAuthorize("hasRole('ADMIN')")
  public StudyProgram save(@Valid @RequestBody StudyProgramRequest studyProgramRequest) {
      StudyProgram studyProgram = modelMapper.map(studyProgramRequest, StudyProgram.class);

      return studyProgramService.save(studyProgram);
  }

  @PutMapping("/study-programs/{studyProgramId}/add-course/{courseId}")
  @PreAuthorize("hasRole('ADMIN')")
  public StudyProgram save(@PathVariable("studyProgramId") Long studyProgramId, @PathVariable("studyProgramId") Long courseId) {
      return studyProgramService.addCourse(studyProgramId, courseId);
  }

  @DeleteMapping("/study-programs/{studyProgramId}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable("studentId") Long studyProgramId) {
    studyProgramService.delete(studyProgramId);
  }
}
