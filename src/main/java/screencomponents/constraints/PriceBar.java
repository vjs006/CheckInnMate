package screencomponents.constraints;

import screencomponents.ScreenPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class PriceBar extends JPanel {
    private JSlider costSlider;
    private JSlider associatedSlider;
    private int bound;
    private int type;

    public static final int LOWER = 1;
    public static final int UPPER = 2;

    public PriceBar(int type) {
        this.associatedSlider = associatedSlider;
        this.type = type;

        setBackground(Color.white);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.black), BorderFactory.createEmptyBorder(5, 0, 5, 0)));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;

        JLabel costTitle = new JLabel();
        if (type == 1)
             costTitle.setText("COST LOWER BOUND");
        else if (type == 2)
            costTitle.setText("COST UPPER BOUND");
        costTitle.setHorizontalAlignment(JLabel.CENTER);
        costTitle.setVerticalAlignment(JLabel.CENTER);
        costTitle.setForeground(Color.black);
        costTitle.setBackground(Color.white);
        costTitle.setFont(new Font("Times New Roman", Font.PLAIN, 10));

        add(new JPanel() {{
            setBackground(Color.white);
            setLayout(new BorderLayout());
            add(costTitle);
        }}, c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        costSlider = new JSlider(0, 10000);
        costSlider.setBackground(Color.white);
        costSlider.setMinorTickSpacing(500);
        costSlider.setMajorTickSpacing(2500);
        if (type == LOWER)
            costSlider.setValue(0);
        else if (type == UPPER)
            costSlider.setValue(10000);
        costSlider.setPaintTicks(true);
        costSlider.setPaintLabels(true);
        costSlider.setSnapToTicks(true);
        //costSlider.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        costSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedValue;
                switch(type) {
                    case LOWER:
                        selectedValue = costSlider.getValue();
                        System.out.println("Selected Value: " + selectedValue);
                        if (selectedValue > associatedSlider.getValue())
                            costSlider.setValue(associatedSlider.getValue());
                        break;
                    case UPPER:
                        selectedValue = costSlider.getValue();
                        System.out.println("Selected Value: " + selectedValue);
                        if (selectedValue < associatedSlider.getValue())
                            costSlider.setValue(associatedSlider.getValue());
                        break;
                }
            }
        });

        add(costSlider, c);
    }
    public void addAssociatedSlider(JSlider slider) {
        associatedSlider = slider;
    }
    public JSlider getCostSlider() {
        return costSlider;
    }
    public JSlider getAssociatedSlider() {
        return associatedSlider;
    }

    public void setAssociatedSlider(JSlider associatedSlider) {
        this.associatedSlider = associatedSlider;
    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setSize(150, 150);
        window.setTitle("CheckInnMate");
        //window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(Color.white);

        //window.add(new PriceBar());
        window.setVisible(true);
    }

}
