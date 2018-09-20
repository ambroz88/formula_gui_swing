package com.ambroz.formula.gui.swing.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.ambroz.formula.gamemodel.labels.GeneralLabels;
import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.track.TrackBuilder;
import com.ambroz.formula.gui.swing.utils.Fonts;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class TopMenuBar extends JMenuBar {

    private JMenu optionsMenu;
    private JMenu language;

    private final RaceModel gameModel;
    private final TrackBuilder builder;
    private GeneralLabels optionsLabels;

    public TopMenuBar(RaceModel gModel, TrackBuilder trackBuilder) {
        this.gameModel = gModel;
        this.builder = trackBuilder;
        optionsLabels = new GeneralLabels(this.gameModel.getLanguage());
        initComponents();
    }

    private void initComponents() {
//        optionsDiag = new OptionsWindow(gameModel);
        optionsMenu = new JMenu();
        optionsMenu.setFont(Fonts.MENU_FONT);
        optionsMenu.setText(optionsLabels.getValue(GeneralLabels.OPTIONS) + "...");
        optionsMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
//                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//                optionsDiag.setLocation(dim.width / 2 - optionsDiag.getWidth() / 2, dim.height / 2 - optionsDiag.getHeight() / 2);
//                optionsDiag.setVisible(true);
            }
        });

        language = new JMenu();
        language.setIcon(new ImageIcon(getClass().getClassLoader().getResource("BritishFlag 24x24.png")));

        language.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (gameModel.getLanguage().equals("CZ")) {
                    language.setIcon(new ImageIcon(getClass().getClassLoader().getResource("BritishFlag 24x24.png")));
                    gameModel.setLanguage("EN");
                    builder.setLanguage("EN");
                } else {
                    language.setIcon(new ImageIcon(getClass().getClassLoader().getResource("CzechFlag 24x24.png")));
                    gameModel.setLanguage("CZ");
                    builder.setLanguage("CZ");
                }
                optionsLabels = new GeneralLabels(gameModel.getLanguage());
                optionsMenu.setText(optionsLabels.getValue(GeneralLabels.OPTIONS) + "...");
            }
        });

        add(optionsMenu);
        add(language);
    }

}
