package data.repository;

import domain.entity.Participant;
import domain.port.ParticipantRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantRepositoryImpl implements ParticipantRepository {
	private Connection connection;

	public ParticipantRepositoryImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Participant> findAll() {
		List<Participant> participants = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM participants");
			while (resultSet.next()) {
				Participant participant = new Participant(
						resultSet.getInt("id"),
						resultSet.getString("full_name"),
						resultSet.getString("rank"),
						resultSet.getString("gender").charAt(0),
						resultSet.getString("birth_date")
				);
				participants.add(participant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return participants;
	}

	@Override
	public int getNextId() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM participants");
			if (resultSet.next()) {
				return resultSet.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public void save(Participant participant) {
		try {
			int nextId = getNextId();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO participants (id, full_name, rank, gender, birth_date) VALUES (?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, nextId);
			preparedStatement.setString(2, participant.getFullName());
			preparedStatement.setString(3, participant.getRank());
			preparedStatement.setString(4, String.valueOf(participant.getGender()));
			preparedStatement.setString(5, participant.getBirthDate());
			preparedStatement.executeUpdate();
			participant.setId(nextId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Participant participant) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE participants SET full_name = ?, rank = ?, gender = ?, birth_date = ? WHERE id = ?");
			preparedStatement.setString(1, participant.getFullName());
			preparedStatement.setString(2, participant.getRank());
			preparedStatement.setString(3, String.valueOf(participant.getGender()));
			preparedStatement.setString(4, participant.getBirthDate());
			preparedStatement.setInt(5, participant.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM participants WHERE id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id FROM participants ORDER BY id");
			List<Integer> ids = new ArrayList<>();
			while (resultSet.next()) {
				ids.add(resultSet.getInt("id"));
			}
			PreparedStatement updateStatement = connection.prepareStatement("UPDATE participants SET id = ? WHERE id = ?");
			for (int i = 0; i < ids.size(); i++) {
				updateStatement.setInt(1, i + 1);
				updateStatement.setInt(2, ids.get(i));
				updateStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
