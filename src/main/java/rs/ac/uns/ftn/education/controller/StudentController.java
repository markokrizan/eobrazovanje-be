package rs.ac.uns.ftn.education.controller;

import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.Role;
import rs.ac.uns.ftn.education.model.Student;
import rs.ac.uns.ftn.education.model.User;
import rs.ac.uns.ftn.education.payload.StudentRequest;
import rs.ac.uns.ftn.education.repository.RoleRepository;
import rs.ac.uns.ftn.education.repository.StudentRepository;
import rs.ac.uns.ftn.education.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class StudentController {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  RoleRepository roleRepository;
  
  @GetMapping("/students")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<Student> getAll(@PageableDefault(size = Student.DEFAULT_PER_PAGE) Pageable pageable) {
      return studentRepository.findAll(pageable);
  }

  @GetMapping("/students/{studentId}")
  @PreAuthorize("hasRole('ADMIN')")
  public Student getOne(@PathVariable("studentId") Long studentId) {    
      return studentRepository.findById(studentId)
        .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
  }

  @PostMapping("/students")
  @PreAuthorize("hasRole('ADMIN')")
  public Student save(@Valid @RequestBody StudentRequest studentRequest) {
      Student student = modelMapper.map(studentRequest, Student.class);
      User user = modelMapper.map(studentRequest, User.class);

      user.setPassword(passwordEncoder.encode(studentRequest.getPersonalIdNumber()));

      Role studentRole = roleRepository.findByName(Role.ROLE_STUDENT)
        .orElseThrow(() -> new ResourceNotFoundException("Role", "name", Role.ROLE_STUDENT));
      user.setRoles(Collections.singleton(studentRole));
  
      userRepository.save(user);

      student.setUser(user);

      return studentRepository.save(student);
  }

  @DeleteMapping("/students/{studentId}")
  @PreAuthorize("hasRole('USER')")
  public void delete(@PathVariable("studentId") Long studentId) {    
    Student student = studentRepository.findById(studentId)
      .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

    userRepository.deleteById(student.getUser().getId());
    studentRepository.deleteById(studentId);
  }
}
