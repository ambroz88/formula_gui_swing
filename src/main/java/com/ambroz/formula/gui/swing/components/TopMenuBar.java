package com.ambroz.formula.gui.swing.components;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.labels.OptionsLabels;
import com.ambroz.formula.gamemodel.track.TrackBuilder;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class TopMenuBar extends JMenuBar {

    public static final Font MENU_FONT = new Font("Segoe UI", 0, 16);

    private JMenu optionsMenu;
    private JMenu language;

    private final RaceModel gameModel;
    private final TrackBuilder builder;
    private OptionsLabels optionsLabels;

    public TopMenuBar(RaceModel gModel, TrackBuilder trackBuilder) {
        this.gameModel = gModel;
        this.builder = trackBuilder;
        optionsLabels = new OptionsLabels(this.gameModel.getLanguage());
        initComponents();
    }

    private void initComponents() {
//        optionsDiag = new OptionsWindow(gameModel);
        optionsMenu = new JMenu() {
            @Override
            public JPopupMenu getPopupMenu() {
                return new JPopupMenu();
            }
        };
        optionsMenu.setText(optionsLabels.getValue(OptionsLabels.TITLE) + "...");
        optionsMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
//                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//                optionsDiag.setLocation(dim.width / 2 - optionsDiag.getWidth() / 2, dim.height / 2 - optionsDiag.getHeight() / 2);
//                optionsDiag.setVisible(true);
            }
        });

        language = new JMenu() {
            @Override
            public JPopupMenu getPopupMenu() {
                return new JPopupMenu();
            }
        };
        language.setIcon(new ImageIcon(getClass().getClassLoader().getResource("CzechFlag 24x24.png")));

        language.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (gameModel.getLanguage().equals("CZ")) {
                    language.setIcon(new ImageIcon(getClass().getClassLoader().getResource("CzechFlag 24x24.png")));
                    gameModel.setLanguage("EN");
                } else {
                    language.setIcon(new ImageIcon(getClass().getClassLoader().getResource("BritishFlag 24x24.png")));
                    gameModel.setLanguage("CZ");
                }
                optionsLabels = new OptionsLabels(gameModel.getLanguage());
                optionsMenu.setText(optionsLabels.getValue(OptionsLabels.TITLE) + "...");
            }
        });

        add(optionsMenu);
        add(language);
    }

}
