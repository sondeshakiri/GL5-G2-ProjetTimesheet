package tn.esprit.spring.services;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.repository.EmployeRepository;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@SpringBootTest
public class EmployeCrudTest {

    @Autowired
    private EmployeRepository employeRepository;

    @Test
    public void testCreateEmploye() {
        Employe employe = new Employe();
        employe.setPrenom("John");
        employe.setNom("Doe");
        employe.setEmail("john.doe@example.com");
        employe.setPassword("password123");

        Employe savedEmploye = employeRepository.save(employe);

        assertNotNull(savedEmploye);
        assertEquals("John", savedEmploye.getPrenom());
        assertEquals("Doe", savedEmploye.getNom());
    }

    @Test
    public void testReadEmploye() {
        Employe employe = employeRepository.findById(1).orElse(null);
        assertNotNull(employe);
        assertEquals(1, employe.getId());
    }

    @Test
    public void testUpdateEmploye() {
        Employe employe = employeRepository.findById(1).orElse(null);
        assertNotNull(employe);

        employe.setPrenom("UpdatedName");
        employeRepository.save(employe);

        Employe updatedEmploye = employeRepository.findById(1).orElse(null);
        assertNotNull(updatedEmploye);
        assertEquals("UpdatedName", updatedEmploye.getPrenom());
    }

    @Test
    public void testDeleteEmploye() {
        employeRepository.deleteById(1);
        Employe employe = employeRepository.findById(1).orElse(null);
        assertNull(employe);
    }
}
