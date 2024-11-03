package tn.esprit.spring.controller;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

@RestController
public class RestControlEmploye {

	private final IEmployeService iemployeservice;
	private final IEntrepriseService ientrepriseservice;

	// Constructor Injection
	public RestControlEmploye(IEmployeService iemployeservice, IEntrepriseService ientrepriseservice) {
		this.iemployeservice = iemployeservice;
		this.ientrepriseservice = ientrepriseservice;
	}

	// API to add an employee
	@PostMapping("/ajouterEmployer")
	public Employe ajouterEmploye(@RequestBody Employe employe) {
		iemployeservice.addOrUpdateEmploye(employe);
		return employe;
	}

	// Update email
	@PutMapping(value = "/modifyEmail/{id}/{newemail}")
	public void mettreAjourEmailByEmployeId(@PathVariable("newemail") String email, @PathVariable("id") int employeId) {
		iemployeservice.mettreAjourEmailByEmployeId(email, employeId);
	}

	// Assign employee to department
	@PutMapping(value = "/affecterEmployeADepartement/{idemp}/{iddept}")
	public void affecterEmployeADepartement(@PathVariable("idemp") int employeId, @PathVariable("iddept") int depId) {
		iemployeservice.affecterEmployeADepartement(employeId, depId);
	}

	// Unassign employee from department
	@PutMapping(value = "/desaffecterEmployeDuDepartement/{idemp}/{iddept}")
	public void desaffecterEmployeDuDepartement(@PathVariable("idemp") int employeId, @PathVariable("iddept") int depId) {
		iemployeservice.desaffecterEmployeDuDepartement(employeId, depId);
	}

	// Add contract
	@PostMapping("/ajouterContrat")
	public int ajouterContrat(@RequestBody Contrat contrat) {
		iemployeservice.ajouterContrat(contrat);
		return contrat.getReference();
	}

	// Assign contract to employee
	@PutMapping(value = "/affecterContratAEmploye/{idcontrat}/{idemp}")
	public void affecterContratAEmploye(@PathVariable("idcontrat") int contratId, @PathVariable("idemp") int employeId) {
		iemployeservice.affecterContratAEmploye(contratId, employeId);
	}

	// Get employee first name by ID
	@GetMapping(value = "getEmployePrenomById/{idemp}")
	public String getEmployePrenomById(@PathVariable("idemp") int employeId) {
		return iemployeservice.getEmployePrenomById(employeId);
	}

	// Delete employee by ID
	@DeleteMapping("/deleteEmployeById/{idemp}")
	public void deleteEmployeById(@PathVariable("idemp") int employeId) {
		iemployeservice.deleteEmployeById(employeId);
	}

	// Delete contract by ID
	@DeleteMapping("/deleteContratById/{idcontrat}")
	public void deleteContratById(@PathVariable("idcontrat") int contratId) {
		iemployeservice.deleteContratById(contratId);
	}

	// Get total number of employees
	@GetMapping(value = "getNombreEmployeJPQL")
	public int getNombreEmployeJPQL() {
		return iemployeservice.getNombreEmployeJPQL();
	}

	// Get all employee names
	@GetMapping(value = "getAllEmployeNamesJPQL")
	public List<String> getAllEmployeNamesJPQL() {
		return iemployeservice.getAllEmployeNamesJPQL();
	}

	// Get all employees in an enterprise
	@GetMapping(value = "getAllEmployeByEntreprise/{identreprise}")
	public List<Employe> getAllEmployeByEntreprise(@PathVariable("identreprise") int identreprise) {
		Entreprise entreprise = ientrepriseservice.getEntrepriseById(identreprise);
		return iemployeservice.getAllEmployeByEntreprise(entreprise);
	}

	// Update email with JPQL
	@PutMapping(value = "/mettreAjourEmailByEmployeIdJPQL/{id}/{newemail}")
	public void mettreAjourEmailByEmployeIdJPQL(@PathVariable("newemail") String email, @PathVariable("id") int employeId) {
		iemployeservice.mettreAjourEmailByEmployeIdJPQL(email, employeId);
	}

	// Delete all contracts with JPQL
	@DeleteMapping("/deleteAllContratJPQL")
	public void deleteAllContratJPQL() {
		iemployeservice.deleteAllContratJPQL();
	}

	// Get salary by employee ID with JPQL
	@GetMapping(value = "getSalaireByEmployeIdJPQL/{idemp}")
	public float getSalaireByEmployeIdJPQL(@PathVariable("idemp") int employeId) {
		return iemployeservice.getSalaireByEmployeIdJPQL(employeId);
	}

	// Get average salary by department ID
	@GetMapping(value = "getSalaireMoyenByDepartementId/{iddept}")
	public Double getSalaireMoyenByDepartementId(@PathVariable("iddept") int departementId) {
		return iemployeservice.getSalaireMoyenByDepartementId(departementId);
	}

	// Get timesheets by mission and date
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut, Date dateFin) {
		return iemployeservice.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	// Get all employees
	@GetMapping(value = "/getAllEmployes")
	public List<Employe> getAllEmployes() {
		return iemployeservice.getAllEmployes();
	}
}
