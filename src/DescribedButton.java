import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DescribedButton extends JPanel {

	private static final long serialVersionUID = 1L;
	JTextField nameField = new JTextField(10);
	JTextField descriptionField = new JTextField(10);

	DescribedButton() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel row1 = new JPanel();
		row1.add(new JLabel("Name:"));
		row1.add(nameField);
		add(row1);
		JPanel row2 = new JPanel();
		row2.add(new JLabel("Description:"));
		row2.add(descriptionField);
		add(row2);
	}

	public String getName() {
		return nameField.getText();
	}

	public String getDescription() {
		return descriptionField.getText();
	}
	
}
