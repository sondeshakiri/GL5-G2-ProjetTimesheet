package tn.esprit.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.dto.DepartementDTO;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.DepartementService;
import tn.esprit.spring.services.IEntrepriseService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/departements")
public class DepartementControllor {

    private final DepartementService departementService;
    private final IEntrepriseService entrepriseService;

    // Constructor Injection
    public DepartementControllor(DepartementService departementService, IEntrepriseService entrepriseService) {
        this.departementService = departementService;
        this.entrepriseService = entrepriseService;
    }

    // API to get all departments
    @GetMapping("/all")
    public List<DepartementDTO> getAllDepartements() {
        return departementService.findAll().stream()
                .map(this::convertToDTO)
                .toList(); // Replaced 'collect(Collectors.toList())' with 'toList()'
    }


    // API to get a department by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<DepartementDTO> getDepartementById(@PathVariable int id) {
        Departement departement = departementService.findById(id);
        return ResponseEntity.ok(convertToDTO(departement));
    }

    // API to create a new department
    @PostMapping("/create")
    public DepartementDTO createDepartement(@RequestBody DepartementDTO departementDTO) {
        Departement departement = convertToEntity(departementDTO);
        return convertToDTO(departementService.save(departement));
    }

    // API to update an existing department
    @PutMapping("/update/{id}")
    public ResponseEntity<DepartementDTO> updateDepartement(@PathVariable int id, @RequestBody DepartementDTO departementDTO) {
        Departement departement = convertToEntity(departementDTO);
        departement.setId(id);
        Departement updatedDepartement = departementService.update(departement);
        return ResponseEntity.ok(convertToDTO(updatedDepartement));
    }

    // //API to delete a department by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable int id) {
        departementService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Conversion methods
    private DepartementDTO convertToDTO(Departement departement) {
        Integer entrepriseId = (departement.getEntreprise() != null) ? departement.getEntreprise().getId() : null;
        return new DepartementDTO(departement.getId(), departement.getName(), entrepriseId);
    }

    private Departement convertToEntity(DepartementDTO dto) {
        Departement departement = new Departement();
        departement.setId(dto.getId());
        departement.setName(dto.getName());

        if (dto.getEntrepriseId() != null) {
            Entreprise entreprise = entrepriseService.getEntrepriseById(dto.getEntrepriseId());
            departement.setEntreprise(entreprise);
        }

        return departement;
    }
}
