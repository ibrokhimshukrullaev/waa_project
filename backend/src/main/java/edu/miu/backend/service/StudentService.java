package edu.miu.backend.service;

import edu.miu.backend.dto.requestDto.StudentDto;
import edu.miu.backend.dto.requestDto.StudentFilterQuery;
import edu.miu.backend.dto.requestDto.UserDto;
import edu.miu.backend.dto.responseDto.StudentResponseDto;
import edu.miu.backend.dto.responseDto.StudentsByCityResponse;
import edu.miu.backend.dto.responseDto.StudentsByStateResponse;
import edu.miu.backend.dto.responseDto.UserResponseDto;
import edu.miu.backend.entity.Student;

import java.util.List;

public interface StudentService {

    StudentResponseDto findByUsername(String username);

    List<StudentResponseDto> findAll();

    Student findById(int id);

    UserResponseDto save(UserDto userDto) throws Exception;

    void delete(int id);

    StudentResponseDto update(StudentDto studentDto, String username) throws Exception;

    void deleteByUsername(String username);

    List<StudentResponseDto> filterStudentsByQuery(StudentFilterQuery filterQuery);

    List<StudentsByCityResponse> findStudentsCountByCity();

    List<StudentsByStateResponse> findStudentsCountByState();
}
