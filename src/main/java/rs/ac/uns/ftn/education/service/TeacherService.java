package rs.ac.uns.ftn.education.service;

import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.education.repository.RoleRepository;
import rs.ac.uns.ftn.education.repository.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.Role;
import rs.ac.uns.ftn.education.model.Teacher;
import rs.ac.uns.ftn.education.payload.TeacherRequest;

@Service
public class TeacherService {

  @Autowired
  private TeacherRepository teacherRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  RoleRepository roleRepository;
  
  public Page<Teacher> getAll(Pageable pageable) {
      return teacherRepository.findAll(pageable);
  }

  public Teacher getOne(Long teacherId) {    
      return teacherRepository.findById(teacherId)
        .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacherId));
  }

  public Teacher save(TeacherRequest teacherRequest) {
      Teacher teacher = modelMapper.map(teacherRequest, Teacher.class);
      teacher.setPassword(passwordEncoder.encode(teacherRequest.getPersonalIdNumber()));

      Role teacherRole = roleRepository.findByName(Role.ROLE_TEACHER)
        .orElseThrow(() -> new ResourceNotFoundException("Role", "name", Role.ROLE_TEACHER));
        teacher.setRoles(Collections.singleton(teacherRole));

      return teacherRepository.save(teacher);
  }

  public void delete(Long teacherId) {
    teacherRepository.deleteById(teacherId);
  }
}
