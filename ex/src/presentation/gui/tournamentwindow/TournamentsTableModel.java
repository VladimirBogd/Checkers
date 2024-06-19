package presentation.gui.tournamentwindow;

import domain.entity.Tournament;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TournamentsTableModel extends AbstractTableModel {
	private final List<Tournament> tournaments;
	private final String[] columnNames = {"ID", "Название", "Место", "Дата"};

	public TournamentsTableModel(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	@Override
	public int getRowCount() {
		return tournaments.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Tournament tournament = tournaments.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return tournament.getId();
			case 1:
				return tournament.getName();
			case 2:
				return tournament.getLocation();
			case 3:
				return tournament.getDate();
			default:
				return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}
