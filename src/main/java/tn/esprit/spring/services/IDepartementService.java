package tn.esprit.spring.services;

import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Departement;

import java.util.List;


public interface IDepartementService {
    int ajouterDepartement(Departement departement);

    List<Departement> getAllDepartements();

    void affecterDepartementAEntreprise(int depId, int entrepriseId);

    void deleteDepartementById(int departementId);
}
