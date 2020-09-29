package com.baufest.tennis.springtennis.service;

import com.baufest.tennis.springtennis.dto.CanchaDTO;
import com.baufest.tennis.springtennis.dto.EntrenadorDTO;
import com.baufest.tennis.springtennis.dto.PartidoDTO;
import com.baufest.tennis.springtennis.mapper.EntrenadorMapper;
import com.baufest.tennis.springtennis.model.Cancha;
import com.baufest.tennis.springtennis.model.Entrenador;
import com.baufest.tennis.springtennis.repository.EntrenadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EntrenadorServiceTest {

	private final List<Entrenador> entrenadoresDePrueba = new ArrayList<>();
	private final List<EntrenadorDTO> entrenadoresDePruebaDTO = new ArrayList<>();

	private final EntrenadorDTO entrenadorAAgregar = new EntrenadorDTO();

	@InjectMocks
	EntrenadorServiceImpl entrenadorService;

	@Mock
	EntrenadorRepository entrenadorRepository;

	@Spy
	EntrenadorMapper mapper = EntrenadorMapper.INSTANCE;

	@BeforeEach
	public void setUp() {
		entrenadoresDePrueba.clear();
		entrenadoresDePrueba.add(new Entrenador());
		entrenadoresDePrueba.add(new Entrenador());
		entrenadoresDePrueba.add(new Entrenador());
		entrenadoresDePrueba.add(new Entrenador());

		entrenadoresDePrueba.get(0).setId(1L);
		entrenadoresDePrueba.get(1).setId(2L);
		entrenadoresDePrueba.get(2).setId(3L);
		entrenadoresDePrueba.get(3).setId(4L);
		entrenadoresDePrueba.get(0).setNombre("entrenador 1");
		entrenadoresDePrueba.get(1).setNombre("entrenador 2");
		entrenadoresDePrueba.get(2).setNombre("entrenador 3");
		entrenadoresDePrueba.get(3).setNombre("entrenador 4");


		entrenadorAAgregar.setId(5L);
		entrenadorAAgregar.setNombre("cancha 5");
	}


	@Test
	void testCrearYGuardarOUpdate() {
		entrenadorService.save(entrenadorAAgregar);
		ArgumentCaptor<Entrenador> argumentCaptor = ArgumentCaptor.forClass(Entrenador.class);
		verify(entrenadorRepository).save(argumentCaptor.capture());
		assertEquals(entrenadorAAgregar.getId(),argumentCaptor.getValue().getId());
		assertEquals(entrenadorAAgregar.getNombre(),argumentCaptor.getValue().getNombre());
	}

	@Test
	void testListEntrenadores() {
		when(entrenadorRepository.findAll()).thenReturn(entrenadoresDePrueba);

		List<EntrenadorDTO> entrenadoresConseguidos = entrenadorService.listAll();

		assertEquals(entrenadoresDePrueba.size(), entrenadoresConseguidos.size());
		assertEquals(entrenadoresDePrueba.get(0).getId(), entrenadoresConseguidos.get(0).getId());
		assertEquals(entrenadoresDePrueba.get(0).getNombre(), entrenadoresConseguidos.get(0).getNombre());
		assertEquals(entrenadoresDePrueba.get(1).getId(), entrenadoresConseguidos.get(1).getId());
		assertEquals(entrenadoresDePrueba.get(1).getNombre(), entrenadoresConseguidos.get(1).getNombre());
		assertEquals(entrenadoresDePrueba.get(2).getId(), entrenadoresConseguidos.get(2).getId());
		assertEquals(entrenadoresDePrueba.get(2).getNombre(), entrenadoresConseguidos.get(2).getNombre());
		assertEquals(entrenadoresDePrueba.get(3).getId(), entrenadoresConseguidos.get(3).getId());
		assertEquals(entrenadoresDePrueba.get(3).getNombre(), entrenadoresConseguidos.get(3).getNombre());
	}

}
