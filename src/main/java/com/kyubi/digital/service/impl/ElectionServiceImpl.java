package com.kyubi.digital.service.impl;

import com.kyubi.digital.service.ElectionService;
import com.kyubi.digital.domain.Election;
import com.kyubi.digital.repository.ElectionRepository;
import com.kyubi.digital.service.dto.ElectionDTO;
import com.kyubi.digital.service.mapper.ElectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Election}.
 */
@Service
@Transactional
public class ElectionServiceImpl implements ElectionService {

    private final Logger log = LoggerFactory.getLogger(ElectionServiceImpl.class);

    private final ElectionRepository electionRepository;

    private final ElectionMapper electionMapper;

    public ElectionServiceImpl(ElectionRepository electionRepository, ElectionMapper electionMapper) {
        this.electionRepository = electionRepository;
        this.electionMapper = electionMapper;
    }

    @Override
    public ElectionDTO save(ElectionDTO electionDTO) {
        log.debug("Request to save Election : {}", electionDTO);
        Election election = electionMapper.toEntity(electionDTO);
        election = electionRepository.save(election);
        return electionMapper.toDto(election);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ElectionDTO> findAll() {
        log.debug("Request to get all Elections");
        return electionRepository.findAll().stream()
            .map(electionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ElectionDTO> findOne(Long id) {
        log.debug("Request to get Election : {}", id);
        return electionRepository.findById(id)
            .map(electionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Election : {}", id);
        electionRepository.deleteById(id);
    }
}
