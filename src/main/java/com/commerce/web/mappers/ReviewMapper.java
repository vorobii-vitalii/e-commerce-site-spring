package com.commerce.web.mappers;

import com.commerce.web.dto.ProductDTO;
import com.commerce.web.dto.ReviewDTO;
import com.commerce.web.dto.UserDTO;
import com.commerce.web.model.Review;

public final class ReviewMapper extends Mapper<Review, ReviewDTO> {

    @Override
    public Review from(ReviewDTO reviewDTO) {
        Review review = new Review();

        review.setId(reviewDTO.getId());
        review.setContent(reviewDTO.getContent());
        review.setRate(reviewDTO.getRate());
        if (reviewDTO.getAuthor() != null)
            review.setAuthor(reviewDTO.getAuthor().toUser());
        if (reviewDTO.getProduct() != null)
            review.setProduct(reviewDTO.getProduct().toProduct());
        review.setStatus(reviewDTO.getStatus());

        return review;
    }

    @Override
    public ReviewDTO to(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setId(review.getId());
        reviewDTO.setContent(review.getContent());
        reviewDTO.setRate(review.getRate());
        reviewDTO.setAuthor(UserDTO.fromUser(review.getAuthor()));
        reviewDTO.setProduct(ProductDTO.fromProduct(review.getProduct()));
        reviewDTO.setStatus(review.getStatus());

        return reviewDTO;
    }

}
