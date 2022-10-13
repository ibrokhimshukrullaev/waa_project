package edu.miu.backend.controller;

import edu.miu.backend.dto.requestDto.UserDto;
import edu.miu.backend.dto.responseDto.UserResponseDto;
import edu.miu.backend.service.FacultyService;
import edu.miu.backend.service.KeycloakService;
import edu.miu.backend.service.StudentService;
import lombok.RequiredArgsConstructor;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    private final StudentService studentService;

    private final FacultyService facultyService;

    private final KeycloakService keycloakService;

    @PostMapping("/role")
    public UserResponseDto createUser(@RequestBody UserDto userDto) throws Exception {
        keycloakService.setUserRole(userDto);
        if (userDto.getRole().equalsIgnoreCase("student")) {
            return studentService.save(userDto);
        } else {
            return facultyService.save(userDto);
        }
    }

    @PutMapping("/name")
    @RolesAllowed("student")
    public void updateCredentials(@RequestBody UserDto userDto) {
        keycloakService.updateCredentials(userDto);
    }

    @PutMapping("/{username}/active")
    @RolesAllowed("admin")
    public void deactivateUser(@PathVariable String username) {
        keycloakService.deactivateUser(username);
    }

    @PutMapping("/{username}/reset-password")
    @RolesAllowed("student")
    public void resetPassword(@PathVariable String username, @RequestParam String password) {
        keycloakService.resetPassword(username, password);
    }

}
