package tn.esprit.spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    private void setUp() {
    	MockitoAnnotations.openMocks(this);
        departement1 = new Departement("Informatique");
        departement2 = new Departement("RH");
    }

    @Test
    private void testGetAllDepartements() {
        List<Departement> departements = Arrays.asList(departement1, departement2);
        when(departementService.findAll()).thenReturn(departements);

        List<Departement> result = departementController.getAllDepartements();
        assertEquals(2, result.size());
        assertEquals("Informatique", result.get(0).getName());
        assertEquals("RH", result.get(1).getName());
    }

    @Test
    private void testGetDepartementById() {
        when(departementService.findById(1)).thenReturn(departement1);

        Departement result = departementController.getDepartementById(1).getBody();
        assertNotNull(result);
        assertEquals("Informatique", result.getName());
    }



    @Test
    private void testCreateDepartement() {
        when(departementService.save(departement1)).thenReturn(departement1);

        Departement result = departementController.createDepartement(departement1);
        assertNotNull(result);
        assertEquals("Informatique", result.getName());
    }

    @Test
    private void testCreateDepartementWithEmptyName() {
        Departement invalidDepartement = new Departement(""); // Nom vide

        Departement result = departementController.createDepartement(invalidDepartement);
        assertNull(result); // Le résultat doit être nul si le nom est vide
    }

    @Test
    private void testCreateDepartementWithNullName() {
        Departement invalidDepartement = new Departement(null); // Nom null

        Departement result = departementController.createDepartement(invalidDepartement);
        assertNull(result); // Le résultat doit être nul si le nom est null
    }

    @Test
    private void testUpdateDepartement() {
        departement1.setId(1);
        when(departementService.update(departement1)).thenReturn(departement1);

        Departement result = departementController.updateDepartement(1, departement1).getBody();
        assertNotNull(result);
        assertEquals("Informatique", result.getName());
    }

    @Test
    private void testUpdateDepartementNotFound() {
        departement1.setId(99);
        when(departementService.update(departement1)).thenReturn(null);

        Departement result = departementController.updateDepartement(99, departement1).getBody();
        assertNull(result); // Si le département n'est pas trouvé, il doit être nul
    }

    @Test
    private void testDeleteDepartement() {
        departementController.deleteDepartement(1);
        verify(departementService, times(1)).delete(1); // Vérifie que la méthode delete a été appelée une fois
    }

    @Test
    private void testDeleteDepartementNotFound() {
        doThrow(new RuntimeException("Departement not found")).when(departementService).delete(99);

        try {
            departementController.deleteDepartement(99);
            fail("Exception not thrown");
        } catch (Exception e) {
            assertEquals("Departement not found", e.getMessage());
        }
    }

    @Test
    private void testCreateDepartementWithExistingName() {
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
    private void testUpdateDepartementWithExistingName() {
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


    
}
