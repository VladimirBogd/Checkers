package domain.entity;

public class Participant {
	private int id;
	private String fullName;
	private String rank;
	private char gender;
	private String birthDate;

	public Participant(int id, String fullName, String rank, char gender, String birthDate) {
		this.id = id;
		this.fullName = fullName;
		this.rank = rank;
		this.gender = gender;
		this.birthDate = birthDate;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
}
