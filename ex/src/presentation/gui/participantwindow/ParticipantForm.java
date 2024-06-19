package presentation.gui.participantwindow;

import domain.entity.Participant;
import domain.usecase.ParticipantUseCase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParticipantForm {
	private final ParticipantUseCase useCase;
	private final ParticipantsWindowController parentController;
	private final Integer participantId;

	private JFrame frame;
	private JPanel contentPanel;
	private JTextField nameField;
	private JComboBox<String> rankField;
	private JComboBox<String> genderField;
	private JTextField dobField;

	public ParticipantForm(Integer participantId, ParticipantUseCase useCase, ParticipantsWindowController parentController) {
		this.participantId = participantId;
		this.useCase = useCase;
		this.parentController = parentController;
		initialize();
		if (participantId != null) {
			loadParticipant(participantId);
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
		frame = new JFrame(participantId == null ? "Добавить участника" : "Изменить участника");
		frame.setSize(500, 330);
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
		inputPanel.setLayout(new GridLayout(4, 2, 15, 15));
		inputPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

		inputPanel.add(createLabel("ФИО:"));
		nameField = createTextField();
		inputPanel.add(nameField);

		inputPanel.add(createLabel("Разряд:"));
		rankField = createComboBox(new String[]{null, "1 юн.", "2 юн.", "3 юн.", "1 вз.", "2 вз.", "3 вз.", "КМС", "МС"});
		inputPanel.add(rankField);

		inputPanel.add(createLabel("Пол:"));
		genderField = createComboBox(new String[]{null, "м", "ж"});
		inputPanel.add(genderField);

		inputPanel.add(createLabel("Дата рождения (гггг-мм-дд):"));
		dobField = createTextField();
		inputPanel.add(dobField);

		contentPanel.add(inputPanel, BorderLayout.CENTER);
	}

	private void createSaveButton() {
		JButton saveButton = new JButton("Сохранить");
		saveButton.setPreferredSize(new Dimension(120, 40));
		saveButton.addActionListener(e -> {
			if (validateInput()) {
				saveParticipant();
			}
		});
		contentPanel.add(saveButton, BorderLayout.SOUTH);
	}

	private boolean validateInput() {
		String fullName = nameField.getText().trim();
		String rank = (String) rankField.getSelectedItem();
		String gender = (String) genderField.getSelectedItem();
		String birthDate = dobField.getText().trim();

		if (fullName.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Пожалуйста, введите ФИО участника.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (rank == null) {
			JOptionPane.showMessageDialog(frame, "Пожалуйста, выберите разряд участника.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (gender == null) {
			JOptionPane.showMessageDialog(frame, "Пожалуйста, выберите пол участника.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (birthDate.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Пожалуйста, введите дату рождения участника.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			LocalDate birthDateObj = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			int age = Period.between(birthDateObj, LocalDate.now()).getYears();
			if (age < 6) {
				JOptionPane.showMessageDialog(frame, "Участнику должно быть не менее 6 лет.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			if (age > 18) {
				JOptionPane.showMessageDialog(frame, "Участнику должно быть не более 18 лет.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (DateTimeParseException ex) {
			JOptionPane.showMessageDialog(frame, "Неправильный формат даты рождения (гггг-мм-дд).", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void loadParticipant(int participantId) {
		Participant participant = useCase.getAllParticipants().stream()
				.filter(p -> p.getId() == participantId)
				.findFirst()
				.orElse(null);
		if (participant != null) {
			nameField.setText(participant.getFullName());
			rankField.setSelectedItem(participant.getRank());
			genderField.setSelectedItem(String.valueOf(participant.getGender()));
			dobField.setText(participant.getBirthDate());
		}
	}

	private void saveParticipant() {
		String fullName = nameField.getText();
		String rank = (String) rankField.getSelectedItem();
		char gender = ((String) genderField.getSelectedItem()).charAt(0);
		String birthDate = dobField.getText();
		Participant participant = new Participant(
				participantId != null ? participantId : useCase.getNextId(),
				fullName,
				rank,
				gender,
				birthDate
		);
		if (participantId == null) {
			useCase.addParticipant(participant);
		} else {
			useCase.updateParticipant(participant);
		}
		parentController.loadParticipants();
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
	private JComboBox<String> createComboBox(String[] items) {
		JComboBox<String> comboBox = new JComboBox<>(items);
		comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		return comboBox;
	}

	public boolean isVisible() {
		return frame.isVisible();
	}
	public void toFront() {
		frame.toFront();
	}
}
