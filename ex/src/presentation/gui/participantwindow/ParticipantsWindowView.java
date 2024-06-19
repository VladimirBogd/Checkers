package presentation.gui.participantwindow;

import presentation.gui.menuwindow.MenuWindowController;

import javax.swing.*;
import java.awt.*;

public class ParticipantsWindowView {
	private JFrame frame;
	private JTable table;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton goToMenuButton;

	public ParticipantsWindowView(MenuWindowController menuWindowController) {
		// Инициализация и настройка окна
		initializeFrame();
		// Создание таблицы
		initializeTable();
		// Создание и объединение панелей в общую панель внизу окна
		initializeBottomPanel();
		// Настройка обработчика закрытия окна
		initializeWindowClosingHandler(menuWindowController);
	}

	private void initializeFrame() {
		frame = new JFrame("Список участников");
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}

	private void initializeTable() {
		table = new JTable();
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		JScrollPane tableScrollPane = new JScrollPane(table);
		frame.add(tableScrollPane, BorderLayout.CENTER);
	}

	private JPanel initializeButtonsPanel() {
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addButton = new JButton("Добавить");
		editButton = new JButton("Изменить");
		deleteButton = new JButton("Удалить");
		buttonsPanel.add(addButton);
		buttonsPanel.add(editButton);
		buttonsPanel.add(deleteButton);
		return buttonsPanel;
	}

	private JPanel initializeGoToMenuPanel() {
		JPanel goToMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		goToMenuPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		goToMenuButton = new JButton("В главное меню");
		goToMenuPanel.add(goToMenuButton);
		return goToMenuPanel;
	}

	private void initializeBottomPanel() {
		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		bottomPanel.add(initializeButtonsPanel());
		bottomPanel.add(initializeGoToMenuPanel());
		frame.add(bottomPanel, BorderLayout.SOUTH);
	}

	private void initializeWindowClosingHandler(MenuWindowController menuWindowController) {
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame,
						"Вы действительно хотите перейти в главное меню?", "Закрытие окна",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					frame.dispose();
					menuWindowController.getView().getFrame().setLocationRelativeTo(null);
					menuWindowController.getView().getFrame().setVisible(true);
				}
			}
		});
	}

	public JFrame getFrame() {
		return frame;
	}
	public JTable getTable() {
		return table;
	}
	public JButton getAddButton() {
		return addButton;
	}
	public JButton getEditButton() {
		return editButton;
	}
	public JButton getDeleteButton() {
		return deleteButton;
	}
	public JButton getGoToMenuButton() {
		return goToMenuButton;
	}
}
