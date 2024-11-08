package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.DTO.DepartementDTO;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.repository.DepartementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartementServiceImpl implements DepartementService {
	public DepartementDTO convertToDto(Departement departement) {
	    // Vérifier si le département est nul pour éviter un NullPointerException
	    if (departement == null) {
	        return null;  // Si le département est nul, retourner null.
	    }

	    // Gérer les listes nulles pour les employés
	    List<String> employeNames = (departement.getEmployes() != null) ?
	            departement.getEmployes().stream()
	                    .map(Employe::getNom)
	                    .collect(Collectors.toList()) : new ArrayList<>();

	    // Gérer les listes nulles pour les missions
	    List<String> missionDescriptions = (departement.getMissions() != null) ?
	            departement.getMissions().stream()
	                    .map(Mission::getDescription)
	                    .collect(Collectors.toList()) : new ArrayList<>();

	    // Créer le DTO avec les valeurs nécessaires
	    return new DepartementDTO(
	            departement.getId(),
	            departement.getName(),
	            departement.getEntreprise() != null ? departement.getEntreprise().getName() : null, // Vérification de null pour l'entreprise
	            employeNames,
	            missionDescriptions
	    );
	}



    @Autowired
    private DepartementRepository departementRepository;

    @Override
    public List<Departement> findAll() {
        return departementRepository.findAll();
    }

    @Override
    public Departement findById(int id) {
        return departementRepository.findById(id).orElse(null);
    }

    @Override
    public Departement save(Departement departement) {
        return departementRepository.save(departement);
    }
    public Departement update(Departement updatedDepartement) {
        if (!departementRepository.existsById(updatedDepartement.getId())) {
            return null; 
        }
        return departementRepository.save(updatedDepartement);
    }
  

    @Override
    public void delete(int id) {
        departementRepository.deleteById(id);
    }
 
}
