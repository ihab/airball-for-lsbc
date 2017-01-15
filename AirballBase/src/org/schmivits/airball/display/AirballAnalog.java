package org.schmivits.airball.display;

import org.schmivits.airball.airdata.Aircraft;
import org.schmivits.airball.airdata.FlightData;
import org.schmivits.airball.display.airball.Airball;
import org.schmivits.airball.widget.Container;
import org.schmivits.airball.widget.Rectangle;
import org.schmivits.airball.widget.Widget;

import java.awt.Color;

public class AirballAnalog extends Container {

    /**
     * TODO: Do not attempt to resize!
     */
    public AirballAnalog(final FlightData model, final float width, final float height) {
        sizeTo(width, height);

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

        DisplayConfiguration config = new DisplayConfiguration(width, height);

        {
            Widget rect = new Rectangle(Color.BLACK);
            rect.moveTo(0, 0);
            rect.sizeTo(width, height);
            mChildren.add(rect);
        }

        {
            Widget airball = new Airball(airballModel, config);
            airball.moveTo(0f, 0f);
            airball.sizeTo(width, height);
            mChildren.add(airball);
        }
    }
}
