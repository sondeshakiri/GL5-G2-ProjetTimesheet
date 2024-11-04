package tn.esprit.spring.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEntrepriseService;
@WebMvcTest(RestControlEntreprise.class)
public class EntrepriseControllerTest {




        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private IEntrepriseService ientrepriseservice;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        void testAjouterEntreprise() throws Exception {
            Entreprise entreprise = new Entreprise();
               entreprise.setId(1);
            mockMvc.perform(post("/ajouterEntreprise")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entreprise)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("1"));

            verify(ientrepriseservice, times(1)).ajouterEntreprise(any(Entreprise.class));
        }

        @Test
        void testAffecterDepartementAEntreprise() throws Exception {
            int depId = 1;
            int entrepriseId = 1;

            mockMvc.perform(put("/affecterDepartementAEntreprise/{iddept}/{identreprise}", depId, entrepriseId))
                    .andExpect(status().isOk());

            verify(ientrepriseservice, times(1)).affecterDepartementAEntreprise(depId, entrepriseId);
        }

        @Test
        void testDeleteEntrepriseById() throws Exception {
            int entrepriseId = 1;

            mockMvc.perform(delete("/deleteEntrepriseById/{identreprise}", entrepriseId))
                    .andExpect(status().isOk());

            verify(ientrepriseservice, times(1)).deleteEntrepriseById(entrepriseId);
        }

        @Test
        void testGetEntrepriseById() throws Exception {
            int entrepriseId = 1;
            Entreprise entreprise = new Entreprise();
            entreprise.setId(entrepriseId);

            when(ientrepriseservice.getEntrepriseById(entrepriseId)).thenReturn(entreprise);

            mockMvc.perform(get("/getEntrepriseById/{identreprise}", entrepriseId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(entreprise.getId()))
                    .andExpect(jsonPath("$.name").value(entreprise.getName()))
                    .andExpect(jsonPath("$.raisonSocial").value(entreprise.getRaisonSocial()));

            verify(ientrepriseservice, times(1)).getEntrepriseById(entrepriseId);
        }

        @Test
        void testAjouterDepartement() throws Exception {
            Departement departement = new Departement();
            departement.setId(1);

            when(ientrepriseservice.ajouterDepartement(any(Departement.class))).thenReturn(1);

            mockMvc.perform(post("/ajouterDepartement")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(departement)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("1"));

            verify(ientrepriseservice, times(1)).ajouterDepartement(any(Departement.class));
        }

        @Test
        void testGetAllDepartementsNamesByEntreprise() throws Exception {
            int entrepriseId = 1;
            List<String> departmentNames = Arrays.asList("Telecom", "Finance");

            when(ientrepriseservice.getAllDepartementsNamesByEntreprise(entrepriseId)).thenReturn(departmentNames);

            mockMvc.perform(get("/getAllDepartementsNamesByEntreprise/{identreprise}", entrepriseId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0]").value("Telecom"))
                    .andExpect(jsonPath("$[1]").value("Finance"));

            verify(ientrepriseservice, times(1)).getAllDepartementsNamesByEntreprise(entrepriseId);
        }

        @Test
        void testDeleteDepartementById() throws Exception {
            int depId = 1;

            mockMvc.perform(delete("/deleteDepartementById/{iddept}", depId))
                    .andExpect(status().isOk());

            verify(ientrepriseservice, times(1)).deleteDepartementById(depId);
        }
    }


