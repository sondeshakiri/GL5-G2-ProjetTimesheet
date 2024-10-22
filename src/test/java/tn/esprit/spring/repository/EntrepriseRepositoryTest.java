package tn.esprit.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.spring.entities.Entreprise;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@DataJpaTest
public class EntrepriseRepositoryTest {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Test
    public void testFindById() {
        Entreprise entreprise = new Entreprise();
        entreprise.setName("TestEntreprise");
        entrepriseRepository.save(entreprise);

        Optional<Entreprise> foundEntreprise = entrepriseRepository.findById(entreprise.getId());
        assertTrue(foundEntreprise.isPresent());
        assertEquals("TestEntreprise", foundEntreprise.get().getName());
    }

    @Test
    public void testSaveEntreprise() {
        Entreprise entreprise = new Entreprise();
        entreprise.setName("SavedEntreprise");

        Entreprise savedEntreprise = entrepriseRepository.save(entreprise);

        assertNotNull(savedEntreprise);
        assertEquals("SavedEntreprise", savedEntreprise.getName());
    }
}
