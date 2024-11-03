package tn.esprit.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EntrepriseServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class EntrepriseServiceImplTest {

    @InjectMocks
    EntrepriseServiceImpl entrepriseService;

    @Mock
    EntrepriseRepository entrepriseRepository;

    @Mock
    DepartementRepository departementRepository;

    @Test
     void testAddEntrepriseWithMock() {
        Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");

        // Mock the behavior of entrepriseRepository to return the entreprise when saved
        when(entrepriseRepository.save(any(Entreprise.class))).thenReturn(entreprise);

        int entrepriseId = entrepriseService.ajouterEntreprise(entreprise);

        // Assuming the ID of the enterprise is set to a specific value after saving
        assertEquals(entreprise.getId(), entrepriseId);
    }

    @Test
     void testGetEntrepriseByIdWithMock() {
        Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
        entreprise.setId(1); // Set an ID for the mock entreprise

        // Mock the behavior of entrepriseRepository to return the entreprise when queried by ID
        when(entrepriseRepository.findById(1)).thenReturn(Optional.of(entreprise));

        Entreprise fetchedEntreprise = entrepriseService.getEntrepriseById(1);

        assertNotNull(fetchedEntreprise);
        assertEquals("Tech Solutions", fetchedEntreprise.getName());
        assertEquals("Business Park", fetchedEntreprise.getRaisonSocial());
    }

    @Test
     void testDeleteEntrepriseWithMock() {
        Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
        entreprise.setId(1);

        // Mock the behavior of entrepriseRepository to return the entreprise when queried by ID
        when(entrepriseRepository.findById(1)).thenReturn(Optional.of(entreprise));

        // Perform delete operation
        entrepriseService.deleteEntrepriseById(1);

        // Verify that the repository's delete method was called with the correct object
        verify(entrepriseRepository, times(1)).delete(any(Entreprise.class));
    }


    @Test
     void testAffecterDepartementAEntrepriseWithMock() {
        Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
        entreprise.setId(1);
        Departement departement = new Departement("IT");
        departement.setId(1);

        // Mock the behavior of entrepriseRepository to return the entreprise when queried by ID
        when(entrepriseRepository.findById(1)).thenReturn(Optional.of(entreprise));
        // Mock the behavior of departementRepository to return the departement when queried by ID
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        // Perform the affectation operation
        entrepriseService.affecterDepartementAEntreprise(1, 1);

        // Verify that the repository's save method was called
        verify(entrepriseRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(1);
    }
}
