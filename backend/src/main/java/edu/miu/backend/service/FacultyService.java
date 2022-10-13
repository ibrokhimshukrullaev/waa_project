package edu.miu.backend.service;

import edu.miu.backend.dto.requestDto.FacultyDto;
import edu.miu.backend.dto.requestDto.UserDto;
import edu.miu.backend.dto.responseDto.FacultyResponseDto;
import edu.miu.backend.dto.responseDto.UserResponseDto;
import edu.miu.backend.entity.Faculty;

import java.util.List;

public interface FacultyService {
    List<FacultyResponseDto> findAll();

    Faculty findById(int id);

    UserResponseDto save(UserDto userDto) throws Exception;

    FacultyResponseDto update(FacultyDto facultyDto, String username) throws Exception;

    void delete(int id);

    FacultyResponseDto findByUsername(String username);

    void deleteByUsername(String username);
}
