package presentation.gui.gameswindow;

import domain.entity.Games;
import domain.usecase.GamesUseCase;
import presentation.gui.statwindow.StatWindowController;
import presentation.gui.tournamentwindow.TournamentsWindowController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GamesWindowController {
	private final GamesWindowView view;
	private final GamesUseCase useCase;
	private final int tournamentId;

	public GamesWindowController(GamesUseCase useCase, int tournamentId, TournamentsWindowController tournamentsWindowController) {
		this.useCase = useCase;
		this.tournamentId = tournamentId;
		this.view = new GamesWindowView(tournamentsWindowController);
		initController();
		initControllerGoToMenuButton(tournamentsWindowController);
		loadParticipants();
		loadGames(false);
		loadResults(false, false);
	}
	public GamesWindowController(GamesUseCase useCase, int tournamentId, StatWindowController statWindowController) {
		this.useCase = useCase;
		this.tournamentId = tournamentId;
		this.view = new GamesWindowView(statWindowController);
		initControllerGoToMenuButton(statWindowController);
		loadParticipants();
		loadGames(true);
		loadResults(true, true);
	}

	private void initControllerGoToMenuButton(TournamentsWindowController tournamentsWindowController) {
		view.getGoToMenuButton().addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(view.getFrame(),
					"Вы действительно хотите перейти к турнирам?", "Закрытие окна",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				view.getFrame().dispose();
				tournamentsWindowController.getView().getFrame().setLocationRelativeTo(null);
				tournamentsWindowController.getView().getFrame().setVisible(true);
				tournamentsWindowController.loadTournaments();
			}
		});
	}
	private void initControllerGoToMenuButton(StatWindowController statWindowController) {
		view.getGoToMenuButton().addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(view.getFrame(),
					"Вы действительно хотите перейти в статистику?", "Закрытие окна",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				view.getFrame().dispose();
				statWindowController.getView().getFrame().setLocationRelativeTo(null);
				statWindowController.getView().getFrame().setVisible(true);
			}
		});
	}
	private void initController() {
		view.getParticipantsButton().addActionListener(e -> {
			new GamesFormParticipants(tournamentId, useCase, GamesWindowController.this);
		});
		view.getStartTournament().addActionListener(e -> {
			if (useCase.getGamesParticipants(tournamentId).size() < 2) {
				JOptionPane.showMessageDialog(null, "В турнире должно участвовать хотя бы 2 игрока", "Ошибка", JOptionPane.ERROR_MESSAGE);
			} else {
				loadGames(true);
				view.getParticipantsButton().setEnabled(false);
				view.getStartTournament().setEnabled(false);
				view.getPointsTable().setEnabled(true);
				view.getResultsButton().setEnabled(true);
			}
		});
		view.getResultsButton().addActionListener(e -> {
			var pointsTable = view.getPointsTable();
			List<Games> gamesParticipants = useCase.getGamesParticipants(tournamentId);
			List<Games> gamesPoints = new ArrayList<>();
			int rowCount = pointsTable.getRowCount();

			for (int row = 0; row < rowCount; row++) {
				for (int col = 0; col < rowCount; col++) {
					Object value = pointsTable.getValueAt(row, col);
					if (row != col) {
						if (value == null) {
							JOptionPane.showMessageDialog(null, "Заполните всю таблицу", "Ошибка", JOptionPane.ERROR_MESSAGE);
							return;
						}
						Games gamePoint = new Games(gamesParticipants.get(row).getId(), gamesParticipants.get(col).getId(), (Double) value);
						gamesPoints.add(gamePoint);
					}
				}
			}
			useCase.saveGamesPoints(tournamentId, gamesPoints);
			// Подсчет результатов
			loadResults(true, false);

			view.getResultsButton().setEnabled(false);
			view.getStartTournament().setEnabled(false);
			view.getPointsTable().setEnabled(false);
			view.getResultsTable().setEnabled(false);
		});
	}

	public void loadParticipants() {
		List<Games> gamesParticipants = useCase.getGamesParticipants(tournamentId);
		GamesParticipantsTableModel participantsTableModel = new GamesParticipantsTableModel(gamesParticipants);
		view.getParticipantsTable().setModel(participantsTableModel);
	}

	public void loadGames(boolean isFinished) {
		List<Games> gamesParticipants = useCase.getGamesParticipants(tournamentId);
		List<Games> gamesPoints;
		if (isFinished) {
			gamesPoints = useCase.getGamesPoints(tournamentId);
		} else {
			gamesPoints = new ArrayList<>();
		}
		GamesPointsTableModel pointsTableModel = new GamesPointsTableModel(gamesParticipants, gamesPoints, isFinished);
		view.getPointsTable().setModel(pointsTableModel);
	}

	public void loadResults(boolean isStarted, boolean isFinished) {
		List<Games> gamesParticipants = useCase.getGamesParticipants(tournamentId);
		List<Games> gamesResults;
		if (isFinished) {
			gamesResults = useCase.getGamesResults(tournamentId);
		} else {
			if (!isStarted) {
				gamesResults = new ArrayList<>(gamesParticipants);
			} else {
				List<Games> gamesPoints = useCase.getGamesPoints(tournamentId);
				gamesResults = calcResults(gamesPoints, gamesParticipants);
				useCase.saveGamesResults(tournamentId, gamesResults);
				useCase.updateStatusTournament(tournamentId);
			}
		}
		GamesResultsTableModel resultsTableModel = new GamesResultsTableModel(gamesResults, isStarted);
		view.getResultsTable().setModel(resultsTableModel);
	}

	public GamesWindowView getView() {
		return view;
	}

	private List<Games> calcResults(List<Games> gamesPoints, List<Games> gamesParticipants) {
		List<Games> gamesResults = new ArrayList<>();
		for (Games gamesParticipant : gamesParticipants) {
			List<Integer> idWins = new ArrayList<>();
			List<Integer> idEquals = new ArrayList<>();
			int id = gamesParticipant.getId();
			Double points = 0.0;
			int win = 0;
			int lose = 0;
			int equal = 0;
			Double SB = 0.0;
			for (Games gamesPoint : gamesPoints) {
				if (gamesPoint.getId1() == id) {
					points += gamesPoint.getPoints();
					if (gamesPoint.getPoints() == 1.0) {
						win++;
						idWins.add(gamesPoint.getId2());
					} else if (gamesPoint.getPoints() == 0.0) {
						lose++;
					} else {
						equal++;
						idEquals.add(gamesPoint.getId2());
					}
				}
			}

			for (Integer idWin : idWins) {
				for (Games gamesPoint : gamesPoints) {
					if (gamesPoint.getId1() == idWin) {
						SB += gamesPoint.getPoints();
					}
				}
			}
			for (Integer idEqual : idEquals) {
				for (Games gamesPoint : gamesPoints) {
					if (gamesPoint.getId1() == idEqual) {
						SB += gamesPoint.getPoints()/2;
					}
				}
			}

			Games gamesResult = new Games(id, win, lose, equal, points, 0, SB);
			gamesResults.add(gamesResult);
		}

		List<Games> sortedGamesResults = new ArrayList<>(gamesResults);
		sortedGamesResults.sort((g1, g2) -> {
			int result = Double.compare(g2.getPoints(), g1.getPoints());
			if (result == 0) {
				result = Double.compare(g2.getSB(), g1.getSB());
			}
			return result;
		});

		int place = 1;
		for (Games gamesResult : sortedGamesResults) {
			gamesResult.setPlace(place++);
		}

		return gamesResults;
	}
}
