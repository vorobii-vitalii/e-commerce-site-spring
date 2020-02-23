package com.commerce.web.service;

import com.commerce.web.dto.EditSpecificationDTO;
import com.commerce.web.dto.SpecificationDTO;
import com.commerce.web.exceptions.SpecificationNameIsTakenException;
import com.commerce.web.exceptions.SpecificationNotFoundByNameException;
import com.commerce.web.exceptions.SpecificationNotFoundException;
import com.commerce.web.exceptions.SpecificationResultIsEmptyException;
import com.commerce.web.model.Specification;

import java.util.List;

public interface SpecificationService {

    SpecificationDTO addSpecification(SpecificationDTO specificationDTO);

    List<Specification> getAll();

    Specification getById(Long id);

    Specification getByName(String name);

    void editById(Long id, EditSpecificationDTO editSpecificationDTO);

    void deleteById(Long id);

}
