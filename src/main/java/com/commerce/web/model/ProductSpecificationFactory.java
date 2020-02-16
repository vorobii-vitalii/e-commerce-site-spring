package com.commerce.web.model;

public final class ProductSpecificationFactory {

    public ProductSpecificationFactory() {
    }

    public static ProductSpecification create(Product product, Specification specification, String value) {

        ProductSpecification productSpecification = new ProductSpecification ();

        productSpecification.setProduct ( product );
        productSpecification.setSpecification ( specification );
        productSpecification.setValue ( value );

        return productSpecification;
    }

}
