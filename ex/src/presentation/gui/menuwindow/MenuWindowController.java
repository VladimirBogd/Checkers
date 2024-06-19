package presentation.gui.menuwindow;

import presentation.config.AppConfig;
import presentation.gui.participantwindow.ParticipantsWindowController;
import presentation.gui.statwindow.StatWindowController;
import presentation.gui.tournamentwindow.TournamentsWindowController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindowController {
	private final MenuWindowView view;

	public MenuWindowController(MenuWindowView view) {
		this.view = view;
		initController();
	}

	private void initController() {
		view.getParticipantsButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ParticipantsWindowController participantsWindowController = new ParticipantsWindowController(AppConfig.provideParticipantUseCase(), MenuWindowController.this);
				participantsWindowController.getView().getFrame().setVisible(true);
				view.getFrame().setVisible(false);
			}
		});

		view.getTournamentsButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TournamentsWindowController tournamentsWindowController = new TournamentsWindowController(AppConfig.provideTournamentUseCase(), MenuWindowController.this);
				tournamentsWindowController.getView().getFrame().setVisible(true);
				view.getFrame().setVisible(false);
			}
		});

		view.getScoresButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StatWindowController scoresWindowController = new StatWindowController(AppConfig.provideTournamentUseCase(), MenuWindowController.this);
				scoresWindowController.getView().getFrame().setVisible(true);
				view.getFrame().setVisible(false);
			}
		});

		view.getExitButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(view.getFrame(),
						"Вы действительно хотите выйти?", "Выход",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	public MenuWindowView getView() {
		return view;
	}
}
