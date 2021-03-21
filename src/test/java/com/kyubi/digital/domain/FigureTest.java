package com.kyubi.digital.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kyubi.digital.web.rest.TestUtil;

public class FigureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Figure.class);
        Figure figure1 = new Figure();
        figure1.setId(1L);
        Figure figure2 = new Figure();
        figure2.setId(figure1.getId());
        assertThat(figure1).isEqualTo(figure2);
        figure2.setId(2L);
        assertThat(figure1).isNotEqualTo(figure2);
        figure1.setId(null);
        assertThat(figure1).isNotEqualTo(figure2);
    }
}
