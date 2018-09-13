package com.ambroz.formula.gui.swing;

import com.ambroz.formula.gamemodel.GameModel;
import com.ambroz.formula.gui.swing.components.RaceComponent;
import com.ambroz.formula.gui.swing.components.TopMenuBar;
import com.ambroz.formula.gui.swing.components.TrackBuilderComponent;
import com.ambroz.formula.gui.swing.components.TracksComponent;
import com.ambroz.formula.gui.swing.tools.MouseDragging;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * Component that shows main window with track and formulas and toolbar.
 *
 * @author Jiri Ambroz
 */
public final class ApplicationWindow extends JFrame {

    private final GameModel gModel;

    public ApplicationWindow() {
        setTitle("Formula Race 1.0");
        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("Helmet 32x32.png"));
        setIconImage(img.getImage());
        setLayout(new BorderLayout());
        this.requestFocusInWindow(true);
        setMinimumSize(new Dimension(700, 400));

        gModel = new GameModel();
        gModel.setLanguage("EN");

        JPanel leftPanel = new JPanel(new BorderLayout());
        TopMenuBar topMenu = new TopMenuBar(gModel);

        JScrollPane scrollTrackSelectorPanel = new JScrollPane(new TracksComponent(gModel));
        scrollTrackSelectorPanel.setPreferredSize(new Dimension(150, 0));
        leftPanel.add(topMenu, BorderLayout.NORTH);
        leftPanel.add(scrollTrackSelectorPanel, BorderLayout.CENTER);

        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        JScrollPane scrollDrawPanel = createDrawPanel();
        JScrollPane scrollTrackBuilderPanel = createTrackBuilderPanel();
        tabs.addTab("Game", scrollDrawPanel);
        tabs.addTab("Build Track", scrollTrackBuilderPanel);

        add(leftPanel, BorderLayout.WEST);
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

    private JScrollPane createDrawPanel() {
        RaceComponent drawPanel = new RaceComponent(gModel);

        JScrollPane scroll = new JScrollPane(drawPanel);
        MouseDragging scrollListener = new MouseDragging(drawPanel, gModel);
        scroll.getViewport().addMouseMotionListener(scrollListener);
        scroll.getViewport().addMouseListener(scrollListener);

        return scroll;
    }

    private JScrollPane createTrackBuilderPanel() {
        TrackBuilderComponent drawPanel = new TrackBuilderComponent(gModel);

        JScrollPane scroll = new JScrollPane(drawPanel);
        MouseDragging scrollListener = new MouseDragging(drawPanel, gModel);
        scroll.getViewport().addMouseMotionListener(scrollListener);
        scroll.getViewport().addMouseListener(scrollListener);

        return scroll;
    }

}
