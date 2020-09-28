package com.baufest.tennis.springtennis.controller;

import com.baufest.tennis.springtennis.dto.CanchaDTO;
import com.baufest.tennis.springtennis.service.CanchaServiceImpl;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(CanchaController.class)
class CanchaControllerTest {
	String basePath = "/springtennis/api/v1/canchas/";
	List<CanchaDTO> canchasDePrueba = new ArrayList<>();
	JSONArray canchasDePruebaEnJSON = new JSONArray();
	CanchaDTO canchaParaAgregar = new CanchaDTO();

	@Autowired
	MockMvc mockMvc;

	@Autowired
	CanchaController canchaController;

	@MockBean
	CanchaServiceImpl canchaService;

	@BeforeEach
	public void setUp() {
		canchasDePrueba.clear();
		canchasDePrueba.add(new CanchaDTO());
		canchasDePrueba.add(new CanchaDTO());
		canchasDePrueba.add(new CanchaDTO());
		canchasDePrueba.add(new CanchaDTO());

		canchasDePrueba.get(0).setNombre("cancha 1");
		canchasDePrueba.get(1).setNombre("cancha 2");
		canchasDePrueba.get(2).setNombre("cancha 3");
		canchasDePrueba.get(3).setNombre("cancha 4");
		canchasDePrueba.get(0).setId(1L);
		canchasDePrueba.get(1).setId(2L);
		canchasDePrueba.get(2).setId(3L);
		canchasDePrueba.get(3).setId(4L);
		canchasDePrueba.get(0).setDireccion("av random 123");
		canchasDePrueba.get(1).setDireccion("calle rgn 321");
		canchasDePrueba.get(2).setDireccion("calle inventada 777");
		canchasDePrueba.get(3).setDireccion("av siempreviva 156");

		canchaParaAgregar.setId(5L);
		canchaParaAgregar.setNombre("cancha 5");
		canchaParaAgregar.setDireccion("calle sin nombre magica");

		canchasDePrueba.forEach((x) -> canchasDePruebaEnJSON.put(x.toJSONObject()));

	}

	@Test
	void testListAll() throws Exception {
		when(canchaService.listAll()).thenReturn(canchasDePrueba);

		mockMvc.perform(MockMvcRequestBuilders.get(basePath).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(canchasDePruebaEnJSON.toString()));
	}

	@Test
	void testGetByID() throws Exception {
		long idCanchaGet = 1L;
		when(canchaService.getById(1L)).thenReturn(canchasDePrueba.get(0));

		mockMvc.perform(MockMvcRequestBuilders.get(basePath + idCanchaGet).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(canchasDePrueba.get(0)
						.toJSONObject().toString()));
	}

	@Test
	void testSaveCancha() throws Exception {
		when(canchaService.save(any())).thenReturn(new CanchaDTO());

		mockMvc.perform(MockMvcRequestBuilders.post(basePath).contentType(MediaType.APPLICATION_JSON)
				.content(canchaParaAgregar.toJSONObject().toString()))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		ArgumentCaptor<CanchaDTO> argumentCaptor = ArgumentCaptor.forClass(CanchaDTO.class);
		verify(canchaService).save(argumentCaptor.capture());
		assertEquals(canchaParaAgregar.getNombre(), argumentCaptor.getValue().getNombre());
	}


	@Test
	void testUpdateCancha() throws Exception {
		when(canchaService.update(any())).thenReturn(new CanchaDTO());

		mockMvc.perform(MockMvcRequestBuilders.put(basePath+ canchasDePrueba.get(0).getId()).contentType(MediaType.APPLICATION_JSON)
				.content(canchaParaAgregar.toJSONObject().toString()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		ArgumentCaptor<CanchaDTO> argumentCaptor = ArgumentCaptor.forClass(CanchaDTO.class);
		verify(canchaService).update(argumentCaptor.capture());
		assertEquals(canchasDePrueba.get(0).getId(), argumentCaptor.getValue().getId());
		assertEquals(canchaParaAgregar.getNombre(), argumentCaptor.getValue().getNombre());
	}

	@Test
	void testDeleteCancha() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(basePath+ canchasDePrueba.get(0).getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
		verify(canchaService).delete(argumentCaptor.capture());
		assertEquals(canchasDePrueba.get(0).getId(), argumentCaptor.getValue());
	}
}
