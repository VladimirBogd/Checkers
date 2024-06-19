package presentation.gui.statwindow;

import domain.entity.Tournament;
import domain.usecase.TournamentUseCase;
import presentation.config.AppConfig;
import presentation.gui.gameswindow.GamesWindowController;
import presentation.gui.menuwindow.MenuWindowController;
import presentation.gui.tournamentwindow.TournamentsTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StatWindowController {
	private final StatWindowView view;
	private final TournamentUseCase useCase;

	public StatWindowController(TournamentUseCase useCase, MenuWindowController menuWindowController) {
		this.useCase = useCase;
		this.view = new StatWindowView(menuWindowController);
		initController(menuWindowController);
		loadTournamentsStat();
	}

	private void initController(MenuWindowController menuWindowController) {
		view.getGamesButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = view.getTable().getSelectedRow();
				if (selectedRow >= 0) {
					int tournamentId = (int) view.getTable().getValueAt(selectedRow, 0);
					GamesWindowController gamesWindowController = new GamesWindowController(AppConfig.provideGamesUseCase(), tournamentId, StatWindowController.this);
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

	public void loadTournamentsStat() {
		List<Tournament> tournaments = useCase.getTournamentsStat();
		TournamentsTableModel model = new TournamentsTableModel(tournaments);
		view.getTable().setModel(model);
	}

	public StatWindowView getView() {
		return view;
	}
}
