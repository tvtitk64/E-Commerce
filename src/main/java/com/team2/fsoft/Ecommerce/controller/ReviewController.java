package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.dto.request.ReviewReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.dto.response.ReviewRes;
import com.team2.fsoft.Ecommerce.service.ReviewService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public MessagesResponse addReview(@RequestBody ReviewReq reviewReq) {
        MessagesResponse ms = new MessagesResponse();
      try {
          reviewService.add(reviewReq);
      }
      catch (Exception ex) {
          ms.code=500;
          ms.message="Add review failed!";
      }
      return ms;
    }
    @GetMapping("/product/{productId}")
    public List<ReviewRes> getAllProductReview(@PathVariable @Positive long productId) {
        return reviewService.findAllByProductId(productId);
    }

}
