package domain.port;

import domain.entity.Tournament;

import java.util.List;

public interface TournamentRepository {
	List<Tournament> findAll();
	int getNextId();
	void save(Tournament tournament);
	void update(Tournament tournament);
	void delete(int id);
	List<Tournament> getTournamentsStat();
}
