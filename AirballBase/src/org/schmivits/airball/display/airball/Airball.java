package org.schmivits.airball.display.airball;

import org.schmivits.airball.airdata.Aircraft;
import org.schmivits.airball.display.DisplayConfiguration;
import org.schmivits.airball.widget.Container;
import org.schmivits.airball.widget.Widget;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Airball extends Container {

    public interface Model {
        float getAlpha();

        float getBeta();

        float getAirspeed();

        Aircraft getAircraft();
    }

    private final DisplayConfiguration mConfig;
    private final Model mModel;
    private final Widget mAirball;
    private final Widget mSpeedTooSmallAirball;
    private final Widget mTotemPole;
    private float mSpeedTooSmallAirballWidth;

    public Airball(Model model, DisplayConfiguration config) {
        mConfig = config;
        mModel = model;
        mChildren.add(mAirball = new AirballCore(mConfig));
        mChildren.add(mSpeedTooSmallAirball = new SpeedTooSmallAirball(config));
        mChildren.add(mTotemPole = new TotemPole(config, model.getAircraft()));
    }

    @Override
    public void onResize() {
        mSpeedTooSmallAirballWidth = getWidth() / 10f;
        mSpeedTooSmallAirball.sizeTo(mSpeedTooSmallAirballWidth, mSpeedTooSmallAirballWidth);
        mTotemPole.sizeTo(getWidth(), getHeight());
    }

    @Override
    public void drawContents(Graphics2D g) {
        Widget airballDisplayWidget;

        float diameter =
                (mModel.getAirspeed() / mModel.getAircraft().getVne()) *
                        getWidth();
        if (diameter < mSpeedTooSmallAirballWidth) {
            mAirball.setVisible(false);
            mSpeedTooSmallAirball.setVisible(true);
            airballDisplayWidget = mSpeedTooSmallAirball;
        } else {
            mAirball.sizeTo(diameter, diameter);
            mAirball.setVisible(true);
            mSpeedTooSmallAirball.setVisible(false);
            airballDisplayWidget = mAirball;
        }

        float y = Scaling.computeY(
                mModel.getAlpha(),
                mModel.getAircraft(),
                getHeight());
        airballDisplayWidget.moveTo(
                airballDisplayWidget.getX(),
                y - (airballDisplayWidget.getHeight() / 2f));

        float x = Scaling.computeX(
                mModel.getBeta(),
                mModel.getAircraft(),
                getWidth());
        airballDisplayWidget.moveTo(
                x - (airballDisplayWidget.getWidth() / 2f),
                airballDisplayWidget.getY());

        super.drawContents(g);
    }
}
