package astromonitor.limitsky.com.astromonitor;

/**
 * Created by Nikit on 11-04-2015.
 * Java Bean/Model for all the active sources.
 */
public class AstroSources {



    private String sourceName;//name of active source
    private String currentFlux;//current flux of that source


    private String satelliteName;//satellite name that calculates data from that source
    private String agencyName;//agency that provided that data
    private String activeStatus;//active status of the source ie if it is inactive,active,highly active.

    public float getActiveLevel() {
        return activeLevel;
    }

    public void setActiveLevel(float activeLevel) {
        this.activeLevel = activeLevel;
    }

    private float activeLevel;


    private String interestLevel;//store like status of the app
    private String rightEssential;
    private String Peak;
    private String alternateName;
    private String tenday;
    private String days;
    private String lastDays;
    private String yesterday;
    private String sourceTypeFullName;
    private String declination;
    private String sourceType;

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    private String mean;

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getDeclination() {
        return declination;
    }

    public void setDeclination(String declination) {
        this.declination = declination;
    }

    public String getPeak() {
        return Peak;
    }
    public void setPeak(String peak) {
        Peak = peak;
    }

    public String getInterestLevel() {
        return interestLevel;
    }

    public void setInterestLevel(String interestLevel) {
        this.interestLevel = interestLevel;
    }

    public String getRightEssential() {
        return rightEssential;
    }

    public void setRightEssential(String rightEssential) {
        this.rightEssential = rightEssential;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public String getTenday() {
        return tenday;
    }

    public void setTenday(String tenday) {
        this.tenday = tenday;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getLastDays() {
        return lastDays;
    }

    public void setLastDays(String lastDays) {
        this.lastDays = lastDays;
    }

    public String getYesterday() {
        return yesterday;
    }

    public void setYesterday(String yesterday) {
        this.yesterday = yesterday;
    }

    public String getSourceTypeFullName() {
        return sourceTypeFullName;
    }

    public void setSourceTypeFullName(String sourceTypeFullName) {
        this.sourceTypeFullName = sourceTypeFullName;
    }







   ;



    // getter and setter method for the model.
    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getCurrentFlux() {
        return currentFlux;
    }

    public void setCurrentFlux(String currentFlux) {
        this.currentFlux = currentFlux;
    }

    public String getSatelliteName() {
        return satelliteName;
    }

    public void setSatelliteName(String satelliteName) {
        this.satelliteName = satelliteName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }


}
