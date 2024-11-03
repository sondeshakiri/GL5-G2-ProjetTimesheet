package tn.esprit.spring.service;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
 class TimesheetServiceImplTestMariem {

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private DepartementRepository deptRepository;

    @Mock
    private TimesheetRepository timesheetRepository;

    @Mock
    private EmployeRepository employeRepository;

    @InjectMocks
    private TimesheetServiceImpl timesheetService;

    private Mission mission;
    private Departement departement;
    private Employe employe;
    private Timesheet timesheet;
    private TimesheetPK timesheetPK;
    private Date dateDebut;
    private Date dateFin;

    @BeforeEach
     void setUp() {
        mission = new Mission();
        mission.setId(1);
        mission.setName("Mission Test");

        departement = new Departement();
        departement.setId(1);
        mission.setDepartement(departement);

        employe = new Employe();
        employe.setId(1);
        employe.setRole(Role.CHEF_DEPARTEMENT);

        dateDebut = new Date();
        dateFin = new Date();

        timesheetPK = new TimesheetPK(1, 1, dateDebut, dateFin);
        timesheet = new Timesheet();
        timesheet.setTimesheetPK(timesheetPK);
    }

    @Test
     void testajouterMission() {
        when(missionRepository.save(any(Mission.class))).thenReturn(mission);

        int missionId = timesheetService.ajouterMission(mission);

        assertEquals(mission.getId(), missionId);
        verify(missionRepository, times(1)).save(mission);
    }

    @Test
     void testAffecterMissionADepartement() {
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(deptRepository.findById(1)).thenReturn(Optional.of(departement));

        timesheetService.affecterMissionADepartement(1, 1);

        verify(missionRepository, times(1)).findById(1);
        verify(deptRepository, times(1)).findById(1);
        assertEquals(departement, mission.getDepartement());
    }

    @Test
     void testAjouterTimesheet() {
        when(timesheetRepository.save(any(Timesheet.class))).thenReturn(timesheet);

        timesheetService.ajouterTimesheet(1, 1, dateDebut, dateFin);

        verify(timesheetRepository, times(1)).save(any(Timesheet.class));
        assertFalse(timesheet.isValide());
    }

    @Test
     void testValiderTimesheet_ChefDeLaMission() {
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(timesheetRepository.findBytimesheetPK(any(TimesheetPK.class))).thenReturn(timesheet);

        employe.setDepartements(Arrays.asList(departement));

        timesheetService.validerTimesheet(1, 1, dateDebut, dateFin, 1);

        assertTrue(timesheet.isValide());
        verify(timesheetRepository, times(1)).findBytimesheetPK(any(TimesheetPK.class));

        // Additional check for date formatting
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(dateFormat.format(dateDebut), dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
    }

    @Test
     void testValiderTimesheet_NotChefDeLaMission() {
        Employe nonChef = new Employe();
        nonChef.setId(2);
        nonChef.setRole(Role.EMPLOYE);

        when(employeRepository.findById(2)).thenReturn(Optional.of(nonChef));
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));

        timesheetService.validerTimesheet(1, 1, dateDebut, dateFin, 2);

        verify(timesheetRepository, never()).findBytimesheetPK(any(TimesheetPK.class));
    }

    @Test
     void testFindAllMissionByEmployeJPQL() {
        when(timesheetRepository.findAllMissionByEmployeJPQL(1)).thenReturn(Arrays.asList(mission));

        List<Mission> missions = timesheetService.findAllMissionByEmployeJPQL(1);

        assertEquals(1, missions.size());
        assertEquals("Mission Test", missions.get(0).getName());
        verify(timesheetRepository, times(1)).findAllMissionByEmployeJPQL(1);
    }

    @Test
     void testGetAllEmployeByMission() {
        when(timesheetRepository.getAllEmployeByMission(1)).thenReturn(Arrays.asList(employe));

        List<Employe> employes = timesheetService.getAllEmployeByMission(1);

        assertEquals(1, employes.size());
        assertEquals("CHEF_DEPARTEMENT", employes.get(0).getRole().name());
        verify(timesheetRepository, times(1)).getAllEmployeByMission(1);
    }
}
