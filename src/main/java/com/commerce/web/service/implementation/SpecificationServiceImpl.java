package com.commerce.web.service.implementation;

import com.commerce.web.dto.EditSpecificationDTO;
import com.commerce.web.dto.SpecificationDTO;
import com.commerce.web.exceptions.SpecificationNameIsTakenException;
import com.commerce.web.exceptions.SpecificationNotFoundByNameException;
import com.commerce.web.exceptions.SpecificationNotFoundException;
import com.commerce.web.exceptions.SpecificationResultIsEmptyException;
import com.commerce.web.model.Specification;
import com.commerce.web.model.Status;
import com.commerce.web.repository.SpecificationRepository;
import com.commerce.web.service.SpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
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

        Specification addedSpecification = specificationRepository.save ( specificationDTO.toSpecification () );

        log.info ( "Added specification {}", addedSpecification );
    }

    @Override
    public List<Specification> getAll () throws SpecificationResultIsEmptyException {

        List<Specification> specifications = specificationRepository.findAll ();

        if (specifications.isEmpty ())
            throw new SpecificationResultIsEmptyException ( "Specifications were not found" );

        log.info ( "Fetched all specifications {}", specifications );

        return specifications;
    }

    @Override
    public Specification getById ( Long id ) throws SpecificationNotFoundException {

        Specification foundSpecification = specificationRepository.findById ( id ).orElse ( null );

        if(foundSpecification == null)
            throw new SpecificationNotFoundException ( "Specification with id " + id + " was not found" );

        log.info ( "Got specification {} by id {}", foundSpecification, id );

        return foundSpecification;
    }

    @Override
    public Specification getByName ( String name ) throws SpecificationNotFoundByNameException {

        Specification foundSpecification = specificationRepository.getSpecificationByName ( name );

        if (foundSpecification == null)
            throw new SpecificationNotFoundByNameException ( name );

        log.info ( "Got specification {} by name {}", foundSpecification, name );

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

        if (providedName != null && !providedName.trim ().equals ( "" ) ) {

            Specification specificationByProvidedName = specificationRepository.getSpecificationByName ( providedName );

            if (specificationByProvidedName != null)
                throw new SpecificationNameIsTakenException ( providedName );

            foundSpecification.setName ( providedName );
        }

        if (providedFormattedName != null && !providedFormattedName.trim ().equals ( "" ) ) {
            foundSpecification.setFormattedName ( providedFormattedName );
        }

        if (providedStatus != null) {
            foundSpecification.setStatus ( providedStatus );
        }

        specificationRepository.save ( foundSpecification );

        log.info ( "Edited specification {}",foundSpecification );
    }

    @Override
    public void deleteById ( Long id ) throws SpecificationNotFoundException {

        Specification specificationToDelete = specificationRepository.getSpecificationById ( id );

        if (specificationToDelete == null)
            throw new SpecificationNotFoundException ( "Specification with id " + id + " was not found");

        specificationToDelete.setStatus ( Status.DELETED );

        specificationRepository.save ( specificationToDelete );

        log.info ( "Deleted specification {}", specificationToDelete );
    }


}
