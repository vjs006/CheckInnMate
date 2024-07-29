package screencomponents.pages;

import hotelcomponents.GameRoom;
import hotelcomponents.Gym;
import hotelcomponents.SwimmingPool;
import layouts.WrapLayout;
import screencomponents.AppDefaultButton;
import screencomponents.ScreenPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

public class UtilizeAmenitiesPagePanel extends JPanel {

    public UtilizeAmenitiesPagePanel() {
        setBackground(Color.white);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createLineBorder(Color.black, 4)));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.white);
        buttonsPanel.setLayout(new WrapLayout(WrapLayout.CENTER, 500, 80));

        buttonsPanel.add(new AppDefaultButton("GAME ROOM") {{
            setFont(new Font("Times New Roman", Font.PLAIN, 60));

        }});
        buttonsPanel.add(new AppDefaultButton("SWIMMING POOL") {{
            setFont(new Font("Times New Roman", Font.PLAIN, 60));
        }});
        buttonsPanel.add(new AppDefaultButton("GYM") {{
            setFont(new Font("Times New Roman", Font.PLAIN, 60));
        }});
        buttonsPanel.add(new AppDefaultButton("RESTAURANT") {{
            setFont(new Font("Times New Roman", Font.PLAIN, 60));
        }});

        for (Component button : buttonsPanel.getComponents()) {
            ((JButton)button).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ScreenPanel screenPanel = (ScreenPanel)UtilizeAmenitiesPagePanel.this.getParent();
                    switch(((AppDefaultButton)button).getOriginalText()) {
                        case "GYM" :
                            screenPanel.remove(1);
                            screenPanel.add(new UseAmenityPagePanel<Gym>(new Gym(LocalTime.of(7, 0),
                                    LocalTime.of(20, 0), 30, 500)));
                            break;
                        case "GAME ROOM" :
                            screenPanel.remove(1);
                            screenPanel.add(new UseAmenityPagePanel<GameRoom>(new GameRoom(LocalTime.of(11, 0),
                                    LocalTime.of(22, 0), 30, 600)));
                            break;
                        case "SWIMMING POOL" :
                            screenPanel.remove(1);
                            screenPanel.add(new UseAmenityPagePanel<SwimmingPool>(new SwimmingPool(LocalTime.of(9, 0),
                                    LocalTime.of(21, 0), 30, 350)));
                            break;
                    }
                    screenPanel.revalidate();
                    screenPanel.repaint();
                }
            });
        }
        add(buttonsPanel, BorderLayout.CENTER);
    }
}
