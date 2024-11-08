package tn.esprit.spring.DTOtest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DTO.DepartementDTO;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    void testGetId() {
        assertEquals(1, departementDTO.getId(), "Departement ID should be 1");
    }

    @Test
    void testSetId() {
        departementDTO.setId(2);
        assertEquals(2, departementDTO.getId(), "Departement ID should be updated to 2");
    }

    @Test
    void testGetName() {
        assertEquals("IT Department", departementDTO.getName(), "Departement name should be 'IT Department'");
    }

    @Test
    void testSetName() {
        departementDTO.setName("HR Department");
        assertEquals("HR Department", departementDTO.getName(), "Departement name should be updated to 'HR Department'");
    }

    @Test
    void testGetEntrepriseName() {
        assertEquals("TechCorp", departementDTO.getEntrepriseName(), "Entreprise name should be 'TechCorp'");
    }

    @Test
    void testSetEntrepriseName() {
        departementDTO.setEntrepriseName("NewCorp");
        assertEquals("NewCorp", departementDTO.getEntrepriseName(), "Entreprise name should be updated to 'NewCorp'");
    }

    @Test
    void testGetEmployeNamesSize() {
        List<String> employeNames = departementDTO.getEmployeNames();
        assertEquals(2, employeNames.size(), "Employe list size should be 2");
    }

    @Test
    void testGetEmployeNamesJohnDoe() {
        List<String> employeNames = departementDTO.getEmployeNames();
        assertTrue(employeNames.contains("John Doe"), "Employe list should contain 'John Doe'");
    }

    @Test
    void testGetEmployeNamesJaneSmith() {
        List<String> employeNames = departementDTO.getEmployeNames();
        assertTrue(employeNames.contains("Jane Smith"), "Employe list should contain 'Jane Smith'");
    }

    @Test
    void testSetEmployeNames() {
        List<String> newEmployeNames = Arrays.asList("Alice Brown", "Bob White");
        departementDTO.setEmployeNames(newEmployeNames);
        assertEquals(newEmployeNames, departementDTO.getEmployeNames(), "Employe names should be updated to new list");
    }

    @Test
    void testGetMissionDescriptionsSize() {
        List<String> missionDescriptions = departementDTO.getMissionDescriptions();
        assertEquals(2, missionDescriptions.size(), "Mission list size should be 2");
    }

    @Test
    void testGetMissionDescriptionsFeature() {
        List<String> missionDescriptions = departementDTO.getMissionDescriptions();
        assertTrue(missionDescriptions.contains("Develop new feature"), "Mission list should contain 'Develop new feature'");
    }

    @Test
    void testGetMissionDescriptionsBugs() {
        List<String> missionDescriptions = departementDTO.getMissionDescriptions();
        assertTrue(missionDescriptions.contains("Fix bugs"), "Mission list should contain 'Fix bugs'");
    }

    @Test
    void testSetMissionDescriptions() {
        List<String> newMissionDescriptions = Arrays.asList("Update system", "Manage database");
        departementDTO.setMissionDescriptions(newMissionDescriptions);
        assertEquals(newMissionDescriptions, departementDTO.getMissionDescriptions(), "Mission descriptions should be updated to new list");
    }

    @Test
    void testEmptyDepartementDTO() {
        Departement emptyDepartement = mock(Departement.class);
        when(emptyDepartement.getId()).thenReturn(2);
        when(emptyDepartement.getName()).thenReturn("Empty Department");
        when(emptyDepartement.getEmployes()).thenReturn(Collections.emptyList());
        when(emptyDepartement.getMissions()).thenReturn(Collections.emptyList());

        DepartementDTO emptyDTO = new DepartementDTO(
                emptyDepartement.getId(),
                emptyDepartement.getName(),
                "Unknown",
                Collections.emptyList(),
                Collections.emptyList()
        );

        assertEquals(2, emptyDTO.getId(), "Departement ID should be 2");
    }

    @Test
    void testEmptyDepartementDTOName() {
        Departement emptyDepartement = mock(Departement.class);
        when(emptyDepartement.getId()).thenReturn(2);
        when(emptyDepartement.getName()).thenReturn("Empty Department");
        when(emptyDepartement.getEmployes()).thenReturn(Collections.emptyList());
        when(emptyDepartement.getMissions()).thenReturn(Collections.emptyList());

        DepartementDTO emptyDTO = new DepartementDTO(
                emptyDepartement.getId(),
                emptyDepartement.getName(),
                "Unknown",
                Collections.emptyList(),
                Collections.emptyList()
        );

        assertEquals("Empty Department", emptyDTO.getName(), "Departement name should be 'Empty Department'");
    }

    @Test
    void testEmptyDepartementDTOEntrepriseName() {
        Departement emptyDepartement = mock(Departement.class);
        when(emptyDepartement.getId()).thenReturn(2);
        when(emptyDepartement.getName()).thenReturn("Empty Department");
        when(emptyDepartement.getEmployes()).thenReturn(Collections.emptyList());
        when(emptyDepartement.getMissions()).thenReturn(Collections.emptyList());

        DepartementDTO emptyDTO = new DepartementDTO(
                emptyDepartement.getId(),
                emptyDepartement.getName(),
                "Unknown",
                Collections.emptyList(),
                Collections.emptyList()
        );

        assertEquals("Unknown", emptyDTO.getEntrepriseName(), "Entreprise name should be 'Unknown'");
    }

    @Test
    void testEmptyDepartementDTOEmployeNames() {
        Departement emptyDepartement = mock(Departement.class);
        when(emptyDepartement.getId()).thenReturn(2);
        when(emptyDepartement.getName()).thenReturn("Empty Department");
        when(emptyDepartement.getEmployes()).thenReturn(Collections.emptyList());
        when(emptyDepartement.getMissions()).thenReturn(Collections.emptyList());

        DepartementDTO emptyDTO = new DepartementDTO(
                emptyDepartement.getId(),
                emptyDepartement.getName(),
                "Unknown",
                Collections.emptyList(),
                Collections.emptyList()
        );

        assertTrue(emptyDTO.getEmployeNames().isEmpty(), "Employe names list should be empty");
    }

    @Test
    void testEmptyDepartementDTOMissionDescriptions() {
        Departement emptyDepartement = mock(Departement.class);
        when(emptyDepartement.getId()).thenReturn(2);
        when(emptyDepartement.getName()).thenReturn("Empty Department");
        when(emptyDepartement.getEmployes()).thenReturn(Collections.emptyList());
        when(emptyDepartement.getMissions()).thenReturn(Collections.emptyList());

        DepartementDTO emptyDTO = new DepartementDTO(
                emptyDepartement.getId(),
                emptyDepartement.getName(),
                "Unknown",
                Collections.emptyList(),
                Collections.emptyList()
        );

        assertTrue(emptyDTO.getMissionDescriptions().isEmpty(), "Mission descriptions list should be empty");
    }

    @Test
    void testUpdatingListsWithDifferentSizes() {
        departementDTO.setEmployeNames(Arrays.asList("Alice Brown"));
        departementDTO.setMissionDescriptions(Arrays.asList("Review process", "Conduct meeting", "Prepare report"));

        assertEquals(1, departementDTO.getEmployeNames().size(), "Employe names list should have 1 entry");
        assertEquals(3, departementDTO.getMissionDescriptions().size(), "Mission descriptions list should have 3 entries");
    }
    
}
