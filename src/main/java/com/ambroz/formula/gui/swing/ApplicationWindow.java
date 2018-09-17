package com.ambroz.formula.gui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ambroz.formula.gamemodel.GameModel;
import com.ambroz.formula.gamemodel.datamodel.Track;
import com.ambroz.formula.gui.swing.components.BuilderMenuBar;
import com.ambroz.formula.gui.swing.components.RaceComponent;
import com.ambroz.formula.gui.swing.components.TopMenuBar;
import com.ambroz.formula.gui.swing.components.TrackBuilderComponent;
import com.ambroz.formula.gui.swing.components.TracksComponent;
import com.ambroz.formula.gui.swing.components.VerticalLabelUI;
import com.ambroz.formula.gui.swing.tools.BuilderMouseController;
import com.ambroz.formula.gui.swing.tools.RaceMouseController;

/**
 * Component that shows main window with track and formulas and toolbar.
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class ApplicationWindow extends JFrame {

    private final GameModel gModel;
    private JTabbedPane tabs;

    public ApplicationWindow() {
        setTitle("Formula Race 1.0");
        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("Helmet 32x32.png"));
        setIconImage(img.getImage());
        setLayout(new BorderLayout());
        requestFocusInWindow(true);
        setMinimumSize(new Dimension(700, 400));

        gModel = new GameModel();
        gModel.setLanguage("EN");

        JPanel leftPanel = initLeftPanel();
        add(leftPanel, BorderLayout.WEST);

        initTabs();
        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        ApplicationWindow gameWindow = new ApplicationWindow();
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        gameWindow.setSize(new Dimension(width, height));
    }

    private JPanel initLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        TopMenuBar topMenu = new TopMenuBar(gModel);
        leftPanel.add(topMenu, BorderLayout.NORTH);

        JScrollPane scrollTrackSelectorPanel = new JScrollPane(new TracksComponent(gModel));
        scrollTrackSelectorPanel.setPreferredSize(new Dimension(150, 0));
        leftPanel.add(scrollTrackSelectorPanel, BorderLayout.CENTER);

        return leftPanel;
    }

    private void initTabs() {
        tabs = new JTabbedPane(JTabbedPane.LEFT);

        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int tabIndex = tabs.getSelectedIndex();
                if (tabIndex == 0) {
                    gModel.setStage(GameModel.FIRST_TURN);
                } else if (tabIndex == 1) {
                    gModel.getTrackBuilder().startBuild(Track.LEFT);
                }
            }
        });
        JScrollPane scrollDrawPanel = createDrawPanel();
        JPanel trackBuilderPanel = createTrackBuilderPanel();
        tabs.addTab(null, scrollDrawPanel);
        tabs.addTab(null, trackBuilderPanel);

        tabs.setTabComponentAt(0, createVerticalLabel(" Play Race "));
        tabs.setTabComponentAt(1, createVerticalLabel(" Build Track "));
    }

    private JLabel createVerticalLabel(String title) {
        JLabel labelGame = new JLabel(title);
        labelGame.setUI(new VerticalLabelUI(false));
        return labelGame;
    }

    private JScrollPane createDrawPanel() {
        RaceComponent drawPanel = new RaceComponent(gModel);

        JScrollPane scroll = new JScrollPane(drawPanel);
        RaceMouseController scrollListener = new RaceMouseController(drawPanel, gModel);
        scroll.getViewport().addMouseMotionListener(scrollListener);
        scroll.getViewport().addMouseListener(scrollListener);

        return scroll;
    }

    private JPanel createTrackBuilderPanel() {
        BuilderMenuBar builderBar = new BuilderMenuBar(gModel.getTrackBuilder());
        TrackBuilderComponent drawPanel = new TrackBuilderComponent(gModel.getTrackBuilder());

        JScrollPane scroll = new JScrollPane(drawPanel);
        BuilderMouseController scrollListener = new BuilderMouseController(drawPanel, gModel.getTrackBuilder());
        scroll.getViewport().addMouseMotionListener(scrollListener);
        scroll.getViewport().addMouseListener(scrollListener);

        JPanel builderPanel = new JPanel(new BorderLayout());
        builderPanel.add(builderBar, BorderLayout.NORTH);
        builderPanel.add(scroll, BorderLayout.CENTER);
        return builderPanel;
    }

}
