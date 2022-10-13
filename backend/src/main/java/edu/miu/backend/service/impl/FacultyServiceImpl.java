package edu.miu.backend.service.impl;

import edu.miu.backend.dto.requestDto.AddressDto;
import edu.miu.backend.dto.requestDto.FacultyDto;
import edu.miu.backend.dto.requestDto.UserDto;
import edu.miu.backend.dto.responseDto.FacultyResponseDto;
import edu.miu.backend.dto.responseDto.UserResponseDto;
import edu.miu.backend.entity.Department;
import edu.miu.backend.entity.Faculty;
import edu.miu.backend.repository.DepartmentRepository;
import edu.miu.backend.repository.FacultyRepository;
import edu.miu.backend.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepo;

    private final DepartmentRepository departmentRepo;

    private final ModelMapper modelMapper;

    @Override
    public List<FacultyResponseDto> findAll() {
        List<Faculty> faculties = facultyRepo.findAll();
        return faculties.stream().map(faculty -> {
            FacultyResponseDto facultyResponseDto = modelMapper.map(faculty, FacultyResponseDto.class);
            if(faculty.getAddress() != null) {
                facultyResponseDto.setAddress(modelMapper.map(faculty.getAddress(), AddressDto.class));
            }
            return facultyResponseDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Faculty findById(int id) {
        return facultyRepo.findById(id).orElse(null);
    }

    @Override
    public UserResponseDto save(UserDto userDto) throws Exception {
        Optional<Faculty> facultyRef = facultyRepo.findByUsername(userDto.getUsername());
        if (facultyRef.isPresent()){
            return modelMapper.map(facultyRef, UserResponseDto.class);
        }
        Faculty faculty = modelMapper.map(userDto, Faculty.class);
        UserResponseDto facultyResponseDto = modelMapper.map(facultyRepo.save(faculty), UserResponseDto.class);
        return facultyResponseDto;
    }

    @Override
    public FacultyResponseDto update(FacultyDto facultyDto, String username) throws Exception {

        Faculty facultyMapped = modelMapper.map(facultyDto, Faculty.class);
        Department department = departmentRepo.findById(facultyDto.getDepartmentId())
                .orElseThrow(()->new Exception("Department not found!"));

        Faculty faculty = facultyRepo.findByUsername(username)
                .orElseThrow(()-> new Exception("Faculty not found!"));

        faculty.setDepartment(department);
        faculty.setAddress(facultyMapped.getAddress());

        FacultyResponseDto facultyResponseDto = modelMapper.map(facultyRepo.save(faculty), FacultyResponseDto.class);
        facultyResponseDto.setAddress(modelMapper.map(faculty.getAddress(), AddressDto.class));
        return facultyResponseDto;
    }

    @Override
    public void delete(int id) {
        facultyRepo.deleteById(id);
    }

    @Override
    public FacultyResponseDto findByUsername(String username) {
        Faculty faculty = facultyRepo.findByUsername(username).orElse(null);
        if (faculty == null) return null;

        FacultyResponseDto facultyResponseDto = modelMapper.map(faculty, FacultyResponseDto.class);
        if(faculty.getAddress() != null) {
            facultyResponseDto.setAddress(modelMapper.map(faculty.getAddress(), AddressDto.class));
        }
        return facultyResponseDto;
    }

    @Override
    public void deleteByUsername(String username) {
        facultyRepo.deleteByUsername(username);
    }
}
