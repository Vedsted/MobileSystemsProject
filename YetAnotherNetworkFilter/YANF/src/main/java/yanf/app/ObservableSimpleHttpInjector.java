package yanf.app;

import android.util.Log;

import com.github.megatronking.netbare.injector.SimpleHttpInjector;

import java.util.ArrayList;
import java.util.List;

public class ObservableSimpleHttpInjector extends SimpleHttpInjector {
    protected List<HitListener> listeners;
    public static final String TAG = ObservableSimpleHttpInjector.class.getSimpleName();

    public ObservableSimpleHttpInjector() {
        this.listeners = new ArrayList<>();
    }

    public void addHitListener(HitListener listener) {
        this.listeners.add(listener);
    }

    protected void notifyHitListeners(Hit hit) {
        if (this.listeners != null) {
            for (HitListener listener : this.listeners) {
                listener.onHit(hit, this);
            }
        }
    }
}
