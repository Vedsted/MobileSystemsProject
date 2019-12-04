package yanf.app;

import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
            domainStats.get(domain).add(hit);
        } else {
            domainStats.get(domain).add(hit);
        }
    }

    public Map<String, Integer> getNumberOfHits() {
        Map<String, Integer> numberOfHitstPerDomain;

        numberOfHitstPerDomain = domainStats.entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toMap(pair -> pair.first, pair -> pair.second));

        return sortByValue(numberOfHitstPerDomain);
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
