package tn.esprit.spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.dto.EmployeDTO;
import tn.esprit.spring.dto.ContratDTO;
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
   private ContratDTO contratDTO;

   @BeforeEach
   void setUp() {
      employeDTO = new EmployeDTO(1, "Said", "Chaieb", "khaled.kallel@ssiiconsulting.tn", true, Role.INGENIEUR.name());

      contratDTO = new ContratDTO();
      contratDTO.setReference(6);
      contratDTO.setSalaire(1400);
      contratDTO.setTypeContrat("CDI");
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
      contrat.setTypeContrat("CDI");

      when(iemployeservice.ajouterContrat(any(Contrat.class))).thenReturn(contrat.getReference());

      int reference = restControlEmploye.ajouterContrat(contratDTO);

      assertEquals(6, reference);
      verify(iemployeservice, times(1)).ajouterContrat(any(Contrat.class));
   }

   @Test
   void testDeleteEmployeById() {
      doNothing().when(iemployeservice).deleteEmployeById(1);

      restControlEmploye.deleteEmployeById(1);

      verify(iemployeservice, times(1)).deleteEmployeById(1);
   }
}
