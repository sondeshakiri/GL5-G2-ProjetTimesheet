package tn.esprit.spring.service;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.services.DepartementServiceImp;
import tn.esprit.spring.services.EntrepriseServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class Integration_testsDepartementServiceImpl {

        @Autowired
        private DepartementServiceImp departementService;

        @Autowired
        private DepartementRepository departementRepository;
        @Autowired
        private EntrepriseServiceImpl entrepriseService;


        @Test
        public void testAjouterDepartementIntegration() {
            Departement departement = new Departement();
            departement.setName("IT");

            int id = departementService.ajouterDepartement(departement);

            assertNotNull(departementRepository.findById(id).orElse(null));
        }

        @Test
        public void testGetAllDepartementsIntegration() {
            Departement departement1 = new Departement();
            departement1.setName("IT");
            departementService.ajouterDepartement(departement1);

            Departement departement2 = new Departement();
            departement2.setName("HR");
            departementService.ajouterDepartement(departement2);

            List<Departement> departements = departementService.getAllDepartements();

            assertEquals(2, departements.size());
        }

        @Test
        public void testDeleteDepartementByIdIntegration() {
            Departement departement = new Departement();
            departement.setName("IT");
            int id = departementService.ajouterDepartement(departement);

            departementService.deleteDepartementById(id);

            assertFalse(departementRepository.findById(id).isPresent());
        }

        @Test
        public void testAffecterDepartementAEntrepriseIntegration() {
        Departement departement = new Departement();
        departement.setName("IT");
        int id = departementService.ajouterDepartement(departement);
            Entreprise entreprise = new Entreprise();
            entreprise.setName("Societe");
            int idEntreprise = entrepriseService.ajouterEntreprise(entreprise);
            departementService.affecterDepartementAEntreprise(id, idEntreprise);
            assertEquals(entreprise, departementRepository.findById(id).get().getEntreprise());
    }}


