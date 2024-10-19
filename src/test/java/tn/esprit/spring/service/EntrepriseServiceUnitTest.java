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

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)  // Use MockitoExtension for JUnit 5
@Slf4j
public class EntrepriseServiceUnitTest {

    @InjectMocks
    EntrepriseServiceImpl entrepriseService; // Injecting the service to be tested

    @Mock
    EntrepriseRepository entrepriseRepository; // Mocking the repository directly

    @Test
    public void testAddEntrepriseWithMock() {
        Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");

        // Mock the repository behavior when save method is called
        when(entrepriseRepository.save(any(Entreprise.class))).thenReturn(entreprise);

        int entrepriseId = entrepriseService.ajouterEntreprise(entreprise);

        // Assuming entrepriseService.ajouterEntreprise returns a fixed id, let's say 1
        assertEquals(1, entrepriseId);  // Verify the expected behavior
    }
}
