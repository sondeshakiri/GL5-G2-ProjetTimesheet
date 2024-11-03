package tn.esprit.spring.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	private final EmployeRepository employeRepository;
	private final DepartementRepository deptRepository;
	private final ContratRepository contratRepository;
	private final TimesheetRepository timesheetRepository;

	// Constructor Injection
	public EmployeServiceImpl(EmployeRepository employeRepository, DepartementRepository deptRepository,
							  ContratRepository contratRepository, TimesheetRepository timesheetRepository) {
		this.employeRepository = employeRepository;
		this.deptRepository = deptRepository;
		this.contratRepository = contratRepository;
		this.timesheetRepository = timesheetRepository;
	}

	@Override
	public Employe authenticate(String login, String password) {
		return employeRepository.getEmployeByEmailAndPassword(login, password);
	}

	@Override
	public int addOrUpdateEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Employe employe = employeRepository.findById(employeId).orElse(null);
		if (employe != null) {
			employe.setEmail(email);
			employeRepository.save(employe);
		}
	}

	@Transactional
	public void affecterEmployeADepartement(int employeId, int depId) {
		Departement depManagedEntity = deptRepository.findById(depId).orElse(null);
		Employe employeManagedEntity = employeRepository.findById(employeId).orElse(null);
		if (depManagedEntity != null && employeManagedEntity != null) {
			depManagedEntity.getEmployes().add(employeManagedEntity);
		}
	}

	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		Departement dep = deptRepository.findById(depId).orElse(null);
		if (dep != null) {
			dep.getEmployes().removeIf(emp -> emp.getId() == employeId);
		}
	}

	public int ajouterContrat(Contrat contrat) {
		contratRepository.save(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		Contrat contratManagedEntity = contratRepository.findById(contratId).orElse(null);
		Employe employeManagedEntity = employeRepository.findById(employeId).orElse(null);
		if (contratManagedEntity != null && employeManagedEntity != null) {
			contratManagedEntity.setEmploye(employeManagedEntity);
			contratRepository.save(contratManagedEntity);
		}
	}

	public String getEmployePrenomById(int employeId) {
		Employe employeManagedEntity = employeRepository.findById(employeId).orElse(null);
		return employeManagedEntity != null ? employeManagedEntity.getPrenom() : null;
	}

	public void deleteEmployeById(int employeId) {
		Employe employe = employeRepository.findById(employeId).orElse(null);
		if (employe != null) {
			employe.getDepartements().forEach(dep -> dep.getEmployes().remove(employe));
			employeRepository.delete(employe);
		}
	}

	public void deleteContratById(int contratId) {
		Contrat contratManagedEntity = contratRepository.findById(contratId).orElse(null);
		if (contratManagedEntity != null) {
			contratRepository.delete(contratManagedEntity);
		}
	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();
	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);
	}

	public void deleteAllContratJPQL() {
		employeRepository.deleteAllContratJPQL();
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut, Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
		return (List<Employe>) employeRepository.findAll();
	}
}
