package model_location;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import model_tag.Tag;

public class LocationStore {
	
	private TreeMap<Integer, Location> theMap;

	public LocationStore() {
		theMap = new TreeMap<Integer, Location>();
	}
	
	public TreeMap<Integer, Location> getLocationStore() {
		return theMap;
	}

	public int size() {
		return theMap.size();
	}
	
	public void add(Location location) {
		theMap.put(location.getID(), location);
	}
	
	public void addAll(TreeMap<Integer, Location> importMap) {
		theMap.putAll(importMap);
	}
	
	public Location remove(Location location) {
		return theMap.remove(location.getID());
	}
	
	public Map findByName(String id) {
		return theMap.values().stream().filter(s -> s.compareTo(new Location(Integer.parseInt(id))) == 0).collect(Collectors.toMap(Location::getID, Location::getName));
	}
	
	public Map find(Predicate<Location> predicate) {
		Map newMap = theMap.values().stream().filter(t-> predicate.test(t)).collect(Collectors.toMap(Location::getID, Location::getName));
		return newMap;
	}
	
	public void display() {
		theMap.values().stream().forEach(System.out::println);;
	}
}
