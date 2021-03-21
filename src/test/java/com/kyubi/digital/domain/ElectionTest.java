package com.kyubi.digital.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kyubi.digital.web.rest.TestUtil;

public class ElectionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Election.class);
        Election election1 = new Election();
        election1.setId(1L);
        Election election2 = new Election();
        election2.setId(election1.getId());
        assertThat(election1).isEqualTo(election2);
        election2.setId(2L);
        assertThat(election1).isNotEqualTo(election2);
        election1.setId(null);
        assertThat(election1).isNotEqualTo(election2);
    }
}
