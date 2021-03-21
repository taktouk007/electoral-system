package com.kyubi.digital.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserAppMapperTest {

    private UserAppMapper userAppMapper;

    @BeforeEach
    public void setUp() {
        userAppMapper = new UserAppMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userAppMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userAppMapper.fromId(null)).isNull();
    }
}
