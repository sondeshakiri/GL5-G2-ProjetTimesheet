package tn.esprit.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.EmployeServiceImpl;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class EmployeServiceImplTest {

    @InjectMocks
    EmployeServiceImpl employeService;

    @Mock
    EmployeRepository employeRepository;

    @Mock
    DepartementRepository departementRepository;

    @Mock
    ContratRepository contratRepository;

    @Mock
    Role role; // Mocking the Role

    @Test
     void testAddOrUpdateEmploye() {
        Employe employe = new Employe("Chaieb", "Said", "john@example.com", true, role);

        when(employeRepository.save(any(Employe.class))).thenReturn(employe);

        int id = employeService.addOrUpdateEmploye(employe);

        assertEquals(employe.getId(), id);
        verify(employeRepository, times(1)).save(employe);
    }

    @Test
     void testMettreAjourEmailByEmployeId() {
        Employe employe = new Employe("Chaieb", "Said", "john@example.com", true, role);
        employe.setId(1);

        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));

        employeService.mettreAjourEmailByEmployeId("newemail@example.com", 1);

        assertEquals("newemail@example.com", employe.getEmail());
        verify(employeRepository, times(1)).save(employe);
    }

    @Test
     void testAffecterEmployeADepartement() {
        Employe employe = new Employe("Chaieb", "Said", "john@example.com", true, role);
        employe.setId(1);

        // Create mock lists to avoid NullPointerException
        ArrayList<Employe> employeList = new ArrayList<>();
        ArrayList<Departement> departementList = new ArrayList<>();
        employe.setDepartements(departementList);

        Departement departement = new Departement("IT");
        departement.setId(1);
        departement.setEmployes(employeList);

        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        employeService.affecterEmployeADepartement(1, 1);

        assertTrue(departement.getEmployes().contains(employe));
        verify(departementRepository, times(1)).findById(1);
        verify(employeRepository, times(1)).findById(1);
    }

    @Test
     void testAjouterContrat() {
        Contrat contrat = new Contrat();
        contrat.setReference(1234);

        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        int reference = employeService.ajouterContrat(contrat);

        assertEquals(1234, reference);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
     void testDeleteEmployeById() {
        Employe employe = new Employe("Chaieb", "Said", "john@example.com", true, role);
        employe.setId(1);

        // Mock non-null lists of departements to avoid NullPointerException
        ArrayList<Departement> departementList = new ArrayList<>();
        employe.setDepartements(departementList);

        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));

        employeService.deleteEmployeById(1);

        verify(employeRepository, times(1)).delete(employe);
    }
}
