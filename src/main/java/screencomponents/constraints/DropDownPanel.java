package screencomponents.constraints;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DropDownPanel<T> extends JPanel {
    private JComboBox<T> dropDownMenu;

    public DropDownPanel(String text, T[] arr) {
        setBackground(Color.white);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.black), BorderFactory.createEmptyBorder(5, 0, 5, 0)));
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 15, 0);


        JLabel textLabel = new JLabel(text);
        textLabel.setBackground(Color.white);
        textLabel.setForeground(Color.black);
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setVerticalAlignment(JLabel.CENTER);
        textLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        add(textLabel, c);

        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);

        dropDownMenu = new JComboBox<T>(arr);
        ((JLabel)dropDownMenu.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        add(dropDownMenu, c);
    }

    public String getCurrentSelection() {
        return (String)dropDownMenu.getSelectedItem();
    }
}
