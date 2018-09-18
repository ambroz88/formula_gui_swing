package com.ambroz.formula.gui.swing.tools;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JViewport;

import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gui.swing.utils.Conversions;

/**
 * This class is mouseAdapter for panel RaceComponent. It says what happen when user clicks into to main panel with the
 * race itself.
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class RaceMouseController extends MouseAdapter {

    private final Cursor defCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private final Cursor hndCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    private final Point pp;
    private final JPanel image;
    private final RaceModel gModel;

    public RaceMouseController(JPanel image, RaceModel gModel) {
        this.image = image;
        this.gModel = gModel;
        pp = new Point();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        image.setCursor(hndCursor);
        JViewport vport = (JViewport) e.getSource();
        Point cp = e.getPoint();
        Point vp = vport.getViewPosition();
        vp.translate(pp.x - cp.x, pp.y - cp.y);
        image.scrollRectToVisible(new Rectangle(vp, vport.getSize()));
        pp.setLocation(cp);
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        pp.setLocation(evt.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        image.setCursor(defCursor);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        gModel.moveWithPlayer(Conversions.clickToPoint(evt));
        image.repaint();
    }

}
