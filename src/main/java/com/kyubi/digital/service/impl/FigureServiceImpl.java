package com.kyubi.digital.service.impl;

import com.kyubi.digital.service.FigureService;
import com.kyubi.digital.domain.Figure;
import com.kyubi.digital.repository.FigureRepository;
import com.kyubi.digital.service.dto.FigureDTO;
import com.kyubi.digital.service.mapper.FigureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Figure}.
 */
@Service
@Transactional
public class FigureServiceImpl implements FigureService {

    private final Logger log = LoggerFactory.getLogger(FigureServiceImpl.class);

    private final FigureRepository figureRepository;

    private final FigureMapper figureMapper;

    public FigureServiceImpl(FigureRepository figureRepository, FigureMapper figureMapper) {
        this.figureRepository = figureRepository;
        this.figureMapper = figureMapper;
    }

    @Override
    public FigureDTO save(FigureDTO figureDTO) {
        log.debug("Request to save Figure : {}", figureDTO);
        Figure figure = figureMapper.toEntity(figureDTO);
        figure = figureRepository.save(figure);
        return figureMapper.toDto(figure);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FigureDTO> findAll() {
        log.debug("Request to get all Figures");
        return figureRepository.findAll().stream()
            .map(figureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<FigureDTO> findOne(Long id) {
        log.debug("Request to get Figure : {}", id);
        return figureRepository.findById(id)
            .map(figureMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Figure : {}", id);
        figureRepository.deleteById(id);
    }
}
