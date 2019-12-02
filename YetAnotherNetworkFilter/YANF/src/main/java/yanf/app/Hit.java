package yanf.app;

import java.util.Date;

public class Hit {
    private String URL;
    private String domain;
    private HitType hitType;
    private long timeStamp;

    public Hit() {

    }

    public Hit(String url, String domain, HitType hítType) {
        URL = url;
        this.domain = domain;
        this.hitType = hítType;
        this.timeStamp = new Date().getTime();
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public HitType getHitType() {
        return hitType;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public enum HitType {
        COOKIE,
        ADVERTISEMENT
    }
}
