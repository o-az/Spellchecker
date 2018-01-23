package ass7.hw2;
/******************************************************************************
 *  A symbol table implemented with a separate chaining hash table.
 *
 *  Note 1: does not support delete().
 *  Note 2: does not do resizing.
 *
 ******************************************************************************/

import javafx.util.Pair;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
 
public class SeparateChainHashing<Key, Value> {

    private int size;       // number of key-value pairs
    private int capacity;       // hash table size
    private LinkedList<Pair<Key, Value>>[] st;   // array of linked-list symbol tables


    // create separate chaining hash table
    public SeparateChainHashing() {
        this(2);//997);
    } 

    // create separate chaining hash table with m lists
    public SeparateChainHashing(int m) {
        this.capacity = m;
        st = new LinkedList[m];
    } 


    // hash value between 0 and m-1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    } 

    // return number of key-value pairs in symbol table
    public int size() {
        return size;
    } 

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // is the key in the symbol table?
    public boolean contains(Key key) {
        return get(key) != null;
    } 

    // return value associated with key, null if no such key
    public Value get(Key key) {
        int i = hash(key);

        LinkedList<Pair<Key, Value>> chain = st[i];
        if(chain == null)
        	return null;

        Iterator<Pair<Key, Value>> it = chain.listIterator();
        while(it.hasNext()) {
            Pair<Key, Value> p = it.next();
            if(p.getKey().equals(key))
            	return p.getValue();
        }
        return null;
    }

    // insert key-value pair into the table
    public void put(Key key, Value val) {
//        if (val == null) {
//            delete(key);
//            return;
//        }

    	// Calculate hash
        int i = hash(key);

        // Linked list is not created yet -> create and add key,value pair
        if(st[i] == null) {
            st[i] = new LinkedList<>();
            st[i].add(new Pair<>(key, val));
            size++;
            return;
        }
        // Otherwise add to the existing chain (dont forget to check if this key already in the chain, then we must just update value for this key)
        Iterator<Pair<Key, Value>> it = st[i].listIterator();
        Pair<Key, Value> p;
        int pos = -1;
        while(it.hasNext()) {
            p = it.next();
            pos++;
            // Found same key already in the chain -> replace value for this key and return
            if(p.getKey().equals(key)) {
                st[i].remove(pos); // it.remove();
                st[i].add(new Pair<>(key, val));
                return;
            }
        }

        st[i].add(new Pair<Key, Value>(key, val));
        size++;
        
        if (capacity < 2 * size)
        	resizeTable();
    }

    // resize table
    private void resizeTable() {
    	LinkedList[] oldTable = st;
    	size = 0;
    	capacity *= 2;
		st = new LinkedList[capacity];

	    // rehash table
        for(int i = 0; i < oldTable.length; ++i) {
        	if (oldTable[i] != null) {
        		Iterator<Pair<Key, Value>> it = oldTable[i].listIterator();
        		while(it.hasNext()) {
        			Pair<Key, Value> p = it.next();
        			put(p.getKey(), p.getValue());
        		}
        	}
        }
    }

	// delete key (and associated value) from the symbol table.
    public void delete(Key key) {
        if(key == null || contains(key) == false) return;

        int i = hash(key);
        int j = -1;
        boolean found = false;
        Iterator<Pair<Key, Value>> it = st[i].listIterator();

        while(it.hasNext() && found == false) {
            Pair<Key, Value> p = it.next();
            j++;
            // Found key
            if(p.getKey().equals(key)) {
                found = true;
                break;
            }
        }
        // If key found in current chain -> remove from list key,value pair
        if(found) st[i].remove(j);

        //throw new UnsupportedOperationException("delete not currently supported");
    }

    // Return true if hashtable contain specified 'key'
    public boolean containsKey(Key key) {
        int i = hash(key);

        Iterator<Pair<Key, Value>> it = st[i].listIterator();
        while(it.hasNext()) {
            Pair<Key, Value> p = it.next();
            // Found key
            if(p.getKey().equals(key)) return true;
        }
        return false;
    }

    // Return true if table contain specified 'value'
    public boolean containsValue(Value value) {
        for(int i = 0; i < st.length; ++i) {
        	if (st[i] == null)
        		continue;
            Iterator<Pair<Key, Value>> it = st[i].listIterator();
            while(it.hasNext()) {
                Pair<Key, Value> p = it.next();
                // Found value
                if(p.getValue().equals(value))
                	return true;
            }
        }
        return false;
    }

    // return all keys as an Iterable
    public Iterable<Key> keys()  {
        Queue<Key> queue = new LinkedList<Key>();

        for (int i = 0; i < capacity; i++) {
        	if (st[i] != null) {
	            Iterator<Pair<Key, Value>> it = st[i].listIterator();
	            while(it.hasNext()) {
	                Pair<Key, Value> p = it.next();
	                queue.add(p.getKey());
	            }
        	}
        }
        return queue;
    }
    // return all values as an Iterable
    public Iterable<Value> values()  {
        Queue<Value> queue = new LinkedList<Value>();

        for (int i = 0; i < capacity; i++) {
            Iterator<Pair<Key, Value>> it = st[i].listIterator();
            while(it.hasNext()) {
                Pair<Key, Value> p = it.next();
                queue.add(p.getValue());
            }
        }
        return queue;
    }
}
