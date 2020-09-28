package com.baufest.tennis.springtennis.service;

import com.baufest.tennis.springtennis.dto.CanchaDTO;
import com.baufest.tennis.springtennis.mapper.CanchaMapper;
import com.baufest.tennis.springtennis.model.Cancha;
import com.baufest.tennis.springtennis.repository.CanchaRepository;
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
class CanchaServiceTest {
	private final List<Cancha> canchasDePrueba = new ArrayList<>();
	private final List<CanchaDTO> canchasDePruebaDTO = new ArrayList<>();

	private final CanchaDTO canchaParaAgregar = new CanchaDTO();

	@InjectMocks
	CanchaServiceImpl canchaService;

	@Mock
	CanchaRepository canchaRepository;

	@Spy
	CanchaMapper mapper = CanchaMapper.INSTANCE;

	@BeforeEach
	public void setUp() {
		canchasDePrueba.clear();
		canchasDePrueba.add(new Cancha());
		canchasDePrueba.add(new Cancha());
		canchasDePrueba.add(new Cancha());
		canchasDePrueba.add(new Cancha());

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

		canchasDePruebaDTO.clear();
		canchasDePruebaDTO.add(new CanchaDTO());
		canchasDePruebaDTO.add(new CanchaDTO());
		canchasDePruebaDTO.add(new CanchaDTO());
		canchasDePruebaDTO.add(new CanchaDTO());

		canchasDePruebaDTO.get(0).setNombre("cancha 1");
		canchasDePruebaDTO.get(1).setNombre("cancha 2");
		canchasDePruebaDTO.get(2).setNombre("cancha 3");
		canchasDePruebaDTO.get(3).setNombre("cancha 4");
		canchasDePruebaDTO.get(0).setId(1L);
		canchasDePruebaDTO.get(1).setId(2L);
		canchasDePruebaDTO.get(2).setId(3L);
		canchasDePruebaDTO.get(3).setId(4L);
		canchasDePruebaDTO.get(0).setDireccion("av random 123");
		canchasDePruebaDTO.get(1).setDireccion("calle rgn 321");
		canchasDePruebaDTO.get(2).setDireccion("calle inventada 777");
		canchasDePruebaDTO.get(3).setDireccion("av siempreviva 156");

		canchaParaAgregar.setId(5L);
		canchaParaAgregar.setNombre("cancha 5");
		canchaParaAgregar.setDireccion("calle sin nombre magica");
	}

	@Test
	void testListCanchas() {
		when(canchaRepository.findAll()).thenReturn(canchasDePrueba);

		List<CanchaDTO> canchasConseguidos = canchaService.listAll();
		assertEquals(canchasDePruebaDTO.toString(), canchasConseguidos.toString());
	}

	@Test
	void testGetCanchaByID() {
		when(canchaRepository.findById(canchasDePrueba.get(0).getId()))
				.thenReturn(Optional.of(canchasDePrueba.get(0)));
		CanchaDTO canchaEncontrado = canchaService.getById(canchasDePrueba.get(0).getId());
		assertEquals(canchasDePruebaDTO.get(0).getId(), canchaEncontrado.getId());
		assertEquals(canchasDePruebaDTO.get(0).getDireccion(), canchaEncontrado.getDireccion());
		assertEquals(canchasDePruebaDTO.get(0).getNombre(), canchaEncontrado.getNombre());
	}

	@Test
	void testSaveOrUpdate() {
		canchaService.save(canchaParaAgregar);
		ArgumentCaptor<Cancha> argumentCaptor = ArgumentCaptor.forClass(Cancha.class);
		verify(canchaRepository).save(argumentCaptor.capture());
		assertEquals(canchaParaAgregar.getId(),argumentCaptor.getValue().getId());
		assertEquals(canchaParaAgregar.getDireccion(),argumentCaptor.getValue().getDireccion());
		assertEquals(canchaParaAgregar.getNombre(),argumentCaptor.getValue().getNombre());
	}

	@Test
	void testDelete() {
		Long idParaBorrar = 1L;
		when(canchaRepository.existsById(1L)).thenReturn(true);
		canchaService.delete(idParaBorrar);
		ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
		verify(canchaRepository).deleteById(argumentCaptor.capture());
		assertEquals(idParaBorrar, argumentCaptor.getValue());
	}

	@Test
	void testDeleteNotFound() {
		Long idParaBorrar = 1L;
		when(canchaRepository.existsById(1L)).thenReturn(false);
		assertThrows(NoSuchElementException.class, () -> canchaService.delete(idParaBorrar));
	}
	@Test
	void testInsertExisting() {
		when(canchaRepository.existsById(canchaParaAgregar.getId())).thenReturn(true);
		assertThrows(IllegalArgumentException.class, () ->  canchaService.save(canchaParaAgregar));
	}
	@Test
	void testUpdateExisting() {
		when(canchaRepository.existsById(canchaParaAgregar.getId())).thenReturn(true);
		canchaService.update(canchaParaAgregar);
		ArgumentCaptor<Cancha> argumentCaptor = ArgumentCaptor.forClass(Cancha.class);
		verify(canchaRepository).save(argumentCaptor.capture());
		assertEquals(canchaParaAgregar.getId(),argumentCaptor.getValue().getId());
		assertEquals(canchaParaAgregar.getDireccion(),argumentCaptor.getValue().getDireccion());
		assertEquals(canchaParaAgregar.getNombre(),argumentCaptor.getValue().getNombre());
	}
	@Test
	void testUpdateNotFound() {
		when(canchaRepository.existsById(canchaParaAgregar.getId())).thenReturn(false);
		assertThrows(NoSuchElementException.class, () ->  canchaService.update(canchaParaAgregar));
	}
}