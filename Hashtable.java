import java.util.NoSuchElementException;

public class Hashtable {
    public class HashNode<k,v>{
        private String key;
        private String value;
        private HashNode next;

        public HashNode(String key, String value){
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private HashNode[] buckets;
    private int size;

    public Hashtable() {
        this.buckets = new HashNode[314527];
        this.size = 0;

    }

    /**
     * returns true if a k/v object pair
     * @param key
     * @return
     */
    public boolean containsKey(String key){
        return get(key) != null;
    }

    /**
     * returns value associated with key
     * returns null if no k/v pair
     * @param key
     * @return
     */
    public String get(String key){
        int bucket_index = find_index(key);
        HashNode temp = buckets[bucket_index];

        if(temp == null) {
            return null;
        }

        while (temp != null){
            if (temp.key.equals(key)){
                return temp.value;
            }
            temp = temp.next;
        }
        return null;
    }

    /**
     * returns index
     * @param key
     * @return
     */
    public int find_index(String key){
        int index = Math.abs(key.hashCode()%buckets.length);
        return index;
    }

    /**
     * adds the k/v pair into the hash table
     * @param key
     * @param value
     */
    public void put(String key, String value){
        int bucket_index = find_index(key);
        HashNode head = buckets[bucket_index];
        HashNode node = new HashNode(key, value);

        if(buckets[bucket_index] == null) {
            buckets[bucket_index] = node;
            size++;
            return;
        } else {

            HashNode start = buckets[bucket_index];
            if (start.key.equals(key)) {

                buckets[bucket_index].value = value;
            }
            while(start.next != null && start.next.key != key) {
                start = start.next;
            }
            if (start.next != null) {

                start.next.value = value;
            }

            HashNode hn = new HashNode(key, value);
            start.next = hn;
            size++;
        }

    }

    /**
     * removes the k/v pair from the hash table
     * @param key
     * @return
     */
    public String remove(String key) throws NoSuchElementException{
        int bucket_index = find_index(key);
        HashNode head = buckets[bucket_index];

        if(head == null) {
            throw new NoSuchElementException();
        }

        if(head.key.equals(key)) {
            buckets[bucket_index] = head.next;
            size--;
            return head.value;
        }

        head = head.next;

        while(head.next != null && head.next.key != key) {
                head =  head.next;
        }
        if (head.next != null) {

            head.next = head.next.next;
            return head.next.value;
        }
        return null;
    }
}