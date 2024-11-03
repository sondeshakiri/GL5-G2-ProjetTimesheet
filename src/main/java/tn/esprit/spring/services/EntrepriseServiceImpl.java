package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

	private static final Logger l = LogManager.getLogger(EntrepriseServiceImpl.class);

	private final EntrepriseRepository entrepriseRepository;
	private final DepartementRepository departementRepository;

	// Constructor Injection
	public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository, DepartementRepository departementRepository) {
		this.entrepriseRepository = entrepriseRepository;
		this.departementRepository = departementRepository;
	}

	public int ajouterEntreprise(Entreprise entreprise) {
		entrepriseRepository.save(entreprise);
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		departementRepository.save(dep);
		return dep.getId();
	}

	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		Entreprise entrepriseManagedEntity = entrepriseRepository.findById(entrepriseId).orElse(null);
		Departement depManagedEntity = departementRepository.findById(depId).orElse(null);

		if (entrepriseManagedEntity != null && depManagedEntity != null) {
			depManagedEntity.setEntreprise(entrepriseManagedEntity);
			departementRepository.save(depManagedEntity);
		}
	}

	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Entreprise entrepriseManagedEntity = entrepriseRepository.findById(entrepriseId).orElse(null);
		List<String> depNames = new ArrayList<>();

		if (entrepriseManagedEntity != null) {
			for (Departement dep : entrepriseManagedEntity.getDepartements()) {
				depNames.add(dep.getName());
			}
		}

		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		entrepriseRepository.findById(entrepriseId).ifPresent(entrepriseRepository::delete);
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		departementRepository.findById(depId).ifPresent(departementRepository::delete);
	}

	public Entreprise getEntrepriseById(int entrepriseId) {
		l.info("méthode: getEntrepriseById");
		return entrepriseRepository.findById(entrepriseId).orElse(null);
	}
}
