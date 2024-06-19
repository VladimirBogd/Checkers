package domain.port;

import domain.entity.Games;

import java.util.List;

public interface GamesRepository {
	List<Games> findAllParticipants();
	void saveGamesParticipants(int tournamentId, Object[] array);
	List<Games> findParticipants(int tournamentId);
	List<Games> findPoints(int tournamentId);
	void saveGamesPoints(int tournamentId, List<Games> gamesPoints);
	List<Games> findResults(int tournamentId);
	void saveGamesResults(int tournamentId, List<Games> gamesResults);
	void updateStatusTournament(int tournamentId);
}
