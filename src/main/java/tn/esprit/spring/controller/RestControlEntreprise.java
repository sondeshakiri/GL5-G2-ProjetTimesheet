package tn.esprit.spring.controller;

import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.dto.EntrepriseDTO;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEntrepriseService;

import java.util.List;

@RestController
public class RestControlEntreprise {

	private final IEntrepriseService ientrepriseservice;

	// Constructor Injection
	public RestControlEntreprise(IEntrepriseService ientrepriseservice) {
		this.ientrepriseservice = ientrepriseservice;
	}

	// Add an enterprise
	@PostMapping("/ajouterEntreprise")
	public int ajouterEntreprise(@RequestBody EntrepriseDTO entrepriseDTO) {
		var entreprise = mapToEntity(entrepriseDTO);
		ientrepriseservice.ajouterEntreprise(entreprise);
		return entreprise.getId();
	}

	// Assign department to enterprise
	@PutMapping(value = "/affecterDepartementAEntreprise/{iddept}/{identreprise}")
	public void affecterDepartementAEntreprise(@PathVariable("iddept") int depId, @PathVariable("identreprise") int entrepriseId) {
		ientrepriseservice.affecterDepartementAEntreprise(depId, entrepriseId);
	}

	// Delete an enterprise by ID
	@DeleteMapping("/deleteEntrepriseById/{identreprise}")
	public void deleteEntrepriseById(@PathVariable("identreprise") int entrepriseId) {
		ientrepriseservice.deleteEntrepriseById(entrepriseId);
	}

	// Get an enterprise by ID
	@GetMapping(value = "getEntrepriseById/{identreprise}")
	public EntrepriseDTO getEntrepriseById(@PathVariable("identreprise") int entrepriseId) {
		var entreprise = ientrepriseservice.getEntrepriseById(entrepriseId);
		return mapToDTO(entreprise);
	}

	// Add a department
	@PostMapping("/ajouterDepartement")
	public int ajouterDepartement(@RequestBody Departement dep) {
		return ientrepriseservice.ajouterDepartement(dep);
	}

	// Get all department names by enterprise ID
	@GetMapping(value = "getAllDepartementsNamesByEntreprise/{identreprise}")
	public List<String> getAllDepartementsNamesByEntreprise(@PathVariable("identreprise") int entrepriseId) {
		return ientrepriseservice.getAllDepartementsNamesByEntreprise(entrepriseId);
	}

	// Delete a department by ID
	@DeleteMapping("/deleteDepartementById/{iddept}")
	public void deleteDepartementById(@PathVariable("iddept") int depId) {
		ientrepriseservice.deleteDepartementById(depId);
	}

	// Utility methods to map between Entreprise and EntrepriseDTO
	private EntrepriseDTO mapToDTO(Entreprise entreprise) {
		return new EntrepriseDTO(entreprise.getId(), entreprise.getName(), entreprise.getRaisonSocial());
	}

	private Entreprise mapToEntity(EntrepriseDTO entrepriseDTO) {
		var entreprise = new Entreprise();
		entreprise.setId(entrepriseDTO.getId());
		entreprise.setName(entrepriseDTO.getName());
		entreprise.setRaisonSocial(entrepriseDTO.getRaisonSocial());
		return entreprise;
	}
}
