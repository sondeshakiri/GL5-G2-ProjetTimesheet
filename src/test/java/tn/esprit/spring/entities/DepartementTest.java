package tn.esprit.spring.entities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartementTest {

    @InjectMocks
    private Departement departement;

    @Mock
    private Entreprise entreprise;

    @Mock
    private List<Employe> employes;

    @Mock
    private List<Mission> missions;

    @BeforeEach
    void setUp() {
        departement = new Departement("IT Department");
        departement.setEmployes(employes);
        departement.setMissions(missions);
        departement.setEntreprise(entreprise);
    }

    @Test
    void testGetName() {
        assertEquals("IT Department", departement.getName());
    }

    @Test
    void testSetName() {
        departement.setName("HR Department");
        assertEquals("HR Department", departement.getName());
    }

    @Test
    void testGetEmployes() {
        // mock the list of employes
        when(employes.size()).thenReturn(5);

        assertNotNull(departement.getEmployes());
        assertEquals(5, departement.getEmployes().size());
    }

    @Test
    void testSetEmployes() {
        List<Employe> mockEmployes = Arrays.asList(mock(Employe.class), mock(Employe.class));
        departement.setEmployes(mockEmployes);
        assertEquals(2, departement.getEmployes().size());
    }

    @Test
    void testGetMissions() {
        // mock the list of missions
        when(missions.size()).thenReturn(3);

        assertNotNull(departement.getMissions());
        assertEquals(3, departement.getMissions().size());
    }

    @Test
    void testSetMissions() {
        List<Mission> mockMissions = Arrays.asList(mock(Mission.class), mock(Mission.class));
        departement.setMissions(mockMissions);
        assertEquals(2, departement.getMissions().size());
    }

    @Test
    void testGetEntreprise() {
        assertEquals(entreprise, departement.getEntreprise());
    }

    @Test
    void testSetEntreprise() {
        Entreprise mockEntreprise = mock(Entreprise.class);
        departement.setEntreprise(mockEntreprise);
        assertEquals(mockEntreprise, departement.getEntreprise());
    }

    @Test
    void testConstructor() {
        Departement dep = new Departement("Sales Department");
        assertEquals("Sales Department", dep.getName());
    }
}
