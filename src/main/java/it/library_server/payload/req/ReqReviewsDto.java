package it.library_server.payload.req;

public record ReqReviewsDto(
        Integer rating,
        String text
) {
}
