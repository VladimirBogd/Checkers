package domain.entity;

public class Games {
	private int id;
	private int participantNumber;
	private String participantName;

	private int id1;
	private int id2;
	private Double points;

	private int win;
	private int lose;
	private int equal;
	private int place;
	private Double SB;

	public Games(int id, int participantNumber, String participantName) {
		this.id = id;
		this.participantNumber = participantNumber;
		this.participantName = participantName;
	}

	public Games(int id1, int id2, Double points) {
		this.id1 = id1;
		this.id2 = id2;
		this.points = points;
	}

	public Games(int id, int win, int lose, int equal, Double points, int place, Double SB) {
		this.id = id;
		this.win = win;
		this.lose = lose;
		this.equal = equal;
		this.points = points;
		this.place = place;
		this.SB = SB;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	public int getParticipantNumber() {
		return participantNumber;
	}
	public String getParticipantName() {
		return participantName;
	}
	public Double getPoints() {
		return points;
	}
	public int getPlace() {
		return place;
	}
	public int getId() {
		return id;
	}
	public int getId1() {
		return id1;
	}
	public int getId2() {
		return id2;
	}
	public void setPoints(Double aValue) {
		this.points = aValue;
	}
	public int getWin() {
		return win;
	}
	public int getLose() {
		return lose;
	}
	public int getEqual() {
		return equal;
	}
	public Double getSB() {
		return SB;
	}
}
