package com.commerce.web.service.implementation;

import com.commerce.web.dto.AddProductRequestDTO;
import com.commerce.web.dto.ProductDTO;
import com.commerce.web.dto.ProductSpecificationDTO;
import com.commerce.web.dto.SpecificationDTO;
import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.exceptions.ProductsResultIsEmptyException;
import com.commerce.web.exceptions.SpecificationNotFoundByNameException;
import com.commerce.web.model.*;
import com.commerce.web.repository.ProductRepository;
import com.commerce.web.repository.UserRepository;
import com.commerce.web.service.ProductService;
import com.commerce.web.service.SpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SpecificationService specificationService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,UserRepository userRepository,SpecificationService specificationService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.specificationService = specificationService;
    }

    @Override
    public void addProduct ( AddProductRequestDTO addProductRequestDTO, User author ) throws SpecificationNotFoundByNameException {

        Product product = productRepository.save ( addProductRequestDTO.toProduct ());

        List<ProductSpecificationDTO> specificationDTOList = addProductRequestDTO.getProductSpecifications ();

        if (specificationDTOList != null) {

            List<ProductSpecification> productSpecifications = new ArrayList<> ();

            for (ProductSpecificationDTO psD: specificationDTOList) {

                Specification specification = specificationService.getByName ( psD.getName () );

                productSpecifications.add ( ProductSpecificationFactory.create ( product, specification, psD.getValue () ));
            }

            product.setProductSpecifications ( productSpecifications );
            productRepository.save ( product );
        }

        log.info ( "Added product {}", product );
    }

    @Override
    public List<Product> getAll () throws ProductsResultIsEmptyException {

        List<Product> fetchedProducts = productRepository.findAll ();

        if (fetchedProducts.isEmpty ())
            throw new ProductsResultIsEmptyException ( "Products were not found" );

        log.info ( "Fetched products {}",fetchedProducts);

        return fetchedProducts;
    }

    @Override
    public Product getById ( Long id ) throws ProductNotFoundException {

        Product foundProduct = productRepository.findById ( id ).orElse ( null );

        if (foundProduct == null)
            throw new ProductNotFoundException ( "Product with id " + id + " was not found" );

        log.info ( "Fetched product {} by id {}",foundProduct,id);

        return foundProduct;
    }

    @Override
    public void deleteById ( Long id ) throws ProductNotFoundException {

        Product foundProduct = productRepository.findById ( id ).orElse ( null );

        if (foundProduct == null)
            throw new ProductNotFoundException ( "Product with id " + id + " was not found" );

        foundProduct.setStatus ( Status.DELETED );

        log.info ( "Deleted product by id {}",id );
    }

}
