package domain.usecase;

import domain.entity.Games;
import domain.port.GamesRepository;

import java.util.List;

public class GamesUseCase {
	private final GamesRepository repository;

	public GamesUseCase(GamesRepository repository) {
		this.repository = repository;
	}

	public List<Games> getAllGamesParticipants() {
		return repository.findAllParticipants();
	}

	public void saveGamesParticipants(int tournamentId, Object[] array) {
		repository.saveGamesParticipants(tournamentId, array);
	}

	public List<Games> getGamesParticipants(int tournamentId) {
		return repository.findParticipants(tournamentId);
	}

	public List<Games> getGamesPoints(int tournamentId) {
		return repository.findPoints(tournamentId);
	}

	public void saveGamesPoints(int tournamentId, List<Games> gamesPoints) {
		repository.saveGamesPoints(tournamentId, gamesPoints);
	}

	public List<Games> getGamesResults(int tournamentId) {
		return repository.findResults(tournamentId);
	}

	public void saveGamesResults(int tournamentId, List<Games> gamesResults) {
		repository.saveGamesResults(tournamentId, gamesResults);
	}

	public void updateStatusTournament(int tournamentId) {
		repository.updateStatusTournament(tournamentId);
	}
}
