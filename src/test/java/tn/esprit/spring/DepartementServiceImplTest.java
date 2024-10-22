package tn.esprit.spring;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.services.DepartementServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartementServiceImplTest {

    @InjectMocks
    private DepartementServiceImpl departementService;

    @Mock
    private DepartementRepository departementRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Departement> departements = new ArrayList<>();
        departements.add(new Departement("IT"));
        departements.add(new Departement("HR"));

        when(departementRepository.findAll()).thenReturn(departements);

        List<Departement> result = departementService.findAll();
        assert(result.size() == 2);
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Departement departement = new Departement("IT");
        when(departementRepository.findById(anyInt())).thenReturn(Optional.of(departement));

        Departement result = departementService.findById(1);
        assert(result.getName().equals("IT"));
        verify(departementRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testSave() {
        Departement departement = new Departement("IT");
        when(departementRepository.save(any(Departement.class))).thenReturn(departement);

        Departement result = departementService.save(departement);
        assert(result.getName().equals("IT"));
        verify(departementRepository, times(1)).save(any(Departement.class));
    }

    @Test
    public void testUpdate() {
        Departement departement = new Departement("IT");
        when(departementRepository.save(any(Departement.class))).thenReturn(departement);

        Departement result = departementService.update(departement);
        assert(result.getName().equals("IT"));
        verify(departementRepository, times(1)).save(any(Departement.class));
    }

    @Test
    public void testDelete() {
        doNothing().when(departementRepository).deleteById(anyInt());
        departementService.delete(1);
        verify(departementRepository, times(1)).deleteById(anyInt());
    }
}
