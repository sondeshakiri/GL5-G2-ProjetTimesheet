package tn.esprit.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.DTO.DepartementDTO;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.services.DepartementServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartementServiceTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;

    private Departement departement;

    @BeforeEach
    public void setup() {
        // Initialize Departement entity and its relationships for testing
        departement = new Departement("Finance");
        Entreprise entreprise = new Entreprise();
        entreprise.setId(1);
        entreprise.setName("Tech Corp");
        departement.setEntreprise(entreprise);

        // Create employees for the department
        Employe employe1 = new Employe();
        employe1.setNom("John Doe");
        Employe employe2 = new Employe();
        employe2.setNom("Jane Doe");
        departement.setEmployes(Arrays.asList(employe1, employe2));

        // Create missions for the department
        Mission mission1 = new Mission();
        mission1.setDescription("Mission 1");
        departement.setMissions(Arrays.asList(mission1));
    }

    @Test
    public void testFindAllDepartements() {
        // Arrange: Prépare une liste de départements à retourner par le dépôt
        Departement departement1 = new Departement("Finance");
        departement1.setId(1);
        Departement departement2 = new Departement("HR");
        departement2.setId(2);

        when(departementRepository.findAll()).thenReturn(Arrays.asList(departement1, departement2));

        // Act: Appeler la méthode findAll du service
        List<DepartementDTO> result = departementService.findAll()
            .stream()
            .map(departementService::convertToDto)
            .collect(Collectors.toList());

        // Assert: Vérifier que le résultat n'est pas null et contient les départements attendus
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Finance", result.get(0).getName());
        assertEquals("HR", result.get(1).getName());

        // Vérifie que findAll a bien été appelé une fois
        verify(departementRepository).findAll();
    }

    @Test
    public void testCreateDepartement() {
        // Convert Departement entity to DTO
        DepartementDTO departementDTO = new DepartementDTO(1, "Finance", "Tech Corp", Arrays.asList("John Doe", "Jane Doe"), Arrays.asList("Mission 1"));

        when(departementRepository.save(departement)).thenReturn(departement);

        Departement createdDepartement = departementService.save(departement);

        assertNotNull(createdDepartement);
        assertEquals("Finance", createdDepartement.getName());
        verify(departementRepository).save(departement); // Verify repository save was called
    }

    @Test
    public void testCreateDepartement_SaveFailed() {
        Departement departementToCreate = new Departement("HR");

        when(departementRepository.save(departementToCreate)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            departementService.save(departementToCreate);
        });

        verify(departementRepository).save(departementToCreate); // Ensure repository method was called
    }

    @Test
    public void testGetDepartementWithRelations() {

        DepartementDTO result = departementService.convertToDto(departement);

        assertNotNull(result);
        assertEquals("Finance", result.getName());
        assertEquals(2, result.getEmployeNames().size());
        assertEquals(1, result.getMissionDescriptions().size());
        assertEquals("Tech Corp", result.getEntrepriseName());
    }
    @Test
    public void testGetDepartementWithRelations_NotFound() {
        // Simuler un département non trouvé dans le repository
        when(departementRepository.findById(1)).thenReturn(Optional.empty());

        // Appeler la méthode du service, qui devrait gérer l'absence du département
        Departement departement = departementRepository.findById(1).orElse(null);

        // Convertir en DTO (ici le département est nul, donc le résultat doit être null)
        DepartementDTO result = departementService.convertToDto(departement);

        // Vérifier que le résultat est bien nul
        assertNull(result);  // Le DTO doit être nul si le département est absent
    }




    @Test
    public void testUpdateDepartement_NotFound() {
        // Arrange: Simulate the department not being found in the repository
        Departement updatedDepartement = new Departement("HR");
        updatedDepartement.setId(1);

        // Act: Try to update the department
        Departement result = departementService.update(updatedDepartement);

        // Assert: Ensure the result is null because the department doesn't exist
        assertNull(result);
        verify(departementRepository, never()).save(updatedDepartement); // Verify save was not called
    }

    @Test
    public void testDeleteDepartement() {
        // Act: Call the delete method
        departementService.delete(1);

        // Assert: Verify deleteById was called
        verify(departementRepository).deleteById(1);
    }


}
