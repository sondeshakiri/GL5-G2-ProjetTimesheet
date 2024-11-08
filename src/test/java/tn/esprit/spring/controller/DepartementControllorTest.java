package tn.esprit.spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tn.esprit.spring.dto.DepartementDTO;
import tn.esprit.spring.dto.EntrepriseDTO;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.DepartementService;
import tn.esprit.spring.services.IEntrepriseService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartementControllerTest {

    @Mock
    private DepartementService departementService;

    @Mock
    private IEntrepriseService entrepriseService;

    @InjectMocks
    private DepartementControllor departementController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDepartements() {
        Departement departement1 = new Departement();
        Departement departement2 = new Departement();
        when(departementService.findAll()).thenReturn(Arrays.asList(departement1, departement2));

        List<DepartementDTO> result = departementController.getAllDepartements();

        assertEquals(2, result.size());
        verify(departementService, times(1)).findAll();
    }

    @Test
    void testGetDepartementById() {
        Departement departement = new Departement();
        departement.setId(1);
        departement.setName("Finance");

        when(departementService.findById(1)).thenReturn(departement);

        ResponseEntity<DepartementDTO> response = departementController.getDepartementById(1);

        assertNotNull(response.getBody());
        assertEquals("Finance", response.getBody().getName());
        verify(departementService, times(1)).findById(1);
    }

    @Test
    void testCreateDepartement() {
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setName("HR");

        Departement departement = new Departement();
        departement.setId(1);
        departement.setName("HR");

        when(departementService.save(any(Departement.class))).thenReturn(departement);

        DepartementDTO result = departementController.createDepartement(departementDTO);

        assertNotNull(result);
        assertEquals("HR", result.getName());
        verify(departementService, times(1)).save(any(Departement.class));
    }

    @Test
    void testUpdateDepartement() {
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setId(1);
        departementDTO.setName("IT");

        Departement updatedDepartement = new Departement();
        updatedDepartement.setId(1);
        updatedDepartement.setName("IT");

        when(departementService.update(any(Departement.class))).thenReturn(updatedDepartement);

        ResponseEntity<DepartementDTO> response = departementController.updateDepartement(1, departementDTO);

        assertNotNull(response.getBody());
        assertEquals("IT", response.getBody().getName());
        verify(departementService, times(1)).update(any(Departement.class));
    }

    @Test
    void testDeleteDepartement() {
        doNothing().when(departementService).delete(1);

        ResponseEntity<Void> response = departementController.deleteDepartement(1);

        assertEquals(204, response.getStatusCodeValue());
        verify(departementService, times(1)).delete(1);
    }
    @Test
    void testCreateDepartement_WithEntrepriseDTO() {
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setName("Finance");
        departementDTO.setEntrepriseId(1); // Assuming this ID is valid

        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();
        entrepriseDTO.setId(1);
        entrepriseDTO.setName("TechCorp");
        entrepriseDTO.setRaisonSocial("Innovative Technology Solutions");

        Departement departement = new Departement();
        departement.setId(1);
        departement.setName("Finance");

        // Mocking the behaviour of the services
        when(departementService.save(any(Departement.class))).thenReturn(departement);

        // Using the default constructor and setting properties
        Entreprise entreprise = new Entreprise();
        entreprise.setId(entrepriseDTO.getId());
        entreprise.setName(entrepriseDTO.getName());
        entreprise.setRaisonSocial(entrepriseDTO.getRaisonSocial());

        when(entrepriseService.getEntrepriseById(1)).thenReturn(entreprise);

        DepartementDTO result = departementController.createDepartement(departementDTO);

        assertNotNull(result);
        assertEquals("Finance", result.getName());
        assertEquals(Integer.valueOf(1), result.getEntrepriseId());
        verify(departementService, times(1)).save(any(Departement.class));
    }

    @Test
    void testUpdateDepartement_WithEntrepriseDTO() {
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setId(1);
        departementDTO.setName("IT");
        departementDTO.setEntrepriseId(1); // Assuming this ID is valid

        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();
        entrepriseDTO.setId(1);
        entrepriseDTO.setName("TechCorp");
        entrepriseDTO.setRaisonSocial("Innovative Technology Solutions");

        Departement updatedDepartement = new Departement();
        updatedDepartement.setId(1);
        updatedDepartement.setName("IT");

        when(departementService.update(any(Departement.class))).thenReturn(updatedDepartement);

        // Using the default constructor and setting properties
        Entreprise entreprise = new Entreprise();
        entreprise.setId(entrepriseDTO.getId());
        entreprise.setName(entrepriseDTO.getName());
        entreprise.setRaisonSocial(entrepriseDTO.getRaisonSocial());

        when(entrepriseService.getEntrepriseById(1)).thenReturn(entreprise);

        ResponseEntity<DepartementDTO> response = departementController.updateDepartement(1, departementDTO);

        assertNotNull(response.getBody());
        assertEquals("IT", response.getBody().getName());
        assertEquals(Integer.valueOf(1), response.getBody().getEntrepriseId());
        verify(departementService, times(1)).update(any(Departement.class));
    }


    @Test
    void testCreateDepartement_NullEntrepriseDTO() {
        DepartementDTO departementDTO = new DepartementDTO();
        departementDTO.setName("Finance");
        departementDTO.setEntrepriseId(null); // Simulating null entreprise ID

        Departement departement = new Departement();
        departement.setId(1);
        departement.setName("Finance");

        when(departementService.save(any(Departement.class))).thenReturn(departement);

        DepartementDTO result = departementController.createDepartement(departementDTO);

        assertNotNull(result);
        assertEquals("Finance", result.getName());
        assertNull(result.getEntrepriseId()); // Ensure no entreprise ID is set
        verify(departementService, times(1)).save(any(Departement.class));
    }

}
