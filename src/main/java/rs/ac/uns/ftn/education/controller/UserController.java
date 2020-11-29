package rs.ac.uns.ftn.education.controller;

import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.Student;
import rs.ac.uns.ftn.education.model.Teacher;
import rs.ac.uns.ftn.education.model.User;
import rs.ac.uns.ftn.education.payload.*;
import rs.ac.uns.ftn.education.repository.StudentRepository;
import rs.ac.uns.ftn.education.repository.TeacherRepository;
import rs.ac.uns.ftn.education.repository.UserRepository;
import rs.ac.uns.ftn.education.security.UserPrincipal;
import rs.ac.uns.ftn.education.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/users/me")
    @PreAuthorize("isAuthenticated()")
    public MeResponse getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));

        Student student = studentRepository.findByUserId(currentUser.getId()).orElse(null);
        Teacher teacher = teacherRepository.findByUserId(currentUser.getId()).orElse(null);

        MeResponse meResponse = new MeResponse();

        meResponse.setUser(user);
        meResponse.setStudent(student);
        meResponse.setTeacher(teacher);

        return meResponse;
    }

    @GetMapping("/users/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public User getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return user;
    }
}
