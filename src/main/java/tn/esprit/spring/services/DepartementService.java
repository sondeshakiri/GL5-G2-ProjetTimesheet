package tn.esprit.spring.services;

import tn.esprit.spring.entities.Departement;

import java.util.List;

public interface DepartementService {
    List<Departement> findAll();
    Departement findById(int id);
    Departement save(Departement departement);
    Departement update(Departement departement);
    void delete(int id);
}

