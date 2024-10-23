package tn.esprit.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TimesheetServiceImplTestMockito {

    
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Sample data setup
        mission = new Mission();
        mission.setId(1);
        departement = new Departement();
        departement.setId(1);
        employe = new Employe();
        employe.setId(1);
        employe.setRole(Role.CHEF_DEPARTEMENT);
        timesheetPK = new TimesheetPK();
        timesheetPK.setIdMission(1);
        timesheetPK.setIdEmploye(1);
        timesheetPK.setDateDebut(new Date());
        timesheetPK.setDateFin(new Date());
        timesheet = new Timesheet();
        timesheet.setTimesheetPK(timesheetPK);
        timesheet.setValide(false);
    }

    @Test
    public void testAjouterMission() {
        when(missionRepository.save(any(Mission.class))).thenReturn(mission);

        int result = timesheetService.ajouterMission(mission);

        assertEquals(1, result);
        verify(missionRepository, times(1)).save(mission);
    }

    @Test
    public void testAffecterMissionADepartement() {
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(deptRepository.findById(1)).thenReturn(Optional.of(departement));

        timesheetService.affecterMissionADepartement(1, 1);

        verify(missionRepository, times(1)).save(mission);
        assertEquals(departement, mission.getDepartement());
    }

    @Test
    public void testAjouterTimesheet() {
        when(timesheetRepository.save(any(Timesheet.class))).thenReturn(timesheet);

        timesheetService.ajouterTimesheet(1, 1, new Date(), new Date());

        verify(timesheetRepository, times(1)).save(any(Timesheet.class));
    }

    @Test
    public void testValiderTimesheetAsChefDepartement() {
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(employeRepository.findById(1)).thenReturn(Optional.of(employe));
        when(timesheetRepository.findBytimesheetPK(any(TimesheetPK.class))).thenReturn(timesheet);

        timesheetService.validerTimesheet(1, 1, new Date(), new Date(), 1);

        assertTrue(timesheet.isValide());
        verify(timesheetRepository, times(1)).findBytimesheetPK(any(TimesheetPK.class));
    }

   

}
