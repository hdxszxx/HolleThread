package entity;

import java.io.Serializable;

/**
 * @author MacFan
 * user:created by MacFan
 * DATE: 2018/7/13
 */
public class XiCiDaiLi implements Serializable {
    private String country;
    private String Ip;
    private String prpo;
    private String address;
    private String isAnon;
    private String type;
    private String speed;
    private String conTime;
    private String existTime;
    private String validateTime;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getPrpo() {
        return prpo;
    }

    public void setPrpo(String prpo) {
        this.prpo = prpo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsAnon() {
        return isAnon;
    }
    public void setIsAnon(String isAnon) {
        this.isAnon = isAnon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getConTime() {
        return conTime;
    }

    public void setConTime(String conTime) {
        this.conTime = conTime;
    }

    public String getExistTime() {
        return existTime;
    }

    public void setExistTime(String existTime) {
        this.existTime = existTime;
    }

    public String getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(String validateTime) {
        this.validateTime = validateTime;
    }

    public XiCiDaiLi(String country, String ip, String prpo, String address, String isAnon, String type, String speed, String conTime, String existTime, String validateTime) {
        this.country = country;
        Ip = ip;
        this.prpo = prpo;
        this.address = address;
        this.isAnon = isAnon;
        this.type = type;
        this.speed = speed;
        this.conTime = conTime;
        this.existTime = existTime;
        this.validateTime = validateTime;
    }

    public XiCiDaiLi() {
    }

    @Override
    public String toString() {
        return "XiCiDaiLi{" +
                "country='" + country + '\'' +
                ", Ip='" + Ip + '\'' +
                ", prpo='" + prpo + '\'' +
                ", address='" + address + '\'' +
                ", isAnon='" + isAnon + '\'' +
                ", type='" + type + '\'' +
                ", speed='" + speed + '\'' +
                ", conTime='" + conTime + '\'' +
                ", existTime='" + existTime + '\'' +
                ", validateTime='" + validateTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()){ return false;}

        XiCiDaiLi xiCiDaiLi = (XiCiDaiLi) o;

        if (Ip != null ? !Ip.equals(xiCiDaiLi.Ip) : xiCiDaiLi.Ip != null){ return false;}
        if (prpo != null ? !prpo.equals(xiCiDaiLi.prpo) : xiCiDaiLi.prpo != null){ return false;}
        return validateTime != null ? validateTime.equals(xiCiDaiLi.validateTime) : xiCiDaiLi.validateTime == null;
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (Ip != null ? Ip.hashCode() : 0);
        result = 31 * result + (prpo != null ? prpo.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (isAnon != null ? isAnon.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (speed != null ? speed.hashCode() : 0);
        result = 31 * result + (conTime != null ? conTime.hashCode() : 0);
        result = 31 * result + (existTime != null ? existTime.hashCode() : 0);
        result = 31 * result + (validateTime != null ? validateTime.hashCode() : 0);
        return result;
    }
}
