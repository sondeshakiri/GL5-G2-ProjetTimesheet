package tn.esprit.spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.dto.EmployeDTO;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestControlEmployeTest {

   @InjectMocks
   private RestControlEmploye restControlEmploye;

   @Mock
   private IEmployeService iemployeservice;

   @Mock
   private IEntrepriseService ientrepriseservice;

   private EmployeDTO employeDTO;

   @BeforeEach
   void setUp() {
      employeDTO = new EmployeDTO();
      employeDTO.setId(1);
      employeDTO.setNom("Chaieb");
      employeDTO.setPrenom("Said");
      employeDTO.setEmail("khaled.kallel@ssiiconsulting.tn");
      employeDTO.setActif(true);
      employeDTO.setRole(Role.INGENIEUR.name());
   }

   @Test
   void testAjouterEmploye() {
      Employe employe = new Employe("Chaieb", "Said", "khaled.kallel@ssiiconsulting.tn", true, Role.INGENIEUR);
      when(iemployeservice.addOrUpdateEmploye(any(Employe.class))).thenReturn(employe.getId());

      EmployeDTO returnedEmployeDTO = restControlEmploye.ajouterEmploye(employeDTO);

      assertNotNull(returnedEmployeDTO);
      assertEquals("Chaieb", returnedEmployeDTO.getNom());
      verify(iemployeservice, times(1)).addOrUpdateEmploye(any(Employe.class));
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
