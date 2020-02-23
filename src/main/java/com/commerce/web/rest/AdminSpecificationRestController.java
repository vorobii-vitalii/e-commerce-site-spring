package com.commerce.web.rest;

import com.commerce.web.dto.EditSpecificationDTO;
import com.commerce.web.dto.SpecificationDTO;
import com.commerce.web.exceptions.SpecificationNameIsTakenException;
import com.commerce.web.exceptions.SpecificationNotFoundException;
import com.commerce.web.exceptions.SpecificationResultIsEmptyException;
import com.commerce.web.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/admin/specifications")
public class AdminSpecificationRestController {

    private final SpecificationService specificationService;

    @Autowired
    public AdminSpecificationRestController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }


    @GetMapping(value = "")
    public ResponseEntity<List<SpecificationDTO>> getAllSpecifications() throws SpecificationResultIsEmptyException {
        return new ResponseEntity<>(specificationService.getAll().stream()
                .map(SpecificationDTO::fromSpecification)
                .collect(Collectors.toList()), HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<SpecificationDTO> getSpecificationById(@PathVariable @Positive Long id) throws SpecificationNotFoundException {
        return new ResponseEntity<>(SpecificationDTO.fromSpecification(specificationService.getById(id)), HttpStatus.OK);
    }


    @PostMapping(value = "/add")
    public ResponseEntity<SpecificationDTO> addSpecification(@Valid @RequestBody SpecificationDTO specificationDTO) throws SpecificationNameIsTakenException {
        return new ResponseEntity<>(specificationService.addSpecification(specificationDTO), HttpStatus.OK);
    }


    @PostMapping(value = "/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editSpecification(@PathVariable @Positive Long id, @Valid @RequestBody EditSpecificationDTO specificationDTO) throws SpecificationNotFoundException, SpecificationNameIsTakenException {
        specificationService.editById(id, specificationDTO);
    }


    @PostMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSpecification(@PathVariable @Positive Long id) throws SpecificationNotFoundException {
        specificationService.deleteById(id);
    }

}
