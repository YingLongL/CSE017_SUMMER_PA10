import java.util.ArrayList;
import java.util.LinkedList;

public class HashMapSC<K, V> extends HashMap<K, V> {
    private LinkedList<MapEntry<K, V>>[] hashTable;
    private int size;
    public static int collisions;
    public static int getIterations;
    public static int removeIterations;

    public HashMapSC() {
        this(100, 0.9);
    }

    public HashMapSC(int capacity) {
        this(capacity, 0.9);
    }

    public HashMapSC(int capacity, double lf) {
        super(lf);
        hashTable = new LinkedList[trim2PowerOf2(capacity)];
        size = 0;
        collisions = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public void clear() {
        size = 0;
        collisions = 0;
        hashTable = new LinkedList[hashTable.length];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(K key) {
        getIterations = 0;
        int index = hash(key.hashCode());
        if (hashTable[index] != null) {
            for (MapEntry<K, V> entry : hashTable[index]) {
                getIterations++;
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void remove(K key) {
        removeIterations = 0;
        int index = hash(key.hashCode());
        if (hashTable[index] != null) {
            for (MapEntry<K, V> entry : hashTable[index]) {
                removeIterations++;
                if (entry.getKey().equals(key)) {
                    hashTable[index].remove(entry);
                    size--;
                    return;
                }
            }
        }
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key.hashCode());
        if (hashTable[index] == null) {
            hashTable[index] = new LinkedList<>();
        } else {
            collisions++;
        }
        for (MapEntry<K, V> entry : hashTable[index]) {
            if (entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }
        hashTable[index].add(new MapEntry<>(key, value));
        size++;
        if (size >= hashTable.length * loadFactor) {
            rehash();
        }
        return value;
    }

    @Override
    protected void rehash() {
        ArrayList<MapEntry<K, V>> list = toList();
        hashTable = new LinkedList[hashTable.length << 1];
        size = 0;
        for (MapEntry<K, V> entry : list) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public ArrayList<MapEntry<K, V>> toList() {
        ArrayList<MapEntry<K, V>> list = new ArrayList<>();
        for (LinkedList<MapEntry<K, V>> bucket : hashTable) {
            if (bucket != null) {
                list.addAll(bucket);
            }
        }
        return list;
    }

    @Override
    public ArrayList<V> values() {
        ArrayList<V> values = new ArrayList<>();
        for (LinkedList<MapEntry<K, V>> bucket : hashTable) {
            if (bucket != null) {
                for (MapEntry<K, V> entry : bucket) {
                    values.add(entry.getValue());
                }
            }
        }
        return values;
    }

    @Override
    public void printCharacteristics() {
        int numBuckets = 0;
        int maxBucketSize = 0;
        int minBucketSize = Integer.MAX_VALUE;
        for (LinkedList<MapEntry<K, V>> bucket : hashTable) {
            if (bucket != null) {
                numBuckets++;
                int bucketSize = bucket.size();
                if (bucketSize > maxBucketSize) {
                    maxBucketSize = bucketSize;
                }
                if (bucketSize < minBucketSize) {
                    minBucketSize = bucketSize;
                }
            }
        }
        if (minBucketSize == Integer.MAX_VALUE) {
            minBucketSize = 0;
        }
        System.out.println("Hashtable capacity: " + hashTable.length);
        System.out.println("Hashtable size: " + size);
        System.out.println("Number of buckets: " + numBuckets);
        System.out.println("Number of collisions: " + collisions);
        System.out.println("Size of the largest bucket: " + maxBucketSize);
        System.out.println("Size of the smallest bucket: " + minBucketSize);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[");
        for (LinkedList<MapEntry<K, V>> bucket : hashTable) {
            if (bucket != null) {
                for (MapEntry<K, V> entry : bucket) {
                    out.append(entry.toString());
                }
                out.append("\n");
            }
        }
        out.append("]");
        return out.toString();
    }
}
