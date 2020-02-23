package com.commerce.web.service;

import com.commerce.web.dto.ReviewDTO;
import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.model.User;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getByProduct(Long productId);

    ReviewDTO getById(Long id);

    List<ReviewDTO> getByParent(Long id);

    ReviewDTO addReview(Long productId, ReviewDTO reviewDTO, User requestUser);

    ReviewDTO addReviewToParent(ReviewDTO reviewDTO, Long parentId, User requestUser);

    ReviewDTO editReview(ReviewDTO reviewDTO, Long id, User requestUser);

    void deleteReview(Long id, User requestUser);

}
