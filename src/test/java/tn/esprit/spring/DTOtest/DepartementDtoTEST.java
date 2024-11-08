package tn.esprit.spring.DTOtest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DTO.DepartementDTO;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class DepartementDtoTEST {

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

        // Create DepartementDTO using mocked data
        departementDTO = new DepartementDTO(
                1,
                "IT Department",
                entreprise.getName(),
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
    void testGetEmployeNames() {
        List<String> employeNames = departementDTO.getEmployeNames();
        assertNotNull(employeNames, "Employe names list should not be null");
        assertEquals(2, employeNames.size(), "There should be 2 employes");
        assertTrue(employeNames.contains("John Doe"), "Employe names should contain 'John Doe'");
        assertTrue(employeNames.contains("Jane Smith"), "Employe names should contain 'Jane Smith'");
    }

    @Test
    void testSetEmployeNames() {
        List<String> newEmployeNames = Arrays.asList("Alice Brown", "Bob White");
        departementDTO.setEmployeNames(newEmployeNames);
        assertEquals(newEmployeNames, departementDTO.getEmployeNames(), "Employe names should be updated");
    }

    @Test
    void testGetMissionDescriptions() {
        List<String> missionDescriptions = departementDTO.getMissionDescriptions();
        assertNotNull(missionDescriptions, "Mission descriptions list should not be null");
        assertEquals(2, missionDescriptions.size(), "There should be 2 mission descriptions");
        assertTrue(missionDescriptions.contains("Develop new feature"), "Mission descriptions should contain 'Develop new feature'");
        assertTrue(missionDescriptions.contains("Fix bugs"), "Mission descriptions should contain 'Fix bugs'");
    }

    @Test
    void testSetMissionDescriptions() {
        List<String> newMissionDescriptions = Arrays.asList("Update system", "Manage database");
        departementDTO.setMissionDescriptions(newMissionDescriptions);
        assertEquals(newMissionDescriptions, departementDTO.getMissionDescriptions(), "Mission descriptions should be updated");
    }

    @Test
    void testEmptyDepartementDTO() {
        DepartementDTO emptyDTO = new DepartementDTO(
                2, "Empty Department", "Unknown", Collections.emptyList(), Collections.emptyList()
        );
        assertTrue(emptyDTO.getEmployeNames().isEmpty(), "Employe names list should be empty");
        assertTrue(emptyDTO.getMissionDescriptions().isEmpty(), "Mission descriptions list should be empty");
    }

    @Test
    void testUpdatingListsWithDifferentSizes() {
        List<String> newEmployeNames = Arrays.asList("Alice", "Bob", "Charlie");
        departementDTO.setEmployeNames(newEmployeNames);
        assertEquals(3, departementDTO.getEmployeNames().size(), "There should be 3 employes now");

        List<String> newMissionDescriptions = Arrays.asList("Implement feature", "Test system");
        departementDTO.setMissionDescriptions(newMissionDescriptions);
        assertEquals(2, departementDTO.getMissionDescriptions().size(), "There should be 2 mission descriptions now");
    }
}
