package com.kyubi.digital.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FigureMapperTest {

    private FigureMapper figureMapper;

    @BeforeEach
    public void setUp() {
        figureMapper = new FigureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(figureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(figureMapper.fromId(null)).isNull();
    }
}
