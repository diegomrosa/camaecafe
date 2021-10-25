package com.camaecafe;

public class User {
	
	
	private Long id;
    private Boolean isHost;
    private String name;
    private Location location;

	public User() {}

	public User(Long id, Boolean isHost, String name) {
		this(id, isHost, name, null);
	}

	public User(Long id, Boolean isHost, String name, Location location) {
		super();
		this.id = id;
		this.isHost = isHost;
		this.name = name;
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public Boolean getIsHost() {
		return isHost;
	}

	public boolean isHost() {
		return this.getIsHost().booleanValue();
	}
	
	public void setIsHost(Boolean isHost) {
		this.isHost = isHost;
	}
	
	public String getName() {
		return name;
	}
}
