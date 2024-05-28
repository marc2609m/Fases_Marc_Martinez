package com.example.Fases_MarcMartinez;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.config.RespuestaError;
import com.example.controller.TapaController;
import com.example.model.Tapa;
import com.example.services.TapaServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers=TapaController.class)
public class TapaControllerTest {
	
	@Autowired
	private MockMvc miniPostman;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private TapaServices tapaServices;
	
	private Tapa t1;
	private Tapa t2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void pedimos_todos_los_productos() throws Exception {
		
		// Arrange
		
		List<Tapa> productos = Arrays.asList(t1, t2);
		when(tapaServices.getAll()).thenReturn(productos);
		
		// Act
		
		MvcResult respuesta = miniPostman.perform(get("/productos").contentType("application/json"))
											.andExpect(status().isOk())
											.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(productos);
		
		// Assert
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
		
	}
	
	@Test
	void pedimos_todos_los_productos_entre_rango_precios() throws Exception {
				
		List<Tapa> productos = Arrays.asList(t1, t2);
		when(tapaServices.getBetweenPriceRange(10, 500)).thenReturn(productos);
			
		MvcResult respuesta = miniPostman.perform(get("/productos").param("min", "10")
																   .param("max","500")
																   .contentType("application/json"))
											.andExpect(status().isOk())
											.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(productos);
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
		
	}
	
	@Test
	void obtenemos_producto_a_partir_de_su_id() throws Exception{
		
		when(tapaServices.read(100L)).thenReturn(Optional.of(t1));
		
		MvcResult respuesta = miniPostman.perform(get("/productos/100").contentType("application/json"))
									.andExpect(status().isOk())
									.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(t1);
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
	
	}
	
	@Test
	void solicitamos_producto_a_partir_de_un_id_inexistente() throws Exception {
		
		when(tapaServices.read(100L)).thenReturn(Optional.empty());
		
		MvcResult respuesta = miniPostman.perform(get("/productos/100").contentType("application/json"))
									.andExpect(status().isNotFound())
									.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(new RespuestaError("No se encuentra el producto con id 100"));
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
	}
	
	@Test
	void crea_producto_ok() throws Exception {
		
		t1.setId(null);
		
		when(tapaServices.create(t1)).thenReturn(1033L);
		
		String requestBody = objectMapper.writeValueAsString(t1);
		
		miniPostman.perform(post("/productos").content(requestBody).contentType("application/json"))
						.andExpect(status().isCreated())
						.andExpect(header().string("Location","http://localhost/productos/1033"));
	}
	
	@Test
	void crear_producto_con_id_NO_NULL() throws Exception{
		
		when(tapaServices.create(t1)).thenThrow(new IllegalStateException("Problema con el id..."));
		
		String requestBody = objectMapper.writeValueAsString(t1);
		
		MvcResult respuesta = miniPostman.perform(post("/productos").content(requestBody).contentType("application/json"))
						.andExpect(status().isBadRequest())
						.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(new RespuestaError("Problema con el id..."));
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
	}
	
	@Test
	void eliminamos_producto_ok() throws Exception{
		
		miniPostman.perform(delete("/productos/789")).andExpect(status().isNoContent());
		
		verify(tapaServices, times(1)).delete(789L);
	}
	
	@Test
	void eliminamos_producto_no_existente() throws Exception{
		
		Mockito.doThrow(new IllegalStateException("xxxx")).when(tapaServices).delete(789L);
		
		MvcResult respuesta = miniPostman.perform(delete("/productos/789"))
								.andExpect(status().isNotFound())
								.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(new RespuestaError("No se encuentra el producto con id [789]. No se ha podido eliminar."));
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
		
	}
	
	// **************************************************************
	//
	// Private Methods
	//
	// **************************************************************
	
	private void initObjects() {
		
		t1 = new Tapa();
		t1.setId(40L);
		t1.setDescripcion("Des1");
		t1.setEnCarta(true);
		t1.setPrecio(4.5);
		t1.setTipo("Carne");
		
		t2 = new Tapa();
		t2.setId(50L);
		t2.setDescripcion("Des2");
		t2.setEnCarta(false);
		t2.setPrecio(2.5);
		t2.setTipo("Pescado");
		
	}
	
}
