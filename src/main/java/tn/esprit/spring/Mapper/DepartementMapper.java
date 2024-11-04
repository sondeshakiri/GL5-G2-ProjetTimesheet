package tn.esprit.spring.Mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tn.esprit.spring.Dtos.DepartementDTO;
import tn.esprit.spring.entities.Departement;

@Component
@AllArgsConstructor
public class DepartementMapper {
    private ModelMapper modelMapper;

        public DepartementDTO convertToDto(Departement departement) {
            return modelMapper.map(departement, DepartementDTO.class);
        }

        public Departement convertToEntity(DepartementDTO departementDTO) {
            return modelMapper.map(departementDTO, Departement.class);
        }
    }

