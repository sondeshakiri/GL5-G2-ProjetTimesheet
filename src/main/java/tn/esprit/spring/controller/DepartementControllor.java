package tn.esprit.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.services.DepartementService;

import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementControllor {

    @Autowired
    private DepartementService departementService;

    @GetMapping
    public List<Departement> getAllDepartements() {
        return departementService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departement> getDepartementById(@PathVariable int id) {
        Departement departement = departementService.findById(id);
        return ResponseEntity.ok(departement);
    }

    @PostMapping
    public Departement createDepartement(@RequestBody Departement departement) {
        return departementService.save(departement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Departement> updateDepartement(@PathVariable int id, @RequestBody Departement departement) {
        departement.setId(id);
        Departement updatedDepartement = departementService.update(departement);
        return ResponseEntity.ok(updatedDepartement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable int id) {
        departementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
