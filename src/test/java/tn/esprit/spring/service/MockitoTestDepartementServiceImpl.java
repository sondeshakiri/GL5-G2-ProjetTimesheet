package tn.esprit.spring.service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.DepartementServiceImp;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MockitoTestDepartementServiceImpl {
   @Mock
    private DepartementRepository departementRepository;
   @Mock
   private EntrepriseRepository entrepriseRepository;
   @InjectMocks
   private DepartementServiceImp departementService;
   private Departement departement;
   private Entreprise entreprise;

   @BeforeEach
    void setUp() {
        departement = new Departement();
        departement.setId(1);
        departement.setName("IT");
         entreprise = new Entreprise();
        entreprise.setId(1);
        entreprise.setName("Societe");
    }
    @Test
    public void testAjouterDepartement() {
       when(departementRepository.save(departement)).thenReturn(departement);
      int id=  departementService.ajouterDepartement(departement);
      assertEquals(id,departement.getId());
      verify(departementRepository,times(1)).save(departement);
   }
   @Test
    public void testDeleteDepartementById() {
       doNothing().when(departementRepository).deleteById(departement.getId());
        departementService.deleteDepartementById(departement.getId());
       verify(departementRepository,times(1)).deleteById(departement.getId());
}
    @Test
    public void testAffecterDepartementAEntreprise() {
       when(departementRepository.findById(1)).thenReturn(java.util.Optional.of(departement));
       when(entrepriseRepository.findById(1)).thenReturn(java.util.Optional.of(entreprise));
       when(departementRepository.save(departement)).thenReturn(departement);
       departementService.affecterDepartementAEntreprise(1,1);
       assertEquals(departement.getEntreprise(),entreprise);
       verify(departementRepository,times(1)).findById(1);
       verify(departementRepository,times(1)).save(departement);
    }
    @Test
    public void testGetAllDepartements(){
       when(departementRepository.findAll()).thenReturn(java.util.Arrays.asList(departement));
      List<Departement> departementList=departementService.getAllDepartements();
        assertEquals(departementList.size(),1);
        verify(departementRepository,times(1)).findAll();
    }


}

