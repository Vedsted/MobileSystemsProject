package yanf.app;

import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsModel extends ViewModel implements HitListener {

    // Key-value pair of Domain and Hit
    private Map<String, List<Hit>> domainStats;

    public static final String TAG = StatisticsModel.class.getSimpleName();

    public StatisticsModel() {
        Log.d(TAG, "StatisticsModel: ctor");
        domainStats = new HashMap<>();
    }

    @Override
    public void onHit(Hit hit, Object sender) {

        String domain = hit.getDomain();

        if (!domainStats.containsKey(domain)) {
            domainStats.put(domain, new ArrayList<>());
        } else {
            domainStats.get(domain).add(hit);
        }
    }

    public Map<String, Integer> getNumberOfHits() {
        Map<String, Integer> numberOfHitstPerDomain;

        numberOfHitstPerDomain = domainStats.entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toMap(pair -> pair.first, pair -> pair.second));

        return numberOfHitstPerDomain;
    }
}
