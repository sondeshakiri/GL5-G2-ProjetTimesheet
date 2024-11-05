package tn.esprit.spring.dto;

public class EntrepriseDTO {
    private int id;
    private String name;
    private String raisonSocial;

    // Constructors
    public EntrepriseDTO() {}

    public EntrepriseDTO(int id, String name, String raisonSocial) {
        this.id = id;
        this.name = name;
        this.raisonSocial = raisonSocial;
    }

    // Getters and Setters
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

    public String getRaisonSocial() {
        return raisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }
}
