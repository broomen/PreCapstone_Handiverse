package model_user;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserStore implements Serializable{
	private static TreeMap<String, User> theMap;
	
	public UserStore() {
		theMap = new TreeMap<String, User>();
	}
	
	public static TreeMap<String, User> getUserStore() {
		return theMap;
	}

	public int size() {
		return theMap.size();
	}
	
	public void add(User user) {
		theMap.put(user.getUsername(), user);
	}
	
	public void addAll(TreeMap<String, User> importMap) {
		theMap.putAll(importMap);
	}
	
	public User remove(User user) {
		return theMap.remove(user.getUsername());
	}
	
	public Map findByName(String name) {
		return theMap.values().stream().filter(s -> s.compareTo(new User(name)) == 0).collect(Collectors.toMap(User::getUsername, User::getPassword));
	}
	
	public Map find(Predicate<User> predicate) {
		Map newMap = theMap.values().stream().filter(t-> predicate.test(t)).collect(Collectors.toMap(User::getUsername, User::getPassword));
		return newMap;
	}
	
	public void display() {
		theMap.values().stream().forEach(System.out::println);;
	}

}
