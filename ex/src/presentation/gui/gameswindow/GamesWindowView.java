package presentation.gui.gameswindow;

import presentation.gui.statwindow.StatWindowController;
import presentation.gui.tournamentwindow.TournamentsWindowController;

import javax.swing.*;
import java.awt.*;

public class GamesWindowView {
	private JFrame frame;
	private JTable participantsTable;
	private JTable pointsTable;
	private JTable resultsTable;
	private JButton participantsButton;
	private JButton resultsButton;
	private JButton startTournament;
	private JButton goToMenuButton;

	public GamesWindowView(TournamentsWindowController tournamentsWindowController) {
		// Инициализация и настройка окна
		initializeFrame();
		// Создание панели с тремя таблицами
		initializeTablePanel();
		// Создание панели с кнопками
		initializeBottomPanel();
		// Настройка обработчика закрытия окна
		initializeWindowClosingHandler(tournamentsWindowController);
	}
	public GamesWindowView(StatWindowController statWindowController) {
		// Инициализация и настройка окна
		initializeFrame();
		// Создание панели с тремя таблицами
		initializeTablePanel();
		// Создание панели с кнопками
		initializeGoToMenuPanelStat();
		// Настройка обработчика закрытия окна
		initializeWindowClosingHandler(statWindowController);
	}

	private void initializeFrame() {
		frame = new JFrame("Партии турнира");
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}

	private void initializeTablePanel() {
		JPanel tablesPanel = new JPanel(new GridLayout(1, 3, 0, 0));
		participantsTable = new JTable();
		participantsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		participantsTable.setEnabled(false);

		pointsTable = new JTable();
		pointsTable.setDefaultEditor(Object.class, new DefaultCellEditor(new JComboBox<>(new Double[]{null, 0.0, 0.5, 1.0})));
		pointsTable.setCellSelectionEnabled(true);
		pointsTable.setColumnSelectionAllowed(false);
		pointsTable.setRowSelectionAllowed(false);
		pointsTable.setEnabled(false);

		resultsTable = new JTable();
		resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsTable.setEnabled(false);

		tablesPanel.add(new JScrollPane(participantsTable));
		tablesPanel.add(new JScrollPane(pointsTable));
		tablesPanel.add(new JScrollPane(resultsTable));
		frame.add(tablesPanel, BorderLayout.CENTER);
	}

	private JPanel initializeButtonsPanel() {
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		participantsButton = new JButton("Заполнить участников");
		startTournament = new JButton("Начать турнир");
		resultsButton = new JButton("Завершить турнир");
		resultsButton.setEnabled(false);
		buttonsPanel.add(participantsButton);
		buttonsPanel.add(startTournament);
		buttonsPanel.add(resultsButton);
		return buttonsPanel;
	}

	private JPanel initializeGoToMenuPanel() {
		JPanel goToMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		goToMenuPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		goToMenuButton = new JButton("К турнирам");
		goToMenuPanel.add(goToMenuButton);
		return goToMenuPanel;
	}

	private void initializeBottomPanel() {
		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		bottomPanel.add(initializeButtonsPanel());
		bottomPanel.add(initializeGoToMenuPanel());
		frame.add(bottomPanel, BorderLayout.SOUTH);
	}

	private void initializeGoToMenuPanelStat() {
		JPanel goToMenuPanel = new JPanel();
		goToMenuButton = new JButton("В статистику");
		goToMenuPanel.add(goToMenuButton);
		frame.add(goToMenuButton, BorderLayout.SOUTH);
	}

	private void initializeWindowClosingHandler(TournamentsWindowController tournamentsWindowController) {
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame,
						"Вы действительно хотите перейти к турнирам?", "Закрытие окна",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					frame.dispose();
					tournamentsWindowController.getView().getFrame().setLocationRelativeTo(null);
					tournamentsWindowController.getView().getFrame().setVisible(true);
				}
			}
		});
	}
	private void initializeWindowClosingHandler(StatWindowController statWindowController) {
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame,
						"Вы действительно хотите перейти в статистику?", "Закрытие окна",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					frame.dispose();
					statWindowController.getView().getFrame().setLocationRelativeTo(null);
					statWindowController.getView().getFrame().setVisible(true);
				}
			}
		});
	}

	public JFrame getFrame() {
		return frame;
	}
	public JTable getParticipantsTable() {
		return participantsTable;
	}
	public JTable getPointsTable() {
		return pointsTable;
	}
	public JTable getResultsTable() {
		return resultsTable;
	}
	public JButton getParticipantsButton() {
		return participantsButton;
	}
	public JButton getResultsButton() {
		return resultsButton;
	}
	public JButton getStartTournament() {
		return startTournament;
	}
	public JButton getGoToMenuButton() {
		return goToMenuButton;
	}
}
