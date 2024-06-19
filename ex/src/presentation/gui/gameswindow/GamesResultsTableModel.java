package presentation.gui.gameswindow;

import domain.entity.Games;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class GamesResultsTableModel extends AbstractTableModel {
	private final List<Games> gamesResults;
	private final String[] columnNames = {"+", "-", "=", "Очки", "Место", "КБ"};
	private final boolean isFinished;

	public GamesResultsTableModel(List<Games> gamesResults, boolean isFinished) {
		this.gamesResults = gamesResults;
		this.isFinished = isFinished;
	}

	@Override
	public int getRowCount() {
		return gamesResults.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (isFinished) {
			Games gamesResult = gamesResults.get(rowIndex);
			return switch (columnIndex) {
				case 0 -> gamesResult.getWin();
				case 1 -> gamesResult.getLose();
				case 2 -> gamesResult.getEqual();
				case 3 -> gamesResult.getPoints();
				case 4 -> gamesResult.getPlace();
				case 5 -> gamesResult.getSB();
				default -> null;
			};
		} else {
		return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}