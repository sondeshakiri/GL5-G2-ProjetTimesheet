package tn.esprit.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.services.DepartementServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartementServiceImplTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Departement departement1 = new Departement();
        Departement departement2 = new Departement();
        when(departementRepository.findAll()).thenReturn(Arrays.asList(departement1, departement2));

        List<Departement> result = departementService.findAll();

        assertEquals(2, result.size());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void testFindAll_EmptyList() {
        when(departementRepository.findAll()).thenReturn(Arrays.asList());

        List<Departement> result = departementService.findAll();

        assertTrue(result.isEmpty());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        Departement departement = new Departement();
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        Departement result = departementService.findById(1);

        assertNotNull(result);
        verify(departementRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_NotFound() {
        when(departementRepository.findById(1)).thenReturn(Optional.empty());

        Departement result = departementService.findById(1);

        assertNull(result);
        verify(departementRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        Departement departement = new Departement();
        when(departementRepository.save(departement)).thenReturn(departement);

        Departement result = departementService.save(departement);

        assertNotNull(result);
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    void testSave_NullDepartement() {
        Departement result = departementService.save(null);

        assertNull(result);
        verify(departementRepository, times(0)).save(any(Departement.class));
    }

    @Test
    void testUpdate_Success() {
        Departement departement = new Departement();
        when(departementRepository.save(departement)).thenReturn(departement);

        Departement result = departementService.update(departement);

        assertNotNull(result);
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    void testDelete_Success() {
        int departementId = 1;
        doNothing().when(departementRepository).deleteById(departementId);

        departementService.delete(departementId);

        verify(departementRepository, times(1)).deleteById(departementId);
    }

    @Test
    void testDelete_NotFound() {
        int departementId = 999;
        doThrow(new IllegalArgumentException("Departement not found")).when(departementRepository).deleteById(departementId);

        assertThrows(IllegalArgumentException.class, () -> departementService.delete(departementId));
        verify(departementRepository, times(1)).deleteById(departementId);
    }
}
