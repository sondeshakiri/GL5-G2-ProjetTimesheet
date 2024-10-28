package tn.esprit.spring.services;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.EmployeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeServiceMockitoTest {

    @Mock
    private EmployeRepository employeRepository;

    @InjectMocks
    private EmployeServiceImpl employeService;

    private Employe employe;

    @Before
    public void setUp() {
        employe = new Employe();
        employe.setId(1);
        employe.setPrenom("John");
        employe.setNom("Doe");
        employe.setEmail("john.doe@example.com");
        employe.setPassword("password123");
    }

    @Test
    public void testAddOrUpdateEmploye() {
        when(employeRepository.save(employe)).thenReturn(employe);

        int id = employeService.addOrUpdateEmploye(employe);
        assertEquals(1, id);

        verify(employeRepository, times(1)).save(employe);
    }

    @Test
    public void testGetEmployeById() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));

        Employe foundEmploye = employeService.getEmployeById(1); // Fixed the method call to employeService
        assertNotNull(foundEmploye);
        assertEquals("John", foundEmploye.getPrenom());
    }

    @Test
    public void testDeleteEmployeById() {
        doNothing().when(employeRepository).deleteById(1);

        employeService.deleteEmployeById(1);

        verify(employeRepository, times(1)).deleteById(1);
    }
}
