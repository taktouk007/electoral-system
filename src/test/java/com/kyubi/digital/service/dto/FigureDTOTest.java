package com.kyubi.digital.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kyubi.digital.web.rest.TestUtil;

public class FigureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FigureDTO.class);
        FigureDTO figureDTO1 = new FigureDTO();
        figureDTO1.setId(1L);
        FigureDTO figureDTO2 = new FigureDTO();
        assertThat(figureDTO1).isNotEqualTo(figureDTO2);
        figureDTO2.setId(figureDTO1.getId());
        assertThat(figureDTO1).isEqualTo(figureDTO2);
        figureDTO2.setId(2L);
        assertThat(figureDTO1).isNotEqualTo(figureDTO2);
        figureDTO1.setId(null);
        assertThat(figureDTO1).isNotEqualTo(figureDTO2);
    }
}
