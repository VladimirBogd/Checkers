package presentation.gui.participantwindow;

import domain.entity.Participant;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ParticipantsTableModel extends AbstractTableModel {
	private final List<Participant> participants;
	private final String[] columnNames = {"ID", "ФИО", "Разряд", "Пол", "Дата рождения"};

	public ParticipantsTableModel(List<Participant> participants) {
		this.participants = participants;
	}

	@Override
	public int getRowCount() {
		return participants.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Participant participant = participants.get(rowIndex);
		return switch (columnIndex) {
			case 0 -> participant.getId();
			case 1 -> participant.getFullName();
			case 2 -> participant.getRank();
			case 3 -> participant.getGender();
			case 4 -> participant.getBirthDate();
			default -> null;
		};
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}
