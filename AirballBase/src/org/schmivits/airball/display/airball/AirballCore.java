package org.schmivits.airball.display.airball;

import org.schmivits.airball.display.DisplayConfiguration;
import org.schmivits.airball.widget.Widget;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class AirballCore extends Widget {

    private static final Color AIRBALL_COLOR = Color.BLUE;

    private final DisplayConfiguration mConfig;

    public AirballCore(DisplayConfiguration config) {
        mConfig = config;
    }

    @Override
    protected void drawContents(Graphics2D g) {
        g.setColor(AIRBALL_COLOR);
        g.fill(new Ellipse2D.Float(
                0,
                0,
                getWidth(),
                getHeight()));
        g.setColor(mConfig.mLineColor);
        g.fill(new Rectangle2D.Float(
                0f,
                getHeight() / 2 - mConfig.mThinLineThickness / 2,
                getWidth(),
                mConfig.mThinLineThickness));
        g.fill(new Rectangle2D.Float(
                getWidth() / 2 - mConfig.mThinLineThickness / 2,
                0f,
                mConfig.mThinLineThickness,
                getHeight()));
    }
}
