import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DescribedButton extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField nameField = new JTextField(10);
	private JTextField descriptionField = new JTextField(10);

	protected DescribedButton() {
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

	protected String getDescription() {
		return descriptionField.getText();
	}

}
