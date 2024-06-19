package data.repository;

import domain.entity.Tournament;
import domain.port.TournamentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentRepositoryImpl implements TournamentRepository {
	private final Connection connection;

	public TournamentRepositoryImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Tournament> findAll() {
		List<Tournament> tournaments = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id, name, location, date, status FROM tournaments " +
					"WHERE status = false " +
					"ORDER BY id");
			while (resultSet.next()) {
				Tournament tournament = new Tournament(
						resultSet.getInt("id"),
						resultSet.getString("name"),
						resultSet.getString("location"),
						resultSet.getString("date")
				);
				tournaments.add(tournament);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tournaments;
	}

	@Override
	public int getNextId() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM tournaments");
			if (resultSet.next()) {
				return resultSet.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public void save(Tournament tournament) {
		try {
			int nextId = getNextId();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tournaments (id, name, location, date) VALUES (?, ?, ?, ?)");
			preparedStatement.setInt(1, nextId);
			preparedStatement.setString(2, tournament.getName());
			preparedStatement.setString(3, tournament.getLocation());
			preparedStatement.setString(4, tournament.getDate());
			preparedStatement.executeUpdate();
			tournament.setId(nextId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Tournament tournament) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tournaments SET name = ?, location = ?, date = ? WHERE id = ?");
			preparedStatement.setString(1, tournament.getName());
			preparedStatement.setString(2, tournament.getLocation());
			preparedStatement.setString(3, tournament.getDate());
			preparedStatement.setInt(4, tournament.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tournaments WHERE id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id FROM tournaments ORDER BY id");
			List<Integer> ids = new ArrayList<>();
			while (resultSet.next()) {
				ids.add(resultSet.getInt("id"));
			}
			PreparedStatement updateStatement = connection.prepareStatement("UPDATE tournaments SET id = ? WHERE id = ?");
			for (int i = 0; i < ids.size(); i++) {
				updateStatement.setInt(1, i + 1);
				updateStatement.setInt(2, ids.get(i));
				updateStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Tournament> getTournamentsStat() {
		List<Tournament> tournaments = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id, name, location, date, status FROM tournaments " +
					"WHERE status = true " +
					"ORDER BY id");
			while (resultSet.next()) {
				Tournament tournament = new Tournament(
						resultSet.getInt("id"),
						resultSet.getString("name"),
						resultSet.getString("location"),
						resultSet.getString("date")
				);
				tournaments.add(tournament);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tournaments;
	}
}
