package tn.esprit.spring.controller;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@RestController
public class RestControlTimesheet {

	private final IEmployeService iemployeservice;
	private final IEntrepriseService ientrepriseservice;
	private final ITimesheetService itimesheetservice;

	// Constructor Injection
	public RestControlTimesheet(IEmployeService iemployeservice, IEntrepriseService ientrepriseservice, ITimesheetService itimesheetservice) {
		this.iemployeservice = iemployeservice;
		this.ientrepriseservice = ientrepriseservice;
		this.itimesheetservice = itimesheetservice;
	}

	// API to add a mission
	@PostMapping("/ajouterMission")
	@ResponseBody
	public int ajouterMission(@RequestBody Mission mission) {
		itimesheetservice.ajouterMission(mission);
		return mission.getId();
	}

	// Assign mission to department
	@PutMapping(value = "/affecterMissionADepartement/{idmission}/{iddept}")
	public void affecterMissionADepartement(@PathVariable("idmission") int missionId, @PathVariable("iddept") int depId) {
		itimesheetservice.affecterMissionADepartement(missionId, depId);
	}

	// Add timesheet
	@PostMapping("/ajouterTimesheet/{idmission}/{idemp}/{dated}/{datef}")
	@ResponseBody
	public void ajouterTimesheet(@PathVariable("idmission") int missionId, @PathVariable("idemp") int employeId,
								 @PathVariable("dated") Date dateDebut, @PathVariable("datef") Date dateFin) {
		itimesheetservice.ajouterTimesheet(missionId, employeId, dateDebut, dateFin);
	}

	// Validate timesheet
	@PutMapping(value = "/validerTimesheet/{idmission}/{iddept}")
	public void validerTimesheet(@PathVariable("idmission") int missionId, @PathVariable("iddept") int depId,
								 @RequestParam Date dateDebut, @RequestParam Date dateFin, @RequestParam int validateurId) {
		itimesheetservice.validerTimesheet(missionId, depId, dateDebut, dateFin, validateurId);
	}

	// Find all missions by employee
	@GetMapping(value = "findAllMissionByEmployeJPQL/{idemp}")
	@ResponseBody
	public List<Mission> findAllMissionByEmployeJPQL(@PathVariable("idemp") int employeId) {
		return itimesheetservice.findAllMissionByEmployeJPQL(employeId);
	}

	// Get all employees by mission
	@GetMapping(value = "getAllEmployeByMission/{idmission}")
	@ResponseBody
	public List<Employe> getAllEmployeByMission(@PathVariable("idmission") int missionId) {
		return itimesheetservice.getAllEmployeByMission(missionId);
	}
}
