package tn.esprit.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.services.IDepartementService;

import java.util.List;

@RestController
public class RestControllerDepartement {
    @Autowired
    private IDepartementService iDepartementService;
    @PostMapping
    public ResponseEntity<?> ajouterDepartement(@RequestBody Departement departement) {
        return new ResponseEntity<>(iDepartementService.ajouterDepartement(departement), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Departement>> getAllDepartements() {
        return new ResponseEntity<>(iDepartementService.getAllDepartements(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> affecterDepartementAEntreprise(int depId,int entrepriseId) {
        iDepartementService.affecterDepartementAEntreprise(depId, entrepriseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteDepartementById(int departementId) {
        iDepartementService.deleteDepartementById(departementId);
        return new ResponseEntity<>(HttpStatus.OK);

}

}

