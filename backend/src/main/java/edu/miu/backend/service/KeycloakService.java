package edu.miu.backend.service;

import edu.miu.backend.dto.requestDto.UserDto;

public interface KeycloakService {

    void setUserRole(UserDto userDto);

    void updateCredentials(UserDto userDto);

    void deactivateUser(String username);

    void resetPassword(String username, String password);
}
