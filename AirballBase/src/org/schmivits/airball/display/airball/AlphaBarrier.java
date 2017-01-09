package org.schmivits.airball.display.airball;

import org.schmivits.airball.display.DisplayConfiguration;
import org.schmivits.airball.widget.Widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class AlphaBarrier extends Widget {

    private static final Color COLOR = Color.RED;

    private final DisplayConfiguration mConfig;

    public AlphaBarrier(DisplayConfiguration config) {
        mConfig = config;
    }

    @Override
    protected void onResize() {
        setWidth(6f * getHeight());
    }

    @Override
    protected void drawContents(Graphics2D g) {
        g.setColor(COLOR);
        g.setStroke(new BasicStroke(mConfig.mThinLineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        float center = getWidth() / 2;
        for (int i = 0; i < 3; i++) {
            g.draw(new Line2D.Float(
                    center - getHeight() * i,
                    0,
                    center - getHeight() * (i + 1),
                    getHeight()));
            g.draw(new Line2D.Float(
                    center + getHeight() * i,
                    0,
                    center + getHeight() * (i + 1),
                    getHeight()));
        }
        g.draw(new Line2D.Float(
                getHeight(),
                0,
                getWidth() - getHeight(),
                0));
    }
}
