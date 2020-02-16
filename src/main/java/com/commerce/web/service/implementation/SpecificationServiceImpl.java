package com.commerce.web.service.implementation;

import com.commerce.web.dto.EditSpecificationDTO;
import com.commerce.web.dto.SpecificationDTO;
import com.commerce.web.exceptions.SpecificationNameIsTakenException;
import com.commerce.web.exceptions.SpecificationNotFoundByNameException;
import com.commerce.web.exceptions.SpecificationNotFoundException;
import com.commerce.web.model.Specification;
import com.commerce.web.model.Status;
import com.commerce.web.repository.SpecificationRepository;
import com.commerce.web.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;

public class SpecificationServiceImpl implements SpecificationService {

    private final SpecificationRepository specificationRepository;

    @Autowired
    public SpecificationServiceImpl(SpecificationRepository specificationRepository) {
        this.specificationRepository = specificationRepository;
    }

    @Override
    public void addSpecification ( SpecificationDTO specificationDTO ) throws SpecificationNameIsTakenException {

        String name = specificationDTO.getName ();

        if (specificationRepository.getSpecificationByName ( name) != null )
            throw new SpecificationNameIsTakenException ( "Specification with name " + name + " is taken");

        specificationRepository.save ( specificationDTO.toSpecification () );
    }

    @Override
    public Specification getByName ( String name ) throws SpecificationNotFoundByNameException {

        Specification foundSpecification = specificationRepository.getSpecificationByName ( name );

        if (foundSpecification == null)
            throw new SpecificationNotFoundByNameException ( name );

        return foundSpecification;
    }

    @Override
    public void editById ( Long id , EditSpecificationDTO editSpecificationDTO ) throws SpecificationNotFoundException, SpecificationNameIsTakenException {

        Specification foundSpecification = specificationRepository.getSpecificationById ( id );

        if (foundSpecification == null)
            throw new SpecificationNotFoundException ( "Specification with id " + id + " was not found");

        String providedName = editSpecificationDTO.getName ();
        String providedFormattedName = editSpecificationDTO.getFormattedName ();
        Status providedStatus = editSpecificationDTO.getStatus ();

        if (providedName != null ) {

            Specification specificationByProvidedName = specificationRepository.getSpecificationByName ( providedName );

            if (specificationByProvidedName != null)
                throw new SpecificationNameIsTakenException ( providedName );

            foundSpecification.setName ( providedName );
        }

        if (providedFormattedName != null) {
            foundSpecification.setFormattedName ( providedFormattedName );
        }

        if (providedStatus != null) {
            foundSpecification.setStatus ( providedStatus );
        }

        specificationRepository.save ( foundSpecification );
    }

    @Override
    public void deleteById ( Long id ) throws SpecificationNotFoundException {

        Specification foundSpecification = specificationRepository.getSpecificationById ( id );

        if (foundSpecification == null)
            throw new SpecificationNotFoundException ( "Specification with id " + id + " was not found");

        foundSpecification.setStatus ( Status.DELETED );
    }

}
