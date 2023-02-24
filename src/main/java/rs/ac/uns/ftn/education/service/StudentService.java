package rs.ac.uns.ftn.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

import javax.mail.MessagingException;

import java.util.HashMap;
import java.time.Year;
import java.util.Collections;

import rs.ac.uns.ftn.education.repository.RoleRepository;
import rs.ac.uns.ftn.education.repository.StudentRepository;
import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.Student;
import rs.ac.uns.ftn.education.model.StudyProgram;
import rs.ac.uns.ftn.education.payload.MailMessageDTO;
import rs.ac.uns.ftn.education.payload.StudentRequest;
import rs.ac.uns.ftn.education.model.Role;

@Service
public class StudentService {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private StudyProgramService studyProgramService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  MailService mailService;

  public Page<Student> getAll(Pageable pageable) {
    return studentRepository.findAll(pageable);
  }

  public Student getOne(Long studentId) {
    return studentRepository.findById(studentId)
        .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
  }

  public Student save(StudentRequest studentRequest) throws MessagingException {
      Student student = modelMapper.map(studentRequest, Student.class);
      student.setPassword(passwordEncoder.encode(studentRequest.getPersonalIdNumber()));

      Role studentRole = roleRepository.findByName(Role.ROLE_STUDENT)
        .orElseThrow(() -> new ResourceNotFoundException("Role", "name", Role.ROLE_STUDENT));
      student.setRoles(Collections.singleton(studentRole));

      Student savedStudent = studentRepository.save(student);
      studentRepository.refresh(savedStudent);

      savedStudent.setSchoolIdNumber(generateStudentIdNumber(savedStudent));

      savedStudent = studentRepository.saveAndFlush(savedStudent);

      if (studentRequest.getId() == null) {
        // MailMessageDTO mailMessageDTO = new MailMessageDTO();
        // mailMessageDTO.setFrom("admin@education.com");
        // mailMessageDTO.setTo(savedStudent.getEmail());
        // mailMessageDTO.setSubject("Your account has been created");
        // mailMessageDTO.setTemplateName("account-created");

        // Map<String, Object> parameters = new HashMap<>();
        // parameters.put("name", savedStudent.getFirstName());
        // parameters.put("username", savedStudent.getUsername());

        // mailMessageDTO.setParameters(parameters);
        // mailService.sendEmail(mailMessageDTO);
      }

      return savedStudent;
  }

  public Student enroll(Long studentId, Long studyProgramId) {
    Student student = getOne(studentId);
    StudyProgram studyProgram = studyProgramService.getOne(studyProgramId);

    student.setStudyProgram(studyProgram);
    Student savedStudent = studentRepository.save(student);
    savedStudent.setSchoolIdNumber(generateStudentIdNumber(savedStudent));

    return studentRepository.saveAndFlush(student);
  }

  public void delete(Long studentId) {
    studentRepository.deleteById(studentId);
  }

  public String generateStudentIdNumber(Student student) {
    if (student.getStudyProgram() == null) {
      return null;
    }

    return String.format(
      "%s-%s-%s", 
      student.getStudyProgram().getPrefix(),
      Long.toString(student.getId()),
      Year.now().toString()
    );
  }
}
