package tn.esprit.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.TimesheetServiceImpl;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TimesheetServiceImplTest {

    @InjectMocks
    TimesheetServiceImpl timesheetService;

    @Mock
    MissionRepository missionRepository;

    @Mock
    DepartementRepository departementRepository;

    @Mock
    TimesheetRepository timesheetRepository;

    @Mock
    EmployeRepository employeRepository;
    @Mock
    Role role; // Mocking the Role

    @Test
     void testAjouterMission() {
        Mission mission = new Mission("Mission A", "Description A");
        mission.setId(1);

        when(missionRepository.save(any(Mission.class))).thenReturn(mission);

        int missionId = timesheetService.ajouterMission(mission);

        assertEquals(mission.getId(), missionId);
        verify(missionRepository, times(1)).save(mission);
    }

    @Test
     void testAffecterMissionADepartement() {
        Mission mission = new Mission("Mission A", "Description A");
        mission.setId(1);

        Departement departement = new Departement("IT");
        departement.setId(1);

        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        timesheetService.affecterMissionADepartement(1, 1);

        assertEquals(departement, mission.getDepartement());
        verify(missionRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(1);
    }

    @Test
     void testAjouterTimesheet() {
        TimesheetPK timesheetPK = new TimesheetPK(1, 1, new Date(), new Date());
        Timesheet timesheet = new Timesheet();
        timesheet.setTimesheetPK(timesheetPK);

        timesheetService.ajouterTimesheet(1, 1, new Date(), new Date());

        verify(timesheetRepository, times(1)).save(any(Timesheet.class));
    }




}
