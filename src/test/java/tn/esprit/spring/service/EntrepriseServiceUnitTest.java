package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.EntrepriseServiceImpl;
import tn.esprit.spring.services.IEntrepriseService;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class EntrepriseServiceUnitTest {

    @InjectMocks
    EntrepriseServiceImpl entrepriseService;

    @Mock
    IEntrepriseService mockEntrepriseService;

    @Test
    public void testAddEntrepriseWithMock() {
        Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
        when(mockEntrepriseService.ajouterEntreprise(any(Entreprise.class))).thenReturn(1);

        int entrepriseId = entrepriseService.ajouterEntreprise(entreprise);
        assertEquals(1, entrepriseId);
    }
}
