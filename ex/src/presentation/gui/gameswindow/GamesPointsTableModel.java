package presentation.gui.gameswindow;

import domain.entity.Games;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

class GamesPointsTableModel extends AbstractTableModel {
	private final List<Games> gamesPoints;
	private final Double[][] arrayPoints;
	private final List<Games> gamesParticipants;

	private final String[] columnNames;

	public GamesPointsTableModel(List<Games> gamesParticipants, List<Games> gamesPoints, boolean isFinished) {
		this.gamesParticipants = gamesParticipants;

		if (!gamesParticipants.isEmpty()) {
			this.columnNames = new String[gamesParticipants.size()];
			for (int i = 0; i < gamesParticipants.size(); i++) {
				int num = gamesParticipants.get(i).getParticipantNumber();
				this.columnNames[i] = Integer.toString(num);
			}
		} else {
			this.columnNames = new String[1];
			this.columnNames[0] = " ";
		}

		for (int i = 0; i < gamesParticipants.size() * (gamesParticipants.size() - 1); i++) {
			gamesPoints.add(new Games(0, 0, (Double) null));
		}
		this.gamesPoints = gamesPoints;

		this.arrayPoints = new Double[gamesParticipants.size()][gamesParticipants.size()];
		getValues();
	}

	@Override
	public int getRowCount() {
		return gamesParticipants.size();
	}
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (!gamesParticipants.isEmpty()) {
			if (rowIndex == columnIndex) {
				return null;
			}
			return arrayPoints[rowIndex][columnIndex];
		} else {
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (rowIndex == columnIndex) {
			return;
		}
		if (aValue == null) {
			arrayPoints[rowIndex][columnIndex] = null;
			arrayPoints[columnIndex][rowIndex] = null;
		} else if ((Double) aValue == 1.0) {
			arrayPoints[rowIndex][columnIndex] = (Double) aValue;
			arrayPoints[columnIndex][rowIndex] = 0.0;
		} else if ((Double) aValue == 0.0) {
			arrayPoints[rowIndex][columnIndex] = (Double) aValue;
			arrayPoints[columnIndex][rowIndex] = 1.0;
		} else if ((Double) aValue == 0.5) {
			arrayPoints[rowIndex][columnIndex] = (Double) aValue;
			arrayPoints[columnIndex][rowIndex] = 0.5;
		}
		fireTableDataChanged();
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return JComboBox.class;
	}
	@Override
	public boolean isCellEditable(int row, int column) {
		return row != column;
	}
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	public void getValues() {
		int k = 0;
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getRowCount(); j++) {
				if (i == j) {
					arrayPoints[i][j] = (double) -1;
				} else {
					arrayPoints[i][j] = gamesPoints.get(k).getPoints();
					k++;
				}
			}
		}
	}
}
