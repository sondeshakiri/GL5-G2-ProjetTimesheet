package tn.esprit.spring.controller;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.services.DepartementService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DepartementControllorTest {

    @InjectMocks
    private DepartementControllor departementController;

    @Mock
    private DepartementService departementService;

    private Departement departement1;
    private Departement departement2;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
        departement1 = new Departement("Informatique");
        departement2 = new Departement("RH");
    }

    @Test
    public void testGetAllDepartements() {
        List<Departement> departements = Arrays.asList(departement1, departement2);
        when(departementService.findAll()).thenReturn(departements);

        List<Departement> result = departementController.getAllDepartements();
        assertEquals(2, result.size());
        assertEquals("Informatique", result.get(0).getName());
        assertEquals("RH", result.get(1).getName());
    }

    @Test
    public void testGetDepartementById() {
        when(departementService.findById(1)).thenReturn(departement1);

        Departement result = departementController.getDepartementById(1).getBody();
        assertNotNull(result);
        assertEquals("Informatique", result.getName());
    }


    
    @Test
    void testGetDepartementByIdNotFound() {
        // Arrange: Mock the service to return null for a non-existent department
        when(departementService.findById(99)).thenReturn(null);

        // Act: Call the GET method on the controller
        ResponseEntity<Departement> response = departementController.getDepartementById(99);

        // Assert: Check that the response status is NOT_FOUND and body is null
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateDepartement() {
        when(departementService.save(departement1)).thenReturn(departement1);

        Departement result = departementController.createDepartement(departement1);
        assertNotNull(result);
        assertEquals("Informatique", result.getName());
    }

    @Test
    public void testCreateDepartementWithEmptyName() {
        Departement invalidDepartement = new Departement(""); // Nom vide

        Departement result = departementController.createDepartement(invalidDepartement);
        assertNull(result); // Le résultat doit être nul si le nom est vide
    }

    @Test
    public void testCreateDepartementWithNullName() {
        Departement invalidDepartement = new Departement(null); // Nom null

        Departement result = departementController.createDepartement(invalidDepartement);
        assertNull(result); // Le résultat doit être nul si le nom est null
    }

    @Test
    public void testUpdateDepartement() {
        departement1.setId(1);
        when(departementService.update(departement1)).thenReturn(departement1);

        Departement result = departementController.updateDepartement(1, departement1).getBody();
        assertNotNull(result);
        assertEquals("Informatique", result.getName());
    }

    @Test
    public void testUpdateDepartementNotFound() {
        departement1.setId(99);
        when(departementService.update(departement1)).thenReturn(null);

        Departement result = departementController.updateDepartement(99, departement1).getBody();
        assertNull(result); // Si le département n'est pas trouvé, il doit être nul
    }

    @Test
    public void testDeleteDepartement() {
        departementController.deleteDepartement(1);
        verify(departementService, times(1)).delete(1); // Vérifie que la méthode delete a été appelée une fois
    }

    @Test
    public void testDeleteDepartementNotFound() {
        doThrow(new RuntimeException("Departement not found")).when(departementService).delete(99);

        try {
            departementController.deleteDepartement(99);
            fail("Exception not thrown");
        } catch (Exception e) {
            assertEquals("Departement not found", e.getMessage());
        }
    }

    @Test
    public void testCreateDepartementWithExistingName() {
        // Simuler qu'un département avec le même nom existe déjà
        when(departementService.save(departement1)).thenThrow(new RuntimeException("Departement with this name already exists"));

        try {
            departementController.createDepartement(departement1);
            fail("Exception not thrown");
        } catch (Exception e) {
            assertEquals("Departement with this name already exists", e.getMessage());
        }
    }

    @Test
    public void testUpdateDepartementWithExistingName() {
        // Simuler que le nom du département mis à jour existe déjà
        departement1.setName("Informatique");
        when(departementService.update(departement1)).thenThrow(new RuntimeException("Departement with this name already exists"));

        try {
            departementController.updateDepartement(1, departement1);
            fail("Exception not thrown");
        } catch (Exception e) {
            assertEquals("Departement with this name already exists", e.getMessage());
        }
    }

    @Test
    public void testDeleteDepartementWithNonexistentId() {
        when(departementService.findById(99)).thenReturn(null);

        Departement result = departementController.getDepartementById(99).getBody();
        assertNull(result); // Le département n'existe pas, il doit être nul
    }


    
}
