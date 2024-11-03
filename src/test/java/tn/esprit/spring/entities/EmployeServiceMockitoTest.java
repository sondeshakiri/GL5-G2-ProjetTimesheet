package tn.esprit.spring.entities;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.EmployeServiceImpl;

public class EmployeServiceMockitoTest {

    @Mock
    private EmployeRepository employeRepository;

    @InjectMocks
    private EmployeServiceImpl employeService;

    private Employe employe;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
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

        Employe foundEmploye = employeService.getEmployeById(1);
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
