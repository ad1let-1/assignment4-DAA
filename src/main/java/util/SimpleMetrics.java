package util;

import java.util.HashMap;
import java.util.Map;

public class SimpleMetrics implements Metrics {

    private final Map<String, Long> counters = new HashMap<>();
    private final Map<String, Long> timers = new HashMap<>();

    @Override
    public void inc(String key) {
        inc(key, 1);
    }

    @Override
    public void inc(String key, long delta) {
        counters.put(key, counters.getOrDefault(key, 0L) + delta);
    }

    @Override
    public long get(String key) {
        return counters.getOrDefault(key, 0L);
    }

    @Override
    public void start(String key) {
        timers.put(key, System.nanoTime());
    }

    @Override
    public long stop(String key) {
        long end = System.nanoTime();
        long start = timers.getOrDefault(key, end);
        long elapsed = end - start;
        inc(key + "_nanos", elapsed);
        timers.remove(key);
        return elapsed;
    }
}
