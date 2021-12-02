package model_review;

public class Review {
	
	private int reviewID;
	private int locID;
	private String reviewContent;
	private int reviewKarma;
	private String reviewDate;
	private String reviewAuthor;
	private int rating;
	
	private static int idCounter = 1;
	
	public Review(String reviewContent, int reviewKarma) {
		this.reviewContent = reviewContent;
		this.reviewKarma = reviewKarma;
	}
	
	public Review(String reviewContent, int reviewKarma, String reviewDate, String reviewAuthor) {
		super();
		this.reviewContent = reviewContent;
		this.reviewKarma = reviewKarma;
		this.reviewDate = reviewDate;
		this.reviewAuthor = reviewAuthor;
	}
	
	
	
	public Review(int reviewID, int locID, String reviewContent, int reviewKarma, String reviewDate,
			String reviewAuthor, int rating) {
		super();
		this.reviewID = reviewID;
		this.locID = locID;
		this.reviewContent = reviewContent;
		this.reviewKarma = reviewKarma;
		this.reviewDate = reviewDate;
		this.reviewAuthor = reviewAuthor;
		this.rating = rating;
		idCounter++;
	}
	
	public Review(int locID, String reviewContent, String reviewAuthor, String reviewDate, int rating) {
		this.reviewID = idCounter++;
		this.locID = locID;
		this.reviewContent = reviewContent;
		this.reviewKarma = 0;
		this.reviewAuthor = reviewAuthor;
		this.reviewDate = reviewDate;
		this.rating = rating;
	}

	public Review(int id) {
		this.reviewID = id;
	}

	public int getReviewID() {
		return reviewID;
	}

	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}

	public int getLocID() {
		return locID;
	}

	public void setLocID(int locID) {
		this.locID = locID;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getContent() {
		return reviewContent;
	}
	public void setContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
	public int getReviewKarma() {
		return reviewKarma;
	}
	public void setReviewKarma(int reviewKarma) {
		this.reviewKarma = reviewKarma;
	}
	public String getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	public String getReviewAuthor() {
		return reviewAuthor;
	}
	public void setReviewAuthor(String reviewAuthor) {
		this.reviewAuthor = reviewAuthor;
	}

	@Override
	public String toString() {
		return "Author: " + reviewAuthor + "\nDate: " + reviewDate + "\nRating: " + String.valueOf(rating) + "/5\nReview Score: " + String.valueOf(reviewKarma) + "\n" + reviewContent + "\n\n-------------------------------------------------\n";
	}
	
	public int compareTo(Review o) {
		if (String.valueOf(reviewID).compareTo(String.valueOf(o.reviewID)) == 0) {
			return 0;
		} else if (String.valueOf(reviewID).compareTo(String.valueOf(o.reviewID)) > 0) {
			return 1;
		} else {
			return -1;
		}
	}
	
	
	
	

}
