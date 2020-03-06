package com.commerce.web.rest;

import com.commerce.web.dto.ReviewDTO;
import com.commerce.web.model.User;
import com.commerce.web.service.ReviewService;
import com.commerce.web.service.UserService;
import com.commerce.web.validators.ReviewValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping(value = "/api/v1/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ReviewValidator reviewValidator;

    public ReviewRestController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.reviewValidator = new ReviewValidator();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReviewDTO> getById(@PathVariable @Positive Long id) {
        ReviewDTO foundReview = reviewService.getById(id);
        return new ResponseEntity<>(foundReview, HttpStatus.OK);
    }

    @GetMapping(value = "/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getProductReviews(@PathVariable @Positive Long productId) {
        List<ReviewDTO> foundReviews = reviewService.getByProduct(productId);
        return new ResponseEntity<>(foundReviews, HttpStatus.OK);
    }

    @GetMapping(value = "/parent/{parentId}")
    public ResponseEntity<List<ReviewDTO>> getChildrenReviews(@PathVariable Long parentId) {
        List<ReviewDTO> foundReviews = reviewService.getByParent(parentId);
        return new ResponseEntity<>(foundReviews, HttpStatus.OK);
    }

    @PostMapping(value = "/{productId}")
    public ResponseEntity<ReviewDTO> addReview(@PathVariable @Positive Long productId, @RequestBody ReviewDTO reviewDTO, Authentication authentication, Errors errors) {
        reviewValidator.validate(reviewDTO, errors);
        User author = userService.findByEmail(authentication.getName());
        return new ResponseEntity<>(reviewService.addReview(productId, reviewDTO, author), HttpStatus.OK);
    }

    @PostMapping(value = "/parent/{parentId}")
    public ResponseEntity<ReviewDTO> addReviewToParent(@PathVariable @Positive Long parentId, @RequestBody ReviewDTO reviewDTO, Authentication authentication) {
        User author = userService.findByEmail(authentication.getName());
        return new ResponseEntity<>(reviewService.addReviewToParent(reviewDTO, parentId, author), HttpStatus.OK);
    }

    @PostMapping(value = "/edit/{id}")
    public ResponseEntity<ReviewDTO> editReview(@PathVariable @Positive Long id, @RequestBody ReviewDTO reviewDTO, Authentication authentication) {
        User requestUser = userService.findByEmail(authentication.getName());
        return new ResponseEntity<>(reviewService.editReview(reviewDTO, id, requestUser), HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteReview(@PathVariable @Positive Long id, Authentication authentication) {
        User requestUser = userService.findByEmail(authentication.getName());
        reviewService.deleteReview(id, requestUser);
    }

}
