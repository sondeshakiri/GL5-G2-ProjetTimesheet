package tn.esprit.spring.entities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.repository.EmployeRepository;

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

        Assertions.assertNotNull(savedEmploye);
        Assertions.assertEquals("John", savedEmploye.getPrenom());
        Assertions.assertEquals("Doe", savedEmploye.getNom());
    }

    @Test
    public void testReadEmploye() {
        Employe employe = employeRepository.findById(8).orElse(null);
        Assertions.assertNotNull(employe);
        Assertions.assertEquals(8, employe.getId());
    }

    @Test
    public void testUpdateEmploye() {
        Employe employe = employeRepository.findById(8).orElse(null);
        Assertions.assertNotNull(employe);

        employe.setPrenom("UpdatedName");
        employeRepository.save(employe);

        Employe updatedEmploye = employeRepository.findById(8).orElse(null);
        Assertions.assertNotNull(updatedEmploye);
        Assertions.assertEquals("UpdatedName", updatedEmploye.getPrenom());
    }

    @Test
    public void testDeleteEmploye() {
        employeRepository.deleteById(8);
        Employe employe = employeRepository.findById(8).orElse(null);
        Assertions.assertNull(employe);
    }
}
