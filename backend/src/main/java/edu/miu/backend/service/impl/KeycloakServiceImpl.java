package edu.miu.backend.service.impl;

import edu.miu.backend.dto.requestDto.UserDto;
import edu.miu.backend.entity.Faculty;
import edu.miu.backend.entity.Student;
import edu.miu.backend.repository.FacultyRepository;
import edu.miu.backend.repository.StudentRepository;
import edu.miu.backend.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final StudentRepository studentRepo;

    private final FacultyRepository facultyRepo;

    private final Keycloak keycloak;

    @Override
    public void setUserRole(UserDto userDto) {
        UserRepresentation userRepresentation = getUserByUsername(userDto.getUsername());
        UserResource userResource = keycloak.realm("waa-project").users().get(userRepresentation.getId());
        RoleRepresentation roleRepresentation = keycloak.realm("waa-project").roles().get(userDto.getRole())
                .toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    @Override
    public void updateCredentials(UserDto userDto) {
        UserRepresentation userRepresentation = getUserByUsername(userDto.getUsername());
        UserResource userResource = keycloak.realm("waa-project").users().get(userRepresentation.getId());

        String username = userDto.getUsername();
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        String email = userDto.getEmail();

        Student student = studentRepo.findByUsername(username).orElse(null);
        Faculty faculty = facultyRepo.findByUsername(username).orElse(null);

        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEmail(email);
        userResource.update(userRepresentation);

        if (student != null) {
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setEmail(email);
            studentRepo.save(student);
        }
        if (faculty != null) {
            faculty.setFirstName(firstName);
            faculty.setLastName(lastName);
            faculty.setEmail(email);
            facultyRepo.save(faculty);
        }

    }

    @Override
    public void deactivateUser(String username) {
        UserRepresentation userRepresentation = getUserByUsername(username);
        UserResource userResource = keycloak.realm("waa-project").users().get(userRepresentation.getId());
        Student student = studentRepo.findByUsername(username).orElse(null);
        Faculty faculty = facultyRepo.findByUsername(username).orElse(null);

        if(userRepresentation != null) {
            if(userRepresentation.isEnabled()) {
                if(faculty != null) {
                    faculty.setActive(false);
                    facultyRepo.save(faculty);
                }
                if(student != null) {
                    student.setActive(false);
                    studentRepo.save(student);
                }
                userRepresentation.setEnabled(false);
                userResource.update(userRepresentation);
            } else {
                if(faculty != null) {
                    faculty.setActive(true);
                    facultyRepo.save(faculty);
                }
                if(student != null) {
                    student.setActive(true);
                    studentRepo.save(student);
                }
                userRepresentation.setEnabled(true);
                userResource.update(userRepresentation);
            }

        }
    }

    @Override
    public void resetPassword(String username, String password) {
        UserRepresentation userRepresentation = getUserByUsername(username);
        UserResource userResource = keycloak.realm("waa-project").users().get(userRepresentation.getId());
        CredentialRepresentation credentialsRepresentation = new CredentialRepresentation();
        credentialsRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialsRepresentation.setValue(password);
        userResource.resetPassword(credentialsRepresentation);
    }

    public UserRepresentation getUserByUsername(String username) {
        return keycloak.realm("waa-project").users().search(username).get(0);
    }
}
