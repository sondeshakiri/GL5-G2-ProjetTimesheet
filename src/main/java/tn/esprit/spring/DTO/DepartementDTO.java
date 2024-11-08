package tn.esprit.spring.DTO;

import java.io.Serializable;
import java.util.List;

public class DepartementDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String name;
    private String entrepriseName;
    private List<String> employeNames;
    private List<String> missionDescriptions;

    // Constructeurs
    public DepartementDTO(int id, String name, String entrepriseName, List<String> employeNames, List<String> missionDescriptions) {
        this.id = id;
        this.name = name;
        this.entrepriseName = entrepriseName;
        this.employeNames = employeNames;
        this.missionDescriptions = missionDescriptions;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntrepriseName() {
        return entrepriseName;
    }

    public void setEntrepriseName(String entrepriseName) {
        this.entrepriseName = entrepriseName;
    }

    public List<String> getEmployeNames() {
        return employeNames;
    }

    public void setEmployeNames(List<String> employeNames) {
        this.employeNames = employeNames;
    }

    public List<String> getMissionDescriptions() {
        return missionDescriptions;
    }

    public void setMissionDescriptions(List<String> missionDescriptions) {
        this.missionDescriptions = missionDescriptions;
    }
}
