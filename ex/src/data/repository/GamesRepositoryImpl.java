package data.repository;

import domain.entity.Games;
import domain.port.GamesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GamesRepositoryImpl implements GamesRepository {
	private final Connection connection;

	public GamesRepositoryImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Games> findAllParticipants() {
		List<Games> games = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM participants");
			while (resultSet.next()) {
				Games gameParticipant = new Games(resultSet.getInt("id"), 0, resultSet.getString("full_name"));
				games.add(gameParticipant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return games;
	}

	@Override
	public void saveGamesParticipants(int tournamentId, Object[] array) {
		try {
			// Сначала удаляем все существующие записи для данного турнира
			deleteExistingParticipants(tournamentId);

			PreparedStatement insertStatement = connection.prepareStatement(
					"INSERT INTO tournament_participants (tournament_id, participant_id, participant_number) VALUES (?, ?, ?)");
			int i = 1;
			for (Object obj : array) {
				String participantString = (String) obj;
				int participantId = getParticipantId(participantString);
				int participantNumber = i;

				insertStatement.setInt(1, tournamentId);
				insertStatement.setInt(2, participantId);
				insertStatement.setInt(3, participantNumber);
				insertStatement.executeUpdate();
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteExistingParticipants(int tournamentId) {
		try {
			PreparedStatement deleteStatement = connection.prepareStatement(
					"DELETE FROM tournament_participants WHERE tournament_id = ?");
			deleteStatement.setInt(1, tournamentId);
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private int getParticipantId(String participantString) {
		int startIndex = participantString.indexOf("(") + 1;
		int endIndex = participantString.indexOf(")");
		String idString = participantString.substring(startIndex, endIndex);
		return Integer.parseInt(idString);
	}

	@Override
	public List<Games> findParticipants(int tournamentId) {
		List<Games> games = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT tp.participant_id, tp.participant_number, p.full_name "
						+ "FROM participants p "
						+ "INNER JOIN tournament_participants tp ON p.id = tp.participant_id "
						+ "WHERE tp.tournament_id = ? "
						+ "ORDER BY tp.participant_number")) {
			statement.setInt(1, tournamentId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Games gameParticipant = new Games(
							resultSet.getInt("participant_id"),
							resultSet.getInt("participant_number"),
							resultSet.getString("full_name"));
					games.add(gameParticipant);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return games;
	}

	@Override
	public List<Games> findPoints(int tournamentId) {
		List<Games> games = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT tg.participant1_id, tg.participant2_id, tg.game_result " +
						"FROM tournament_games tg " +
						"WHERE tg.tournament_id = ?")) {
			statement.setInt(1, tournamentId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Games game = new Games(resultSet.getInt("participant1_id"), resultSet.getInt("participant2_id"), resultSet.getDouble("game_result"));
					games.add(game);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return games;
	}

	@Override
	public void saveGamesPoints(int tournamentId, List<Games> gamesPoints) {
		try {
			PreparedStatement insertStatement = connection.prepareStatement(
					"INSERT INTO tournament_games (tournament_id, participant1_id, participant2_id, game_result) VALUES (?, ?, ?, ?)");
			for (Games gamesPoint : gamesPoints) {
				insertStatement.setInt(1, tournamentId);
				insertStatement.setInt(2, gamesPoint.getId1());
				insertStatement.setInt(3, gamesPoint.getId2());
				insertStatement.setDouble(4, gamesPoint.getPoints());
				insertStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Games> findResults(int tournamentId) {
		List<Games> gamesResults = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM tournament_results tr "
						+ "WHERE tr.tournament_id =?")) {
			statement.setInt(1, tournamentId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Games game = new Games(
							resultSet.getInt("id"),
							resultSet.getInt("win"),
							resultSet.getInt("lose"),
							resultSet.getInt("equal"),
							resultSet.getDouble("points"),
							resultSet.getInt("place"),
							resultSet.getDouble("SB")
					);
					gamesResults.add(game);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gamesResults;
	}

	@Override
	public void saveGamesResults(int tournamentId, List<Games> gamesResults) {
		try {
			PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO tournament_results (tournament_id, participant_id, win, lose, equal, points, place, SB) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			for (Games gamesResult : gamesResults) {
				insertStatement.setInt(1, tournamentId);
				insertStatement.setInt(2, gamesResult.getId());
				insertStatement.setInt(3, gamesResult.getWin());
				insertStatement.setDouble(4, gamesResult.getLose());
				insertStatement.setDouble(5, gamesResult.getEqual());
				insertStatement.setDouble(6, gamesResult.getPoints());
				insertStatement.setInt(7, gamesResult.getPlace());
				insertStatement.setDouble(8, gamesResult.getSB());
				insertStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateStatusTournament(int tournamentId) {
		try {
			PreparedStatement updateStatement = connection.prepareStatement("UPDATE tournaments SET status = true WHERE id = ?");
			updateStatement.setInt(1, tournamentId);
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
