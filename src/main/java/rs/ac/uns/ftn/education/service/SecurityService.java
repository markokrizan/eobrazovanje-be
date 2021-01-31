package rs.ac.uns.ftn.education.service;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.education.model.Role;
import rs.ac.uns.ftn.education.model.User;
import rs.ac.uns.ftn.education.model.Exam;
import rs.ac.uns.ftn.education.payload.ExamRegistrationRequest;
import rs.ac.uns.ftn.education.payload.GradeRequest;
import rs.ac.uns.ftn.education.payload.UserRequest;
import rs.ac.uns.ftn.education.repository.EngagementRepository;
import rs.ac.uns.ftn.education.security.UserPrincipal;

@Service
public class SecurityService {

  @Autowired
  private StudentService studentService;
  
  @Autowired
  private TeacherService teacherService;

  @Autowired
  private EngagementRepository engagementRepository;

  @Autowired
  private ExamService examService;

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

  public boolean canUpdateExamRegistration(ExamRegistrationRequest examRegistrationRequest, UserPrincipal currentUser) {
    if (!currentUserHasRoles(currentUser, Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_STUDENT))) {
      return false;
    }

    if (currentUserHasRoles(currentUser, Arrays.asList(Role.ROLE_ADMIN))) {
      return true;
    }

    return examRegistrationRequest.getStudent().getId() == currentUser.getId();
  }

  public boolean canUpdateGrade(GradeRequest gradeRequest, UserPrincipal currentUser) {
    if (!currentUserHasRoles(currentUser, Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_TEACHER))) {
      return false;
    }

    if (currentUserHasRoles(currentUser, Arrays.asList(Role.ROLE_ADMIN))) {
      return true;
    }

    Exam exam = examService.getOne(gradeRequest.getExam().getId());

    return engagementRepository.findByCourse_IdAndTeacher_Id(
      exam.getCourse().getId(),
      currentUser.getId()
    ) != null;
  }

  private boolean currentUserHasRoles(UserPrincipal currentUser, List<String> roles) {
    HashSet<String> intersection = new HashSet<>(); 

    intersection.addAll(getCurrentUserRoles(currentUser));
    intersection.retainAll(roles);

    return !intersection.isEmpty();
  }

  private List<String> getCurrentUserRoles(UserPrincipal currentUser) {
    return currentUser
      .getAuthorities()
      .stream()
      .map(grantedAuthority -> grantedAuthority.getAuthority())
      .collect(Collectors.toList());
  }
}
