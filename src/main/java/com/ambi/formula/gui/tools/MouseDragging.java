package com.ambi.formula.gui.tools;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JViewport;

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gui.swing.subcomponents.Draw;
import com.ambi.formula.gui.utils.Conversions;

/**
 * This class is mouseAdapter for panel Draw. It says what happen when user clicks into to main game
 * panel.
 *
 * @author Jiri Ambroz
 */
public class MouseDragging extends MouseAdapter {

    private final Cursor defCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private final Cursor hndCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    private final Point pp;
    private final Draw image;
    private final GameModel gModel;
    private boolean edit;

    public MouseDragging(Draw image, GameModel gModel) {
        this.image = image;
        this.gModel = gModel;
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
        if (gModel.isTrackEdit(Conversions.clickToPoint(evt))) {
            edit = true;
        } else {
            edit = false;
            pp.setLocation(evt.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        gModel.windowMouseReleased(Conversions.clickToPoint(evt));
        image.setCursor(defCursor);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        gModel.windowMouseClicked(Conversions.clickToPoint(evt));
        image.repaint();
    }

}
