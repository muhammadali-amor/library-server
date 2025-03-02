package it.library_server.controller;

import it.library_server.implement.controller.ReviewsControllerImpl;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.req.ReqReviewsDto;
import it.library_server.payload.res.ResReviewsDto;
import it.library_server.service.ReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/comment")
public class ReviewsController implements ReviewsControllerImpl {
    private final ReviewsService reviewsService;


    @Override
    @GetMapping("/{id}")
    public HttpEntity<?> getAllComment(@PathVariable Long id) {
        List<ResReviewsDto> allComment = reviewsService.getAllComment(id);
        return ResponseEntity.ok(allComment);
    }

    @Override
    @GetMapping("/rating/{id}")
    public HttpEntity<?> getAllReting(@PathVariable Long id) {
        double colculatorReting = reviewsService.getColculatorReting(id);
        return ResponseEntity.ok(colculatorReting);
    }

    @Override
    @PostMapping("/send")
    public HttpEntity<?> sendComment(@RequestParam(name = "userId") UUID userId, @RequestParam(name = "bookId") Long bookId, @RequestBody ReqReviewsDto reviewsDto) {
        ApiResponse<?> apiResponse = reviewsService.sendComment(userId, bookId, reviewsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
