package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

import java.util.List;

@Service
public class DepartementServiceImp implements IDepartementService {


    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private EntrepriseRepository entrepriseRepository;


   public int ajouterDepartement(Departement departement){
      departementRepository.save(departement);
            return departement.getId();
    }
    public List<Departement> getAllDepartements(){
        return (List<Departement>) departementRepository.findAll();
    }
   public void affecterDepartementAEntreprise(int depId, int entrepriseId){
        Departement departement= departementRepository.findById(depId).get();
        Entreprise entreprise=entrepriseRepository.findById(entrepriseId).get();
        departement.setEntreprise(entreprise);
        departementRepository.save(departement);
    }
    public void deleteDepartementById(int departementId){
        departementRepository.deleteById(departementId);
    }

}
