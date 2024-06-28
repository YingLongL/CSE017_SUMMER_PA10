import java.util.ArrayList;

public class HashMapLP<K, V> extends HashMap<K, V> {
    private MapEntry<K, V>[] hashTable;
    private int size;
    public static int collisions;
    public static int getIterations;
    public static int removeIterations;

    public HashMapLP() {
        this(100, 0.9);
    }

    public HashMapLP(int capacity) {
        this(capacity, 0.9);
    }

    public HashMapLP(int capacity, double lf) {
        super(lf);
        hashTable = new MapEntry[trim2PowerOf2(capacity)];
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
        hashTable = new MapEntry[hashTable.length];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(K key) {
        getIterations = 0;
        int index = hash(key.hashCode());
        while (hashTable[index] != null) {
            getIterations++;
            if (hashTable[index].getKey().equals(key)) {
                return hashTable[index].getValue();
            }
            index = (index + 1) % hashTable.length;
        }
        return null;
    }

    @Override
    public void remove(K key) {
        removeIterations = 0;
        int index = hash(key.hashCode());
        ArrayList<MapEntry<K, V>> cluster = new ArrayList<>();
        while (hashTable[index] != null) {
            removeIterations++;
            if (hashTable[index].getKey().equals(key)) {
                hashTable[index] = null;
                size--;
            } else {
                cluster.add(hashTable[index]);
                hashTable[index] = null;
            }
            index = (index + 1) % hashTable.length;
        }
        for (MapEntry<K, V> entry : cluster) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key.hashCode());
        while (hashTable[index] != null) {
            collisions++;
            if (hashTable[index].getKey().equals(key)) {
                V oldValue = hashTable[index].getValue();
                hashTable[index].setValue(value);
                return oldValue;
            }
            index = (index + 1) % hashTable.length;
        }
        hashTable[index] = new MapEntry<>(key, value);
        size++;
        if (size >= hashTable.length * loadFactor) {
            rehash();
        }
        return value;
    }

    @Override
    protected void rehash() {
        ArrayList<MapEntry<K, V>> list = toList();
        hashTable = new MapEntry[hashTable.length << 1];
        size = 0;
        for (MapEntry<K, V> entry : list) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public ArrayList<MapEntry<K, V>> toList() {
        ArrayList<MapEntry<K, V>> list = new ArrayList<>();
        for (MapEntry<K, V> entry : hashTable) {
            if (entry != null) {
                list.add(entry);
            }
        }
        return list;
    }

    @Override
    public ArrayList<V> values() {
        ArrayList<V> values = new ArrayList<>();
        for (MapEntry<K, V> entry : hashTable) {
            if (entry != null) {
                values.add(entry.getValue());
            }
        }
        return values;
    }

    @Override
    public void printCharacteristics() {
        int numClusters = 0;
        int maxClusterSize = 0;
        int minClusterSize = Integer.MAX_VALUE;
        int currentClusterSize = 0;
        for (MapEntry<K, V> entry : hashTable) {
            if (entry != null) {
                currentClusterSize++;
            } else {
                if (currentClusterSize > 0) {
                    numClusters++;
                    if (currentClusterSize > maxClusterSize) {
                        maxClusterSize = currentClusterSize;
                    }
                    if (currentClusterSize < minClusterSize) {
                        minClusterSize = currentClusterSize;
                    }
                    currentClusterSize = 0;
                }
            }
        }
        if (minClusterSize == Integer.MAX_VALUE) {
            minClusterSize = 0;
        }
        System.out.println("Hashtable capacity: " + hashTable.length);
        System.out.println("Hashtable size: " + size);
        System.out.println("Number of clusters: " + numClusters);
        System.out.println("Number of collisions: " + collisions);
        System.out.println("Size of the largest cluster: " + maxClusterSize);
        System.out.println("Size of the smallest cluster: " + minClusterSize);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[");
        for (MapEntry<K, V> entry : hashTable) {
            if (entry != null) {
                out.append(entry.toString());
            }
        }
        out.append("]");
        return out.toString();
    }
}
