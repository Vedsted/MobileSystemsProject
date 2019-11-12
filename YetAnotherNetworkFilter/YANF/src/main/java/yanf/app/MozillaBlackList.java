package yanf.app;

import java.util.ArrayList;
import java.util.HashSet;

public class MozillaBlackList {

    private ArrayList<Company> advertisements;
    private ArrayList<Company> content;
    //private ArrayList<Company> analytics;
    private ArrayList<Company> fingerprinting;
    private ArrayList<Company> social;
    private ArrayList<Company> disconnect;
    private String TAG = "BlackList";

    public MozillaBlackList(ArrayList<Company> advertisements, ArrayList<Company> content, ArrayList<Company> fingerprinting, ArrayList<Company> social, ArrayList<Company> disconnect) {
        this.advertisements = advertisements;
        this.content = content;
        this.fingerprinting = fingerprinting;
        this.social = social;
        this.disconnect = disconnect;
    }

    public ArrayList<Company> getAdvertisements() {
        return advertisements;
    }

    public ArrayList<Company> getContent() {
        return content;
    }

    public ArrayList<Company> getFingerprinting() {
        return fingerprinting;
    }

    public ArrayList<Company> getSocial() {
        return social;
    }

    public ArrayList<Company> getDisconnect() {
        return disconnect;
    }

    public HashSet<String> getDomains(String blacklist_category) {
        switch (blacklist_category) {
            case "None":
                return new HashSet<>();
            case "Advertising":
                return this.extractDomains(this.advertisements);
            case "Content":
                return this.extractDomains(this.content);
            case "Fingerprinting":
                return this.extractDomains(this.fingerprinting);
            case "Social":
                return this.extractDomains(this.social);
            case "Disconnect":
                return this.extractDomains(this.disconnect);
            case "Paranoid":
                return this.getAllDomains();
            default:
                return new HashSet<>();
        }
    }

    public HashSet<String> getAllDomains() {

        HashSet<String> allDomains = new HashSet<>();
        allDomains.addAll(extractDomains(this.advertisements));
        //allDomains.addAll(extractDomains(this.analytics));
        allDomains.addAll(extractDomains(this.content));
        allDomains.addAll(extractDomains(this.disconnect));
        allDomains.addAll(extractDomains(this.fingerprinting));
        allDomains.addAll(extractDomains(this.social));

        return allDomains;
    }

    private HashSet<String> extractDomains(ArrayList<Company> companies) {
        return companies.stream().map(company -> company.getDomains()).reduce((h1, h2) -> {
            h1.addAll(h2);
            return h1;
        }).get();
    }
}
