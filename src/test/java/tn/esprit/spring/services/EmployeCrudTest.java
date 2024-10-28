package tn.esprit.spring.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.repository.EmployeRepository;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
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
        Employe employe = employeRepository.findById(8).orElse(null);
        assertNotNull(employe);
        assertEquals(8, employe.getId());
    }

    @Test
    public void testUpdateEmploye() {
        Employe employe = employeRepository.findById(8).orElse(null);
        assertNotNull(employe);

        employe.setPrenom("UpdatedName");
        employeRepository.save(employe);

        Employe updatedEmploye = employeRepository.findById(8).orElse(null);
        assertNotNull(updatedEmploye);
        assertEquals("UpdatedName", updatedEmploye.getPrenom());
    }

    @Test
    public void testDeleteEmploye() {
        employeRepository.deleteById(8);
        Employe employe = employeRepository.findById(8).orElse(null);
        assertNull(employe);
    }
}
