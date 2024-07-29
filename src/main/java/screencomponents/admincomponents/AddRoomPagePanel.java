package screencomponents.admincomponents;

import javax.swing.*;
import java.awt.*;

public class AddRoomPagePanel extends JPanel {
    public AddRoomPagePanel(){
        setBackground(Color.white);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createLineBorder(Color.black, 4)));
        add(new AddRoomPanel(), new GridBagConstraints());

    }
}
