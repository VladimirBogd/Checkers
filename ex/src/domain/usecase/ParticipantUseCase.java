package domain.usecase;

import domain.entity.Participant;
import domain.port.ParticipantRepository;

import java.util.List;

public class ParticipantUseCase {
	private final ParticipantRepository repository;

	public ParticipantUseCase(ParticipantRepository repository) {
		this.repository = repository;
	}

	public List<Participant> getAllParticipants() {
		return repository.findAll();
	}

	public void addParticipant(Participant participant) {
		repository.save(participant);
	}

	public void updateParticipant(Participant participant) {
		repository.update(participant);
	}

	public void deleteParticipant(int id) {
		repository.delete(id);
	}

	public int getNextId() {
		return repository.getNextId();
	}
}
