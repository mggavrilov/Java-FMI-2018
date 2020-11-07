package bg.uni.sofia.fmi.mjt.cache;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MemCache<K, V> implements Cache<K, V> {
	private long capacity;
	private Map<K, V> cache;
	private Map<K, LocalDateTime> expirationTimes;

	private int successfulGets;
	private int totalGets;

	private static final int DEFAULT_CAPACITY = 1000;

	public MemCache() {
		init(DEFAULT_CAPACITY);
	}

	public MemCache(long capacity) {
		init(capacity);
	}

	private void init(long capacity) {
		this.capacity = capacity;
		cache = new HashMap<K, V>();
		expirationTimes = new HashMap<K, LocalDateTime>();
	}

	@Override
	public V get(K key) {
		totalGets++;

		if (!cache.containsKey(key)) {
			return null;
		}

		if (expirationTimes.get(key) != null && expirationTimes.get(key).isBefore(LocalDateTime.now())) {
			removeKey(key);
			return null;
		}

		successfulGets++;
		return cache.get(key);
	}

	@Override
	public void set(K key, V value, LocalDateTime expiresAt) throws CapacityExceededException {
		if (key == null || value == null) {
			return;
		}

		if (cache.containsKey(key)) {
			putKey(key, value, expiresAt);
			return;
		}

		if (capacity == cache.size()) {
			for (K k : expirationTimes.keySet()) {
				if (expirationTimes.get(k) == null) {
					continue;
				}

				if (expirationTimes.get(k).isBefore(LocalDateTime.now())) {
					removeKey(k);

					putKey(key, value, expiresAt);

					return;
				}
			}

			throw new CapacityExceededException();
		} else {
			putKey(key, value, expiresAt);
		}
	}

	@Override
	public LocalDateTime getExpiration(K key) {
		return expirationTimes.get(key);
	}

	@Override
	public boolean remove(K key) {
		if (cache.containsKey(key)) {
			removeKey(key);
			return true;
		}

		return false;
	}

	@Override
	public long size() {
		return cache.size();
	}

	@Override
	public void clear() {
		cache.clear();
		expirationTimes.clear();

		successfulGets = 0;
		totalGets = 0;
	}

	@Override
	public double getHitRate() {
		if (totalGets == 0) {
			return 0;
		}

		return successfulGets / (double) totalGets;
	}

	private void putKey(K key, V value, LocalDateTime expiresAt) {
		cache.put(key, value);
		expirationTimes.put(key, expiresAt);
	}

	private void removeKey(K key) {
		cache.remove(key);
		expirationTimes.remove(key);
	}
}
