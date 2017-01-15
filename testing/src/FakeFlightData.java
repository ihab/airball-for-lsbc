import org.schmivits.airball.airdata.Aircraft;
import org.schmivits.airball.airdata.Airdata;
import org.schmivits.airball.airdata.FlightData;
import org.schmivits.airball.airdata.UpdateSourceHelper;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FakeFlightData implements FlightData {

    UpdateSourceHelper mUpdateSourceHelper = new UpdateSourceHelper();

    private Aircraft mAircraft = new Aircraft() {
        @Override
        public float getVs0() {
            return 37.0f;
        }

        @Override
        public float getVs1() {
            return 41.0f;
        }

        @Override
        public float getVfe() {
            return 70.0f;
        }

        @Override
        public float getVno() {
            return 90.0f;
        }

        @Override
        public float getVne() {
            return 131.0f;
        }

        @Override
        public float getAs() {
            return 15.0f;
        }

        @Override
        public float getAmin() {
            return 0.0f;
        }

        @Override
        public float getAx() {
            return 8.0f;
        }

        @Override
        public float getAy() {
            return 5.0f;
        }

        @Override
        public float getAref() {
            return 9.0f;
        }

        @Override
        public float getBfs() {
            return 15.0f;
        }
    };

    private VaryingValue mAirspeed = new VaryingValue(0.0f, mAircraft.getVne());
    private VaryingValue mAlpha = new VaryingValue(mAircraft.getAmin(), mAircraft.getAs());
    private VaryingValue mBeta = new VaryingValue(-1.0f * mAircraft.getBfs(), mAircraft.getBfs());
    private VaryingValue mAltitude = new VaryingValue(0.0f, 5000.0f);
    private VaryingValue mClimbRate = new VaryingValue(-1000.0f, 1000.0f);

    private Airdata mAirdata = new Airdata() {
        @Override
        public float getAirspeed() {
            return mAirspeed.getValue();
        }

        @Override
        public float getAlpha() {
            return mAlpha.getValue();
        }

        @Override
        public float getBeta() {
            return mBeta.getValue();
        }

        @Override
        public float getAltitude() {
            return mAltitude.getValue();
        }

        @Override
        public float getClimbRate() {
            return mClimbRate.getValue();
        }
    };

    private final Timer mTimer;

    public FakeFlightData() {
        mTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(System.currentTimeMillis());
            }
        });
        mTimer.start();
    }

    private void update(long t) {
        mAirspeed.update(t);
        mAlpha.update(t);
        mBeta.update(t);
        mAltitude.update(t);
        mClimbRate.update(t);
        mUpdateSourceHelper.fire();
    }

    @Override
    public void addUpdateListener(Runnable r) {
        mUpdateSourceHelper.addUpdateListener(r);
    }

    @Override
    public void removeUpdateListener(Runnable r) {
        mUpdateSourceHelper.removeUpdateListener(r);
    }

    @Override
    public Aircraft getAircraft() {
        return mAircraft;
    }

    @Override
    public Airdata getAirdata() {
        return mAirdata;
    }

    @Override
    public String getConnectionStatus() {
        return null;
    }

    @Override
    public void destroy() {
        mTimer.stop();
    }
}
