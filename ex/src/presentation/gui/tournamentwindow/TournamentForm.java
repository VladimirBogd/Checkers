package presentation.gui.tournamentwindow;

import domain.entity.Tournament;
import domain.usecase.TournamentUseCase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TournamentForm {
	private final TournamentUseCase useCase;
	private final TournamentsWindowController parentController;
	private final Integer tournamentId;

	private JPanel contentPanel;
	private JFrame frame;
	private JTextField nameField;
	private JTextField locationField;
	private JTextField dateField;

	public TournamentForm(Integer tournamentId, TournamentUseCase useCase, TournamentsWindowController parentController) {
		this.tournamentId = tournamentId;
		this.useCase = useCase;
		this.parentController = parentController;
		initialize();
		if (tournamentId != null) {
			loadTournament(tournamentId);
		}
	}

	private void initialize() {
		createFrame();
		createContentPanel();
		createInputPanel();
		createSaveButton();
		frame.setVisible(true);
	}

	private void createFrame() {
		frame = new JFrame(tournamentId == null ? "Добавить турнир" : "Изменить турнир");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
	}

	private void createContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		contentPanel.setLayout(new BorderLayout());
		frame.add(contentPanel, BorderLayout.CENTER);
	}

	private void createInputPanel() {
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(3, 2, 15, 15));
		inputPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

		inputPanel.add(createLabel("Название:"));
		nameField = createTextField();
		inputPanel.add(nameField);

		inputPanel.add(createLabel("Место:"));
		locationField = createTextField();
		inputPanel.add(locationField);

		inputPanel.add(createLabel("Дата (гггг-мм-дд):"));
		dateField = createTextField();
		inputPanel.add(dateField);

		contentPanel.add(inputPanel, BorderLayout.CENTER);
	}

	private void createSaveButton() {
		JButton saveButton = new JButton("Сохранить");
		saveButton.setPreferredSize(new Dimension(120, 40));
		saveButton.addActionListener(e -> {
			if (validateInput()) {
				saveTournament();
			}
		});
		contentPanel.add(saveButton, BorderLayout.SOUTH);
	}

	private boolean validateInput() {
		String name = nameField.getText().trim();
		String location = locationField.getText().trim();
		String date = dateField.getText().trim();

		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Пожалуйста, введите название турнира.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (location.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Пожалуйста, введите место проведения турнира.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (date.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Пожалуйста, введите дату проведения турнира.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			LocalDate tournamentDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			if (tournamentDate.getYear() < 2020) {
				JOptionPane.showMessageDialog(frame, "Турниры проводятся только с 2020 года.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (DateTimeParseException ex) {
			JOptionPane.showMessageDialog(frame, "Неправильный формат даты (гггг-мм-дд).", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void loadTournament(int tournamentId) {
		Tournament tournament = useCase.getAllTournaments().stream()
				.filter(t -> t.getId() == tournamentId)
				.findFirst()
				.orElse(null);
		if (tournament != null) {
			nameField.setText(tournament.getName());
			locationField.setText(tournament.getLocation());
			dateField.setText(tournament.getDate());
		}
	}

	private void saveTournament() {
		String name = nameField.getText().trim();
		String location = locationField.getText().trim();
		String date = dateField.getText().trim();

		Tournament tournament = new Tournament(
				tournamentId != null ? tournamentId : useCase.getNextId(),
				name,
				location,
				date
		);

		if (tournamentId == null) {
			useCase.addTournament(tournament);
		} else {
			useCase.updateTournament(tournament);
		}

		parentController.loadTournaments();
		frame.dispose();
	}

	private JLabel createLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("Arial", Font.BOLD, 14));
		return label;
	}
	private JTextField createTextField() {
		JTextField textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		return textField;
	}

	public boolean isVisible() {
		return frame.isVisible();
	}
	public void toFront() {
		frame.toFront();
	}
}
