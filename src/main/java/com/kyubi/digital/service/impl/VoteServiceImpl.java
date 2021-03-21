package com.kyubi.digital.service.impl;

import com.kyubi.digital.service.VoteService;
import com.kyubi.digital.domain.Vote;
import com.kyubi.digital.repository.VoteRepository;
import com.kyubi.digital.service.dto.VoteDTO;
import com.kyubi.digital.service.mapper.VoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Vote}.
 */
@Service
@Transactional
public class VoteServiceImpl implements VoteService {

    private final Logger log = LoggerFactory.getLogger(VoteServiceImpl.class);

    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;

    public VoteServiceImpl(VoteRepository voteRepository, VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
    }

    @Override
    public VoteDTO save(VoteDTO voteDTO) {
        log.debug("Request to save Vote : {}", voteDTO);
        Vote vote = voteMapper.toEntity(voteDTO);
        vote = voteRepository.save(vote);
        return voteMapper.toDto(vote);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoteDTO> findAll() {
        log.debug("Request to get all Votes");
        return voteRepository.findAll().stream()
            .map(voteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<VoteDTO> findOne(Long id) {
        log.debug("Request to get Vote : {}", id);
        return voteRepository.findById(id)
            .map(voteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vote : {}", id);
        voteRepository.deleteById(id);
    }
}
