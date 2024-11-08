package tn.esprit.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EntrepriseServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class EntrepriseServiceImplTest {

   @InjectMocks
   EntrepriseServiceImpl entrepriseService;

   @Mock
   EntrepriseRepository entrepriseRepository;

   @Mock
   DepartementRepository departementRepository;

   @Test
   void testAddEntrepriseWithMock() {
      Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
      entreprise.setId(1); // Ensure the ID is set for the test

      // Mock the behavior of entrepriseRepository to return the entreprise when saved
      when(entrepriseRepository.save(any(Entreprise.class))).thenReturn(entreprise);

      int entrepriseId = entrepriseService.ajouterEntreprise(entreprise);

      assertEquals(entreprise.getId(), entrepriseId);
   }

   @Test
   void testGetEntrepriseByIdWithMock() {
      Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
      entreprise.setId(1); // Set an ID for the mock entreprise

      // Mock the behavior of entrepriseRepository to return the entreprise when queried by ID
      when(entrepriseRepository.findById(1)).thenReturn(Optional.of(entreprise));

      Entreprise fetchedEntreprise = entrepriseService.getEntrepriseById(1);

      assertNotNull(fetchedEntreprise);
      assertEquals("Tech Solutions", fetchedEntreprise.getName());
      assertEquals("Business Park", fetchedEntreprise.getRaisonSocial());
   }

   @Test
   void testDeleteEntrepriseWithMock() {
      Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
      entreprise.setId(1);

      when(entrepriseRepository.findById(1)).thenReturn(Optional.of(entreprise));

      entrepriseService.deleteEntrepriseById(1);

      verify(entrepriseRepository, times(1)).delete(any(Entreprise.class));
   }

   @Test
   void testAffecterDepartementAEntrepriseWithMock() {
      Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
      entreprise.setId(1);
      Departement departement = new Departement("IT");
      departement.setId(1);

      when(entrepriseRepository.findById(1)).thenReturn(Optional.of(entreprise));
      when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

      entrepriseService.affecterDepartementAEntreprise(1, 1);

      verify(entrepriseRepository, times(1)).findById(1);
      verify(departementRepository, times(1)).findById(1);
   }

   @Test
   void testGetAllDepartementsNamesByEntreprise() {
      Entreprise entreprise = new Entreprise("Tech Solutions", "Business Park");
      entreprise.setId(1);
      Departement dep1 = new Departement("HR");
      Departement dep2 = new Departement("Finance");

      entreprise.setDepartements(Arrays.asList(dep1, dep2));

      when(entrepriseRepository.findById(1)).thenReturn(Optional.of(entreprise));

      List<String> names = entrepriseService.getAllDepartementsNamesByEntreprise(1);

      assertNotNull(names);
      assertEquals(2, names.size());
      assertEquals("HR", names.get(0));
      assertEquals("Finance", names.get(1));
      verify(entrepriseRepository, times(1)).findById(1);
   }

   @Test
   void testDeleteDepartementWithMock() {
      Departement departement = new Departement("Finance");
      departement.setId(1);

      when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

      entrepriseService.deleteDepartementById(1);

      verify(departementRepository, times(1)).delete(any(Departement.class));
   }

   @Test
   void testGetEntrepriseByIdNotFound() {
      when(entrepriseRepository.findById(1)).thenReturn(Optional.empty());

      Entreprise fetchedEntreprise = entrepriseService.getEntrepriseById(1);

      assertNull(fetchedEntreprise); // Expect null if not found
      verify(entrepriseRepository, times(1)).findById(1);
   }

   @Test
   void testDeleteEntrepriseNotFound() {
      when(entrepriseRepository.findById(1)).thenReturn(Optional.empty());

      entrepriseService.deleteEntrepriseById(1);

      verify(entrepriseRepository, never()).delete(any(Entreprise.class)); // Ensure delete is not called
   }
}
