package domain.usecase;

import domain.entity.Tournament;
import domain.port.TournamentRepository;

import java.util.List;

public class TournamentUseCase {
	private TournamentRepository repository;

	public TournamentUseCase(TournamentRepository repository) {
		this.repository = repository;
	}

	public List<Tournament> getAllTournaments() {
		return repository.findAll();
	}

	public void addTournament(Tournament tournament) {
		repository.save(tournament);
	}

	public void updateTournament(Tournament tournament) {
		repository.update(tournament);
	}

	public void deleteTournament(int id) {
		repository.delete(id);
	}

	public int getNextId() {
		return repository.getNextId();
	}

	public List<Tournament> getTournamentsStat() {
		return repository.getTournamentsStat();
	}
}
