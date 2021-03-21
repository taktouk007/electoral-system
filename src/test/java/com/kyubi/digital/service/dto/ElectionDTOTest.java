package com.kyubi.digital.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kyubi.digital.web.rest.TestUtil;

public class ElectionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElectionDTO.class);
        ElectionDTO electionDTO1 = new ElectionDTO();
        electionDTO1.setId(1L);
        ElectionDTO electionDTO2 = new ElectionDTO();
        assertThat(electionDTO1).isNotEqualTo(electionDTO2);
        electionDTO2.setId(electionDTO1.getId());
        assertThat(electionDTO1).isEqualTo(electionDTO2);
        electionDTO2.setId(2L);
        assertThat(electionDTO1).isNotEqualTo(electionDTO2);
        electionDTO1.setId(null);
        assertThat(electionDTO1).isNotEqualTo(electionDTO2);
    }
}
