package com.ambroz.formula.gui.swing.utils;

import com.ambroz.formula.gamemodel.datamodel.Point;
import java.awt.event.MouseEvent;
import javax.swing.JViewport;

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
