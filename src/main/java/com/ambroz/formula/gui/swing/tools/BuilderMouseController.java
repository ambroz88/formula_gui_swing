package com.ambroz.formula.gui.swing.tools;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JViewport;

import com.ambroz.formula.gamemodel.track.TrackBuilder;
import com.ambroz.formula.gui.swing.utils.Conversions;

/**
 * This class is mouseAdapter for panel TrackBuilderComponent. It says what happen when user clicks into to panel for
 * drawing track.
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class BuilderMouseController extends MouseAdapter {

    private final Cursor defCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private final Cursor hndCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    private final Point pp;
    private final JPanel image;
    private final TrackBuilder builder;
    private boolean edit;

    public BuilderMouseController(JPanel image, TrackBuilder gModel) {
        this.image = image;
        this.builder = gModel;
        pp = new Point();
        edit = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!edit) {
            image.setCursor(hndCursor);
            JViewport vport = (JViewport) e.getSource();
            Point cp = e.getPoint();
            Point vp = vport.getViewPosition();
            vp.translate(pp.x - cp.x, pp.y - cp.y);
            image.scrollRectToVisible(new Rectangle(vp, vport.getSize()));
            pp.setLocation(cp);
        }
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        if (builder.memorizeTrackPoint(Conversions.clickToPoint(evt))) {
            edit = true;
        } else {
            edit = false;
            pp.setLocation(evt.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        builder.replaceTrackPoint(Conversions.clickToPoint(evt));
        image.setCursor(defCursor);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        builder.buildTrack(Conversions.clickToPoint(evt));
    }

}
