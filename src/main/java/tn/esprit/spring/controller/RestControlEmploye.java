package tn.esprit.spring.controller;

import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.dto.EmployeDTO;
import tn.esprit.spring.dto.ContratDTO;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employe")
public class RestControlEmploye {

	private final IEmployeService iemployeservice;
	private final IEntrepriseService ientrepriseservice;

	public RestControlEmploye(IEmployeService iemployeservice, IEntrepriseService ientrepriseservice) {
		this.iemployeservice = iemployeservice;
		this.ientrepriseservice = ientrepriseservice;
	}

	// API to add an employee
	@PostMapping("/ajouter")
	public EmployeDTO ajouterEmploye(@RequestBody EmployeDTO employeDTO) {
		Employe employe = mapToEntity(employeDTO);
		iemployeservice.addOrUpdateEmploye(employe);
		return mapToDTO(employe);
	}

	// Update employee email
	@PutMapping("/modifyEmail/{id}/{newemail}")
	public void mettreAjourEmailByEmployeId(@PathVariable("newemail") String email, @PathVariable("id") int employeId) {
		iemployeservice.mettreAjourEmailByEmployeId(email, employeId);
	}

	// Assign employee to department
	@PutMapping("/affecterEmployeADepartement/{idemp}/{iddept}")
	public void affecterEmployeADepartement(@PathVariable("idemp") int employeId, @PathVariable("iddept") int depId) {
		iemployeservice.affecterEmployeADepartement(employeId, depId);
	}

	// Unassign employee from department
	@PutMapping("/desaffecterEmployeDuDepartement/{idemp}/{iddept}")
	public void desaffecterEmployeDuDepartement(@PathVariable("idemp") int employeId, @PathVariable("iddept") int depId) {
		iemployeservice.desaffecterEmployeDuDepartement(employeId, depId);
	}

	// Add contract
	@PostMapping("/ajouterContrat")
	public int ajouterContrat(@RequestBody ContratDTO contratDTO) {
		Contrat contrat = mapToEntity(contratDTO);
		iemployeservice.ajouterContrat(contrat);
		return contrat.getReference();
	}

	// Assign contract to employee
	@PutMapping("/affecterContratAEmploye/{idcontrat}/{idemp}")
	public void affecterContratAEmploye(@PathVariable("idcontrat") int contratId, @PathVariable("idemp") int employeId) {
		iemployeservice.affecterContratAEmploye(contratId, employeId);
	}

	// Get employee first name by ID
	@GetMapping("/getEmployePrenomById/{idemp}")
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
	@GetMapping("/getNombreEmployeJPQL")
	public int getNombreEmployeJPQL() {
		return iemployeservice.getNombreEmployeJPQL();
	}

	// Get all employee names
	@GetMapping("/getAllEmployeNamesJPQL")
	public List<String> getAllEmployeNamesJPQL() {
		return iemployeservice.getAllEmployeNamesJPQL();
	}

	// Get all employees in an enterprise
	@GetMapping("/getAllEmployeByEntreprise/{identreprise}")
	public List<EmployeDTO> getAllEmployeByEntreprise(@PathVariable("identreprise") int identreprise) {
		return iemployeservice.getAllEmployeByEntreprise(ientrepriseservice.getEntrepriseById(identreprise))
				.stream()
				.map(this::mapToDTO)
				.toList(); // Using `toList()` for simplicity and efficiency
	}

	// Update email with JPQL
	@PutMapping("/mettreAjourEmailByEmployeIdJPQL/{id}/{newemail}")
	public void mettreAjourEmailByEmployeIdJPQL(@PathVariable("newemail") String email, @PathVariable("id") int employeId) {
		iemployeservice.mettreAjourEmailByEmployeIdJPQL(email, employeId);
	}

	// Delete all contracts with JPQL
	@DeleteMapping("/deleteAllContratJPQL")
	public void deleteAllContratJPQL() {
		iemployeservice.deleteAllContratJPQL();
	}

	// Get salary by employee ID with JPQL
	@GetMapping("/getSalaireByEmployeIdJPQL/{idemp}")
	public float getSalaireByEmployeIdJPQL(@PathVariable("idemp") int employeId) {
		return iemployeservice.getSalaireByEmployeIdJPQL(employeId);
	}

	// Get average salary by department ID
	@GetMapping("/getSalaireMoyenByDepartementId/{iddept}")
	public Double getSalaireMoyenByDepartementId(@PathVariable("iddept") int departementId) {
		return iemployeservice.getSalaireMoyenByDepartementId(departementId);
	}

	// Get timesheets by mission and date
	@GetMapping("/getTimesheetsByMissionAndDate")
	public List<Timesheet> getTimesheetsByMissionAndDate(@RequestParam Employe employe, @RequestParam Mission mission,
														 @RequestParam Date dateDebut, @RequestParam Date dateFin) {
		return iemployeservice.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	// Get all employees
	@GetMapping("/getAllEmployes")
	public List<EmployeDTO> getAllEmployes() {
		return iemployeservice.getAllEmployes().stream()
				.map(this::mapToDTO)
				.toList();
	}

	// Utility methods to map between Employe and EmployeDTO
	private EmployeDTO mapToDTO(Employe employe) {
		return new EmployeDTO(employe.getId(), employe.getPrenom(), employe.getNom(),
				employe.getEmail(), employe.isActif(), employe.getRole().name());
	}

	private Employe mapToEntity(EmployeDTO employeDTO) {
		Employe employe = new Employe();
		employe.setId(employeDTO.getId());
		employe.setPrenom(employeDTO.getPrenom());
		employe.setNom(employeDTO.getNom());
		employe.setEmail(employeDTO.getEmail());
		employe.setActif(employeDTO.isActif());
		employe.setRole(Role.valueOf(employeDTO.getRole()));
		return employe;
	}

	private ContratDTO mapToDTO(Contrat contrat) {
		return new ContratDTO(contrat.getReference(), contrat.getDateDebut(), contrat.getTypeContrat(), contrat.getSalaire());
	}

	private Contrat mapToEntity(ContratDTO contratDTO) {
		Contrat contrat = new Contrat();
		contrat.setReference(contratDTO.getReference());
		contrat.setDateDebut(contratDTO.getDateDebut());
		contrat.setTypeContrat(contratDTO.getTypeContrat());
		contrat.setSalaire(contratDTO.getSalaire());
		return contrat;
	}
}
