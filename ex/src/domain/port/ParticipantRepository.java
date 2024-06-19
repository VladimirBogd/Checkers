package domain.port;

import domain.entity.Participant;

import java.util.List;

public interface ParticipantRepository {
	List<Participant> findAll();
	int getNextId();
	void save(Participant participant);
	void update(Participant participant);
	void delete(int id);
}
