package com.ambroz.formula.gui.swing.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.ambroz.formula.gamemodel.enums.Language;
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
    private JButton language;

    private final RaceModel gameModel;
    private final TrackBuilder builder;
    private GeneralLabels optionsLabels;

    public TopMenuBar(RaceModel gModel, TrackBuilder trackBuilder) {
        this.gameModel = gModel;
        this.builder = trackBuilder;
        optionsLabels = new GeneralLabels(this.gameModel.getLanguage().toString());
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        setPreferredSize(new Dimension(200, 50));
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

        language = new JButton();
        language.setIcon(new ImageIcon(getClass().getClassLoader().getResource("BritishFlag 36x36.png")));
        language.setMargin(new Insets(0, 0, 0, 0));
        language.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (gameModel.getLanguage().equals(Language.Czech)) {
                    language.setIcon(new ImageIcon(getClass().getClassLoader().getResource("BritishFlag 36x36.png")));
                    gameModel.setLanguage(Language.English);
                    builder.setLanguage(Language.English);
                } else {
                    language.setIcon(new ImageIcon(getClass().getClassLoader().getResource("CzechFlag 36x36.png")));
                    gameModel.setLanguage(Language.Czech);
                    builder.setLanguage(Language.Czech);
                }
                optionsLabels = new GeneralLabels(gameModel.getLanguage().toString());
                optionsMenu.setText(optionsLabels.getValue(GeneralLabels.OPTIONS) + "...");
            }
        });

        add(optionsMenu, BorderLayout.WEST);
        add(language, BorderLayout.EAST);
    }

}
