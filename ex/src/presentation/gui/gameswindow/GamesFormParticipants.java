package presentation.gui.gameswindow;

import domain.entity.Games;
import domain.usecase.GamesUseCase;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamesFormParticipants {
	private final JFrame frame;
	private final GamesUseCase useCase;
	private final GamesWindowController parentController;
	private final int tournamentId;

	private JList<String> listFrom;
	private DefaultListModel listModelFrom;
	private JButton addButton;
	private JList<String> listTo;
	private DefaultListModel listModelTo;
	private JButton removeButton;
	private JButton saveButton;

	public GamesFormParticipants(int tournamentId, GamesUseCase useCase, GamesWindowController parentController) {
		this.tournamentId = tournamentId;
		this.useCase = useCase;
		this.parentController = parentController;

		frame = new JFrame("Добавить участников");
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		initComponents();
		placeComponents();
		initListeners();

		frame.setVisible(true);
	}

	private void initComponents() {
		listModelFrom = new DefaultListModel();
		listFrom = new JList(listModelFrom);
		listFrom.setSelectedIndex(0);
		listFrom.setFocusable(false);
		addButton = new JButton("Добавить");

		listModelTo = new DefaultListModel();
		listTo = new JList(listModelTo);
		listTo.setSelectedIndex(0);
		listTo.setFocusable(false);
		removeButton = new JButton("Удалить");

		saveButton = new JButton("Сохранить");
		loadParticipants();
		listFrom.setSelectedIndex(0);
		listTo.setSelectedIndex(0);
	}

	private void placeComponents() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5, 0));

		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(1, 2, 5, 0));

		JPanel panelFrom = new JPanel();
		panelFrom.setLayout(new BorderLayout());
		panelFrom.add(new JScrollPane(listFrom), BorderLayout.CENTER);
		panelFrom.add(addButton, BorderLayout.SOUTH);

		JPanel panelTo = new JPanel();
		panelTo.setLayout(new BorderLayout());
		panelTo.add(new JScrollPane(listTo), BorderLayout.CENTER);
		panelTo.add(removeButton, BorderLayout.SOUTH);

		upperPanel.add(panelFrom);
		upperPanel.add(panelTo);

		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		lowerPanel.add(saveButton);

		mainPanel.add(upperPanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);

		frame.getContentPane().add(mainPanel);
	}

	private void initListeners() {
		addButton.addActionListener(e -> {
			String selectedFrom = listFrom.getSelectedValue();
			if (selectedFrom != null) {
				int id = getIdFromString(selectedFrom);
				if (!isIdPresentInListTo(id)) {
					listModelTo.addElement(selectedFrom);
				} else {
					JOptionPane.showMessageDialog(frame, "Участник с id " + id + " уже присутствует в списке.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				}
				int selectedIndex = listFrom.getSelectedIndex();
				if (selectedIndex < listFrom.getModel().getSize() - 1) {
					listFrom.setSelectedIndex(selectedIndex + 1);
				} else {
					listFrom.setSelectedIndex(0);
				}
			}
		});

		removeButton.addActionListener(e -> {
			String selectedTo = listTo.getSelectedValue();
			if (selectedTo!= null) {
				listModelTo.removeElement(selectedTo);
				listTo.setSelectedIndex(0);
			}
		});

		saveButton.addActionListener(e -> {
			saveParticipants();
			frame.dispose();
		});
	}

	private int getIdFromString(String str) {
		int startIndex = str.indexOf("(") + 1;
		int endIndex = str.indexOf(")");
		String idStr = str.substring(startIndex, endIndex);
		return Integer.parseInt(idStr);
	}

	private boolean isIdPresentInListTo(int id) {
		for (int i = 0; i < listModelTo.getSize(); i++) {
			String item = (String) listModelTo.getElementAt(i);
			int itemId = getIdFromString(item);
			if (itemId == id) {
				return true;
			}
		}
		return false;
	}

	private void loadParticipants() {
		List<Games> games = useCase.getAllGamesParticipants();
		listModelFrom.clear();
		for (Games game : games) {
			listModelFrom.addElement("(" + game.getId() + "): " + game.getParticipantName());
		}
		games = useCase.getGamesParticipants(tournamentId);
		listModelTo.clear();
		for (Games game : games) {
			listModelTo.addElement("(" + game.getId() + "): " + game.getParticipantName());
		}
	}

	private void saveParticipants() {
		useCase.saveGamesParticipants(tournamentId, listModelTo.toArray());
		parentController.loadParticipants();
		parentController.loadGames(false);
		parentController.loadResults(false, false);
	}
}
