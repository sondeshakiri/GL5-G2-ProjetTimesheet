package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@RestController
public class RestControlEntreprise {

	private final IEntrepriseService ientrepriseservice;

	// Constructor Injection
	public RestControlEntreprise( IEntrepriseService ientrepriseservice) {
		this.ientrepriseservice = ientrepriseservice;
	}

	// Add an enterprise
	@PostMapping("/ajouterEntreprise")
	@ResponseBody
	public int ajouterEntreprise(@RequestBody Entreprise ssiiConsulting) {
		ientrepriseservice.ajouterEntreprise(ssiiConsulting);
		return ssiiConsulting.getId();
	}

	// Assign department to enterprise
	@PutMapping(value = "/affecterDepartementAEntreprise/{iddept}/{identreprise}")
	public void affecterDepartementAEntreprise(@PathVariable("iddept") int depId, @PathVariable("identreprise") int entrepriseId) {
		ientrepriseservice.affecterDepartementAEntreprise(depId, entrepriseId);
	}

	// Delete an enterprise by ID
	@DeleteMapping("/deleteEntrepriseById/{identreprise}")
	@ResponseBody
	public void deleteEntrepriseById(@PathVariable("identreprise") int entrepriseId) {
		ientrepriseservice.deleteEntrepriseById(entrepriseId);
	}

	// Get an enterprise by ID
	@GetMapping(value = "getEntrepriseById/{identreprise}")
	@ResponseBody
	public Entreprise getEntrepriseById(@PathVariable("identreprise") int entrepriseId) {
		return ientrepriseservice.getEntrepriseById(entrepriseId);
	}

	// Add a department
	@PostMapping("/ajouterDepartement")
	@ResponseBody
	public int ajouterDepartement(@RequestBody Departement dep) {
		return ientrepriseservice.ajouterDepartement(dep);
	}

	// Get all department names by enterprise ID
	@GetMapping(value = "getAllDepartementsNamesByEntreprise/{identreprise}")
	@ResponseBody
	public List<String> getAllDepartementsNamesByEntreprise(@PathVariable("identreprise") int entrepriseId) {
		return ientrepriseservice.getAllDepartementsNamesByEntreprise(entrepriseId);
	}

	// Delete a department by ID
	@DeleteMapping("/deleteDepartementById/{iddept}")
	@ResponseBody
	public void deleteDepartementById(@PathVariable("iddept") int depId) {
		ientrepriseservice.deleteDepartementById(depId);
	}
}
