package com.commerce.web.service;

import com.commerce.web.dto.EditSpecificationDTO;
import com.commerce.web.dto.SpecificationDTO;
import com.commerce.web.exceptions.SpecificationNameIsTakenException;
import com.commerce.web.exceptions.SpecificationNotFoundByNameException;
import com.commerce.web.exceptions.SpecificationNotFoundException;
import com.commerce.web.model.Specification;

public interface SpecificationService {

    void addSpecification( SpecificationDTO specificationDTO ) throws SpecificationNameIsTakenException;

    Specification getByName(String name) throws SpecificationNotFoundByNameException;

    void editById( Long id, EditSpecificationDTO editSpecificationDTO ) throws SpecificationNotFoundException, SpecificationNameIsTakenException;

    void deleteById(Long id) throws SpecificationNotFoundException;

}
