package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.request.ReviewReq;
import com.team2.fsoft.Ecommerce.dto.response.ReviewRes;
import com.team2.fsoft.Ecommerce.entity.Product;
import com.team2.fsoft.Ecommerce.entity.Review;
import com.team2.fsoft.Ecommerce.repository.ProductRepository;
import com.team2.fsoft.Ecommerce.repository.ReviewRepository;
import com.team2.fsoft.Ecommerce.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }


    @Override
    public void add(ReviewReq reviewReq) {
        Optional<Product> productOptional = productRepository.findById(reviewReq.productId);
        if (productOptional.isPresent()) {
            Review review = new Review();
            review.setContent(reviewReq.content);
            review.setRate(reviewReq.rate);
            review.setProduct(productOptional.get());
            reviewRepository.save(review);
        }
    }

    @Override
    public List<ReviewRes> findAllByProductId(long productId) {
        return null;
    }


}
