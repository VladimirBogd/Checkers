package presentation.gui.tournamentwindow;

import domain.entity.Tournament;
import domain.usecase.TournamentUseCase;
import presentation.config.AppConfig;
import presentation.gui.gameswindow.GamesWindowController;
import presentation.gui.menuwindow.MenuWindowController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TournamentsWindowController {
	private final TournamentsWindowView view;
	private final TournamentUseCase useCase;
	private TournamentForm tournamentForm;
	private TournamentsTableModel model;

	public TournamentsWindowController(TournamentUseCase useCase, MenuWindowController menuWindowController) {
		this.useCase = useCase;
		this.view = new TournamentsWindowView(menuWindowController);
		initController(menuWindowController);
		loadTournaments();
	}

	private void initController(MenuWindowController menuWindowController) {
		view.getAddButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tournamentForm == null || !tournamentForm.isVisible()) {
					tournamentForm = new TournamentForm(null, useCase, TournamentsWindowController.this);
				} else {
					tournamentForm.toFront();
				}
			}
		});

		view.getEditButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = view.getTable().getSelectedRow();
				if (selectedRow >= 0) {
					int id = (int) view.getTable().getValueAt(selectedRow, 0);
					new TournamentForm(id, useCase, TournamentsWindowController.this);
				} else {
					JOptionPane.showMessageDialog(view.getFrame(), "Выберите турнир для изменения.");
				}
			}
		});

		view.getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = view.getTable().getSelectedRow();
				if (selectedRow >= 0) {
					int id = (int) view.getTable().getValueAt(selectedRow, 0);
					useCase.deleteTournament(id);
					loadTournaments();
				} else {
					JOptionPane.showMessageDialog(view.getFrame(), "Выберите турнир для удаления.");
				}
			}
		});

		view.getGamesButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = view.getTable().getSelectedRow();
				if (selectedRow >= 0) {
					int tournamentId = (int) view.getTable().getValueAt(selectedRow, 0);
					GamesWindowController gamesWindowController = new GamesWindowController(AppConfig.provideGamesUseCase(), tournamentId, TournamentsWindowController.this);
					gamesWindowController.getView().getFrame().setVisible(true);
					view.getFrame().setVisible(false);
				} else {
					JOptionPane.showMessageDialog(view.getFrame(), "Выберите турнир для просмотра партий.");
				}
			}
		});

		view.getGoToMenuButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(view.getFrame(),
						"Вы действительно хотите перейти в главное меню?", "Закрытие окна",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					view.getFrame().dispose();
					menuWindowController.getView().getFrame().setLocationRelativeTo(null);
					menuWindowController.getView().getFrame().setVisible(true);
				}
			}
		});
	}

	public void loadTournaments() {
		List<Tournament> tournaments = useCase.getAllTournaments();
		model = new TournamentsTableModel(tournaments);
		view.getTable().setModel(model);
	}

	public TournamentsWindowView getView() {
		return view;
	}
}
