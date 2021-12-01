package model_tag;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import model_location.Location;
import model_location.LocationStore;


public class TagStore {
	private TreeMap<Integer, Tag> theMap;
	
	public TagStore() {
		theMap = new TreeMap<Integer, Tag>();
	}
	
	public TreeMap<Integer, Tag> getTagStore() {
		return theMap;
	}
	

	public int size() {
		return theMap.size();
	}
	
	public void add(Tag tag) {
		theMap.put(tag.getID(), tag);
	}
	
	public void addAll(TreeMap<Integer, Tag> importMap) {
		theMap.putAll(importMap);
	}
	
	public Tag remove(Tag tag) {
		return theMap.remove(tag.getID());
	}
	
	public Map findByName(String name) {
		return theMap.values().stream().filter(s -> s.compareTo(new Tag(name)) == 0).collect(Collectors.toMap(Tag::getID, Tag::getDesc));
	}
	
	public Map find(Predicate<Tag> predicate) {
		Map newMap = theMap.values().stream().filter(t-> predicate.test(t)).collect(Collectors.toMap(Tag::getID, Tag::getDesc));
		return newMap;
	}
	
	public void display() {
		theMap.values().stream().forEach(System.out::println);;
	}

}
