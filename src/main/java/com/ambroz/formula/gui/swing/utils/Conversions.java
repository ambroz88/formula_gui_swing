package com.ambroz.formula.gui.swing.utils;

import java.awt.event.MouseEvent;

import javax.swing.JViewport;

import com.ambroz.formula.gamemodel.datamodel.Point;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class Conversions {

    public static Point clickToGamePoint(int gridSize, MouseEvent evt) {
        Point click = Conversions.clickToPoint(evt);
        click.toGridUnits(gridSize);
        return click;
    }

    private static Point clickToPoint(MouseEvent evt) {
        java.awt.Point dif = ((JViewport) evt.getSource()).getViewPosition();
        return new Point(evt.getPoint().x + dif.x, evt.getPoint().y + dif.y);
    }

}
