package com.kyubi.digital.service.mapper;


import com.kyubi.digital.domain.*;
import com.kyubi.digital.service.dto.FigureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Figure} and its DTO {@link FigureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface FigureMapper extends EntityMapper<FigureDTO, Figure> {

    @Mapping(source = "candidate.id", target = "candidateId")
    FigureDTO toDto(Figure figure);

    @Mapping(source = "candidateId", target = "candidate")
    Figure toEntity(FigureDTO figureDTO);

    default Figure fromId(Long id) {
        if (id == null) {
            return null;
        }
        Figure figure = new Figure();
        figure.setId(id);
        return figure;
    }
}
