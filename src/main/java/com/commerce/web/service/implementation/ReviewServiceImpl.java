package com.commerce.web.service.implementation;

import com.commerce.web.dto.ReviewDTO;
import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.exceptions.ReviewNotFoundException;
import com.commerce.web.exceptions.ReviewOwnerWrongException;
import com.commerce.web.exceptions.ReviewResultEmptyException;
import com.commerce.web.mappers.ReviewMapper;
import com.commerce.web.model.Product;
import com.commerce.web.model.Review;
import com.commerce.web.model.Status;
import com.commerce.web.model.User;
import com.commerce.web.repository.ProductRepository;
import com.commerce.web.repository.ReviewRepository;
import com.commerce.web.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.reviewMapper = new ReviewMapper();
    }

    @Override
    public List<ReviewDTO> getByProduct(Long productId)  {

        Product product = productRepository.findById(productId).orElse(null);

        if (product == null)
            throw new ProductNotFoundException("Product with id " + productId + " was not found");

        List<Review> foundReviews = reviewRepository.findByProductAndParentNull(product);

        if (foundReviews.isEmpty())
            throw new ReviewResultEmptyException("Reviews were not found");

        List<ReviewDTO> foundReviewDTOs = foundReviews.stream().map(reviewMapper::to).collect(Collectors.toList());

        log.info("Fetched reviews: {}", foundReviews);
        return foundReviewDTOs;
    }

    @Override
    public ReviewDTO getById(Long id) {

        Review review = reviewRepository.findById(id).orElse(null);

        log.info("FOUND REVIEW {}", review);

        if (review == null) {
            throw new ReviewNotFoundException("Review with id " + id + " not found");
        }

        ReviewDTO reviewDTO = reviewMapper.to(review);

        log.info("Fetched review {} by id {}", reviewDTO, id);

        return reviewDTO;
    }

    @Override
    public List<ReviewDTO> getByParent(Long id) {

        Review parentReview = reviewRepository.findById(id).orElseThrow(() -> {
            throw new ReviewNotFoundException("Parent review with id " + id + " was not found");
        });

        List<Review> childrenReviews = parentReview.getChildren();

        if (childrenReviews.isEmpty())
            throw new ReviewResultEmptyException("Children reviews were not found");

        List<ReviewDTO> childrenReviewDTOs = childrenReviews.stream().map(reviewMapper::to).collect(Collectors.toList());

        log.info("Fetched children reviews {} by parent id {}", childrenReviewDTOs, parentReview);

        return childrenReviewDTOs;
    }

    @Override
    public ReviewDTO addReview(Long productId, ReviewDTO reviewDTO, User requestUser) {

        Review reviewToAdd = reviewMapper.from(reviewDTO);
        reviewToAdd.setAuthor(requestUser);

        Product product = productRepository.findById(productId).orElseThrow(() -> {
            throw new ProductNotFoundException("Product was not found");
        });

        reviewToAdd.setStatus(Status.ACTIVE);
        reviewToAdd.setProduct(product);

        ReviewDTO addedReview = reviewMapper.to(reviewRepository.save(reviewToAdd));

        log.info("Added review {}", addedReview);

        return addedReview;
    }

    @Override
    public ReviewDTO addReviewToParent(ReviewDTO reviewDTO, Long parentId, User requestUser) {

        Review reviewToAdd = reviewMapper.from(reviewDTO);
        reviewToAdd.setAuthor(requestUser);

        Review parentReview = reviewRepository.findById(parentId).orElseThrow(() -> {
            throw new ReviewNotFoundException("Parent review with id " + parentId + " not found.");
        });

        // Set not-null parent to review
        reviewToAdd.setParent(parentReview);

        // Set product
        reviewToAdd.setProduct(parentReview.getProduct());

        // Set status to active
        reviewToAdd.setStatus(Status.ACTIVE);

        // Save review in database
        ReviewDTO savedReview = reviewMapper.to(reviewRepository.save(reviewToAdd));

        log.info("Added review {} to parent review {}", savedReview, reviewMapper.to(parentReview));

        return savedReview;
    }

    @Override
    public ReviewDTO editReview(ReviewDTO reviewDTO, Long id, User requestUser) {

        Review reviewToEdit = reviewRepository.getById(id);

        if (reviewToEdit == null)
            throw new ReviewNotFoundException("Review with id " + id + " was not found");

        if (!reviewToEdit.getAuthor().equals(requestUser))
            throw new ReviewOwnerWrongException("You have no permission");

        if (reviewDTO.getRate() != null)
            reviewToEdit.setRate(reviewDTO.getRate());

        if (reviewDTO.getContent() != null)
            reviewToEdit.setContent(reviewDTO.getContent());

        ReviewDTO savedReviewDTO = reviewMapper.to(reviewRepository.save(reviewToEdit));

        log.info("Edited review {}", savedReviewDTO);

        return savedReviewDTO;
    }

    @Override
    public void deleteReview(Long id, User requestUser) {

        Review reviewToDelete = reviewRepository.getById(id);

        if (reviewToDelete == null)
            throw new ReviewNotFoundException("Review with id " + id + " was not found");

        if (!reviewToDelete.getAuthor().equals(requestUser))
            throw new ReviewOwnerWrongException("You have no permission");

        reviewToDelete.setStatus(Status.DELETED);

        Review deletedReview = reviewRepository.save(reviewToDelete);

        log.info("Deleted review {}", reviewMapper.to(deletedReview));
    }

}
