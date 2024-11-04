package tn.esprit.spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tn.esprit.spring.dtos.DepartementDTO;
import tn.esprit.spring.mapper.DepartementMapper;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.services.DepartementService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartementControllorTest {
    @Mock
    private DepartementMapper departementMapper;
    @Mock
    private DepartementService departementService;

    @InjectMocks
    private DepartementControllor departementController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDepartements() {
        Departement departement1 = new Departement();
        Departement departement2 = new Departement();
        when(departementService.findAll()).thenReturn(Arrays.asList(departement1, departement2));

        List<Departement> result = departementController.getAllDepartements();

        assertEquals(2, result.size());
        verify(departementService, times(1)).findAll();
    }

    @Test
    void testGetDepartementById() {
        Departement departement = new Departement();
        when(departementService.findById(1)).thenReturn(departement);

        ResponseEntity<Departement> response = departementController.getDepartementById(1);

        assertNotNull(response.getBody());
        assertEquals(departement, response.getBody());
        verify(departementService, times(1)).findById(1);
    }

    @Test
    void testCreateDepartement() {
        DepartementDTO departementDTO = new DepartementDTO();
        Departement departement = new Departement();

        // Mock the mapper to convert DepartementDTO to Departement

        when(departementMapper.convertToEntity(departementDTO)).thenReturn(departement);
        when(departementService.save(departement)).thenReturn(departement);

        Departement result = departementController.createDepartement(departementDTO);

        assertNotNull(result);
        assertEquals(departement, result);
        verify(departementMapper, times(1)).convertToEntity(departementDTO);
        verify(departementService, times(1)).save(departement);
    }

    @Test
    void testUpdateDepartement() {
        int id = 1;
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setId(id);
        Departement departement = new Departement();

        // Mock the mapper to convert DepartementDTO to Departement
        when(departementMapper.convertToEntity(departementDTO)).thenReturn(departement);
        when(departementService.update(departement)).thenReturn(departement);

        ResponseEntity<Departement> response = departementController.updateDepartement(id, departementDTO);

        assertNotNull(response.getBody());
        assertEquals(departement, response.getBody());
        verify(departementMapper, times(1)).convertToEntity(departementDTO);
        verify(departementService, times(1)).update(departement);
    }
    @Test
    void testDeleteDepartement() {
        doNothing().when(departementService).delete(1);

        ResponseEntity<Void> response = departementController.deleteDepartement(1);

        assertEquals(204, response.getStatusCodeValue());
        verify(departementService, times(1)).delete(1);
    }
}
