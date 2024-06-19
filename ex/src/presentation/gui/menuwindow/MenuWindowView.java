package presentation.gui.menuwindow;

import javax.swing.*;
import java.awt.*;

public class MenuWindowView {
	private final JFrame frame;

	private JLabel titleLabel;
	private JButton participantsButton;
	private JButton tournamentsButton;
	private JButton scoresButton;
	private JButton exitButton;
	private JLabel imageLabel;

	public MenuWindowView() {
		frame = new JFrame("Главное меню");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		initComponents();
		placeComponents();

		frame.setVisible(true);

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame,
						"Вы действительно хотите выйти?", "Выход",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	private void initComponents() {
		titleLabel = new JLabel("Главное меню");
		titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 35f));
		participantsButton = new JButton("Участники");
		participantsButton.setFont(participantsButton.getFont().deriveFont(Font.PLAIN, 18f));
		tournamentsButton = new JButton("Турниры");
		tournamentsButton.setFont(tournamentsButton.getFont().deriveFont(Font.PLAIN, 18f));
		scoresButton = new JButton("Статистика");
		scoresButton.setFont(scoresButton.getFont().deriveFont(Font.PLAIN, 18f));
		exitButton = new JButton("Выход");
		exitButton.setFont(exitButton.getFont().deriveFont(Font.PLAIN, 18f));
		ImageIcon imageIcon = new ImageIcon("V:\\InteliJProjects\\Kursovik\\src\\Images\\checkers.png");
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(330, 440, Image.SCALE_SMOOTH));
		imageLabel = new JLabel(imageIcon);
	}

	private void placeComponents() {
		frame.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// Заголовок
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(20, 20, 20, 20);
		frame.getContentPane().add(titleLabel, gbc);

		// Панель с кнопками
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 2.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(20, 20, 20, 20);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(4, 1, 10, 20));
		buttonsPanel.add(participantsButton);
		buttonsPanel.add(tournamentsButton);
		buttonsPanel.add(scoresButton);
		buttonsPanel.add(exitButton);
		frame.getContentPane().add(buttonsPanel, gbc);

		// Изображение
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(20, 20, 20, 20);
		frame.getContentPane().add(imageLabel, gbc);
	}

	public JFrame getFrame() {
		return frame;
	}
	public JButton getParticipantsButton() {
		return participantsButton;
	}
	public JButton getTournamentsButton() {
		return tournamentsButton;
	}
	public JButton getScoresButton() {
		return scoresButton;
	}
	public JButton getExitButton() {
		return exitButton;
	}
}
