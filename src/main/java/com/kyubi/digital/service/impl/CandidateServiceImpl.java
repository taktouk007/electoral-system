package com.kyubi.digital.service.impl;

import com.kyubi.digital.service.CandidateService;
import com.kyubi.digital.domain.Candidate;
import com.kyubi.digital.repository.CandidateRepository;
import com.kyubi.digital.service.dto.CandidateDTO;
import com.kyubi.digital.service.mapper.CandidateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Candidate}.
 */
@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private final Logger log = LoggerFactory.getLogger(CandidateServiceImpl.class);

    private final CandidateRepository candidateRepository;

    private final CandidateMapper candidateMapper;

    public CandidateServiceImpl(CandidateRepository candidateRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public CandidateDTO save(CandidateDTO candidateDTO) {
        log.debug("Request to save Candidate : {}", candidateDTO);
        Candidate candidate = candidateMapper.toEntity(candidateDTO);
        candidate = candidateRepository.save(candidate);
        return candidateMapper.toDto(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateDTO> findAll() {
        log.debug("Request to get all Candidates");
        return candidateRepository.findAll().stream()
            .map(candidateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CandidateDTO> findOne(Long id) {
        log.debug("Request to get Candidate : {}", id);
        return candidateRepository.findById(id)
            .map(candidateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Candidate : {}", id);
        candidateRepository.deleteById(id);
    }
}
