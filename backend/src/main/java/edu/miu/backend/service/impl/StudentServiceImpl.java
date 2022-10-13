package edu.miu.backend.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.backend.dto.requestDto.*;
import edu.miu.backend.dto.responseDto.StudentResponseDto;
import edu.miu.backend.dto.responseDto.StudentsByCityResponse;
import edu.miu.backend.dto.responseDto.StudentsByStateResponse;
import edu.miu.backend.dto.responseDto.UserResponseDto;
import edu.miu.backend.entity.*;
import edu.miu.backend.enums.S3BucketName;
import edu.miu.backend.repository.DepartmentRepository;
import edu.miu.backend.repository.StudentRepository;
import edu.miu.backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;

    private final DepartmentRepository departmentRepo;

    private final ModelMapper modelMapper;

    private final ObjectMapper objectMapper;

    private final AmazonS3 amazonS3Client;

    private String AWS_URL = String.format("https://%s.s3.amazonaws.com/",
            S3BucketName.STUDENT_BUCKET.getS3BucketName());

    @Override
    public StudentResponseDto findByUsername(String username) {
        Student student = studentRepo.findByUsername(username).orElse(null);
        if (student == null)
            return null;

        StudentResponseDto studentResponseDto = modelMapper.map(student, StudentResponseDto.class);
        if (student.getAddress() != null) {
            studentResponseDto.setAddress(modelMapper.map(student.getAddress(), AddressDto.class));
        }
        return studentResponseDto;
    }

    @Override
    public List<StudentResponseDto> findAll() {
        List<Student> students = studentRepo.findAll();
        return students.stream()
                .map(student -> {
                    StudentResponseDto studentResponseDto = modelMapper.map(student, StudentResponseDto.class);
                    if (student.getAddress() != null) {
                        studentResponseDto.setAddress(modelMapper.map(student.getAddress(), AddressDto.class));
                    }
                    return studentResponseDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Student findById(int id) {
        return studentRepo.findById(id).orElse(null);
    }

    @Override
    public UserResponseDto save(UserDto userDto) throws Exception {

        if (studentRepo.findByUsername(userDto.getUsername()).isPresent()) {
            throw new Exception("User already exists");
        }
        Student student = modelMapper.map(userDto, Student.class);

        UserResponseDto studentResponseDto = modelMapper.map(studentRepo.save(student), UserResponseDto.class);

        return studentResponseDto;
    }

    @Override
    public void delete(int id) {
        studentRepo.deleteById(id);
    }

    @Override
    public StudentResponseDto update(StudentDto studentDto, String username) throws Exception {

        Student student = studentRepo.findByUsername(username).orElseThrow(() -> new Exception("Student not found!"));
        Student mappedStudent = modelMapper.map(studentDto, Student.class);
        Department department = departmentRepo.findById(studentDto.getDepartmentId())
                .orElseThrow(() -> new Exception("Department not found!"));

        student.setGpa(mappedStudent.getGpa());
        student.setMajor(department);
        AddressDto address = null;
        try {
            address = objectMapper.readValue(studentDto.getAddress(), AddressDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        student.setAddress(modelMapper.map(address, Address.class));

        if (studentDto.getFile() != null) {
            String url = uploadFileToAWSAndGetUrl(studentDto.getFile());
            student.setCv(url);
        }

        StudentResponseDto studentResponseDto = modelMapper.map(student, StudentResponseDto.class);
        studentResponseDto.setAddress(address);
        return studentResponseDto;
    }

    @Override
    public void deleteByUsername(String username) {
        studentRepo.deleteByUsername(username);
    }

    @Override
    public List<StudentResponseDto> filterStudentsByQuery(StudentFilterQuery filterQuery) {
        List<Student> students = studentRepo.findAll();
        String city = filterQuery.getCity();
        String state = filterQuery.getState();
        String major = filterQuery.getMajor();
        String name = filterQuery.getName();

        if (city != null) {
            students = filterStudentsByCity(students, city);
        }

        if (state != null) {
            students = filterStudentsByState(students, state);
        }

        if (major != null) {
            students = filterStudentsByMajor(students, major);
        }

        if(name != null) {
            students = filterStudentsByName(students, name);
        }
        return students.stream()
                .map(student -> {
                    StudentResponseDto studentResponseDto = modelMapper.map(student, StudentResponseDto.class);
                    if (student.getAddress() != null) {
                        studentResponseDto.setAddress(modelMapper.map(student.getAddress(), AddressDto.class));
                    }
                    return studentResponseDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentsByCityResponse> findStudentsCountByCity() {
        HashMap<String, Integer> studentsByCityMap = new HashMap<>();
        List<Student> students = studentRepo.findAll();

        for(Student student: students) {
            String city = student.getAddress().getCity();
            studentsByCityMap.put(city, studentsByCityMap.getOrDefault(city, 0) + 1 );
        }

        List<StudentsByCityResponse> result = new ArrayList<>();
        for(String city: studentsByCityMap.keySet()) {
            result.add(new StudentsByCityResponse(city, studentsByCityMap.get(city)));
        }

        return result;
    }

    @Override
    public List<StudentsByStateResponse> findStudentsCountByState() {
        HashMap<String, Integer> studentsByStateMap = new HashMap<>();
        List<Student> students = studentRepo.findAll();
        System.out.println(students);
        for(Student student: students) {
            String state = student.getAddress().getState();
            studentsByStateMap.put(state, studentsByStateMap.getOrDefault(state, 0) + 1 );
        }

        List<StudentsByStateResponse> result = new ArrayList<>();
        for(String state: studentsByStateMap.keySet()) {
            result.add(new StudentsByStateResponse(state, studentsByStateMap.get(state)));
        }

        return result;
    }

    private List<Student> filterStudentsByCity(List<Student> students, String city) {
        return students.stream()
                .filter(student -> student.getAddress().getCity().equals(city))
                .collect(Collectors.toList());
    }

    private List<Student> filterStudentsByState(List<Student> students, String state) {
        return students.stream()
                .filter(student -> student.getAddress().getState().equals(state))
                .collect(Collectors.toList());
    }

    private List<Student> filterStudentsByMajor(List<Student> students, String major) {
        return students.stream()
                .filter(student -> student.getMajor().getName().equals(major))
                .collect(Collectors.toList());
    }

    private List<Student> filterStudentsByName(List<Student> students, String name) {
        return students.stream()
                .filter(student -> student.getFirstName().contains(name) || student.getLastName().contains(name))
                .collect(Collectors.toList());
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private String uploadFileToAWSAndGetUrl(MultipartFile multipartFile) {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        String keyName = generateFileName(multipartFile);
        try {
            amazonS3Client
                    .putObject(S3BucketName.STUDENT_BUCKET.getS3BucketName(), keyName, multipartFile.getInputStream(),
                            metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AWS_URL.concat(keyName);
    }

}
