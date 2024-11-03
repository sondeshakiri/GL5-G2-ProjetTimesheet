package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class TimesheetServiceImpl implements ITimesheetService {

	private static final Logger logger = LogManager.getLogger(TimesheetServiceImpl.class);

	private final MissionRepository missionRepository;
	private final DepartementRepository departementRepository;
	private final TimesheetRepository timesheetRepository;
	private final EmployeRepository employeRepository;

	// Constructor Injection
	public TimesheetServiceImpl(MissionRepository missionRepository, DepartementRepository departementRepository,
								TimesheetRepository timesheetRepository, EmployeRepository employeRepository) {
		this.missionRepository = missionRepository;
		this.departementRepository = departementRepository;
		this.timesheetRepository = timesheetRepository;
		this.employeRepository = employeRepository;
	}

	public int ajouterMission(Mission mission) {
		missionRepository.save(mission);
		return mission.getId();
	}

	public void affecterMissionADepartement(int missionId, int depId) {
		Mission mission = missionRepository.findById(missionId).orElse(null);
		Departement dep = departementRepository.findById(depId).orElse(null);

		if (mission != null && dep != null) {
			mission.setDepartement(dep);
			missionRepository.save(mission);
		}
	}

	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);

		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); // Default to not validated
		timesheetRepository.save(timesheet);
	}

	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		logger.info("In validerTimesheet method");

		Employe validateur = employeRepository.findById(validateurId).orElse(null);
		Mission mission = missionRepository.findById(missionId).orElse(null);

		if (validateur == null || mission == null) {
			logger.error("Validateur or Mission not found.");
			return;
		}

		// Check if the employee is a department head
		if (!Role.CHEF_DEPARTEMENT.equals(validateur.getRole())) {
			logger.warn("Employee must be a department head to validate a timesheet!");
			return;
		}

		// Check if they are the department head of the mission's department
		boolean chefDeLaMission = validateur.getDepartements().stream()
				.anyMatch(dep -> dep.getId() == mission.getDepartement().getId());

		if (!chefDeLaMission) {
			logger.warn("Employee must be the department head of the mission's department.");
			return;
		}

		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet = timesheetRepository.findBytimesheetPK(timesheetPK);

		if (timesheet != null) {
			timesheet.setValide(true);

			// Read a date from the database with conditional formatting
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			logger.info("dateDebut : {}", () -> dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
		}
	}

	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
	}

	public List<Employe> getAllEmployeByMission(int missionId) {
		return timesheetRepository.getAllEmployeByMission(missionId);
	}
}
