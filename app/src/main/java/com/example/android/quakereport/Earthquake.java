package com.example.android.quakereport;

/**
 * {@link Earthquake} contains information about a single earthquake
 */
public class Earthquake {
    /** Magnitude of the earthquake */
    private String mMagnitude;
    /** Location of the earthquake */
    private String mLocation;
    /** Date of the earthquake */
    private String mDate;

    /**
     * @param mag is the magnitude or size of the earthquake
     * @param location is the city where it was registered
     * @param date is the date the earthquake happened
     */
    public Earthquake(String mag, String location, String date){
        setMag(mag);
        setLocation(location);
        setDate(date);
    }

    /** Sets the {@param Magnitude} of the earthquake */
    public void setMag(String Magnitude) {
        this.mMagnitude = Magnitude;
    }

    /** Sets the {@param Location} of the earthquake */
    public void setLocation(String Location) {
        this.mLocation = Location;
    }

    /** Sets the {@param Date} of the earthquake */
    public void setDate(String Date) {
        this.mDate = Date;
    }

    /** Returns the magnitude of the earthquake */
    public String getMag() {
        return mMagnitude;
    }

    /** Returns the location of the earthquake */
    public String getLocation() {
        return mLocation;
    }

    /** Returns the date of the earthquake */
    public String getDate() {
        return mDate;
    }
}
