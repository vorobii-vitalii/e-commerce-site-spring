package com.commerce.web.repository;

import com.commerce.web.model.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecificationRepository extends JpaRepository<Specification,Long> {

    Specification getSpecificationByName(String name);

    Specification getSpecificationById(Long id);

}
