package presentation.gui.participantwindow;

import domain.entity.Participant;
import domain.usecase.ParticipantUseCase;
import presentation.gui.menuwindow.MenuWindowController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ParticipantsWindowController {
	private final ParticipantsWindowView view;
	private final ParticipantUseCase useCase;
	private ParticipantForm participantForm;

	public ParticipantsWindowController(ParticipantUseCase useCase, MenuWindowController menuWindowController) {
		this.useCase = useCase;
		this.view = new ParticipantsWindowView(menuWindowController);
		initController(menuWindowController);
		loadParticipants();
	}

	private void initController(MenuWindowController menuWindowController) {
		view.getAddButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (participantForm == null || !participantForm.isVisible()) {
					participantForm = new ParticipantForm(null, useCase, ParticipantsWindowController.this);
				} else {
					participantForm.toFront();
				}
			}
		});

		view.getEditButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = view.getTable().getSelectedRow();
				if (selectedRow >= 0) {
					int id = (int) view.getTable().getValueAt(selectedRow, 0);
					new ParticipantForm(id, useCase, ParticipantsWindowController.this);
				} else {
					JOptionPane.showMessageDialog(view.getFrame(), "Выберите участника для изменения.");
				}
			}
		});

		view.getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = view.getTable().getSelectedRow();
				if (selectedRow >= 0) {
					int id = (int) view.getTable().getValueAt(selectedRow, 0);
					useCase.deleteParticipant(id);
					loadParticipants();
				} else {
					JOptionPane.showMessageDialog(view.getFrame(), "Выберите участника для удаления.");
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

	public void loadParticipants() {
		List<Participant> participants = useCase.getAllParticipants();
		ParticipantsTableModel model = new ParticipantsTableModel(participants);
		view.getTable().setModel(model);
	}

	public ParticipantsWindowView getView() {
		return view;
	}
}
