package com.example.vehiclefuel.models;
import java.util.Objects;

public class MpgData {

    // {"avgMpg":"33.869466922","cityPercent":"45","highwayPercent":"55","maxMpg":"36","minMpg":"30","recordCount":"4","vehicleId":"31819"}

    private String avgMpg;
    private String cityPercent;
    private String highwayPercent;
    private String maxMpg;
    private String minMpg;
    private String recordCount;
    private String vehicleId;



    public MpgData() {
    }

    public MpgData(String avgMpg, String cityPercent, String highwayPercent, String maxMpg, String minMpg, String recordCount, String vehicleId) {
        this.avgMpg = avgMpg;
        this.cityPercent = cityPercent;
        this.highwayPercent = highwayPercent;
        this.maxMpg = maxMpg;
        this.minMpg = minMpg;
        this.recordCount = recordCount;
        this.vehicleId = vehicleId;
    }

    public String getAvgMpg() {
        return this.avgMpg;
    }

    public void setAvgMpg(String avgMpg) {
        this.avgMpg = avgMpg;
    }

    public String getCityPercent() {
        return this.cityPercent;
    }

    public void setCityPercent(String cityPercent) {
        this.cityPercent = cityPercent;
    }

    public String getHighwayPercent() {
        return this.highwayPercent;
    }

    public void setHighwayPercent(String highwayPercent) {
        this.highwayPercent = highwayPercent;
    }

    public String getMaxMpg() {
        return this.maxMpg;
    }

    public void setMaxMpg(String maxMpg) {
        this.maxMpg = maxMpg;
    }

    public String getMinMpg() {
        return this.minMpg;
    }

    public void setMinMpg(String minMpg) {
        this.minMpg = minMpg;
    }

    public String getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }

    public String getVehicleId() {
        return this.vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public MpgData avgMpg(String avgMpg) {
        setAvgMpg(avgMpg);
        return this;
    }

    public MpgData cityPercent(String cityPercent) {
        setCityPercent(cityPercent);
        return this;
    }

    public MpgData highwayPercent(String highwayPercent) {
        setHighwayPercent(highwayPercent);
        return this;
    }

    public MpgData maxMpg(String maxMpg) {
        setMaxMpg(maxMpg);
        return this;
    }

    public MpgData minMpg(String minMpg) {
        setMinMpg(minMpg);
        return this;
    }

    public MpgData recordCount(String recordCount) {
        setRecordCount(recordCount);
        return this;
    }

    public MpgData vehicleId(String vehicleId) {
        setVehicleId(vehicleId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MpgData)) {
            return false;
        }
        MpgData mpgData = (MpgData) o;
        return Objects.equals(avgMpg, mpgData.avgMpg) && Objects.equals(cityPercent, mpgData.cityPercent) && Objects.equals(highwayPercent, mpgData.highwayPercent) && Objects.equals(maxMpg, mpgData.maxMpg) && Objects.equals(minMpg, mpgData.minMpg) && Objects.equals(recordCount, mpgData.recordCount) && Objects.equals(vehicleId, mpgData.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(avgMpg, cityPercent, highwayPercent, maxMpg, minMpg, recordCount, vehicleId);
    }

    @Override
    public String toString() {
        return "{" +
            " avgMpg='" + getAvgMpg() + "'" +
            ", cityPercent='" + getCityPercent() + "'" +
            ", highwayPercent='" + getHighwayPercent() + "'" +
            ", maxMpg='" + getMaxMpg() + "'" +
            ", minMpg='" + getMinMpg() + "'" +
            ", recordCount='" + getRecordCount() + "'" +
            ", vehicleId='" + getVehicleId() + "'" +
            "}";
    }
    

}

