package rs.ac.uns.ftn.education.service;

import java.util.Map;

import javax.mail.MessagingException;

import java.util.HashMap;
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
import rs.ac.uns.ftn.education.payload.MailMessageDTO;
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

  @Autowired
  MailService mailService;

  public Page<Teacher> getAll(Pageable pageable) {
    return teacherRepository.findAll(pageable);
  }

  public Teacher getOne(Long teacherId) {
    return teacherRepository.findById(teacherId)
        .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacherId));
  }

  public Teacher save(TeacherRequest teacherRequest) throws MessagingException {
      Teacher teacher = modelMapper.map(teacherRequest, Teacher.class);
      teacher.setPassword(passwordEncoder.encode(teacherRequest.getPersonalIdNumber()));

      Role teacherRole = roleRepository.findByName(Role.ROLE_TEACHER)
        .orElseThrow(() -> new ResourceNotFoundException("Role", "name", Role.ROLE_TEACHER));
        teacher.setRoles(Collections.singleton(teacherRole));

      Teacher savedTeacher = teacherRepository.save(teacher);

      if (teacherRequest.getId() == null) {
        // MailMessageDTO mailMessageDTO = new MailMessageDTO();
        // mailMessageDTO.setFrom("admin@education.com");
        // mailMessageDTO.setTo(savedTeacher.getEmail());
        // mailMessageDTO.setSubject("Your account has been created");
        // mailMessageDTO.setTemplateName("account-created");
  
        // Map<String, Object> parameters = new HashMap<>();
        // parameters.put("name", savedTeacher.getFirstName());
        // parameters.put("username", savedTeacher.getUsername());
  
        // mailMessageDTO.setParameters(parameters);
        // mailService.sendEmail(mailMessageDTO);
      }

      return savedTeacher;
  }

  public void delete(Long teacherId) {
    teacherRepository.deleteById(teacherId);
  }
}
