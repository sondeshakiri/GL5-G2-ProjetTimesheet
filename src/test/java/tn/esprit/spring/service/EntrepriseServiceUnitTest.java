package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EntrepriseServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class) // Use MockitoExtension for JUnit 5
@Slf4j
class EntrepriseServiceUnitTest {

    @InjectMocks
    EntrepriseServiceImpl entrepriseService; // Injecting the service to be tested

    @Mock
    EntrepriseRepository entrepriseRepository; // Mocking the repository directly

    @Test
    void testAddEntrepriseWithMock() {
        // Prepare the entreprise entity to be added
        Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");

        // Mock the repository behavior to return the entreprise with a fixed id
        entreprise.setId(1); // Assuming a fixed ID for testing
        when(entrepriseRepository.save(any(Entreprise.class))).thenReturn(entreprise);

        // Execute the method under test
        int entrepriseId = entrepriseService.ajouterEntreprise(entreprise);

        // Verify that the returned ID matches the expected mocked value
        assertEquals(1, entrepriseId); // Verify the expected behavior
    }

    @Test
    void testGetEntrepriseByIdWithMock() {
        Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
        entreprise.setId(1); // Set an ID for the mock entreprise

        // Mock the behavior of entrepriseRepository to return the entreprise when queried by ID
        when(entrepriseRepository.findById(1)).thenReturn(Optional.of(entreprise));

        // Execute the method under test
        Entreprise fetchedEntreprise = entrepriseService.getEntrepriseById(1);

        // Verify that the fetched entreprise is not null and matches the expected values
        assertNotNull(fetchedEntreprise);
        assertEquals(entreprise.getId(), fetchedEntreprise.getId());
        assertEquals(entreprise.getName(), fetchedEntreprise.getName());
        assertEquals(entreprise.getRaisonSocial(), fetchedEntreprise.getRaisonSocial());
    }
}
