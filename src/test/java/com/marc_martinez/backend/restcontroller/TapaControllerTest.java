package com.marc_martinez.backend.restcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marc_martinez.backend.model.Tapa;
import com.marc_martinez.backend.services.TapaServices;

@WebMvcTest(controllers=TapaController.class)
public class TapaControllerTest {
	
	@Autowired
	private MockMvc miniPostman;
	
	@MockBean
	private TapaServices tapaServices;
	
	Tapa t1, t2;
	
	@BeforeEach
	void init() {
	    initObjects();
	}

	
	private void initObjects() {
		t1 = new Tapa();
		t1.setId(20L);
		t1.setNombre("Tapa 1");
		t1.setDescripcion("Des Tapa 1");
		t1.setPrecio(2.5);
		t1.setTipo("Tipo 1");
		t1.setEnCarta(true);
		
		t2 = new Tapa();
		t2.setId(30L);
		t2.setNombre("Tapa 2");
		t2.setDescripcion("Des Tapa 2");
		t2.setPrecio(4.0);
		t2.setTipo("Tipo 2");
		t2.setEnCarta(false);
	}	
	
	@Test
    void pedimos_todas_las_tapas() throws Exception {
        // Arrange
        List<Tapa> tapas = Arrays.asList(t1, t2);
        when(tapaServices.getAll()).thenReturn(tapas);

        ObjectMapper objectMapper = new ObjectMapper();
        String respuestaEsperada = objectMapper.writeValueAsString(tapas);

        // Act
        MvcResult respuesta = miniPostman.perform(get("/tapas/tapa").contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = respuesta.getResponse().getContentAsString();

        // Assert
        assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
    }
}
