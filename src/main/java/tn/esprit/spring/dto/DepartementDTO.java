package tn.esprit.spring.dto;

public class DepartementDTO {
    private int id;
    private String name;
    private Integer entrepriseId; // Assuming only ID is needed for referencing the entreprise

    // Constructors, getters, and setters
    public DepartementDTO() {}

    public DepartementDTO(int id, String name, Integer entrepriseId) {
        this.id = id;
        this.name = name;
        this.entrepriseId = entrepriseId;
    }

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

    public Integer getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(Integer entrepriseId) {
        this.entrepriseId = entrepriseId;
    }
}
