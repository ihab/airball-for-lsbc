package org.schmivits.airball.display;

import org.schmivits.airball.airdata.Aircraft;
import org.schmivits.airball.airdata.FlightData;
import org.schmivits.airball.display.airball.Airball;
import org.schmivits.airball.display.altitude.AltitudeTape;
import org.schmivits.airball.display.climbrate.ClimbRateTape;
import org.schmivits.airball.display.speed.SpeedTape;
import org.schmivits.airball.widget.Container;
import org.schmivits.airball.widget.Rectangle;
import org.schmivits.airball.widget.Widget;

import java.awt.Color;

public class AirballPFD extends Container {

    /**
     * TODO: Do not attempt to resize!
     */
    public AirballPFD(final FlightData model, final float width, final float height) {
        sizeTo(width, height);

        Widget r = new Rectangle(Color.BLACK);
        r.moveTo(0f, 0f);
        r.sizeTo(width, height);
        getChildren().add(r);

        SpeedTape.Model speedTapeModel = new SpeedTape.Model() {
            @Override
            public float getSpeed() {
                return model.getAirdata().getAirspeed();
            }

            @Override
            public Aircraft getAircraft() {
                return model.getAircraft();
            }
        };

        Airball.Model airballModel = new Airball.Model() {
            @Override
            public float getAlpha() {
                return model.getAirdata().getAlpha();
            }

            @Override
            public float getBeta() {
                return model.getAirdata().getBeta();
            }

            @Override
            public float getAirspeed() {
                return model.getAirdata().getAirspeed();
            }

            @Override
            public Aircraft getAircraft() {
                return model.getAircraft();
            }
        };

        AltitudeTape.Model altitudeTapeModel = new AltitudeTape.Model() {
            @Override
            public float getAltitude() {
                return model.getAirdata().getAltitude();
            }
        };

        ClimbRateTape.Model climbRateTapeModel = new ClimbRateTape.Model() {
            @Override
            public float getClimbRate() {
                return model.getAirdata().getClimbRate();
            }
        };

        float instrumentGap = (float) Math.floor(width / 75);
        float altitudeTapeWidth = 0.175f * width;
        float speedTapeWidth = 0.125f * width;
        float climbRateTapeWidth = 0.05f * width;
        float airballWidth =
                getWidth() - speedTapeWidth - altitudeTapeWidth - climbRateTapeWidth
                        - 3 * instrumentGap;

        DisplayConfiguration config = new DisplayConfiguration(width, height);

        Widget airball;
        float x = 0f;
        mChildren.add(new SpeedTape(
                speedTapeModel,
                config,
                x,
                0f,
                speedTapeWidth,
                getHeight()));
        x += speedTapeWidth + instrumentGap;
        mChildren.add(airball = new Airball(airballModel, config));
        airball.moveTo(x, 0f);
        airball.sizeTo(airballWidth, getHeight());
        x += airballWidth + instrumentGap;

        mChildren.add(new AltitudeTape(
                altitudeTapeModel,
                config,
                x,
                0f,
                altitudeTapeWidth,
                getHeight()));
        x += altitudeTapeWidth + instrumentGap;

        mChildren.add(new ClimbRateTape(
                climbRateTapeModel,
                config,
                x,
                0f,
                climbRateTapeWidth,
                getHeight()));
    }
}
