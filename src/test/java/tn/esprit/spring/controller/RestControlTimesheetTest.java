package tn.esprit.spring.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.services.ITimesheetService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
 class RestControlTimesheetTest {

    @InjectMocks
    RestControlTimesheet restControlTimesheet;

    @Mock
    ITimesheetService itimesheetservice;

    @Test
     void testAjouterMission() {
        Mission mission = new Mission("mamission", "c ma mission");

        when(itimesheetservice.ajouterMission(any(Mission.class))).thenReturn(mission.getId());

        int missionId = restControlTimesheet.ajouterMission(mission);

        assertEquals(mission.getId(), missionId);
        verify(itimesheetservice, times(1)).ajouterMission(mission);
    }

    @Test
     void testAffecterMissionADepartement() {
        doNothing().when(itimesheetservice).affecterMissionADepartement(1, 1);

        restControlTimesheet.affecterMissionADepartement(1, 1);

        verify(itimesheetservice, times(1)).affecterMissionADepartement(1, 1);
    }

    @Test
     void testValiderTimesheet() {
        // Using matchers for all arguments
        doNothing().when(itimesheetservice).validerTimesheet(eq(1), eq(1), any(), any(), eq(1));

        restControlTimesheet.validerTimesheet(1, 1, null, null, 1);

        verify(itimesheetservice, times(1)).validerTimesheet(eq(1), eq(1), any(), any(), eq(1));
    }


    @Test
     void testGetAllEmployeByMission() {
        restControlTimesheet.getAllEmployeByMission(1);

        verify(itimesheetservice, times(1)).getAllEmployeByMission(1);
    }
}
