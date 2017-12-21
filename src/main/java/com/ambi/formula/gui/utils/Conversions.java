package com.ambi.formula.gui.utils;

import java.awt.event.MouseEvent;

import javax.swing.JViewport;

import com.ambi.formula.gamemodel.datamodel.Point;

/**
 *
 * @author Jiri Ambroz
 */
public final class Conversions {

    public static Point clickToPoint(MouseEvent evt) {
        java.awt.Point dif = ((JViewport) evt.getSource()).getViewPosition();
        return new Point(evt.getPoint().x + dif.x, evt.getPoint().y + dif.y);
    }

}
