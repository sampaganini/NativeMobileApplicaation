package com.example.fixnow.models;

public class opinionRequest {
    private Long leftBy;
    private Long leftFor;
    private Float starRating;
    private String content;

    public opinionRequest(Long leftBy, Long leftFor, Float starRating, String content) {
        this.leftBy = leftBy;
        this.leftFor = leftFor;
        this.starRating = starRating;
        this.content = content;
    }

    public Long getLeftBy() {
        return leftBy;
    }

    public void setLeftBy(Long leftBy) {
        this.leftBy = leftBy;
    }

    public Long getLeftFor() {
        return leftFor;
    }

    public void setLeftFor(Long leftFor) {
        this.leftFor = leftFor;
    }

    public Float getStarRating() {
        return starRating;
    }

    public void setStarRating(Float starRating) {
        this.starRating = starRating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
