package com.ambroz.formula.gui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ambroz.formula.gamemodel.datamodel.Paper;
import com.ambroz.formula.gamemodel.datamodel.PropertyChanger;
import com.ambroz.formula.gamemodel.enums.FormulaType;
import com.ambroz.formula.gamemodel.race.Formula;
import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.track.TrackBuilder;
import com.ambroz.formula.gui.swing.components.FormulaPanel;
import com.ambroz.formula.gui.swing.components.TabsComponent;
import com.ambroz.formula.gui.swing.components.TrackListComponent;
import com.ambroz.formula.gui.swing.menu.TopMenuBar;
import com.ambroz.formula.gui.swing.windows.ResultWindow;

/**
 * Component that shows main window with track and formulas and toolbar.
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class ApplicationWindow extends JFrame implements PropertyChangeListener {

    private RaceModel raceModel;
    private TrackBuilder builder;
    private List<FormulaPanel> playerList;

    private TrackListComponent trackList;
    private JTabbedPane tabs;
    private JPanel playersPanel;

    public ApplicationWindow() {
        initWindow();
        initGameLogic();

        add(initLeftPanel(), BorderLayout.WEST);

        initTabs();
        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        ApplicationWindow gameWindow = new ApplicationWindow();
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth() - 50;
        int height = gd.getDisplayMode().getHeight() - 50;
        gameWindow.setSize(new Dimension(width, height));
    }

    private void initWindow() {
        setTitle("Formula Race 1.3");
        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("Helmet 32x32.png"));
        setIconImage(img.getImage());
        setLayout(new BorderLayout());
        requestFocusInWindow(true);
        setMinimumSize(new Dimension(800, 600));
    }

    private void initGameLogic() {
        Paper paper = new Paper();

        raceModel = new RaceModel(paper);
        raceModel.addPropertyChangeListener(this);

        builder = new TrackBuilder(paper);
        playerList = new ArrayList<>();
    }

    private JPanel initLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        playersPanel = new JPanel();

        TopMenuBar topMenu = new TopMenuBar(raceModel, builder);
        trackList = new TrackListComponent(raceModel, builder);
        JScrollPane trackSelectorPanel = new JScrollPane(trackList);
        trackSelectorPanel.setPreferredSize(new Dimension(TrackListComponent.LIST_WIDTH, 0));

        leftPanel.add(topMenu, BorderLayout.NORTH);
        leftPanel.add(trackSelectorPanel, BorderLayout.CENTER);
        leftPanel.add(playersPanel, BorderLayout.SOUTH);
        return leftPanel;
    }

    private void initTabs() {
        tabs = new TabsComponent(raceModel, builder);

        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int tabIndex = tabs.getSelectedIndex();

                if (tabIndex == 0) {
                    trackList.setActiveTab(TrackListComponent.RACE);
                    playersPanel.setVisible(true);
                    trackList.setCoordinatesVisibility(false);
                } else if (tabIndex == 1) {
                    trackList.setActiveTab(TrackListComponent.BUILD);
                    playersPanel.setVisible(false);
                    trackList.setCoordinatesVisibility(true);
                }

            }
        });

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertyChanger.LANGUAGE)) {
            for (FormulaPanel panel : playerList) {
                panel.setLanguage(raceModel.getLanguage());
            }
        } else if (evt.getPropertyName().equals(PropertyChanger.RACE_NEW_GAME)) {
            playersPanel.removeAll();
            int racers = 0;
            FormulaPanel panel;
            Formula form;

            for (int i = 1; i <= raceModel.getTurnMaker().getFormulaCount(); i++) {
                form = raceModel.getTurnMaker().getFormula(i);

                if (form.getType() != FormulaType.None) {
                    panel = new FormulaPanel(form);
                    panel.setLanguage(raceModel.getLanguage());
                    playersPanel.add(panel);
                    playerList.add(panel);
                    racers++;
                }
            }

            playersPanel.setLayout(new GridLayout(racers, 1));
        } else if (evt.getPropertyName().equals(PropertyChanger.RACE_WINNER)) {
            ResultWindow results = new ResultWindow(raceModel);
            results.setVisible(true);
        }
    }

}
