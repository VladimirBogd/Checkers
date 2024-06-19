import presentation.gui.menuwindow.MenuWindowView;
import presentation.gui.menuwindow.MenuWindowController;

public class Main {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MenuWindowController(new MenuWindowView());
			}
		});
	}
}