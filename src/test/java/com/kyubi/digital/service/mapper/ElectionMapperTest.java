package com.kyubi.digital.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ElectionMapperTest {

    private ElectionMapper electionMapper;

    @BeforeEach
    public void setUp() {
        electionMapper = new ElectionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(electionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(electionMapper.fromId(null)).isNull();
    }
}
