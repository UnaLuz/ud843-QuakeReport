package com.example.android.quakereport;

/**
 * {@link Earthquake} contains information about a single earthquake
 */
public class Earthquake {
    /**
     * Magnitude of the earthquake
     */
    private float mMagnitude;
    /**
     * Location of the earthquake
     */
    private String mLocation;
    /**
     * Time in milliseconds of the earthquake
     */
    private long mTimeInMilliseconds;
    /**
     * Url of the earthquake info
     */
    private String mUrl;

    /**
     * @param mag          is the magnitude or size of the earthquake
     * @param location     is the city where it was registered
     * @param timeInMillis is the date the earthquake happened
     * @param url          is the url that takes to the earthquake info page
     */
    public Earthquake(float mag, String location, Long timeInMillis, String url) {
        setMag(mag);
        setLocation(location);
        setTimeInMillis(timeInMillis);
        setUrl(url);
    }

    /**
     * Returns the time of the earthquake
     */
    public long getTimeInMillis() {
        return mTimeInMilliseconds;
    }

    /**
     * Sets the {@param Date} of the earthquake
     */
    public void setTimeInMillis(Long timeInMillis) {
        this.mTimeInMilliseconds = timeInMillis;
    }

    /**
     * Returns the magnitude of the earthquake
     */
    public float getMag() {
        return mMagnitude;
    }

    /**
     * Sets the {@param Magnitude} of the earthquake
     */
    public void setMag(float Magnitude) {
        this.mMagnitude = Magnitude;
    }

    /**
     * Returns the location of the earthquake
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * Sets the {@param Location} of the earthquake
     */
    public void setLocation(String Location) {
        this.mLocation = Location;
    }

    /**
     * Returns the url of the earthquake
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Sets the {@param url} of the earthquake info
     */
    public void setUrl(String url) {
        this.mUrl = url;
    }
}
