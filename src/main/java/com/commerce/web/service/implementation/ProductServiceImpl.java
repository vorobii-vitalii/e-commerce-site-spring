package com.commerce.web.service.implementation;

import com.commerce.web.dto.AddProductRequestDTO;
import com.commerce.web.dto.EditProductRequestDTO;
import com.commerce.web.dto.AddProductSpecificationDTO;
import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.exceptions.ProductsResultIsEmptyException;
import com.commerce.web.exceptions.SpecificationNotFoundByNameException;
import com.commerce.web.model.*;
import com.commerce.web.repository.ProductRepository;
import com.commerce.web.repository.ProductSpecificationRepository;
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
    private final ProductSpecificationRepository productSpecificationRepository;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            UserRepository userRepository,
            SpecificationService specificationService,
            ProductSpecificationRepository productSpecificationRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.specificationService = specificationService;
        this.productSpecificationRepository = productSpecificationRepository;
    }

    @Override
    public void addProduct ( AddProductRequestDTO addProductRequestDTO , User author ) throws SpecificationNotFoundByNameException {

        Product product = productRepository.save ( addProductRequestDTO.toProduct ());

        List<AddProductSpecificationDTO> specificationDTOList = addProductRequestDTO.getProductSpecifications ();

        if (specificationDTOList != null) {

            List<ProductSpecification> productSpecifications = new ArrayList<> ();

            for (AddProductSpecificationDTO psD: specificationDTOList) {

                Specification specification = specificationService.getByName ( psD.getName () );

                ProductSpecification productSpecification = ProductSpecificationFactory.create ( product, specification, psD.getValue () );

                productSpecifications.add ( productSpecificationRepository.save ( productSpecification ) );
            }

            product.setProductSpecifications ( productSpecifications );
        }

        product.setUser ( author );

        productRepository.save ( product );

        log.info ( "Added product" );
    }

    @Override
    public void editProductById ( Long id , EditProductRequestDTO editProductRequestDTO ) throws ProductNotFoundException, SpecificationNotFoundByNameException {

        Product product = productRepository.findById ( id ).orElse ( null );

        if(product == null)
            throw new ProductNotFoundException ( "Product with id " + id + " not found." );

        String providedName = editProductRequestDTO.getName ();
        String providedDescription = editProductRequestDTO.getDescription ();
        Double providedCost = editProductRequestDTO.getCost ();
        Status providedStatus = editProductRequestDTO.getStatus ();
        List<AddProductSpecificationDTO> addProductSpecificationDTOList = editProductRequestDTO.getProductSpecifications ();

        if (providedName != null && !providedName.trim ().equals ( "" )) {
            product.setName ( providedName );
        }

        if (providedDescription != null && !providedDescription.trim ().equals ( "" )) {
            product.setDescription ( providedDescription );
        }

        if (providedCost != null && providedCost > 0) {
            product.setCost ( providedCost );
        }

        if (providedStatus != null) {
            product.setStatus ( providedStatus );
        }

        if ( addProductSpecificationDTOList != null) {

            List<ProductSpecification> productSpecifications = new ArrayList<> ();

            // Fetch old product specs
            List<ProductSpecification> oldProductSpecifications = product.getProductSpecifications ();

            try {
                for (AddProductSpecificationDTO psD: addProductSpecificationDTOList) {

                    Specification specification = specificationService.getByName ( psD.getName () );

                    ProductSpecification productSpecification = ProductSpecificationFactory.create ( product, specification, psD.getValue () );

                    productSpecifications.add ( productSpecificationRepository.save ( productSpecification ) );
                }
            }
            catch(SpecificationNotFoundByNameException e) {

                productSpecifications.stream ().forEach ( productSpecification -> {
                    productSpecificationRepository.delete ( productSpecification );
                });
                throw new SpecificationNotFoundByNameException ( "Wrong specification name" );
            }

            // Delete old product specifications
            oldProductSpecifications.stream ().forEach ( oldProductSpecification -> {
                productSpecificationRepository.delete ( oldProductSpecification );
            });

            product.setProductSpecifications ( productSpecifications );
        }


        Product savedProduct = productRepository.save ( product );

        log.info ( "Edited product ");
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

        productRepository.save ( foundProduct );

        log.info ( "Deleted product by id {}",id );
    }

}
