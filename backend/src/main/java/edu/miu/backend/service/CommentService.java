package edu.miu.backend.service;


import edu.miu.backend.dto.requestDto.CommentDto;
import edu.miu.backend.dto.responseDto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    List<CommentResponseDto> findAll();

    CommentResponseDto findById(int id);

    CommentResponseDto save(CommentDto commentDto);

    void delete(int id);

    CommentResponseDto update(CommentDto commentDto, int id) throws Exception;

    List<CommentResponseDto> findByUsername(String username);
}
