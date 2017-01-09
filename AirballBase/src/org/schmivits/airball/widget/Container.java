package org.schmivits.airball.widget;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Container extends Widget {

    protected final List<Widget> mChildren = new ArrayList<Widget>();

    public Container() {}

    public List<Widget> getChildren() {
        return mChildren;
    }

    @Override
    protected void drawContents(Graphics2D g) {
        for (Widget w : mChildren) {
            w.draw(g);
        }
    }

    protected void center(Widget w) {
        w.moveTo(
                (getWidth() - w.getWidth()) / 2f,
                (getHeight() - w.getHeight()) / 2f);
    }

    protected void centerX(Widget w) {
        w.moveTo(
                (getWidth() - w.getWidth()) / 2f,
                w.getY());
    }

    protected void centerY(Widget w) {
        w.moveTo(
                w.getX(),
                (getHeight() - w.getHeight()) / 2f);
    }
}
