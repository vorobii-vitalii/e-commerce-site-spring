package com.commerce.web.validators;

import com.commerce.web.dto.ReviewDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ReviewValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ReviewDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ReviewDTO review = (ReviewDTO) o;

        Integer rate = review.getRate();
        String content = review.getContent();

        if (rate == null && content == null)
            errors.reject("Request is empty");
    }

}
