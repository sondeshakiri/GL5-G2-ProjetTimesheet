package tn.esprit.spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.dto.EntrepriseDTO;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEntrepriseService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestControlEntrepriseTest {

    @Mock
    private IEntrepriseService entrepriseService;

    @InjectMocks
    private RestControlEntreprise restControlEntreprise;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAjouterEntreprise() {
        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();
        entrepriseDTO.setName("TechCorp");
        entrepriseDTO.setRaisonSocial("Innovative Technology Solutions");

        // Create a mock Enterprise object
        Entreprise entreprise = new Entreprise();
        entreprise.setId(1); // Set an ID
        entreprise.setName("TechCorp");
        entreprise.setRaisonSocial("Innovative Technology Solutions");

        // Mocking the behavior of the service
        when(entrepriseService.ajouterEntreprise(any(Entreprise.class))).thenReturn(0); // Return the ID directly

        // Call the controller method
        int resultId = restControlEntreprise.ajouterEntreprise(entrepriseDTO);
        // Assertions
        assertEquals(0, resultId);
        verify(entrepriseService, times(1)).ajouterEntreprise(any(Entreprise.class));
    }


    @Test
    void testAffecterDepartementAEntreprise() {
        int depId = 1;
        int entrepriseId = 1;

        doNothing().when(entrepriseService).affecterDepartementAEntreprise(depId, entrepriseId);

        restControlEntreprise.affecterDepartementAEntreprise(depId, entrepriseId);

        verify(entrepriseService, times(1)).affecterDepartementAEntreprise(depId, entrepriseId);
    }

    @Test
    void testDeleteEntrepriseById() {
        int entrepriseId = 1;

        doNothing().when(entrepriseService).deleteEntrepriseById(entrepriseId);

        restControlEntreprise.deleteEntrepriseById(entrepriseId);

        verify(entrepriseService, times(1)).deleteEntrepriseById(entrepriseId);
    }

    @Test
    void testGetEntrepriseById() {
        int entrepriseId = 1;
        Entreprise entreprise = new Entreprise();
        entreprise.setId(entrepriseId);
        entreprise.setName("TechCorp");
        entreprise.setRaisonSocial("Innovative Technology Solutions");

        when(entrepriseService.getEntrepriseById(entrepriseId)).thenReturn(entreprise);

        EntrepriseDTO result = restControlEntreprise.getEntrepriseById(entrepriseId);

        assertNotNull(result);
        assertEquals(entrepriseId, result.getId());
        assertEquals("TechCorp", result.getName());
        verify(entrepriseService, times(1)).getEntrepriseById(entrepriseId);
    }

    @Test
    void testAjouterDepartement() {
        Departement departement = new Departement();
        departement.setId(1);
        departement.setName("Finance");

        when(entrepriseService.ajouterDepartement(any(Departement.class))).thenReturn(departement.getId());

        int resultId = restControlEntreprise.ajouterDepartement(departement);

        assertEquals(1, resultId);
        verify(entrepriseService, times(1)).ajouterDepartement(any(Departement.class));
    }

    @Test
    void testGetAllDepartementsNamesByEntreprise() {
        int entrepriseId = 1;
        List<String> departementNames = Arrays.asList("HR", "Finance");

        when(entrepriseService.getAllDepartementsNamesByEntreprise(entrepriseId)).thenReturn(departementNames);

        List<String> result = restControlEntreprise.getAllDepartementsNamesByEntreprise(entrepriseId);

        assertEquals(2, result.size());
        assertEquals("HR", result.get(0));
        assertEquals("Finance", result.get(1));
        verify(entrepriseService, times(1)).getAllDepartementsNamesByEntreprise(entrepriseId);
    }

    @Test
    void testDeleteDepartementById() {
        int depId = 1;

        doNothing().when(entrepriseService).deleteDepartementById(depId);

        restControlEntreprise.deleteDepartementById(depId);

        verify(entrepriseService, times(1)).deleteDepartementById(depId);
    }
}
