package yanf.app;

import java.util.HashSet;

public class Company {
    private String name;
    private String website;
    private HashSet<String> domains;

    public Company(String name, String website, HashSet<String> domains) {
        this.name = name;
        this.website = website;
        this.domains = domains;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public HashSet<String> getDomains() {
        return domains;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", domains=" + domains +
                '}';
    }
}
