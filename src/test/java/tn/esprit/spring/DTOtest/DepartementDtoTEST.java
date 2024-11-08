package tn.esprit.spring.DTOtest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DTO.DepartementDTO;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;

import java.util.Arrays;

class DepartementDtoTEST {

    @Mock
    private Departement departement;

    @Mock
    private Entreprise entreprise;

    @Mock
    private Employe employe1;

    @Mock
    private Employe employe2;

    @Mock
    private Mission mission1;

    @Mock
    private Mission mission2;

    private DepartementDTO departementDTO;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Mock Entreprise behavior
        when(entreprise.getName()).thenReturn("TechCorp");

        // Mock Employe behavior
        when(employe1.getNom()).thenReturn("John Doe");
        when(employe2.getNom()).thenReturn("Jane Smith");

        // Mock Mission behavior
        when(mission1.getDescription()).thenReturn("Develop new feature");
        when(mission2.getDescription()).thenReturn("Fix bugs");

        // Mock Departement behavior
        when(departement.getId()).thenReturn(1);
        when(departement.getName()).thenReturn("IT Department");
        when(departement.getEntreprise()).thenReturn(entreprise);
        when(departement.getEmployes()).thenReturn(Arrays.asList(employe1, employe2));
        when(departement.getMissions()).thenReturn(Arrays.asList(mission1, mission2));

        // Create DepartementDTO using mocked data
        departementDTO = new DepartementDTO(
                departement.getId(),
                departement.getName(),
                departement.getEntreprise().getName(),
                Arrays.asList("John Doe", "Jane Smith"),
                Arrays.asList("Develop new feature", "Fix bugs")
        );
    }

    @Test
    void testDepartementDTOConversion() {
        // Verify the conversion of data
        assertEquals(1, departementDTO.getId());
        assertEquals("IT Department", departementDTO.getName());
        assertEquals("TechCorp", departementDTO.getEntrepriseName());
        assertEquals(2, departementDTO.getEmployeNames().size());
        assertEquals(2, departementDTO.getMissionDescriptions().size());

        // Verify employee names
        assertTrue(departementDTO.getEmployeNames().contains("John Doe"));
        assertTrue(departementDTO.getEmployeNames().contains("Jane Smith"));

        // Verify mission descriptions
        assertTrue(departementDTO.getMissionDescriptions().contains("Develop new feature"));
        assertTrue(departementDTO.getMissionDescriptions().contains("Fix bugs"));
    }

    @Test
    void testDepartementDTOEmptyValues() {
        // Test with empty lists
        Departement emptyDepartement = mock(Departement.class);
        when(emptyDepartement.getId()).thenReturn(2);
        when(emptyDepartement.getName()).thenReturn("Empty Department");
        when(emptyDepartement.getEmployes()).thenReturn(Arrays.asList());
        when(emptyDepartement.getMissions()).thenReturn(Arrays.asList());

        DepartementDTO emptyDTO = new DepartementDTO(
                emptyDepartement.getId(),
                emptyDepartement.getName(),
                "Unknown",
                Arrays.asList(),
                Arrays.asList()
        );

        assertEquals(2, emptyDTO.getId());
        assertEquals("Empty Department", emptyDTO.getName());
        assertTrue(emptyDTO.getEmployeNames().isEmpty());
        assertTrue(emptyDTO.getMissionDescriptions().isEmpty());
    }
}
