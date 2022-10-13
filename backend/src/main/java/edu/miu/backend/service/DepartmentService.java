package edu.miu.backend.service;

import edu.miu.backend.dto.requestDto.DepartmentDto;
import edu.miu.backend.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();

    Department findById(int id);

    Department save(Department department);

    void delete(int id);

    Department update(DepartmentDto departmentDto, int id) throws Exception;
}
