package com.example.user_management.models;
import java.util.Objects;

public class Review {

    private String review;

    private int rating;

    private String reviewedUserId;

    private String reviewerId;



    public Review() {
    }

    public Review(String review, int rating, String reviewedUserId, String reviewerId) {
        this.review = review;
        this.rating = rating;
        this.reviewedUserId = reviewedUserId;
        this.reviewerId = reviewerId;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewedUserId() {
        return this.reviewedUserId;
    }

    public void setReviewedUserId(String reviewedUserId) {
        this.reviewedUserId = reviewedUserId;
    }

    public String getReviewerId() {
        return this.reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Review review(String review) {
        setReview(review);
        return this;
    }

    public Review rating(int rating) {
        setRating(rating);
        return this;
    }

    public Review reviewedUserId(String reviewedUserId) {
        setReviewedUserId(reviewedUserId);
        return this;
    }

    public Review reviewerId(String reviewerId) {
        setReviewerId(reviewerId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Review)) {
            return false;
        }
        Review review = (Review) o;
        return Objects.equals(review, review.review) && rating == review.rating && Objects.equals(reviewedUserId, review.reviewedUserId) && Objects.equals(reviewerId, review.reviewerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(review, rating, reviewedUserId, reviewerId);
    }

    @Override
    public String toString() {
        return "{" +
            " review='" + getReview() + "'" +
            ", rating='" + getRating() + "'" +
            ", reviewedUserId='" + getReviewedUserId() + "'" +
            ", reviewerId='" + getReviewerId() + "'" +
            "}";
    }

}
