package com.commerce.web.service.implementation;

import com.commerce.web.dto.ReviewDTO;
import com.commerce.web.exceptions.*;
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

    public static boolean isEmptyRequest(ReviewDTO reviewDTO) {
        return reviewDTO.getContent() == null && reviewDTO.getRate() == null && reviewDTO.getStatus() == null;
    }

    @Override
    public List<ReviewDTO> getByProduct(Long productId)  {

        Product product = productRepository.findById(productId).orElse(null);

        log.info("PRODUCT {}", product);

        if (product == null)
            throw new ProductNotFoundException("Product with id " + productId + " was not found");

        //List<Review> foundReviews = reviewRepository.findByProductAndParentNull(product);
        List<Review> foundReviews = reviewRepository.findAll();

        if (foundReviews.isEmpty())
            throw new ReviewResultEmptyException("Reviews were not found");

        log.info("Fetched reviews: {}", foundReviews);

        return foundReviews.stream().map(reviewMapper::to).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getById(Long id) {

        Review review = reviewRepository.findById(id).orElse(null);

        log.info("FOUND REVIEW {}", review);

        if (review == null)
            throw new ReviewNotFoundException("Review with id " + id + " not found");

        log.info("Fetched review {} by id {}", review, id);

        return reviewMapper.to(review);
    }

    @Override
    public List<ReviewDTO> getByParent(Long id) {

        Review parentReview = reviewRepository.findById(id).orElse(null);

        if (parentReview == null)
            throw new ReviewNotFoundException("Parent review with id " + id + " was not found");

        List<Review> childrenReviews = parentReview.getChildren();

        if (childrenReviews.isEmpty())
            throw new ReviewResultEmptyException("Children reviews were not found");

        log.info("Fetched children comments {} by parent id {}", childrenReviews, parentReview);

        return childrenReviews.stream().map(reviewMapper::to).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO addReview(Long productId, ReviewDTO reviewDTO, User requestUser) {

        if (ReviewServiceImpl.isEmptyRequest(reviewDTO))
            throw new ReviewEmptyRequestException("Empty request");

        Review reviewToAdd = reviewMapper.from(reviewDTO);
        reviewToAdd.setAuthor(requestUser);

        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) throw new ProductNotFoundException("Product was not found");
        reviewToAdd.setProduct(product);

        reviewToAdd.setStatus(Status.ACTIVE);

        Review addedReview = reviewRepository.save(reviewToAdd);
        log.info("Added review {}", addedReview);

        return reviewMapper.to(addedReview);
    }

    @Override
    public ReviewDTO addReviewToParent(ReviewDTO reviewDTO, Long parentId, User requestUser) {

        if (ReviewServiceImpl.isEmptyRequest(reviewDTO))
            throw new ReviewEmptyRequestException("Empty request");

        log.info("REQUEST giAN {} PARENT IDD {}", reviewDTO, parentId);

        Review reviewToAdd = reviewMapper.from(reviewDTO);
        reviewToAdd.setAuthor(requestUser);

        Review parentReview = reviewRepository.findById(parentId).orElse(null);

        if (parentReview == null)
            throw new ReviewNotFoundException("Parent review with id " + parentId + " not found.");

        // Set not-null parent to review
        reviewToAdd.setParent(parentReview);

        // Set product
        reviewToAdd.setProduct(parentReview.getProduct());

        // Set status to active
        reviewToAdd.setStatus(Status.ACTIVE);

        // Save review in database
        Review savedReview = reviewRepository.save(reviewToAdd);

        log.info("Added review {} to parent review {}", savedReview, parentReview);

        return reviewMapper.to(savedReview);
    }

    @Override
    public ReviewDTO editReview(ReviewDTO reviewDTO, Long id, User requestUser) {

        if (ReviewServiceImpl.isEmptyRequest(reviewDTO))
            throw new ReviewEmptyRequestException("Empty request");

        Review reviewToEdit = reviewRepository.findById(id).orElse(null);

        if (reviewToEdit == null)
            throw new ReviewNotFoundException("Review with id " + id + " was not found");

        if (!reviewToEdit.getAuthor().equals(requestUser))
            throw new ReviewOwnerWrongException("You have no permission");

        if (reviewDTO.getRate() != null)
            reviewToEdit.setRate(reviewDTO.getRate());

        if (reviewDTO.getContent() != null)
            reviewToEdit.setContent(reviewDTO.getContent());

        Review savedReview = reviewRepository.save(reviewToEdit);

        log.info("Edited review {}", savedReview);

        return reviewMapper.to(savedReview);
    }

    @Override
    public void deleteReview(Long id, User requestUser) {

        Review reviewToDelete = reviewRepository.findById(id).orElse(null);

        if (reviewToDelete == null)
            throw new ReviewNotFoundException("Review with id " + id + " was not found");

        if (!reviewToDelete.getAuthor().equals(requestUser))
            throw new ReviewOwnerWrongException("You have no permission");

        reviewToDelete.setStatus(Status.DELETED);

        Review deletedReview = reviewRepository.save(reviewToDelete);

        log.info("Deleted review {}", deletedReview);
    }

}
