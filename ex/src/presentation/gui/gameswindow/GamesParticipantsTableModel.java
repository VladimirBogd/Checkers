package presentation.gui.gameswindow;

import domain.entity.Games;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GamesParticipantsTableModel extends AbstractTableModel {
	private final List<Games> games;
	private final String[] columnNames = {"Номер", "ФИО"};

	public GamesParticipantsTableModel(List<Games> games) {
		this.games = games;
	}

	@Override
	public int getRowCount() {
		return games.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Games game = games.get(rowIndex);
		return switch (columnIndex) {
			case 0 -> game.getParticipantNumber();
			case 1 -> game.getParticipantName();
			default -> null;
		};
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}