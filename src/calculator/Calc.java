package calculator;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Calc extends JFrame {

	JLabel label = new JLabel();
	JTextField textbox = new JTextField("");
	String nowform = "";
	Font font = new Font("Yu Gothic UI", Font.PLAIN, 32);
	Font font2 = new Font("Yu Gothic UI", Font.PLAIN, 24);
	Font font3 = new Font("Consolas", Font.PLAIN, 24);

	Form form = new Form();

	Calc() {
		setTitle("Calculator");
		setBounds(100, 100, 640, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setLayout(null);
		textbox.setBounds(15, 64, 600, 48);
		textbox.setFont(font2);
		label.setText("Show calculated answer on here.");
		label.setBounds(10, 10, 500, 28);
		label.setFont(font);
		this.add(textbox);
		this.add(label);
	}

    private void setListener() {
        textbox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateForm();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateForm();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateForm();
            }
            private void calculateForm() {
                if (nowform.equals(textbox.getText())) {
                    return;
                }
                form.init(textbox.getText());
                form.split();
                form.splitOut();
                label.setText(form.calc());
                nowform = new String(textbox.getText());
            }
        });
    }

	public static void main(String[] args) {
		Calc m = new Calc();
        m.setListener();
	}

}