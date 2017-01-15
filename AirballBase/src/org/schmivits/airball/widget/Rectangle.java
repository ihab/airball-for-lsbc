package org.schmivits.airball.widget;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Rectangle extends Widget {

    private final Color mColor;
    private final Rectangle2D.Double mRect = new Rectangle2D.Double();

    public Rectangle(Color color) {
        mColor = color;
        onResize();
    }

    @Override
    protected void onResize() {
        mRect.setRect(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void drawContents(Graphics2D g) {
        g.setColor(mColor);
        g.fill(mRect);
    }
}
