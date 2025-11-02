package util;

public interface Metrics {
    void inc(String key);              // +1
    void inc(String key, long delta);  // +delta
    long get(String key);              // read

    void start(String key);            // start timer
    long stop(String key);             // stop timer, return nanos
}
