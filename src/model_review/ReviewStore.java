package model_review;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ReviewStore {
	
	private TreeMap<Integer, Review> theMap;

	public ReviewStore() {
		theMap = new TreeMap<Integer, Review>();
	}
	
	public TreeMap<Integer, Review> getReviewStore() {
		return theMap;
	}

	public int size() {
		return theMap.size();
	}
	
	public void add(Review review) {
		theMap.put(review.getReviewID(), review);
	}
	
	public void addAll(TreeMap<Integer, Review> importMap) {
		theMap.putAll(importMap);
	}
	
	public Review remove(Review review) {
		return theMap.remove(review.getReviewID());
	}
	
	public Map findByName(String id) {
		return theMap.values().stream().filter(s -> s.compareTo(new Review(Integer.parseInt(id))) == 0).collect(Collectors.toMap(Review::getReviewID, Review::getContent));
	}
	
	public Map find(Predicate<Review> predicate) {
		Map newMap = theMap.values().stream().filter(t-> predicate.test(t)).collect(Collectors.toMap(Review::getReviewID, Review::getContent));
		return newMap;
	}
	
	public void display() {
		theMap.values().stream().forEach(System.out::println);;
	}
}

