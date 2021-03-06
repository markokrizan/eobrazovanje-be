package rs.ac.uns.ftn.education.controller;

import rs.ac.uns.ftn.education.exception.AppException;
import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.User;
import rs.ac.uns.ftn.education.model.Role;
import rs.ac.uns.ftn.education.payload.*;
import rs.ac.uns.ftn.education.repository.RoleRepository;
import rs.ac.uns.ftn.education.repository.UserRepository;
import rs.ac.uns.ftn.education.security.UserPrincipal;
import rs.ac.uns.ftn.education.service.MailService;
import rs.ac.uns.ftn.education.security.CurrentUser;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository<User> userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MailService mailService;

    @GetMapping("/users/me")
    @PreAuthorize("isAuthenticated()")
    public User getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getUsers(@PageableDefault(size = 10) Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @GetMapping("/users/username-availability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public User getUserProfile(@PathVariable(value = "username") String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public User save(@Valid @RequestBody UserRequest userRequest) throws MessagingException {
        User user = modelMapper.map(userRequest, User.class);

        user.setPassword(passwordEncoder.encode(user.getPersonalIdNumber()));

        if (userRequest.getRole() != null) {
            Role userRole = roleRepository.findByName(userRequest.getRole())
            .orElseThrow(() -> new AppException("Role " + userRequest.getRole() + " doesen't exist."));

            user.setRoles(Collections.singleton(userRole));
        }

        User savedUser = userRepository.save(user);

        if (userRequest.getId() == null) {
            MailMessageDTO mailMessageDTO = new MailMessageDTO();
            mailMessageDTO.setFrom("admin@education.com");
            mailMessageDTO.setTo(savedUser.getEmail());
            mailMessageDTO.setSubject("Your account has been created");
            mailMessageDTO.setTemplateName("account-created");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", savedUser.getFirstName());
            parameters.put("username", savedUser.getUsername());

            mailMessageDTO.setParameters(parameters);
            mailService.sendEmail(mailMessageDTO);
        }

        return savedUser;
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable("userId") Long userId) {
        userRepository.deleteById(userId);
    }
}
