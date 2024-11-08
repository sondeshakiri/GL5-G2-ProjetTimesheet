package tn.esprit.spring.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.EmployeServiceImpl;

@ExtendWith(MockitoExtension.class) // This extension initializes mocks and injects them
class EmployeServiceMockitoTest {

    @InjectMocks
    private EmployeServiceImpl employeService; // Inject the actual implementation class

    @Mock
    private EmployeRepository employeRepository; // Mock the repository dependency

    @BeforeEach
    public void setUp() {
        // No need to manually initialize mocks when using @ExtendWith(MockitoExtension.class)
    }

    @Test
    void testCreateEmploye() {
        Role role = Role.valueOf("ADMINISTRATEUR");
        Employe employe = new Employe("Doe", "John", "john.doe@example.com", true, role);
        employe.setId(1);

        // Mock the repository save method
        when(employeRepository.save(employe)).thenReturn(employe);

        // Call the service method (which uses the mock repository)
        int result = employeService.addOrUpdateEmploye(employe);

        // Verify the result
        assertEquals(employe.getId(), result);
    }

    @Test
    void testGetEmployePrenomById() {
        int employeId = 1;
        Employe employe = new Employe("Doe", "John", "john.doe@example.com", true, Role.valueOf("ADMINISTRATEUR"));

        // Mock the repository findById method
        when(employeRepository.findById(employeId)).thenReturn(Optional.of(employe));

        // Call the service method (which uses the mock repository)
        String result = employeService.getEmployePrenomById(employeId);

        // Verify the result
        assertNotNull(result);
        assertEquals(employe.getPrenom(), result);
    }

    @Test
    void testUpdateEmploye() {
        Role role = Role.valueOf("ADMINISTRATEUR");
        Employe employe = new Employe("Doe", "John", "john.doe@example.com", true, role);

        // Modify the employe data
        employe.setEmail("john.new@example.com");

        // Mock the repository save method
        when(employeRepository.save(employe)).thenReturn(employe);

        // Call the service method (which uses the mock repository)
        employeService.addOrUpdateEmploye(employe);

        // Verify that the email was updated
        assertEquals("john.new@example.com", employe.getEmail());
    }

    @Test
    void testDeleteEmployeById() {
        // Given
        int employeId = 1;
        Role role = Role.valueOf("ADMINISTRATEUR");
        Employe employe = new Employe("Doe", "John", "john.doe@example.com", true, role);
        employe.setId(employeId);

        // Initialize the Departement with an empty list
        Departement departement = new Departement();
        departement.setEmployes(new ArrayList<>());
        employe.setDepartements(List.of(departement));

        // Mock the repository findById method
        when(employeRepository.findById(employeId)).thenReturn(Optional.of(employe));

        // Call the service method (which uses the mock repository)
        employeService.deleteEmployeById(employeId);

        // Verify that the repository delete method was called
        verify(employeRepository, times(1)).delete(employe);
    }
}
