import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

import junit.framework.TestCase;

/**
 * ThreadedHashMapTest class
 * 
 * @author Robert Lewis
 * @version 1.0
 */
public class ThreadedHashMapTest extends TestCase {
    /**
     * empty map
     */
    public void testThreadMapEmpty() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertEquals(null, tMap.get(0));
        assertEquals(null, tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        // checks entrySet
        assertNotNull(tMap.entrySet());
        assertEquals(0, tMap.entrySet().size());
        assertTrue(tMap.entrySet().isEmpty());
        // checks KeySet
        assertNotNull(tMap.keySet());
        assertEquals(0, tMap.keySet().size());
        assertTrue(tMap.keySet().isEmpty());
        // checks ValueSet
        assertNotNull(tMap.values());
        assertEquals(0, tMap.values().size());
        assertTrue(tMap.values().isEmpty());
        // checks entryList
        assertNotNull(tMap.entryList());
        assertEquals(0, tMap.entryList().size());
        assertTrue(tMap.entryList().isEmpty());
        // checks KeyList
        assertNotNull(tMap.keyList());
        assertEquals(0, tMap.keyList().size());
        assertTrue(tMap.keyList().isEmpty());
        // checks ValueList
        assertNotNull(tMap.valueList());
        assertEquals(0, tMap.valueList().size());
        assertTrue(tMap.valueList().isEmpty());
    }

    /**
     * testing put / get in Hashmap
     */
    public void testThreadMapPG() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));

        // add to hashMap element 1
        assertEquals("value1", tMap.put(0, "value2"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value2", tMap.get(0));

        // add to hashMap element 2
        assertEquals(null, tMap.put(1, "value3"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value2", tMap.get(0));
        assertEquals("value3", tMap.get(1));

        // add to hashMap element 3
        assertEquals(null, tMap.put(2, "value4"));
        assertEquals(3, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value2", tMap.get(0));
        assertEquals("value3", tMap.get(1));
        assertEquals("value4", tMap.get(2));

        // add to hashMap element 4
        assertEquals(null, tMap.put(4, "value5"));
        assertEquals(4, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value2", tMap.get(0));
        assertEquals("value3", tMap.get(1));
        assertEquals("value4", tMap.get(2));
        assertEquals("value5", tMap.get(4));

        // add to hashMap element 5
        assertEquals(null, tMap.put(7, "value6"));
        assertEquals(5, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value2", tMap.get(0));
        assertEquals("value3", tMap.get(1));
        assertEquals("value4", tMap.get(2));
        assertEquals("value5", tMap.get(4));
        assertEquals("value6", tMap.get(7));
        // add to hashMap element 6
        assertEquals(null, tMap.put(10, "value7"));
        assertEquals(6, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value2", tMap.get(0));
        assertEquals("value3", tMap.get(1));
        assertEquals("value4", tMap.get(2));
        assertEquals("value5", tMap.get(4));
        assertEquals("value6", tMap.get(7));
        assertEquals("value7", tMap.get(10));
    }
    
    /**
     * testing putAll / get in Hashmap with ThreadedHashMap
     */
    public void testHashMapVsThreadedHashMap() {
        HashMap<Integer, String> tMap = 
                new HashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        // add to hashMap element 2
        assertEquals(null, tMap.put(5, "value5"));
        // add to hashMap element 3
        assertEquals(null, tMap.put(10, "value10"));
        // add to hashMap element 4
        assertEquals(null, tMap.put(3, "value3"));
        assertEquals(4, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));

        
        ThreadedHashMap<Integer, String> tMap2 = 
                new ThreadedHashMap<Integer, String>();
        List<Integer> list = tMap2.keyList();
        assertNotNull(tMap2);
        assertEquals(0, tMap2.size());
        assertTrue(tMap2.isEmpty());
        assertNull(tMap2.get(0));
        assertNull(tMap2.remove(0));
        assertFalse(tMap2.containsKey(0));
        assertFalse(tMap2.containsValue("value1"));
        // add to hashMap element 1
        assertEquals(null, tMap2.put(0, "value3"));
        // add to hashMap element 2
        assertEquals(null, tMap2.put(1, "value2"));
        // add to hashMap element 3
        assertEquals(null, tMap2.put(10, "value9"));
        // add to hashMap element 4
        assertEquals(null, tMap2.put(7, "value7"));
        // add to hashMap element 5
        assertEquals(null, tMap2.put(8, "value3"));
        // add to hashMap element 6
        assertEquals(null, tMap2.put(12, "value12"));
        // add to hashMap element 7
        assertEquals(null, tMap2.put(13, "value13"));
        // add to hashMap element 8
        assertEquals(null, tMap2.put(9, "value11"));
        assertEquals(8, tMap2.size());
        
        //putAll tMap into tMap2
        tMap2.putAll(tMap);
        assertEquals(10, tMap2.size());
        assertEquals("value1", tMap2.get(0));
        assertEquals("value2", tMap2.get(1));
        assertEquals("value10", tMap2.get(10));
        assertEquals("value7", tMap2.get(7));
        assertEquals("value3", tMap2.get(8));
        assertEquals("value12", tMap2.get(12));
        assertEquals("value13", tMap2.get(13));
        assertEquals("value11", tMap2.get(9));
        assertEquals("value5", tMap2.get(5));
        assertEquals("value3", tMap2.get(3));
        
        //check order in keylist
        list = tMap2.keyList();
        assertEquals(10, list.size());
        int key = list.get(0);
        assertEquals(0, key);
        key = list.get(1);
        assertEquals(1, key);
        key = list.get(2);
        assertEquals(10, key);
        key = list.get(3);
        assertEquals(7, key);
        key = list.get(4);
        assertEquals(8, key);
        key = list.get(5);
        assertEquals(12, key);
        key = list.get(6);
        assertEquals(13, key);
        key = list.get(7);
        assertEquals(9, key);
        key = list.get(8);
        assertEquals(3, key);
        key = list.get(9);
        assertEquals(5, key);
        
    }
    
    /**
     * testing putAll / get in Hashmap with ThreadedHashMap
     */
    public void testHashMapVsThreadedHashMap2() {
        HashMap<Integer, String> tMap = 
                new HashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        // add to hashMap element 2
        assertEquals(null, tMap.put(5, "value5"));
        // add to hashMap element 3
        assertEquals(null, tMap.put(10, "value10"));
        // add to hashMap element 4
        assertEquals(null, tMap.put(3, "value3"));
        assertEquals(4, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));

        
        ThreadedHashMap<Integer, String> tMap2 = 
                new ThreadedHashMap<Integer, String>();
        List<Integer> list = tMap2.keyList();
        assertNotNull(tMap2);
        assertEquals(0, tMap2.size());
        assertTrue(tMap2.isEmpty());
        assertNull(tMap2.get(0));
        assertNull(tMap2.remove(0));
        assertFalse(tMap2.containsKey(0));
        assertFalse(tMap2.containsValue("value1"));
        
        //putAll tMap into tMap2
        tMap2.putAll(tMap);
        assertEquals(4, tMap2.size());
        assertEquals("value10", tMap2.get(10));
        assertEquals("value5", tMap2.get(5));
        assertEquals("value3", tMap2.get(3));
        assertEquals("value1", tMap2.get(0));
        
        //check order in keylist
        list = tMap2.keyList();
        assertEquals(4, list.size());
        int key = list.get(0);
        assertEquals(0, key);
        key = list.get(1);
        assertEquals(3, key);
        key = list.get(2);
        assertEquals(5, key);
        key = list.get(3);
        assertEquals(10, key);
        
    }
    
    /**
     * testing putAll / get in ThreadedHashmap with ThreadedHashMap
     */
    public void testThreadedHashMapVsTreadedHashMap() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        // add to hashMap element 2
        assertEquals(null, tMap.put(5, "value5"));
        // add to hashMap element 3
        assertEquals(null, tMap.put(10, "value10"));
        // add to hashMap element 4
        assertEquals(null, tMap.put(3, "value3"));
        assertEquals(4, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        
        List<Integer> list = tMap.keyList();
        int key = list.get(0);
        assertEquals(0, key);
        key = list.get(1);
        assertEquals(5, key);
        key = list.get(2);
        assertEquals(10, key);
        key = list.get(3);
        assertEquals(3, key);

        
        ThreadedHashMap<Integer, String> tMap2 = 
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap2);
        assertEquals(0, tMap2.size());
        assertTrue(tMap2.isEmpty());
        assertNull(tMap2.get(0));
        assertNull(tMap2.remove(0));
        assertFalse(tMap2.containsKey(0));
        assertFalse(tMap2.containsValue("value1"));
        // add to hashMap element 1
        assertEquals(null, tMap2.put(0, "value3"));
        // add to hashMap element 2
        assertEquals(null, tMap2.put(1, "value2"));
        // add to hashMap element 3
        assertEquals(null, tMap2.put(10, "value9"));
        // add to hashMap element 4
        assertEquals(null, tMap2.put(7, "value7"));
        // add to hashMap element 5
        assertEquals(null, tMap2.put(8, "value3"));
        // add to hashMap element 6
        assertEquals(null, tMap2.put(12, "value12"));
        // add to hashMap element 7
        assertEquals(null, tMap2.put(13, "value13"));
        // add to hashMap element 8
        assertEquals(null, tMap2.put(9, "value11"));
        assertEquals(8, tMap2.size());
        
        list = tMap2.keyList();
        key = list.get(0);
        assertEquals(0, key);
        key = list.get(1);
        assertEquals(1, key);
        key = list.get(2);
        assertEquals(10, key);
        key = list.get(3);
        assertEquals(7, key);
        key = list.get(4);
        assertEquals(8, key);
        key = list.get(5);
        assertEquals(12, key);
        key = list.get(6);
        assertEquals(13, key);
        key = list.get(7);
        assertEquals(9, key);
        
        //putAll tMap into tMap2
        tMap2.putAll(tMap);
        assertEquals(10, tMap2.size());
        assertEquals("value1", tMap2.get(0));
        assertEquals("value2", tMap2.get(1));
        assertEquals("value10", tMap2.get(10));
        assertEquals("value7", tMap2.get(7));
        assertEquals("value3", tMap2.get(8));
        assertEquals("value12", tMap2.get(12));
        assertEquals("value13", tMap2.get(13));
        assertEquals("value11", tMap2.get(9));
        
        //keyList order after putAll
        list = tMap2.keyList();
        key = list.get(0);
        assertEquals(0, key);
        key = list.get(1);
        assertEquals(1, key);
        key = list.get(2);
        assertEquals(10, key);
        key = list.get(3);
        assertEquals(7, key);
        key = list.get(4);
        assertEquals(8, key);
        key = list.get(5);
        assertEquals(12, key);
        key = list.get(6);
        assertEquals(13, key);
        key = list.get(7);
        assertEquals(9, key);
        key = list.get(8);
        assertEquals(5, key);
        key = list.get(9);
        assertEquals(3, key);
    }
    
    /**
     * testing putAll / get in ThreadedHashmap with ThreadedHashMap
     */
    public void testThreadedHashMapVsTreadedHashMap2() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        // add to hashMap element 2
        assertEquals(null, tMap.put(5, "value5"));
        // add to hashMap element 3
        assertEquals(null, tMap.put(10, "value10"));
        // add to hashMap element 4
        assertEquals(null, tMap.put(3, "value3"));
        assertEquals(4, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));

        
        ThreadedHashMap<Integer, String> tMap2 = 
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap2);
        assertEquals(0, tMap2.size());
        assertTrue(tMap2.isEmpty());
        assertNull(tMap2.get(0));
        assertNull(tMap2.remove(0));
        assertFalse(tMap2.containsKey(0));
        assertFalse(tMap2.containsValue("value1"));
        // add to hashMap element 1
        assertEquals(null, tMap2.put(0, "value3"));
        // add to hashMap element 2
        assertEquals(null, tMap2.put(1, "value2"));
        // add to hashMap element 3
        assertEquals(null, tMap2.put(10, "value9"));
        // add to hashMap element 4
        assertEquals(null, tMap2.put(7, "value7"));
        // add to hashMap element 5
        assertEquals(null, tMap2.put(8, "value3"));
        // add to hashMap element 6
        assertEquals(null, tMap2.put(12, "value12"));
        // add to hashMap element 7
        assertEquals(null, tMap2.put(13, "value13"));
        // add to hashMap element 8
        assertEquals(null, tMap2.put(9, "value11"));
        assertEquals(8, tMap2.size());
        
        //putAll tMap into tMap2
        tMap2.putAll(tMap);
        assertEquals(10, tMap2.size());
        assertEquals("value1", tMap2.get(0));
        assertEquals("value2", tMap2.get(1));
        assertEquals("value10", tMap2.get(10));
        assertEquals("value7", tMap2.get(7));
        assertEquals("value3", tMap2.get(8));
        assertEquals("value12", tMap2.get(12));
        assertEquals("value13", tMap2.get(13));
        assertEquals("value11", tMap2.get(9));
    }

    /**
     * testing put / remove / get in Hashmap
     */
    public void testThreadMapPRG() {
        ThreadedHashMap<Integer, String> tMap =
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));

        // remove element from hashMap
        assertEquals("value1", tMap.remove(0));
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        assertEquals(null, tMap.get(0));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));

        // add to hashMap element 2
        assertEquals(null, tMap.put(1, "value2"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));

        // remove element from hashMap
        assertEquals("value1", tMap.remove(0));
        assertEquals(null, tMap.remove(5));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        assertEquals(null, tMap.get(0));
        assertEquals("value2", tMap.get(1));

    }

    /**
     * testing put / get / contains in Hashmap
     */
    public void testThreadMapPGC() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        assertTrue(tMap.containsKey(0));
        assertFalse(tMap.containsKey(1));
        assertTrue(tMap.containsValue("value1"));
        assertFalse(tMap.containsValue("value2"));

        // add to hashMap element 2
        assertEquals(null, tMap.put(1, "value2"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        assertEquals("value2", tMap.get(1));
        assertTrue(tMap.containsKey(0));
        assertTrue(tMap.containsKey(1));
        assertTrue(tMap.containsValue("value1"));
        assertTrue(tMap.containsValue("value2"));
    }

    /**
     * testing put / clear in Hashmap
     */
    public void testThreadMapPC() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));

        // add to hashMap element 2
        assertEquals(null, tMap.put(1, "value2"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        assertEquals("value2", tMap.get(1));

        // clear the hashMap
        tMap.clear();
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        assertFalse(tMap.containsKey(1));
        assertFalse(tMap.containsValue("value2"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
    }

    /**
     * testing put / entrylist in Hashmap
     */
    public void testThreadMapPEl() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        List<Entry<Integer, String>> list = tMap.entryList();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        assertEquals(0, list.size());

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        list = tMap.entryList();
        assertEquals(1, list.size());
        int key = list.get(0).getKey();
        assertEquals(0, key);

        // add to hashMap element 2
        assertEquals(null, tMap.put(1, "value2"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        assertEquals("value2", tMap.get(1));
        list = tMap.entryList();
        assertEquals(2, list.size());
        key = list.get(0).getKey();
        assertEquals(0, key);
        key = list.get(1).getKey();
        assertEquals(1, key);

        // add to hashMap element 3
        assertEquals(null, tMap.put(2, "value3"));
        assertEquals(3, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        assertEquals("value2", tMap.get(1));
        assertEquals("value3", tMap.get(2));
        list = tMap.entryList();
        assertEquals(3, list.size());
        key = list.get(0).getKey();
        assertEquals(0, key);
        key = list.get(1).getKey();
        assertEquals(1, key);
        key = list.get(2).getKey();
        assertEquals(2, key);
        
        // remove element from HashMap check entryList
        assertEquals("value2", tMap.remove(1));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        assertEquals("value3", tMap.get(2));
        list = tMap.entryList();
        assertEquals(2, list.size());
        key = list.get(0).getKey();
        assertEquals(0, key);
        key = list.get(1).getKey();
        assertEquals(2, key);
    }

    /**
     * testing put / Keylist in Hashmap
     */
    public void testThreadMapPKl() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        List<Integer> list = tMap.keyList();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        assertEquals(0, list.size());

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        list = tMap.keyList();
        assertEquals(1, list.size());
        int key = list.get(0);
        assertEquals(0, key);

        // add to hashMap element 2
        assertEquals(null, tMap.put(1, "value2"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        assertEquals("value2", tMap.get(1));
        list = tMap.keyList();
        assertEquals(2, list.size());
        key = list.get(0);
        assertEquals(0, key);
        key = list.get(1);
        assertEquals(1, key);

        // add to hashMap element 1
        assertEquals(null, tMap.put(2, "value3"));
        assertEquals(3, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        assertEquals("value2", tMap.get(1));
        assertEquals("value3", tMap.get(2));
        list = tMap.keyList();
        assertEquals(3, list.size());
        key = list.get(0);
        assertEquals(0, key);
        key = list.get(1);
        assertEquals(1, key);
        key = list.get(2);
        assertEquals(2, key);
    }

    /**
     * testing put / Valuelist in Hashmap
     */
    public void testThreadMapPVl() {
        ThreadedHashMap<Integer, String> tMap =
                new ThreadedHashMap<Integer, String>();
        List<String> list = tMap.valueList();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        assertEquals(0, list.size());

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        list = tMap.valueList();
        assertEquals(1, list.size());
        String value = list.get(0);
        assertEquals("value1", value);

        // add to hashMap element 2
        assertEquals(null, tMap.put(1, "value2"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        assertEquals("value2", tMap.get(1));
        list = tMap.valueList();
        assertEquals(2, list.size());
        value = list.get(0);
        assertEquals("value1", value);
        value = list.get(1);
        assertEquals("value2", value);

        // add to hashMap element 1
        assertEquals(null, tMap.put(2, "value3"));
        assertEquals(3, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        assertEquals("value2", tMap.get(1));
        assertEquals("value3", tMap.get(2));
        list = tMap.valueList();
        assertEquals(3, list.size());
        value = list.get(0);
        assertEquals("value1", value);
        value = list.get(1);
        assertEquals("value2", value);
        value = list.get(2);
        assertEquals("value3", value);
    }
    
    /**
     * testing put / Entryset in Hashmap
     */
    public void testThreadMapPEs() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        Set<Entry<Integer, String>> eSet = tMap.entrySet();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        assertEquals(0, eSet.size());
        
        Iterator<Entry<Integer, String>> itr = eSet.iterator();
        assertFalse(itr.hasNext());
        
        boolean caught = false;
        try {
            itr.next();   
        }
        catch (NoSuchElementException u) {
            caught = true;
        }
        assertTrue(caught);
        
        caught = false;
        try {
            itr.remove();   
        }
        catch (IllegalStateException u) {
            caught = true;
        }
        assertTrue(caught);
        
        
        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        eSet = tMap.entrySet();
        assertEquals(1, eSet.size());
        Entry<Integer, String> temp = new
                AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        assertTrue(eSet.contains(temp));
        // add to hashMap element 1
        assertEquals(null, tMap.put(1, "value6"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value6", tMap.get(1));
        eSet = tMap.entrySet();
        assertEquals(2, eSet.size());
        temp = new AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        assertTrue(eSet.contains(temp));
        temp = new AbstractMap.SimpleEntry<Integer, String>(1, "value6");
        assertTrue(eSet.contains(temp));
        // remove element from hashMap
        assertEquals("value1", tMap.remove(0));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals(null, tMap.get(0));
        eSet = tMap.entrySet();
        assertEquals(1, eSet.size());
        temp = new AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        assertFalse(eSet.contains(temp));
        temp = new AbstractMap.SimpleEntry<Integer, String>(1, "value6");
        assertTrue(eSet.contains(temp));
        temp = new AbstractMap.SimpleEntry<Integer, String>(9, "value9");
        assertFalse(eSet.contains(temp));
        caught = false;
        try {
            eSet.add(temp);
        }
        catch (UnsupportedOperationException e) {
            caught = true;
        }
        assertTrue(caught);
    }
    
    /**
     * testing put / Keyset in Hashmap
     */
    public void testThreadMapPKs() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        Set<Integer> kSet = tMap.keySet();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        assertEquals(0, kSet.size());
        
        Iterator<Integer> itr = kSet.iterator();
        assertFalse(itr.hasNext());
        
        boolean caught = false;
        try {
            itr.next();   
        }
        catch (NoSuchElementException u) {
            caught = true;
        }
        assertTrue(caught);
        
        caught = false;

        
        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        kSet = tMap.keySet();
        assertEquals(1, kSet.size());
        Entry<Integer, String> temp = new
                AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        Integer key = temp.getKey();
        assertTrue(kSet.contains(key));
        
        // add to hashMap element 1
        assertEquals(null, tMap.put(1, "value6"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value6", tMap.get(1));
        kSet = tMap.keySet();
        assertEquals(2, kSet.size());
        temp = new AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        assertTrue(kSet.contains(temp.getKey()));
        temp = new AbstractMap.SimpleEntry<Integer, String>(1, "value6");
        assertTrue(kSet.contains(temp.getKey()));
        // remove element from hashMap
        assertEquals("value1", tMap.remove(0));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals(null, tMap.get(0));
        kSet = tMap.keySet();
        assertEquals(1, kSet.size());
        temp = new AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        assertFalse(kSet.contains(temp.getKey()));
        temp = new AbstractMap.SimpleEntry<Integer, String>(1, "value6");
        assertTrue(kSet.contains(temp.getKey()));
        temp = new AbstractMap.SimpleEntry<Integer, String>(9, "value9");
        assertFalse(kSet.contains(temp.getKey()));
        caught = false;
        try {
            kSet.add(temp.getKey());
        }
        catch (UnsupportedOperationException e) {
            caught = true;
        }
        assertTrue(caught);
        
        kSet = tMap.keySet();
        assertEquals(1, kSet.size());
        
        itr = kSet.iterator();
        assertTrue(itr.hasNext());
        Integer x = 1;
        
        assertEquals(x, itr.next());
        assertFalse(itr.hasNext());
        
        caught = false;
        try {
            itr.next();   
        }
        catch (NoSuchElementException u) {
            caught = true;
        }
        assertTrue(caught);
        
        caught = false;
        
        itr.remove();
        assertEquals(0, kSet.size());
        assertEquals(0, tMap.size());
        assertFalse(tMap.containsKey(1));
        
    }
    
    /**
     * testing put / Valueset in Hashmap
     */
    public void testThreadMapPVs() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        Set<String> vSet = tMap.values();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        assertEquals(0, vSet.size());
        
        Iterator<String> itr = vSet.iterator();
        assertFalse(itr.hasNext());
        
        boolean caught = false;
        try {
            itr.next();   
        }
        catch (NoSuchElementException u) {
            caught = true;
        }
        assertTrue(caught);
        
        caught = false;

        caught = false;
        try {
            itr.remove();   
        }
        catch (IllegalStateException u) {
            caught = true;
        }
        assertTrue(caught);

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        vSet = tMap.values();
        assertEquals(1, vSet.size());
        Entry<Integer, String> temp = new
                AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        String value = temp.getValue();
        assertTrue(vSet.contains(value));
        
        // add to hashMap element 1
        assertEquals(null, tMap.put(1, "value6"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value6", tMap.get(1));
        vSet = tMap.values();
        assertEquals(2, vSet.size());
        temp = new AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        assertTrue(vSet.contains(temp.getValue()));
        temp = new AbstractMap.SimpleEntry<Integer, String>(1, "value6");
        assertTrue(vSet.contains(temp.getValue()));
        // remove element from hashMap
        assertEquals("value1", tMap.remove(0));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals(null, tMap.get(0));
        vSet = tMap.values();
        assertEquals(1, vSet.size());
        temp = new AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        assertFalse(vSet.contains(temp.getValue()));
        temp = new AbstractMap.SimpleEntry<Integer, String>(1, "value6");
        assertTrue(vSet.contains(temp.getValue()));
        temp = new AbstractMap.SimpleEntry<Integer, String>(9, "value9");
        assertFalse(vSet.contains(temp.getValue()));
        caught = false;
        try {
            vSet.add(temp.getValue());
        } 
        catch (UnsupportedOperationException e) {
            caught = true;
        }
        assertTrue(caught);
    }
    
    /**
     * testing remove on redlist with one element
     */
    public void testRedlistRemove1() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        
        // remove element from hashMap
        assertEquals("value1", tMap.remove(0));
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertEquals(null, tMap.get(0));
        
    }
    
    /**
     * testing remove on redlist with two element - head
     */
    public void testRedlistRemove2() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        // add to hashMap element 2
        assertEquals(null, tMap.put(1, "value1"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(1));
        
        // remove element from hashMap
        assertEquals("value1", tMap.remove(0));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals(null, tMap.get(0));
        assertEquals("value1", tMap.get(1));
        
    }
    
    /**
     * testing remove on redlist with two element - tail
     */
    public void testRedlistRemove3() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        // add to hashMap element 2
        assertEquals(null, tMap.put(1, "value1"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(1));
        
        // remove element from hashMap
        assertEquals("value1", tMap.remove(1));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals(null, tMap.get(1));
        assertEquals("value1", tMap.get(0));
        
    }
    
    /**
     * testing remove on redlist with three element - middle
     */
    public void testRedlistRemove4() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));

        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        // add to hashMap element 2
        assertEquals(null, tMap.put(1, "value1"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(1));
        // add to hashMap element 2
        assertEquals(null, tMap.put(2, "value1"));
        assertEquals(3, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(2));
        
        // remove element from hashMap
        assertEquals("value1", tMap.remove(1));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals(null, tMap.get(1));
        assertEquals("value1", tMap.get(0));
        assertEquals("value1", tMap.get(2));
        
    }
    
    /**
     * test ketList for hint
     */
    public void testKeyListHint() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        List<Integer> list = tMap.keyList();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        
        assertEquals(null, tMap.put(0, "value0"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        list = tMap.keyList();
        assertEquals(1, list.size());
        int key = list.get(0);
        assertEquals(0, key);
        
        assertEquals(null, tMap.put(1, "value1"));
        assertEquals(2, tMap.size());
        assertFalse(tMap.isEmpty());
        list = tMap.keyList();
        assertEquals(2, list.size());
        key = list.get(0);
        assertEquals(0, key);
        key = list.get(1);
        assertEquals(1, key);
        
        assertEquals(null, tMap.put(2, "value2"));
        assertEquals(3, tMap.size());
        assertFalse(tMap.isEmpty());
        list = tMap.keyList();
        assertEquals(3, list.size());
        key = list.get(0);
        assertEquals(0, key);
        key = list.get(1);
        assertEquals(1, key);
        key = list.get(2);
        assertEquals(2, key);
        
        assertEquals(null, tMap.put(3, "value3"));
        assertEquals(4, tMap.size());
        assertFalse(tMap.isEmpty());
        list = tMap.keyList();
        assertEquals(4, list.size());
        key = list.get(0);
        assertEquals(0, key);
        key = list.get(1);
        assertEquals(1, key);
        key = list.get(2);
        assertEquals(2, key);
        key = list.get(3);
        assertEquals(3, key);
        
        assertEquals("value0", tMap.put(0, "value4"));
        assertEquals(4, tMap.size());
        assertFalse(tMap.isEmpty());
        list = tMap.keyList();
        assertEquals(4, list.size());
        key = list.get(0);
        assertEquals(0, key);
        key = list.get(1);
        assertEquals(1, key);
        key = list.get(2);
        assertEquals(2, key);
        key = list.get(3);
        assertEquals(3, key);
    }
    
    /**
     * testing put / Keyset in Hashmap
     */
    public void testThreadMapPKs2() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        Set<Integer> kSet = tMap.keySet();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        assertEquals(0, kSet.size());
        
        Iterator<Integer> itr = kSet.iterator();
        assertFalse(itr.hasNext());
        
        boolean caught = false;
        try {
            itr.next();   
        }
        catch (NoSuchElementException u) {
            caught = true;
        }
        assertTrue(caught);
        
        caught = false;
        
        try {
            itr.remove();
        }
        catch (IllegalStateException e) {
            caught = true;
        }
        assertTrue(caught);

        
        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        kSet = tMap.keySet();
        assertEquals(1, kSet.size());
        Entry<Integer, String> temp = new
                AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        Integer key = temp.getKey();
        assertTrue(kSet.contains(key));
        
        
        caught = false;
        try {
            kSet.add(temp.getKey());
        }
        catch (UnsupportedOperationException e) {
            caught = true;
        }
        assertTrue(caught);
        
        kSet = tMap.keySet();
        assertEquals(1, kSet.size());
        
        kSet = tMap.keySet();
        itr = kSet.iterator();
        assertTrue(itr.hasNext());
        Integer x = 0;
        
        assertEquals(x, itr.next());
        assertFalse(itr.hasNext());
        
        caught = false;
        try {
            itr.next();   
        }
        catch (NoSuchElementException u) {
            caught = true;
        }
        assertTrue(caught);
        
        caught = false;
        
        itr.remove();
        assertEquals(0, kSet.size());
        assertEquals(0, tMap.size());
        assertFalse(tMap.containsKey(1));
    }
    
    /**
     * testing put / Keyset in Hashmap
     */
    public void testThreadMapPVs2() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        Set<String> vSet = tMap.values();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        assertEquals(0, vSet.size());
        
        Iterator<String> itr = vSet.iterator();
        assertFalse(itr.hasNext());
        
        boolean caught = false;
        try {
            itr.next();   
        }
        catch (NoSuchElementException u) {
            caught = true;
        }
        assertTrue(caught);
        
        caught = false;
        
        try {
            itr.remove();
        }
        catch (IllegalStateException e) {
            caught = true;
        }
        assertTrue(caught);

        
        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        vSet = tMap.values();
        assertEquals(1, vSet.size());
        Entry<Integer, String> temp = new
                AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        String key = temp.getValue();
        assertTrue(vSet.contains(key));
        
        
        caught = false;
        try {
            vSet.add(temp.getValue());
        }
        catch (UnsupportedOperationException e) {
            caught = true;
        }
        assertTrue(caught);
        
        vSet = tMap.values();
        assertEquals(1, vSet.size());
        
        vSet = tMap.values();
        itr = vSet.iterator();
        assertTrue(itr.hasNext());
        String x = "value1";
        
        assertEquals(x, itr.next());
        assertFalse(itr.hasNext());
        
        caught = false;
        try {
            itr.next();   
        }
        catch (NoSuchElementException u) {
            caught = true;
        }
        assertTrue(caught);
        
        caught = false;
        
        itr.remove();
        assertEquals(0, vSet.size());
        assertEquals(0, tMap.size());
        assertFalse(tMap.containsKey(1));
    }
    
    /**
     * testing put / Keyset in Hashmap
     */
    public void testThreadMapPEs2() {
        ThreadedHashMap<Integer, String> tMap = 
                new ThreadedHashMap<Integer, String>();
        Set<Entry<Integer, String>> eSet = tMap.entrySet();
        assertNotNull(tMap);
        assertEquals(0, tMap.size());
        assertTrue(tMap.isEmpty());
        assertNull(tMap.get(0));
        assertNull(tMap.remove(0));
        assertFalse(tMap.containsKey(0));
        assertFalse(tMap.containsValue("value1"));
        assertEquals(0, eSet.size());
        
        Iterator<Entry<Integer, String>> itr = eSet.iterator();
        assertFalse(itr.hasNext());
        
        boolean caught = false;
        try {
            itr.next();   
        }
        catch (NoSuchElementException u) {
            caught = true;
        }
        assertTrue(caught);
        
        caught = false;
        
        try {
            itr.remove();
        }
        catch (IllegalStateException e) {
            caught = true;
        }
        assertTrue(caught);

        
        // add to hashMap element 1
        assertEquals(null, tMap.put(0, "value1"));
        assertEquals(1, tMap.size());
        assertFalse(tMap.isEmpty());
        assertEquals("value1", tMap.get(0));
        eSet = tMap.entrySet();
        assertEquals(1, eSet.size());
        Entry<Integer, String> temp = new
                AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        
        assertTrue(eSet.contains(temp));
        
        
        caught = false;
        try {
            eSet.add(temp);
        }
        catch (UnsupportedOperationException e) {
            caught = true;
        }
        assertTrue(caught);
        
        eSet = tMap.entrySet();
        assertEquals(1, eSet.size());
        
        eSet = tMap.entrySet();
        itr = eSet.iterator();
        assertTrue(itr.hasNext());
        Integer x = 0;
        temp = new AbstractMap.SimpleEntry<Integer, String>(0, "value1");
        
        assertEquals(temp, itr.next());
        assertFalse(itr.hasNext());
        
        caught = false;
        try {
            itr.next();   
        }
        catch (NoSuchElementException u) {
            caught = true;
        }
        assertTrue(caught);
        
        caught = false;
        
        itr.remove();
        assertEquals(0, eSet.size());
        assertEquals(0, tMap.size());
        assertFalse(tMap.containsKey(1));
    }
}
