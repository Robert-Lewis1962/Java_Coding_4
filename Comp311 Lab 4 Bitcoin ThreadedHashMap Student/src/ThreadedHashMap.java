import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * ThreadedHashMap Class
 * 
 * @author Robert Lewis
 * @version 1.0
 * 
 * @param <K> the Key
 * @param <V> the Value
 */

public class ThreadedHashMap<K, V> implements Map<K, V> {
    private ArrayList<LinkedList<Node>> hashTable;
    private int count;
    private Node head;
    private Node tail;

    /**
     * Constructor of ThreadedHashMap
     */
    public ThreadedHashMap() {
        this.hashTable = new ArrayList<LinkedList<Node>>();
        LinkedList<Node> list = new LinkedList<Node>();
        this.hashTable.add(list);
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    /**
     * puts a key,value pair into the HashMap O(1)
     * 
     * @param key   the indices to the HashMap
     * @param value the value being stored
     * 
     * @return the value
     */
    public V put(K key, V value) {
        // reHash
        if ((this.count + 1) / this.hashTable.size() >= .75) {
            ArrayList<LinkedList<Node>> tempTable =
                    new ArrayList<LinkedList<Node>>();

            int i = 0;
            while (i < this.hashTable.size() * 2) {
                LinkedList<Node> tempBucket =
                        new LinkedList<Node>();
                tempTable.add(tempBucket);
                i++;
            }
            if (this.count > 0) {
                for (int j = 0; j < this.hashTable.size(); j++) {
                    LinkedList<Node> list = this.hashTable.get(j);
                    for (int k = 0; k < list.size(); k++) {
                        Node item = list.get(k);
                        K tempKey = item.getData().getKey();
                        int indice = 
                                Math.abs(tempKey.hashCode() % tempTable.size());
                        LinkedList<Node> temp = tempTable.get(indice);
                        temp.add(item);
                    }
                }
            }
            this.hashTable = tempTable;
        }
        // end rehash
        int indice = Math.abs(key.hashCode() % this.hashTable.size());
        LinkedList<Node> list = this.hashTable.get(indice);

        // tells if the key is in the bucket
        for (int i = 0; i < list.size(); i++) {
            Node item = list.get(i);
            Entry<K, V> pair = item.getData();
            if (key.equals(pair.getKey())) {
                V oldValue = this.get(key);
                pair.setValue(value);
                return oldValue;
            }
        }
        Entry<K, V> item = new AbstractMap.SimpleEntry<K, V>(key, value);
        Node node = new Node(item);
        if (this.count == 0) {
            // first element on the list
            this.head = node;
            this.tail = node;
        } 
        else {
            // last element on the list
            tail.setNext(node);
            node.setPrevious(tail);
            this.tail = node;
        }
        this.count++;
        V oldValue = this.get(key);
        list.add(node);
        return oldValue;
    }

    /**
     * number of entries
     * 
     * @return the number of entries
     */
    public int size() {
        return this.count;
    }

    /**
     * checks to see if the HashMap is empty
     * 
     * @return true/false
     */
    public boolean isEmpty() {
        return this.count == 0;
    }

    /**
     * clears out the HashMap
     */
    public void clear() {
        this.hashTable = new ArrayList<LinkedList<Node>>();
        LinkedList<Node> list = new LinkedList<Node>();
        this.hashTable.add(list);

        this.count = 0;
    }

    /**
     * gets a value from the HashMap by its key O(1)
     * 
     * @param key that holds the value
     * 
     * @return value of key
     */
    public V get(Object key) {
        int indice = Math.abs(key.hashCode() % hashTable.size());
        LinkedList<Node> list = this.hashTable.get(indice);

        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            Entry<K, V> item = node.getData();
            if (key.equals(item.getKey())) {
                return item.getValue();
            }
        }

        return null;
    }

    /**
     * removes a value from the HashMap by its key O(1)
     * 
     * @param key that holds the value
     * 
     * @return value of key
     */
    public V remove(Object key) {
        int indice = Math.abs(key.hashCode() % hashTable.size());
        LinkedList<Node> list = this.hashTable.get(indice);

        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            Entry<K, V> item = node.getData();
            if (key.equals(item.getKey())) {
                if (this.count == 1) {
                    V value = item.getValue();
                    list.remove(node);
                    this.count--;
                    return value;
                }
                Node nexNode;
                Node preNode;
                // element at the start of the list
                if (this.head.equals(node)) {
                    nexNode = node.getNext();                   
                    this.head = nexNode;
                    V value = item.getValue();
                    list.remove(node);
                    this.count--;
                    return value;
                } 
                // element at the end of the list
                else if (this.tail.equals(node)) {
                    preNode = node.getPrevious();
                    this.tail = preNode;
                    V value = item.getValue();
                    list.remove(node);
                    this.count--;
                    return value;
                } 
                // element in the middle of the list
                else {
                    nexNode = node.getNext();
                    preNode = node.getPrevious();
                    nexNode.setPrevious(preNode);
                    preNode.setNext(nexNode);
                    V value = item.getValue();
                    list.remove(node);
                    this.count--;
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * checks to see if the key is in the HashMap O(1)
     * 
     * @param key to see if it is in the HashMap
     * 
     * @return true/false on key
     */
    public boolean containsKey(Object key) {
        return this.get(key) != null;
    }

    /**
     * checks to see if the value is in the HashMap O(1)
     * 
     * @param value to see if it is in the HashMap
     * 
     * @return true/false on value
     */
    public boolean containsValue(Object value) {
        for (int i = 0; i < this.hashTable.size(); i++) {
            LinkedList<Node> list = this.hashTable.get(i);

            for (int j = 0; j < list.size(); j++) {
                Node node = list.get(j);
                Entry<K, V> item = node.getData();
                if (value.equals(item.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * puts another map into this HashMap
     * 
     * @param map is another map
     * 
     */
    public void putAll(Map<? extends K, ? extends V> map) {
        Set<?> setlist = map.entrySet();
        Iterator<?> itr = setlist.iterator();

        while (itr.hasNext()) {
            @SuppressWarnings("unchecked")
            Entry<K, V> item = (Entry<K, V>) itr.next();
            K key = item.getKey();
            V value = item.getValue();
            this.put(key, value);
        }
        
    }

    /**
     * returns a set of entries
     * 
     * @return the set
     */
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new MyEntrySet();
        return entrySet;
    }

    /**
     * gives you the list of entries
     * 
     * @return the list of entries
     */
    public List<Entry<K, V>> entryList() {
        List<Entry<K, V>> entryList = new ArrayList<Entry<K, V>>();
        Node temp = this.head;
        int i = 0;
        
        while (i < this.count) {
            Entry<K, V> item = temp.getData();
            entryList.add(item);
            temp = temp.getNext();
            i++;
        }
        
        return entryList;
    }

    /**
     * gives you the set of keys
     * 
     * @return the set of keys
     */
    public Set<K> keySet() {
        Set<K> keySet = new MyKeySet();
        return keySet;
    }

    /**
     * gives you the list of keys
     * 
     * @return the list of keys
     */
    public List<K> keyList() {
        List<K> keyList = new ArrayList<K>();
        Node temp = this.head;
        int i = 0;
        
        while (i < this.count) {
            Entry<K, V> item = temp.getData();
            keyList.add(item.getKey());
            temp = temp.getNext();
            i++;
        }
        
        return keyList;
    }

    /**
     * gives you the set of values
     * 
     * @return the set of values
     */
    public Set<V> values() {
        Set<V> valueSet = new MyValueSet();
        return valueSet;
    }

    /**
     * gives you the list of values
     * 
     * @return list of values
     */
    public List<V> valueList() {
        List<V> valueList = new LinkedList<V>();
        Node temp = this.head;
        int i = 0;
        
        while (i < this.count) {
            Entry<K, V> item = temp.getData();
            valueList.add(item.getValue());
            temp = temp.getNext();
            i++;
        }
        return valueList;
    }
    
    /**
     * Node Class
     * @author Robert Lewis
     * @version 1.0
     *
     */
    private class Node {
        private Entry<K, V> data;
        private Node next;
        private Node previous;

        
        /**
         * Node constructor
         * @param entry that is being added to the ThreadedHashMap
         */
        public Node(Entry<K, V> entry) {
            this.data = entry;
            this.next = null;
            this.previous = null;
        }
        /**
         * set the next link to the next node
         * @param nextNode the next node
         */
        public void setNext(Node nextNode) {
            this.next = nextNode;
        }
        /**
         * set the previous link to the previous node 
         * @param previousNode previous node
         */
        public void setPrevious(Node previousNode) {
            this.previous = previousNode;
        }
        /**
         * gets the entry
         * @return the entry in the node
         */
        public Entry<K, V> getData() {
            return this.data;
        }
        /**
         * gets the next node
         * @return the next node
         */
        public Node getNext() {
            return this.next;
        }
        /**
         * gets the previous node
         * @return the previous node
         */
        public Node getPrevious() {
            return this.previous;
        }
    }
    
    /**
     * MyEntrySet Inner-Class
     * @author Robert Lewis
     * @version 1.0
     */
    private class MyEntrySet extends AbstractSet<Entry<K, V>>  {

        @Override
        public boolean add(Entry<K, V> e) {
            throw new  UnsupportedOperationException();
        }
        
        @Override
        public Iterator<Entry<K, V>> iterator() {
            Iterator<Entry<K, V>> itr = new MyEntryIterator();
            return itr;
        }

        @Override
        public int size() {
            return count;
        }
        
        @Override
        public boolean contains(Object o) {
            for (int i = 0; i < hashTable.size(); i++) {
                LinkedList<Node> list = hashTable.get(i);

                for (int j = 0; j < list.size(); j++) {
                    Node node = list.get(j);
                    Entry<K, V> item = node.getData();
                    // fixed part o != null
                    if (o.equals(item) && o != null) {
                        return true;
                    }
                }
            }
            
            return false;   
        }
        
    }
    /**
     * MyEntryItartor Inner-Class
     * @author Robert Lewis
     * @version 1.0
     */
    private class MyEntryIterator implements Iterator<Entry<K, V>> {
        private int position = 0;
        private Node temp = head;
        private Node prev = null;
        /**
         * returns true if position is less than count
         */
        public boolean hasNext() {
            return position < count;
        }
        /**
         * gives you the entry 
         */
        public Entry<K, V> next() {
            if (temp == null) {
                throw new NoSuchElementException();
            }
            Entry<K, V> item = temp.getData();
            prev = temp;
            temp = temp.getNext();
            position++;
            return item;
        }
        /**
         * never being used
         */
        public void remove() {
            Node node = temp;
            //Node nexNode;
            //Node preNode;
            if (node == prev) {
                throw new IllegalStateException();
            }
            
            Entry<K, V> item = prev.getData();
            K key = item.getKey();
            int indice = Math.abs(key.hashCode() % hashTable.size());
            LinkedList<Node> list = hashTable.get(indice);
            list.remove(prev);
            count--; 
        }
    }
    
    /**
     * MyKeySet Inner-Class
     * @author Robert Lewis
     * @version 1.0
     */
    private class MyKeySet extends AbstractSet<K>  {

        @Override
        public boolean add(K k) {
            throw new  UnsupportedOperationException();
        }
        
        @Override
        public Iterator<K> iterator() {
            Iterator<K> itr = new MyKeyIterator();
            return itr;
        }

        @Override
        public int size() {
            return count;
        }
        
        @Override
        public boolean contains(Object o) {
            for (int i = 0; i < hashTable.size(); i++) {
                LinkedList<Node> list = hashTable.get(i);

                for (int j = 0; j < list.size(); j++) {
                    Node node = list.get(j);
                    Entry<K, V> item = node.getData();
                    K key = item.getKey();
                    if (o.equals(key)) {
                        return true;
                    }
                }
            }
            
            return false;   
        }
        
    }
    /**
     * MyKeyItartor Inner-Class
     * @author Robert Lewis
     * @version 1.0
     */
    private class MyKeyIterator implements Iterator<K> {
        private int position = 0;
        private Node temp = head;
        private Node prev = null;
        /**
         * returns true if position is less than count
         */
        public boolean hasNext() {
            return position < count;
        }
        /**
         * gives you the entry 
         */
        public K next() {
            if (temp == null) {
                throw new NoSuchElementException();
            }
            Entry<K, V> item = temp.getData();
            K key = item.getKey();
            prev = temp;
            temp = temp.getNext();
            position++;
            return key;
        }
        /**
         * never being used
         */
        public void remove() {
            Node node = temp;
            //Node nexNode;
            //Node preNode;
            if (node == prev) {
                throw new IllegalStateException();
            }
            
            Entry<K, V> item = prev.getData();
            K key = item.getKey();
            int indice = Math.abs(key.hashCode() % hashTable.size());
            LinkedList<Node> list = hashTable.get(indice);
            list.remove(prev);
            count--;
        }
     
    }
    
    /**
     * MyValueSet Inner-Class
     * @author Robert Lewis
     * @version 1.0
     */
    private class MyValueSet extends AbstractSet<V>  {

        @Override
        public boolean add(V v) {
            throw new  UnsupportedOperationException();
        }
        
        @Override
        public Iterator<V> iterator() {
            Iterator<V> itr = new MyValueIterator();
            return itr;
        }

        @Override
        public int size() {
            return count;
        }
        
        @Override
        public boolean contains(Object o) {
            for (int i = 0; i < hashTable.size(); i++) {
                LinkedList<Node> list = hashTable.get(i);

                for (int j = 0; j < list.size(); j++) {
                    Node node = list.get(j);
                    Entry<K, V> item = node.getData();
                    V value = item.getValue();
                    if (o.equals(value)) {
                        return true;
                    }
                }
            }
            
            return false;   
        }
        
    }
    /**
     * MyValueItartor Inner-Class
     * @author Robert Lewis
     * @version 1.0
     */
    private class MyValueIterator implements Iterator<V> {
        private int position = 0;
        private Node temp = head;
        private Node prev = null;
        /**
         * returns true if position is less than count
         */
        public boolean hasNext() {
            return position < count;
        }
        /**
         * gives you the entry 
         */
        public V next() {
            if (temp == null) {
                throw new NoSuchElementException();
            }
            Entry<K, V> item = temp.getData();
            V value = item.getValue();
            prev = temp;
            temp = temp.getNext();
            position++;
            return value;
        }
        /**
         * never being used
         */
        public void remove() {
            Node node = temp;
            //Node nexNode;
            //Node preNode;
            if (node == prev) {
                throw new IllegalStateException();
            }
            
            Entry<K, V> item = prev.getData();
            K key = item.getKey();
            int indice = Math.abs(key.hashCode() % hashTable.size());
            LinkedList<Node> list = hashTable.get(indice);
            list.remove(prev);
            count--; 
        }
     
    }
}
