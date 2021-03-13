package adapters;

import interfaces.HCollection;
import interfaces.HIterator;
import interfaces.HSet;
import exceptions.IllegalStateException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

/**
 * Implementation of Set interface Java 1.4.2.
 * Object Adapter of an Hashtable.
 * @author Giacomo Camposampiero
 */
public class Set implements HSet{

    Hashtable table;
    
    /**
     * Empty constructor.
     * This constructor will initialize an empty set, with no elements contained.
     */
    public Set() {
        this.table = new Hashtable();
    }
    
    /**
     * Adds the specified element to this set if it is not already present.
     * More formally, adds the specified element, o, to this set if this set 
     * contains no element e such that (o==null ? e==null : o.equals(e)). 
     * If this set already contains the specified element, the call leaves this 
     * set unchanged and returns false. In combination with the restriction on 
     * constructors, this ensures that sets never contain duplicate elements.
     * 
     * null is not a valid value in then set, so the attempt of adding a null to 
     * the set will result in the throwing of an un-checked exception
     * The method is based on hashCode, and hash collision problem is solved in 
     * a trivial way: only the first object with a specific hash is inserted; 
     * the insertion of a different object that has the same hash won't result 
     * in a modification of the set.
     * @param o  element to be appended to this set. 
     * @return true if this set did not already contain the specified element.  
     * @throws NullPointerException if the specified element is null.
     */
    @Override
    public boolean add(Object o) {
        if(contains(o)) return false;
        table.put(o.hashCode(), o);
        return true;
    }

    /**
     * Adds all of the elements in the specified collection to this set if they're
     * not already present.
     * If the specified collection is also a set, the addAll operation effectively 
     * modifies this set so that its value is the union of the two sets. 
     * The behavior of this operation is unspecified if the specified collection 
     * is modified while the operation is in progress. 
     * 
     * Hash collision problem is solved in a trivial way: only the first object 
     * with a specific hash is inserted; the insertion of a different object that
     * has the same hash won't result in a modification of the set.
     * @param c  collection whose elements are to be added to this set. 
     * @return true if this set changed as a result of the call.
     * @throws NullPointerException if the specified element is null.
     * @throws NullPointerException if the specified collection contains an element which is null.
     */
    @Override
    public boolean addAll(HCollection c) {
        HIterator it = c.iterator();
        boolean res = false;
        while(it.hasNext()) 
            res = add(it.next()) || res;
        return res;
    }

    /**
     * Removes all of the elements from this set.
     * This set will be empty after this call returns. 
     */
    @Override
    public void clear() {
        table.clear();
    }

    /**
     * Returns true if this set contains the specified element.
     * More formally, returns true if and only if this set contains an element e 
     * such that (o==null ? e==null : o.equals(e)). 
     * @param o  element whose presence in this set is to be tested. 
     * @return  true if this set contains the specified element. 
     * @throws NullPointerException if the specified element is null.
     */
    @Override
    public boolean contains(Object o) {
        int hash = o.hashCode();
        if(!table.containsKey(hash)) return false;
        return o.equals(table.get(hash));
    }

    /**
     * Returns true if this set contains all of the elements of the specified collection. 
     * If the specified collection is also a set, this method returns true if it 
     * is a subset of this set.
     * @param c  collection to be checked for containment in this set. 
     * @return  true if this set contains all of the elements of the specified 
     *          collection.
     * @throws NullPointerException if the specified collection is null.
     */
    @Override
    public boolean containsAll(HCollection c) {
        HIterator it = c.iterator();
        boolean res = true;
        while(it.hasNext())
            res = contains(it.next()) && res;
        return res;
    }

    /**
     * Compares the specified object with this set for equality.
     * Returns true if the specified object is also a set, the two sets have the
     * same size, and every member of the specified set is contained in this set 
     * (or equivalently, every member of this set is contained in the specified set). 
     * This definition ensures that the equals method works properly across 
     * different implementations of the set interface. 
     * @param o  the object to be compared for equality with this set. 
     * @return  true if the specified object is equal to this set.
     */
    @Override 
    public boolean equals(Object o) {
        if (o == this) return true; 
        if (!(o.getClass().equals(this.getClass()))) { 
            return false; 
        }    
        Set other = (Set) o;
        if (other.size() != size()) return false;
        HIterator it = iterator();
        while(it.hasNext())
            if(!other.contains(it.next())) 
                return false;
        return true;
    }
    
    /**
     * Returns the hash code value for this set.
     * The hash code of a set is defined to be the sum of the hash codes of the 
     * elements in the set, where the hashcode of a null element is defined to 
     * be zero. This ensures that s1.equals(s2) implies that s1.hashCode()==s2.hashCode() 
     * for any two sets s1 and s2, as required by the general contract of the 
     * Object.hashCode method. 
     * @return  the hash code value for this set.
     */
    @Override 
    public int hashCode() {
        int hash = 0;
        HIterator it = iterator();
        while(it.hasNext()) 
            hash += it.next().hashCode();
        return hash;
    }
    
    /**
     * Returns true if this set contains no elements. 
     * @return  true if this set contains no elements.
     */
    @Override
    public boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Returns an iterator over the elements in this set.
     * The elements are returned in no particular order.
     * If many iterators are istantied on a single instance of the object and 
     * they just perform reading operations, there are no cuncurrency problems.
     * If a structural modification (add or remove to the main set) is performed,
     * all the iterator (except the one which performe the modification, if it's
     * a remove) become invalide, and their behaviour is not longer consistent
     * with the specification (unexpected exception or null values could be
     * retrieved by the iterator).
     * @return an iterator over the elements in this set.
     */
    @Override
    public HIterator iterator() {
        return new SetIterator();
    }

    /**
     * Removes the specified element from this set if it is present.
     * More formally, removes an element e such that (o==null ? e==null : o.equals(e)), 
     * if the set contains such an element. Returns true if the set contained the 
     * specified element (or equivalently, if the set changed as a result of the call). 
     * 
     * The set will not contain the specified element once the call returns null,
     * as this set does not accept null elements.
     * @param o  element to be removed from this set, if present. 
     * @return  true if this set contained the specified element. 
     * @throws NullPointerException if the specified element is null.
     */
    @Override
    public boolean remove(Object o) {
        if(!contains(o)) return false;
        table.remove(o.hashCode());
        return true;
    }

    /**
     * Removes from this set all of its elements that are contained in the 
     * specified collection.
     * If the specified collection is also a set, this operation effectively 
     * modifies this set so that its value is the asymmetric set difference of 
     * the two sets. 
     * @param c  collection that defines which elements will be removed from this set.  
     * @return true if this set changed as a result of the call. 
     * @throws NullPointerException if the specified set is null.
     */
    @Override
    public boolean removeAll(HCollection c) {
        if(c == null) throw new NullPointerException();
        HIterator it = iterator();
        boolean removed = false;
        while(it.hasNext()) {
            Object elem = it.next();
            if(c.contains(elem)) {
                remove(elem);
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Retains only the elements in this set that are contained in the specified 
     * collection.
     * In other words, removes from this set all of its elements that are not 
     * contained in the specified collection. If the specified collection is 
     * also a set, this operation effectively modifies this set so that its 
     * value is the intersection of the two sets.
     * @param c  collection that defines which elements this set will retain. 
     * @return  true if this collection changed as a result of the call. 
     * @throws NullPointerException if the specified set is null.
     */
    @Override
    public boolean retainAll(HCollection c) {
        if(c == null) throw new NullPointerException();
        List toRemove = new List();
        HIterator it = iterator();
        while(it.hasNext()) {
            Object elem = it.next();
            if(!c.contains(elem)) toRemove.add(elem);
        }
        return removeAll(toRemove); 
    }
    
    /**
     * Returns the number of elements in this set (its cardinality). 
     * @return the number of elements in this set.
     */
    @Override
    public int size() {
        return table.size();
    }

    /**
     * Returns an array containing all of the elements in this set.
     * @return  an array containing all of the elements in this set.
     */
    @Override
    public Object[] toArray() {
        Object[] res = new Object[size()];
        HIterator it = iterator();
        int i = 0;
        while(it.hasNext()) 
            res[i++] = it.next();
        return res;
    }

    /**
     * Returns an array containing all of the elements in this set.
     * The runtime type of the returned array is that of the specified array if 
     * a parameter is big enough to contain all the elements of this set (all 
     * the elements of the list are copied inside the a parameter).
     * 
     * If a is not big enough, a new Object[] array will be instantiated, and 
     * the return array won't be of the specified array type.
     * The returned array will be "safe" in that no references to it are 
     * maintained by this list. (In other words, this method must allocate a new
     * array even if this set is backed by an array). The caller is thus free to
     * modify the returned array.
     * 
     * If the parameter a is big enough to contain all the element of this list, 
     * the elements will be stored in the parametric array. Only the first size() 
     * cell of the array will be modified, the others will mantain their actual 
     * value.
     * 
     * There is not a specified order used to write elements in the array 
     * (as the set doesn't properly has an order), so the order of the elements 
     * could change from invocation to invocation.
     * @param a the array into which the elements of this list are to be stored, if it is big enough 
     * @return an array containing the elements of this list. 
     * @throws NullPointerException if the parameter is null
     */
    @Override
    public Object[] toArray(Object[] a) {
        if(a == null) throw new NullPointerException();
        Object[] res; 
        if(a.length >= size()) {
            res = a;
        } else {
            res = new Object[size()];
        }        
        int i = 0;
        HIterator it = iterator();
        while(it.hasNext()) res[i++] = it.next();
        return res;
    }
    
    class SetIterator implements HIterator {
        
        Object currentKey;
        Enumeration keys;
        
        public SetIterator() {
            currentKey = null;
            keys = table.keys();
        }
        
        @Override
        public boolean hasNext() {
            return keys.hasMoreElements();
        }

        @Override
        public Object next() {
            if(!hasNext()) throw new NoSuchElementException();   
            currentKey = keys.nextElement();
            return table.get(currentKey);
        }

        @Override
        public void remove() {
            if(currentKey == null) throw new IllegalStateException();
            table.remove(currentKey);
            currentKey = null;
        }
        
    }
    
    @Override
    public String toString() {
        String res = "";
        HIterator it = iterator();
        while(it.hasNext()) res += it.next().toString() + " ";
        return res;
    }

}