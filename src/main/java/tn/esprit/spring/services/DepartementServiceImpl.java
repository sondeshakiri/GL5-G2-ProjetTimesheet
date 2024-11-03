package tn.esprit.spring.services;

import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.repository.DepartementRepository;

import java.util.List;

@Service
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository departementRepository;

    // Constructor Injection
    public DepartementServiceImpl(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    @Override
    public List<Departement> findAll() {
        return departementRepository.findAll();
    }

    @Override
    public Departement findById(int id) {
        return departementRepository.findById(id).orElse(null);
    }

    @Override
    public Departement save(Departement departement) {
        return departementRepository.save(departement);
    }

    @Override
    public Departement update(Departement departement) {
        return departementRepository.save(departement);
    }

    @Override
    public void delete(int id) {
        departementRepository.deleteById(id);
    }
}
