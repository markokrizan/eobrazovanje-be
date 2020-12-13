package rs.ac.uns.ftn.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.education.model.Role;
import rs.ac.uns.ftn.education.model.User;
import rs.ac.uns.ftn.education.payload.UserRequest;
import rs.ac.uns.ftn.education.security.UserPrincipal;


@Service
public class SecurityService {

  @Autowired
  private StudentService studentService;
  
  @Autowired
  private TeacherService teacherService;

  public boolean isRoleAccessingSelf(String role, Long modelId, UserPrincipal currentUser) {
    switch(role) {
      case Role.ROLE_STUDENT:
        User student = studentService.getOne(modelId);

        return student.getId() == currentUser.getId();
      case Role.ROLE_TEACHER:
        User teacher = teacherService.getOne(modelId);

        return teacher.getId() == currentUser.getId();
      case Role.ROLE_ADMIN:
        return true;
      default:
        return false;
    }
  }

  public boolean isRoleSavingSelf(String role, UserRequest userRequest, UserPrincipal currentUser) {
    if (userRequest.getId() == null) {
      return false;
    }

    return userRequest.getId() == currentUser.getId();
  }
}
