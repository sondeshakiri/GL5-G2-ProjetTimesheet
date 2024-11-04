package tn.esprit.spring.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import java.util.List;
@Data
public class DepartementDTO {

    private int id;
    private String name;
    @JsonIgnore
    private List<Employe> employes;
    private List<Mission> missions;
    private Entreprise entreprise;

}
