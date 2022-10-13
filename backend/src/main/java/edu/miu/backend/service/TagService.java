package edu.miu.backend.service;



import edu.miu.backend.dto.requestDto.TagDto;

import java.util.List;

public interface TagService {
    List<TagDto> findAll();

    List<TagDto> searchAllByName(String searchKey);

    TagDto findById(int id);

    TagDto save(TagDto tagDto);

    void delete(int id);

    TagDto update(TagDto tagDto, int id) throws Exception;
}
