package org.schmivits.airball.airdata;

public interface FlightData extends UpdateSource {

    /**
     * The aircraft configuration.
     */
    Aircraft getAircraft();

    /**
     * The current airdata
     */
    Airdata getAirdata();

    /**
     * The current connection status; null if all is ok
     */
    String getConnectionStatus();

    /**
     * Destroy and free up resources and background threads
     */
    void destroy();
}
