package com.ambi.formula.gui.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gamemodel.labels.OptionsLabels;
import com.ambi.formula.gui.swing.subcomponents.StartMenu;
import com.ambi.formula.gui.swing.subcomponents.TrackMenu;
import com.ambi.formula.gui.swing.windows.OptionsWindow;

/**
 *
 * @author Jiri Ambroz
 */
public final class TopMenuBar extends JMenuBar {

    public static final Font MENU_FONT = new Font("Segoe UI", 0, 16);

    private JMenu optionsMenu;
    private StartMenu startMenu;
    private TrackMenu trackMenu;
    private OptionsWindow optionsDiag;
    private JMenu language;

    private final GameModel gModel;
    private OptionsLabels optionsLabels;

    public TopMenuBar(GameModel gModel) {
        this.gModel = gModel;
        optionsLabels = new OptionsLabels(this.gModel.getLanguage());
        initComponents();
    }

    private void initComponents() {
        gModel.setLanguage("EN");

        trackMenu = new TrackMenu(gModel.getBuilder());
        startMenu = new StartMenu(gModel);
        optionsDiag = new OptionsWindow(gModel);
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
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                optionsDiag.setLocation(dim.width / 2 - optionsDiag.getWidth() / 2, dim.height / 2 - optionsDiag.getHeight() / 2);
                optionsDiag.setVisible(true);
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
                if (gModel.getLanguage().equals("CZ")) {
                    language.setIcon(new ImageIcon(getClass().getClassLoader().getResource("CzechFlag 24x24.png")));
                    gModel.setLanguage("EN");
                } else {
                    language.setIcon(new ImageIcon(getClass().getClassLoader().getResource("BritishFlag 24x24.png")));
                    gModel.setLanguage("CZ");
                }
                optionsLabels = new OptionsLabels(gModel.getLanguage());
                optionsMenu.setText(optionsLabels.getValue(OptionsLabels.TITLE) + "...");
            }
        });

        add(trackMenu);
        add(startMenu);
        add(optionsMenu);
        add(language);
    }

}
