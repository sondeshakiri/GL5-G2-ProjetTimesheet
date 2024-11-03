package tn.esprit.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.services.DepartementService;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/departements")
public class DepartementControllor {


    private final DepartementService departementService;

    // API pour obtenir tous les départements
    @GetMapping("/all")
    public List<Departement> getAllDepartements() {
        return departementService.findAll();
    }

    // API pour obtenir un département par son ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Departement> getDepartementById(@PathVariable int id) {
        Departement departement = departementService.findById(id);
        return ResponseEntity.ok(departement);
    }

    // API pour créer un nouveau département
    @PostMapping("/create")
    public Departement createDepartement(@RequestBody Departement departement) {
        return departementService.save(departement);
    }

    // API pour mettre à jour un département existant
    @PutMapping("/update/{id}")
    public ResponseEntity<Departement> updateDepartement(@PathVariable int id, @RequestBody Departement departement) {
        departement.setId(id);
        Departement updatedDepartement = departementService.update(departement);
        return ResponseEntity.ok(updatedDepartement);
    }

    // API pour supprimer un département par son ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable int id) {
        departementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
