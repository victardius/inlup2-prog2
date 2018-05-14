import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class CoordinatesButton extends JPanel {
	
	private static final long serialVersionUID = 1L;
	JTextField xCoordinate = new JTextField(3);
	JTextField yCoordinate = new JTextField(3);

	CoordinatesButton() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel row1 = new JPanel();
		row1.add(new JLabel("X:"));
		row1.add(xCoordinate);
		add(row1);
		JPanel row2 = new JPanel();
		row2.add(new JLabel("Y:"));
		row2.add(yCoordinate);
		add(row2);
	}

	public int getXCoordinate() {
		return Integer.parseInt(xCoordinate.getText());
	}

	public int getYCoordinate() {
		return Integer.parseInt(yCoordinate.getText());
	}
}
