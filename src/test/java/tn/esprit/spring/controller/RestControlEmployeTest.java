package tn.esprit.spring.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.IEmployeService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
 class RestControlEmployeTest {

    @InjectMocks
    RestControlEmploye restControlEmploye;

    @Mock
    IEmployeService iemployeservice;

    @Mock
    Role role; // Mocking the Role

    @Test
     void testAjouterEmploye() {
        // Creating an Employe object with the mocked role
        Employe employe = new Employe("Chaieb","Said" ,"Khaled.kallel@ssiiconsulting.tn", true, role);

        // Mock the behavior of iemployeservice to return the employe after calling addOrUpdateEmploye
        when(iemployeservice.addOrUpdateEmploye(any(Employe.class))).thenReturn(employe.getId());

        // Execute the method under test
        Employe returnedEmploye = restControlEmploye.ajouterEmploye(employe);

        // Assertions to validate behavior
        assertNotNull(returnedEmploye);
        assertEquals("Chaieb", returnedEmploye.getNom());

        // Verify that the addOrUpdateEmploye method was called once
        verify(iemployeservice, times(1)).addOrUpdateEmploye(employe);
    }
    @Test
     void testMettreAjourEmailByEmployeId() {
        doNothing().when(iemployeservice).mettreAjourEmailByEmployeId(anyString(), anyInt());

        restControlEmploye.mettreAjourEmailByEmployeId("newemail@test.com", 1);

        verify(iemployeservice, times(1)).mettreAjourEmailByEmployeId("newemail@test.com", 1);
    }

    @Test
     void testAjouterContrat() {
        Contrat contrat = new Contrat();
        contrat.setReference(6);
        contrat.setSalaire(1400);

        when(iemployeservice.ajouterContrat(any(Contrat.class))).thenReturn(contrat.getReference());

        int reference = restControlEmploye.ajouterContrat(contrat);

        assertEquals(6, reference);
        verify(iemployeservice, times(1)).ajouterContrat(contrat);
    }

    @Test
     void testDeleteEmployeById() {
        doNothing().when(iemployeservice).deleteEmployeById(1);

        restControlEmploye.deleteEmployeById(1);

        verify(iemployeservice, times(1)).deleteEmployeById(1);
    }
}
