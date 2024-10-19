package tn.esprit.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEntrepriseService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)@SpringBootTest
@Slf4j
public class EntrepriseServiceImplTest {

    @Autowired
    IEntrepriseService entrepriseService;

    @Test
    public void testAddEntreprise() {
        Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
        int entrepriseId = entrepriseService.ajouterEntreprise(entreprise);
        Entreprise addedEntreprise = entrepriseService.getEntrepriseById(entrepriseId);

        // Assert the enterprise is successfully added
        assertNotNull(addedEntreprise);
        assertEquals(entreprise.getName(), addedEntreprise.getName());
        assertEquals(entreprise.getRaisonSocial(), addedEntreprise.getRaisonSocial());

        // Clean up
        entrepriseService.deleteEntrepriseById(entrepriseId);
    }

    @Test
    public void testUpdateEntreprise() {
        Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
        int entrepriseId = entrepriseService.ajouterEntreprise(entreprise);

        // Modify enterprise details
        Entreprise existingEntreprise = entrepriseService.getEntrepriseById(entrepriseId);
        existingEntreprise.setName("Tech Innovators");
        entrepriseService.ajouterEntreprise(existingEntreprise);

        // Fetch updated enterprise and verify changes
        Entreprise updatedEntreprise = entrepriseService.getEntrepriseById(entrepriseId);
        assertEquals("Tech Innovators", updatedEntreprise.getName());

        // Clean up
        entrepriseService.deleteEntrepriseById(entrepriseId);
    }

    @Test
    public void testDeleteEntreprise() {
        Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
        int entrepriseId = entrepriseService.ajouterEntreprise(entreprise);

        // Assert enterprise exists
        Entreprise addedEntreprise = entrepriseService.getEntrepriseById(entrepriseId);
        assertNotNull(addedEntreprise);

        // Delete enterprise and verify deletion
        entrepriseService.deleteEntrepriseById(entrepriseId);
        Entreprise deletedEntreprise = entrepriseService.getEntrepriseById(entrepriseId);
        assertNull(deletedEntreprise);  // Should return null after deletion
    }

    @Test
    public void testGetEntrepriseById() {
        Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
        int entrepriseId = entrepriseService.ajouterEntreprise(entreprise);

        // Fetch enterprise by ID and verify its data
        Entreprise fetchedEntreprise = entrepriseService.getEntrepriseById(entrepriseId);
        assertNotNull(fetchedEntreprise);
        assertEquals("Tech Solutions", fetchedEntreprise.getName());

        // Clean up
        entrepriseService.deleteEntrepriseById(entrepriseId);
    }
}
