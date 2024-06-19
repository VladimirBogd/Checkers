package domain.entity;

public class Tournament {
	private int id;
	private String name;
	private String location;
	private String date;

	public Tournament(int id, String name, String location, String date) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.date = date;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
