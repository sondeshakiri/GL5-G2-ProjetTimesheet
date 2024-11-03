package tn.esprit.spring.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.TimesheetServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TimesheetServiceImplTest {

    @InjectMocks
    private TimesheetServiceImpl timesheetService;

    @Mock
    private MissionRepository missionRepository;
    @Mock
    private DepartementRepository departementRepository;
    @Mock
    private TimesheetRepository timesheetRepository;
    @Mock
    private EmployeRepository employeRepository;

    private Mission mission;
    private Departement departement;
    private Employe employe;
    private Timesheet timesheet;
    private TimesheetPK timesheetPK;

    @BeforeEach
    public void setup() {
        mission = new Mission();
        mission.setId(1);
        departement = new Departement();
        departement.setId(1);
        employe = new Employe();
        employe.setId(1);
        employe.setRole(Role.CHEF_DEPARTEMENT);
        timesheetPK = new TimesheetPK(1, 1, new Date(), new Date());
        timesheet = new Timesheet();
        timesheet.setTimesheetPK(timesheetPK);
        timesheet.setValide(false);
    }

    @Test
    public void testAjouterMission() {
        when(missionRepository.save(any(Mission.class))).thenReturn(mission);
        int missionId = timesheetService.ajouterMission(mission);
        assertEquals(1, missionId);
        verify(missionRepository, times(1)).save(mission);
    }

    @Test
    public void testAffecterMissionADepartement() {
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        timesheetService.affecterMissionADepartement(1, 1);

        verify(missionRepository, times(1)).save(mission);
        assertEquals(departement, mission.getDepartement());
    }

    @Test
    public void testAjouterTimesheet() {
        timesheetService.ajouterTimesheet(1, 1, new Date(), new Date());
        verify(timesheetRepository, times(1)).save(any(Timesheet.class));
    }

    @Test
    public void testValiderTimesheet_ChefDepartement() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(timesheetRepository.findBytimesheetPK(timesheetPK)).thenReturn(timesheet);

        timesheetService.validerTimesheet(1, 1, new Date(), new Date(), 1);

        assertTrue(timesheet.isValide());
    }

    @Test
    public void testValiderTimesheet_NonChefDepartement() {
        employe.setRole(Role.EMPLOYE); // Change role to not chef
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));

        timesheetService.validerTimesheet(1, 1, new Date(), new Date(), 1);

        assertFalse(timesheet.isValide());
        verify(timesheetRepository, never()).save(any(Timesheet.class));
    }

    @Test
    public void testValiderTimesheet_ChefMaisNotOfMission() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(timesheetRepository.findBytimesheetPK(timesheetPK)).thenReturn(timesheet);

        // Setting mission to another department
        Departement anotherDepartement = new Departement();
        anotherDepartement.setId(2);
        mission.setDepartement(anotherDepartement);

        timesheetService.validerTimesheet(1, 1, new Date(), new Date(), 1);

        assertFalse(timesheet.isValide());
        verify(timesheetRepository, never()).save(any(Timesheet.class));
    }

    @Test
    public void testFindAllMissionByEmployeJPQL() {
        when(timesheetRepository.findAllMissionByEmployeJPQL(1)).thenReturn(Arrays.asList(mission));
        List<Mission> missions = timesheetService.findAllMissionByEmployeJPQL(1);
        assertEquals(1, missions.size());
    }

    @Test
    public void testGetAllEmployeByMission() {
        when(timesheetRepository.getAllEmployeByMission(1)).thenReturn(Arrays.asList(employe));
        List<Employe> employes = timesheetService.getAllEmployeByMission(1);
        assertEquals(1, employes.size());
    }

    // Edge Cases
    @Test
    public void testAffecterMissionADepartement_MissionNotFound() {
        when(missionRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            timesheetService.affecterMissionADepartement(1, 1);
        });
    }

    @Test
    public void testAffecterMissionADepartement_DepartementNotFound() {
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(departementRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            timesheetService.affecterMissionADepartement(1, 1);
        });
    }

    @Test
    public void testValiderTimesheet_MissionNotFound() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));
        when(missionRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            timesheetService.validerTimesheet(1, 1, new Date(), new Date(), 1);
        });
    }

    @Test
    public void testValiderTimesheet_TimesheetNotFound() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(timesheetRepository.findBytimesheetPK(timesheetPK)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            timesheetService.validerTimesheet(1, 1, new Date(), new Date(), 1);
        });
    }

}
