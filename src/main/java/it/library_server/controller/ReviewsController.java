package it.library_server.controller;

import it.library_server.implement.controller.ReviewsControllerImpl;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.ReviewsDto;
import it.library_server.service.ReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/comment")
public class ReviewsController implements ReviewsControllerImpl {
    private final ReviewsService reviewsService;

    @Override
    @PostMapping("/send")
    public HttpEntity<?> sendComment(@RequestParam(name = "userId") UUID userId, @RequestParam(name = "bookId") Long bookId, @RequestBody ReviewsDto reviewsDto) {
        ApiResponse<?> apiResponse = reviewsService.sendComment(userId, bookId, reviewsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

}
