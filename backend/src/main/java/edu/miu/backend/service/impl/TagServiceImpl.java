package edu.miu.backend.service.impl;

import edu.miu.backend.dto.requestDto.TagDto;
import edu.miu.backend.entity.Tag;
import edu.miu.backend.repository.TagRepository;
import edu.miu.backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepo;

    private final ModelMapper modelMapper;

    @Override
    public List<TagDto> findAll() {
        List<Tag> tags = tagRepo.findAll();
        return tags.stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TagDto> searchAllByName(String searchKey) {
        List<Tag> tags = tagRepo.findAllByNameContainingIgnoreCase(searchKey).orElse(null);
        if(tags == null) return null;
        return tags.stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(int id) {
        Tag tag = tagRepo.findById(id).orElse(null);
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public TagDto save(TagDto tagDto) {
        return modelMapper.map(tagRepo.save(modelMapper.map(tagDto, Tag.class)), TagDto.class);
    }

    @Override
    public void delete(int id) {
        tagRepo.deleteById(id);
    }

    @Override
    public TagDto update(TagDto tagDto, int id) throws Exception {
        Tag tag = tagRepo.findById(id).orElseThrow(() -> new Exception("Tag not found"));
        tag.setName(tagDto.getName());

        return modelMapper.map(tagRepo.save(tag), TagDto.class);
    }
}
