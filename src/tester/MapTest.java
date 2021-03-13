package tester;

import adapters.List;
import adapters.Map;
import interfaces.HCollection;
import interfaces.HIterator;
import interfaces.HMap;
import interfaces.HSet;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * Test suite for Map class
 * @author Giacomo Camposampiero
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MapTest {
    
    Map instance;
    
    public MapTest() {
    }
    
    @Before
    public void setUp() {
        instance = new Map();
    }

    /**
     * @title Test #1 of clear method, of class Map.
     * @description This test tests the behaviour of clear method when is called on an empty Map.
     * @expectedResults The map (which was already empty) is expected to be still empty after the call of the method.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method isEmpty()
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the call of this method.
     */
    @Test
    public void testClear_empty() {
        instance.clear();
        boolean result = instance.isEmpty();
        assertEquals("pulizia di una mappa vuota, controllo che la dimensione sia nulla", true, result);    
    }
    
    /**
     * @title Test #2 of clear method, of class Map.
     * @description This test tests the behaviour of clear() method when is called on a non-empty Map.
     * @expectedResults The map (which has been filled with different entry) is expected to be empty after the call of the method.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods isEmpty() and put()
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance must contains no entries after the execution of the test.
     */
    @Test
    public void testClear_notEmpty() {
        instance.put("pippo", "pippo");
        instance.put("pluto", "pluto");
        instance.clear();
        boolean result = instance.isEmpty();
        assertEquals("pulizia di una mappa popolata, controllo che la dimensione sia nulla", true, result);    
    }

    /**
     * @title Test #1 of containsKey method, of class Map.
     * @description This test tests the behaviour of containsKey() method when is called on an empty Map
     * @expectedResults The map doesn't contain the specified key, as the map is empty.
     * @actualResult As expected result.
     * @dependencies The correctness of this test doesn't depends on the correctness of other map methods
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testContainsKey_empty() {
        boolean result = instance.containsKey("pluto");
        assertEquals("una mappa vuota non può contenere chiavi", false, result);
    }
    
    /**
     * @title Test #2 of containsKey method, of class Map.
     * @description This test tests the behaviour of containsKey() method when is called on a Map which not contains that key
     * @expectedResults The map isn't expected to contain the specified key.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but it has to contain the element added).
     */
    @Test
    public void testContainsKey_notContained() {
        instance.put("pippo", "asso");
        boolean result = instance.containsKey("pluto");
        assertEquals("chiave non contenuta nella mappa", false, result);
    }
    
    /**
     * @title Test #3 of containsKey method, of class Map.
     * @description This test tests the behaviour of containsKey() method when is called on a Map which contains that key
     * @expectedResults The map is expected to contains the specified key, which has been added to the map before the invocation of the method.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but it has to contain the element added).
     */
    @Test
    public void testContainsKey_contained() {
        instance.put("pippo", "asso");
        boolean result = instance.containsKey("pippo");
        assertEquals("chiave contenuta nella mappa, controllo che la trovi", true, result);
    }
    
    /**
     * @title Test #4 of containsKey method, of class Map.
     * @description This test tests the behaviour of containsKey() method when is called on a Map which contains a key that hash the same hash
     * @expectedResults The map is not expected to contain the specified value, as it performs a comparation not only on the hash, but also on the equality of key objects.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but it has to contain the element added).
     */
    @Test
    public void testContainsKey_sameHash() {
        instance.put("AaAaAa", "asso");
        boolean result = instance.containsKey("AaAaBB");
        assertEquals("il confronto non dovrebbe essere basato solo sul confronto dell'hash", false, result);
    }
    
    /**
     * @title Test #5 of containsKey method, of class Map.
     * @description This test tests the behaviour of containsKey() method when is called on a Map using as parameter a null reference (NullPointerException expected)
     * @expectedResults The map is expected to throw a NullPointerException.
     * @actualResult As expected result.
     * @dependencies  This test doesn't depend on the correctness of any other method of the class.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsKey_exceptions() {
        instance.containsKey(null);
    }

    /**
     * @title Test #1 of containsValue method, of class Map.
     * @description This test tests the behaviour of containsValue() method when is called on an empty Map
     * @expectedResults The map is expected not to contained the specified value, as the map is empty.
     * @actualResult As expected result.
     * @dependencies  This test doesn't depend on the correctness of any other method of the class.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testContainsValue_empty() {
        boolean result = instance.containsValue("pippo");
        assertEquals("una mappa vuota non contiene valori", false, result);    
    }
    
    /**
     * @title Test #2 of containsValue method, of class Map.
     * @description This test tests the behaviour of containsValue() method when is called on a Map which not contains the value specified
     * @expectedResults The map is expected not to contained the specified value, which wasn't putted to the map before.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of method put()
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but still have to contains the element that was added).
     */
    @Test
    public void testContainsValue_notContained() {
        instance.put("pippo", "asso");
        boolean result = instance.containsValue("pluto");
        assertEquals("valore non contenuto nella mappa", false, result);    
    }
    
    /**
     * @title Test #3 of containsValue method, of class Map.
     * @description This test tests the behaviour of containsValue() method when is called on a Map which not contains the value specified
     * @expectedResults The map is expected to contain the value specified, as it was previously added to the map.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of method put()
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but still have to contains the element that was added).
     */
    @Test
    public void testContainsValue_contained() {
        instance.put("pippo", "asso");
        boolean result = instance.containsValue("asso");
        assertEquals("valore contenuto nella mappa, verifica anche che sia utilizzato il metodo equals() per confrontare gli oggetti", true, result);    
    }
    
    /**
     * @title Test #4 of containsKey method, of class Map.
     * @description This test tests the behaviour of containsValue() method when is called on a Map using as parameter a null reference (NullPointerException expected)
     * @expectedResults The map is expected to throw a NullPointerException.
     * @actualResult As expected result.
     * @dependencies  This test doesn't depend on the correctness of any other method of the class.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsValue_exceptions() {
        instance.containsValue(null);
    }
    
    /**
     * @title Test #1 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method when called on an empty map.
     * @expectedResults The set is expected to be empty.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method isEmpty.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method.
     */
    @Test
    public void testEntry_empty() {
        HSet entries = instance.entrySet();
        assertEquals("una mappa vuota non ha entry", true, entries.isEmpty());
    }
    
    /**
     * @title Test #2 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method when called on a not-empty map.
     * @expectedResults The set size is expected to equals to the number of entries previously putted in the map. The set is also expected to contain all the entries contained by the map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of methods put, isEmpty and contains.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method.
     */
    @Test
    public void testEntry_notEmpty() {
        instance.put("pippo", "pluto");
        instance.put("masso", "asso");
        HSet entries = instance.entrySet();
        assertEquals("la dimensione del set deve equivalere al numero di entry inserite", 2, entries.size());
        assertEquals("il deve contenere effettivamente le entry", true, entries.contains(instance.new MapEntry("pippo", "pluto")) && entries.contains(instance.new MapEntry("masso", "asso")));
    }
    
    /**
     * @title Test #3 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method when called on a not-empty map which contains duplicate values.
     * @expectedResults The set size is expected to equals to the number of entries previously putted in the map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of methods size and put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method.
     */
    @Test
    public void testEntry_duplicates() {
        instance.put("pippo", "pluto");
        instance.put("masso", "pluto");
        instance.put("basso", "pippo");
        HSet entries = instance.entrySet();
        assertEquals("la dimensione della collezione deve equivalere al numero di entry inserite", 3, entries.size());
    }
    
    /**
     * @title Test #4 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, this test that the new set returned is backed to the map and that, if a structural modification is performed in the set, it is reflected in the map too.
     * @expectedResults The modifications to the returned set must be reflected to the main map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method size, isEmpty, containsValue and put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should be modified by the execution of the method.
     */
    @Test
    public void testEntry_backed() {
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        instance.put("masso", "asso");
        HSet entries = instance.entrySet();
        entries.remove(instance.new MapEntry("pippo", "pluto"));
        assertEquals("modifica apportata al set", 2, entries.size());
        assertEquals("il set riflette la rimozione sulla mappa", 2, instance.size());
        assertEquals("l'elemento rimosso non è più contenuto", false, instance.containsKey("pippo") && instance.containsValue("pluto"));
        HCollection param = new List();
        param.add(instance.new MapEntry("paperino", "topolino"));
        param.add(instance.new MapEntry("pranzo", "cena"));
        entries.removeAll(param);
        assertEquals("modifica apportata al set", 1, entries.size());
        assertEquals("il set riflette la rimozione sulla mappa", 1, instance.size());
        assertEquals("l'elemento rimosso non è più contenuto", false, instance.containsKey("paperino") && instance.containsValue("topolino"));
        entries.retainAll(param);
        assertEquals("modifica apportata al set", true, entries.isEmpty());
        assertEquals("il set riflette la rimozione sulla mappa", true, instance.isEmpty());
        assertEquals("l'elemento rimosso non è più contenuto", false, instance.containsKey("masso") && instance.containsValue("asso"));
    }
    
    /**
     * @title Test #5 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests the iterator of the set of entries returned.
     * @expectedResults The iterator must be capable of iterating all map entries and the modifications performed by the iterator should be reflected to the main map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method size, containsValue and put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should be modified by the execution of the method.
     */
    @Test
    public void testEntry_iterator() {
        HIterator it = instance.entrySet().iterator();
        assertEquals("l'iteratore delle entry di una mappa vuota non deve avere next", false, it.hasNext());
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        instance.put("masso", "asso");
        it = instance.entrySet().iterator();
        int i = 0;
        Object second = null;
        while(it.hasNext()){
            Map.Entry tmp = (Map.Entry) it.next();
            if(i==1) {
                second = tmp;
            }
            assertEquals("l'elemento restituito dall'iteratore è contenuto nella mappa", true, instance.containsKey(tmp.getKey()) && instance.containsValue(tmp.getValue()));
            i++;
        }
        it = instance.entrySet().iterator();
        Map.Entry removed = (Map.Entry) it.next();
        it.remove();
        assertEquals("la collezione riflette la rimozione sulla mappa", 2, instance.size());
        assertEquals("l'elemento rimosso non è più contenuto", false, instance.containsKey(removed.getKey()));
        assertEquals("elemento successivo coerente", second, it.next()); 
    }
    
    /**
     * @title Test #6 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it test hashCode and equals method of the returned set of entries.
     * @expectedResults Two entry set should equals when one of them contains all and only the elements of the other. hashcode and equals should be coherents.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test
    public void testntry_equalsHash() {
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        Map other = new Map();
        other.put("pippo", "pluto");
        other.put("paperino", "topolino");
        assertEquals("dovrebbero equivalere", true, instance.entrySet().equals(other.entrySet()));
        assertEquals("stessi hash", true, instance.entrySet().hashCode()==other.entrySet().hashCode());
        instance.put("asso", "bello");
        assertEquals("non dovrebbero equivalere", false, instance.entrySet().equals(other.entrySet()));
        assertEquals("non dovrebbero avere gli stessi hash", false, instance.entrySet().hashCode()==other.entrySet().hashCode());
    }
    
    /**
     * @title Test #7 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests clear method of the returned set.
     * @expectedResults The clear method should delete all the entries of the map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should be modified by the execution of the method tested.
     */
    @Test
    public void testEntry_clear() {
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        instance.put("asso", "bello");
        HSet entries = instance.entrySet();
        entries.clear();
        assertEquals("la mappa dovrebbe essere vuota", true, instance.isEmpty());
        assertEquals("la collezione deve essere vuota", true, entries.isEmpty());
    }
    
    /**
     * @title Test #8 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests containsAll method of the returned set.
     * @expectedResults The method should correctly verify if a collection of entries is contained or not in the map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test
    public void testEntry_containsAll() {
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        instance.put("asso", "bello");
        HCollection param = new List();
        param.add(instance.new MapEntry("pippo", "pluto"));
        param.add(instance.new MapEntry("paperino", "topolino"));
        HSet entries = instance.entrySet();
        assertEquals("tutte le entry sono contenute", true, entries.containsAll(param));
        param.add(instance.new MapEntry("basso", "razzo"));
        assertEquals("non tutte le entry sono contenuti", false, entries.containsAll(param));
    }
    
    /**
     * @title Test #9 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testEntry_exception1() {
        instance.entrySet().add("pippo");
    }
    
    /**
     * @title Test #10 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testEntry_exception2() {
        instance.entrySet().addAll(new List());
    }
    
    /**
     * @title Test #11 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testEntry_exception3() {
        instance.entrySet().containsAll(null);
    }
    
    /**
     * @title Test #12 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testEntry_exception4() {
        instance.entrySet().contains(null);
    }
    
    /**
     * @title Test #13 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testEntry_exception5() {
        instance.entrySet().remove(null);
    }
    
    /**
     * @title Test #14 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testEntry_exception6() {
        instance.entrySet().removeAll(null);
    }
    
    /**
     * @title Test #15 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testEntry_exception7() {
        instance.entrySet().retainAll(null);
    }
    
    /**
     * @title Test #16 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testEntry_exception8() {
        instance.entrySet().toArray(null);
    }
    
    /**
     * @title Test #17 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NoSuchElementException.class)
    public void testEntry_exception9() {
        instance.entrySet().iterator().next();
    }
    
    /**
     * @title Test #18 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = exceptions.IllegalStateException.class)
    public void testEntry_exception10() {
        instance.entrySet().iterator().remove();
    }
    
    /**
     * @title Test #19 of entrySet method, of class Map.
     * @description This test tests the behaviour of entrySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = exceptions.IllegalStateException.class)
    public void testEntry_exception11() {
        instance.put("pippo", "pluto");
        HIterator it = instance.entrySet().iterator();
        it.next();
        it.remove();
        it.remove();
    }
    
    /**
     * @title Test #1 of equals method, of class Map.
     * @description This test tests the behaviour of equals() method when both the two maps compared are empty
     * @expectedResults Both maps are empty, so they are expected to be equal.
     * @actualResult As expected result.
     * @dependencies  This test doesn't depend on the correctness of any other method of the class.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testEquals_empty() {
        HMap other = new Map();
        boolean result = instance.equals(other);
        assertEquals("due mappe vuote dovrebbero essere equivalenti", true, result);    
    }
    
    /**
     * @title Test #2 of equals method, of class Map.
     * @description This test tests the behaviour of equals() method when one of the two maps compared is empty
     * @expectedResults The maps are expected not to equals, as one of them is empty and the other is not.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but still have to contain the entry added while testing).
     */
    @Test
    public void testEquals_oneIsEmpty() {
        Map other = new Map();
        other.put("pippo", "asso");
        boolean result = instance.equals(other);
        assertEquals("una mappa vuota e l'altra no, non sono equivalenti", false, result);    
    }
    
    /**
     * @title Test #3 of equals method, of class Map.
     * @description This test tests the behaviour of equals() method when the two maps are not equivalents
     * @expectedResults The maps are expected not to equals, as they contain different entries.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but still have to contain the entry added while testing).
     */
    @Test
    public void testEquals_notEquivalents() {
        instance.put("pluto", "beta");
        Map other = new Map();
        other.put("pippo", "asso");
        boolean result = instance.equals(other);
        assertEquals("mappe non vuote e non equivalenti tra loro", false, result);    
    }
    
    /**
     * @title Test #4 of equals method, of class Map.
     * @description This test tests the behaviour of equals() method when the two maps are equivalents
     * @expectedResults The maps are expected to equals, as they contain the same entries.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but still have to contain the entries added while testing).
     */
    @Test
    public void testEquals_equivalents() {
        instance.put("pippo", "asso");
        instance.put("paperino", "masso");
        Map other = new Map();
        other.put("pippo", "asso");
        other.put("paperino", "masso");
        boolean result = instance.equals(other);
        assertEquals("mappe non vuote ed equivalenti tra loro", true, result);    
    }
    
    /**
     * @title Test #5 of equals method, of class Map.
     * @description This test tests the behaviour of equals() method when the two contains the same set of keys, but different values
     * @expectedResults The maps are expected not to equals, as they contain the same the same key set, but with different values associated.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but still have to contain the entry added while testing).
     */
    @Test
    public void testEquals_sameKeys() {
        instance.put("pippo", "beta");
        Map other = new Map();
        other.put("pippo", "asso");
        boolean result = instance.equals(other);
        assertEquals("mappe aventi lo stesso set di chiavi, ma values differenti", false, result);    
    }
    
    /**
     * @title Test #6 of equals method, of class Map.
     * @description This test tests the behaviour of equals() method when the two contains the same set of values, but different keys
     * @expectedResults The maps are expected not to equals, as they contain the same the same values set, but with different keys associated.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but still have to contain the entry added while testing).
     */
    @Test
    public void testEquals_sameValues() {
        instance.put("pluto", "asso");
        Map other = new Map();
        other.put("pippo", "asso");
        boolean result = instance.equals(other);
        assertEquals("mappe aventi lo stesso set di values, ma keys differenti", false, result);    
    }

    /**
     * @title Test #1 of get method, of class Map.
     * @description This test tests the behaviour of get() method when is called on an empty Map
     * @expectedResults The map is expected to return a null reference, which is the return value used when the specified key isn't contained in the map (which is empty).
     * @actualResult As expected result.
     * @dependencies  The correctness of this test doesn't depends on any other method of class Map.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testGet_empty() {
        Object result = instance.get("pippo");
        assertEquals("metodo invocato su di una mappa vuota, null expected", null, result);  
    }
    
    /**
     * @title Test #2 of get method, of class Map.
     * @description This test tests the behaviour of get() method when is called using a not contained key as parameter
     * @expectedResults The map is expected to return a null reference, which is the return value used when the specified key isn't contained in the map.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but still have to contain the entry added while testing).
     */
    @Test
    public void testGet_notContained() {
        instance.put("pippo", "asso");
        Object result = instance.get("pluto");
        assertEquals("metodo invocato utilizzando come parametro una chiave non presente", null, result);  
    }
    
    /**
     * @title Test #3 of get method, of class Map.
     * @description This test tests the behaviour of get() method when is called using a contained key as parameter
     * @expectedResults The map is expected to return the value associated with the specified key in the map.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but still have to contain the entry added while testing).
     */
    @Test
    public void testGet_contained() {
        instance.put("pippo", "asso");
        Object result = instance.get("pippo");
        assertEquals("metodo invocato utilizzando come parametro una chiave presente", "asso", result);  
    }
    
    /**
     * @title Test #4 of get method, of class Map.
     * @description This test tests the behaviour of get() method when is called using a key which is not contained, but has the same hash of one of the key contained
     * @expectedResults The map is expected to return a null reference, as the specified key has the same hash of a key contained in the map, but the comparison isn't performed just with an hash comparison.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but still have to contain the entry added while testing).
     */
    @Test
    public void testGet_hash() {
        instance.put("AaAaAa", "asso");
        Object result = instance.get("AaAaBB");
        assertEquals("test utilizzo hash nel confronto della chiave", null, result);  
    }
    
    /**
     * @title Test #5 of get method, of class Map.
     * @description This test tests the behaviour of get() method when is called on a Map using as parameter a null reference (NullPointerException expected)
     * @expectedResults The map is expected to throw a NullPointerException when a null reference is used as parameter.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testGet_exceptions() {
        instance.get(null);
    }
    
    /**
     * @title Test of get and containsKey method, of class Map.
     * @description This test tests the coherence between the methods containsKey() and get(). In fact, this implementation of the map doesn't accept null elements, so a null value is returned only when the key is not contained in the map
     * @expectedResults A coherent behaviour between containsKey() and get() methods is expected.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but still have to contain the entry added while testing).
     */
    @Test
    public void testGet_containsKey() {
        instance.put("pippo", "asso");
        boolean result = instance.get("pluto") == null && !instance.containsKey("pluto");
        assertEquals("controllo coerenza tra metodi get e containsKey, chiave non contenuta", true, result);  
        result = instance.get("pippo") == null && !instance.containsKey("pippo");
        assertEquals("controllo coerenza tra metodi get e containsKey, chiave contenuta", false, result);
    } 
        
    /**
     * @title Test #1 of hashCode method, of class Map.
     * @description This test tests the behaviour of hashCode() method when is called on an empty map.
     * @expectedResults The result hash is expected to be 0, as is definide as the sum of all the map entries hashcode, and the map is empty.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods of the class Map.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testHashCode_empty() {
        int result = instance.hashCode();
        assertEquals("hash di una mappa vuota deve essere zero", 0, result);        
    }
    
    /**
     * @title Test #2 of hashCode method, of class Map.
     * @description This test tests the behaviour of hashCode() method when is called on a not-empty map.
     * @expectedResults The result hash is expected to the sum of all the map entries hashcode, as it defined in this way.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of the method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but the map still have to contain the entry inserted during the testing process).
     */
    @Test
    public void testHashCode_notEmpty() {
        String val1 = "pippo", key1 = "asso", val2 = "pluto", key2 = "topolino";
        instance.put(key1, val1);
        instance.put(key2, val2);
        int expected = (key1.hashCode()^val1.hashCode()) + (key2.hashCode()^val2.hashCode());
        int result = instance.hashCode();
        assertEquals("hash di una mappa piena deve essere uguale alla somma degli hash delle sue entry", expected, result);        
    }

    /**
     * @title Test #1 of isEmpty method, of class Map.
     * @description This test tests the behaviour of the method isEmpty() when called on an empty Map
     * @expectedResults A just instancied map is expected to be empty.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method of Map.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testIsEmpty_empty() {
        boolean result = instance.isEmpty();
        assertEquals("controllo su collezione vuota", true, result); 
    }
    
    /**
     * @title Test #2 of isEmpty method, of class Map.
     * @description This test tests the behaviour of the method isEmpty() when called on an empty Map.
     * @expectedResults An element was added to the map, the map should not be empty.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested (but still have to contain the entry added while testing).
     */
    @Test
    public void testIsEmpty_notEmpty() {
        instance.put("pippo", "asso");
        boolean result = instance.isEmpty();
        assertEquals("controllo su collezione non vuota", false, result); 
    }
    
    /**
     * @title Test #3 of isEmpty method, of class Map.
     * @description This test tests the coherence between methods size() and isEmpty().
     * @expectedResults A coherent behaviour is expected between size() and isEmpty() methods.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testIsEmpty_size() {
        boolean size = (instance.size() == 0);
        boolean isEmpty = instance.isEmpty();
        assertEquals("i metodi devono essere coerenti quando la collezione è vuota", size, isEmpty);
        instance.put("pippo", "pluto");
        size = (instance.size() == 0);
        isEmpty = instance.isEmpty();
        assertEquals("i metodi devono essere coerenti quando la collezione è piena", size, isEmpty);
    }
    
    /**
     * @title Test #1 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method when called on an empty map.
     * @expectedResults The set is expected to be empty.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method isEmpty.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method.
     */
    @Test
    public void testKeys_empty() {
        HSet keys = instance.keySet();
        assertEquals("una mappa vuota non ha chiavi", true, keys.isEmpty());
    }
    
    /**
     * @title Test #2 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method when called on a not-empty map.
     * @expectedResults The set size is expected to equals to the number of entries previously putted in the map. The set is also expected to contain all the keys contained by the map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of methods put, isEmpty and contains.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method.
     */
    @Test
    public void testKeys_notEmpty() {
        instance.put("pippo", "pluto");
        instance.put("masso", "asso");
        HSet keys = instance.keySet();
        assertEquals("la dimensione del set deve equivalere al numero di entry inserite", 2, keys.size());
        assertEquals("il deve contenere effettivamente le chiavi", true, keys.contains("pippo")&&keys.contains("masso"));
    }
    
    /**
     * @title Test #3 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method when called on a not-empty map which contains duplicate values.
     * @expectedResults The set size is expected to equals to the number of entries previously putted in the map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of methods size and put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method.
     */
    @Test
    public void testKeys_duplicates() {
        instance.put("pippo", "pluto");
        instance.put("masso", "pluto");
        instance.put("basso", "pippo");
        HSet keys = instance.keySet();
        assertEquals("la dimensione della collezione deve equivalere al numero di entry inserite", 3, keys.size());
    }
    
    /**
     * @title Test #4 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, this test that the new set returned is backed to the map and that, if a structural modification is performed in the set, it is reflected in the map too.
     * @expectedResults The iterator must be able to iterate all the map, and the changes performed by the iterator must be reflected to the main map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method size, isEmpty, containsValue and put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should be modified by the execution of the method.
     */
    @Test
    public void testKeys_backed() {
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        instance.put("masso", "asso");
        HSet keys = instance.keySet();
        keys.remove("pippo");
        assertEquals("modifica apportata al set", 2, keys.size());
        assertEquals("il set riflette la rimozione sulla mappa", 2, instance.size());
        assertEquals("l'elemento rimosso non è più contenuto", false, instance.containsValue("pippo"));
        HCollection param = new List();
        param.add("topolino");
        param.add("paperino");
        keys.removeAll(param);
        assertEquals("modifica apportata al set", 1, keys.size());
        assertEquals("il set riflette la rimozione sulla mappa", 1, instance.size());
        assertEquals("l'elemento rimosso non è più contenuto", false, instance.containsValue("paperino"));
        keys.retainAll(param);
        assertEquals("modifica apportata al set", true, keys.isEmpty());
        assertEquals("il set riflette la rimozione sulla mappa", true, instance.isEmpty());
        assertEquals("l'elemento rimosso non è più contenuto", false, instance.containsValue("masso"));
    }
    
    /**
     * @title Test #5 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the iterator of the set of keys returned.
     * @expectedResults The iterator must be capable of iterating all map keys and the modifications performed by the iterator should be reflected to the main map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method size, containsValue and put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should be modified by the execution of the method.
     */
    @Test
    public void testKeys_iterator() {
        HIterator it = instance.keySet().iterator();
        assertEquals("l'iteratore delle chiavi di una mappa vuota non deve avere next", false, it.hasNext());
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        instance.put("masso", "asso");
        it = instance.keySet().iterator();
        int i = 0;
        Object second = null;
        while(it.hasNext()){
            if(i==1) {
                second = it.next();
                assertEquals("l'elemento restituito dall'iteratore è contenuto nella mappa", true, instance.containsKey(second));
            } else {
                assertEquals("l'elemento restituito dall'iteratore è contenuto nella mappa", true, instance.containsKey(it.next()));
            }
            i++;
            
        }
        it = instance.keySet().iterator();
        Object removed = it.next();
        it.remove();
        assertEquals("la collezione riflette la rimozione sulla mappa", 2, instance.size());
        assertEquals("l'elemento rimosso non è più contenuto", false, instance.containsKey(removed));
        assertEquals("elemento successivo coerente", second, it.next()); 
    }
    
    /**
     * @title Test #6 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it test hashCode and equals method of the returned collection.
     * @expectedResults Two values set should equals when one of them contains all the elements of the other. hashcode and equals should be coherents.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test
    public void testKeys_equalsHash() {
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        Map other = new Map();
        other.put("pippo", "bufu");
        other.put("paperino", "topolino");
        assertEquals("dovrebbero equivalere", true, instance.keySet().equals(other.keySet()));
        assertEquals("stessi hash", true, instance.keySet().hashCode()==other.keySet().hashCode());
        instance.put("asso", "bello");
        assertEquals("non dovrebbero equivalere", false, instance.keySet().equals(other.keySet()));
        assertEquals("non dovrebbero avere gli stessi hash", false, instance.keySet().hashCode()==other.keySet().hashCode());
    }
    
    /**
     * @title Test #7 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests clear method of the returned set.
     * @expectedResults The clear method should delete all the entries of the map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should be modified by the execution of the method tested.
     */
    @Test
    public void testKeys_clear() {
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        instance.put("asso", "bello");
        HSet keys = instance.keySet();
        keys.clear();
        assertEquals("la mappa dovrebbe essere vuota", true, instance.isEmpty());
        assertEquals("la collezione deve essere vuota", true, keys.isEmpty());
    }
    
    /**
     * @title Test #8 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests containsAll method of the returned set.
     * @expectedResults The method should correctly verify if a collection of keys is contained or not in the map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test
    public void testKeys_containsAll() {
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        instance.put("asso", "bello");
        HCollection param = new List();
        param.add("pippo");
        param.add("masso");
        HSet keys = instance.keySet();
        assertEquals("non tutte le chiavi sono contenuti", false, keys.containsAll(param));
        param.remove("masso");
        param.add("asso");
        assertEquals("tutte le chiave sono contenuti", true, keys.containsAll(param));
    }
    
    /**
     * @title Test #9 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testKeys_exception1() {
        instance.keySet().add("pippo");
    }
    
    /**
     * @title Test #10 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testKeys_exception2() {
        instance.keySet().addAll(new List());
    }
    
    /**
     * @title Test #11 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testKeys_exception3() {
        instance.keySet().containsAll(null);
    }
    
    /**
     * @title Test #12 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testKeys_exception4() {
        instance.keySet().contains(null);
    }
    
    /**
     * @title Test #13 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testKeys_exception5() {
        instance.keySet().remove(null);
    }
    
    /**
     * @title Test #14 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testKeys_exception6() {
        instance.keySet().removeAll(null);
    }
    
    /**
     * @title Test #15 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testKeys_exception7() {
        instance.keySet().retainAll(null);
    }
    
    /**
     * @title Test #16 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testKeys_exception8() {
        instance.keySet().toArray(null);
    }
    
    /**
     * @title Test #17 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NoSuchElementException.class)
    public void testKeys_exception9() {
        instance.keySet().iterator().next();
    }
    
    /**
     * @title Test #18 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = exceptions.IllegalStateException.class)
    public void testKeys_exception10() {
        instance.keySet().iterator().remove();
    }
    
    /**
     * @title Test #19 of keySet method, of class Map.
     * @description This test tests the behaviour of keySet() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = exceptions.IllegalStateException.class)
    public void testKeys_exception11() {
        instance.put("pippo", "pluto");
        HIterator it = instance.keySet().iterator();
        it.next();
        it.remove();
        it.remove();
    }

    /**
     * @title Test #1 of put method, of class Map.
     * @description This test tests the behaviour of method put() when is called on an empty Map.
     * @expectedResults The map is expected to accept the new entry (as it is empty) and to return a null reference.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method isEmpty().
     * @preConditions The set instance must be a new istance of Map.
     * @postConditions The set instance should be modified by the call of the tested method (new entries added).
     */
    @Test
    public void testPut_empty() {
        Object result = instance.put("pippo", "pluto");
        assertEquals("aggiunta a mappa vuota", null, result);
        assertEquals("dimensione mappa cambiata", false, instance.isEmpty());
    }
    
    /**
     * @title Test #2 of put method, of class Map.
     * @description This test tests the behaviour of method put() when is called on a not-empty Map, but the entry is not contained in the map.
     * @expectedResults The map is expected to accept the new entry and to return a null reference.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method size().
     * @preConditions The set instance must be a new istance of Map.
     * @postConditions The set instance should be modified by the call of the tested method (new entries added).
     */
    @Test
    public void testPut_notContained() {
        instance.put("paperino", "asso");
        Object result = instance.put("pippo", "pluto");
        assertEquals("aggiunta di entry non presente", null, result);
        assertEquals("dimensione mappa cambiata", 2, instance.size());
        
    }
    
    /**
     * @title Test #3 of put method, of class Map.
     * @description This test tests the behaviour of method put() when is called on a not-empty Map and the entry is already contained in the map.
     * @expectedResults The map is expected to update the old value associated with that key and to update it with the new one specified.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods size() and get().
     * @preConditions The set instance must be a new istance of Map.
     * @postConditions The set instance should be modified by the call of the tested method (values modified).
     */
    @Test
    public void testPut_contained() {
        instance.put("pippo", "asso");
        Object result = instance.put("pippo", "pluto");
        assertEquals("aggiunta di entry con chiave già presente", "asso", result);
        assertEquals("dimensione mappa non cambiata", 1, instance.size());
        assertEquals("valore aggiornato", "pluto", instance.get("pippo"));
    }
    
    /**
     * @title Test #4 of put method, of class Map.
     * @description This test tests the behaviour of method put() when is called on a not-empty Map and the map contains a key with the same hash of the key specified, but a differente value.
     * @expectedResults The map is expected to add the new entry, as the key comparison isn't based only on hash comparison.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method size().
     * @preConditions The set instance must be a new istance of Map.
     * @postConditions The set instance should be modified by the call of the tested method (values modified).
     */
    @Test
    public void testPut_hash() {
        instance.put("AaAaAa", "asso");
        Object result = instance.put("AaAaBB", "pluto");
        assertEquals("aggiunta di entry con chiave che ha hash uguale ad una già presente", null, result);
        assertEquals("dimensione mappa cambiata", 2, instance.size());
    }
    
    /**
     * @title Test #5 of put method, of class Map.
     * @description This test tests the behaviour of put() method when is called on a Map using as parameter a null reference (NullPointerException expected)
     * @expectedResults The map is expected to throw a NullPointerException.
     * @actualResult As expected result.
     * @dependencies  This test doesn't depend on the correctness of any other method of the class.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testPut_exception1() {
        instance.put(null,"pippo");
    }
    
    /**
     * @title Test #6 of put method, of class Map.
     * @description This test tests the behaviour of put() method when is called on a Map using as parameter a null reference (NullPointerException expected)
     * @expectedResults The map is expected to throw a NullPointerException.
     * @actualResult As expected result.
     * @dependencies  This test doesn't depend on the correctness of any other method of the class.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testPut_exception2() {
        instance.put("pippo", null);
    }
    
    /**
     * @title Test #7 of put method, of class Map.
     * @description This test tests the behaviour of put() method when is called on a Map using as parameter a null reference (NullPointerException expected)
     * @expectedResults The map is expected to throw a NullPointerException.
     * @actualResult As expected result.
     * @dependencies  This test doesn't depend on the correctness of any other method of the class.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance doesn't have to be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testPut_exception3() {
        instance.put(null, null);
    }
    
    /**
     * @title Test of put and remove methods, of class Map.
     * @description This test tests the behaviour of class Map when multiple put/remove operations are performed in sequence.
     * @expectedResults The map is expected to perform all the put/remove operations successfully.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of methods size() and isEmpty().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance has to be modified by the operations perfomed on it.
     */
    @Test
    public void testPut_Remove() {
        instance.put("pippo", "pluto");
        assertEquals("inserito il primo elemento", 1, instance.size());
        instance.put("pippo", "pluto");
        instance.put("asso", "topolino");    
        assertEquals("inserimento di due elementi, di cui uno già presente", 2, instance.size());
        instance.remove("pippo");
        assertEquals("rimozione di un elemento", 1, instance.size());
        instance.put("pippo", "pluto");
        assertEquals("inserimento di un elemento appena eliminato", 2, instance.size());
        instance.remove("pippo");
        instance.remove("asso");
        assertEquals("inserimento di tutti gli elementi della mappa", true, instance.isEmpty());
    }

    /**
     * @title Test #1 of putAll method, of class Map.
     * @description This test tests the behaviour of class Map when putAll() method is called using an empty map as parameter
     * @expectedResults The map shouldn't do anything.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of method isEmpty
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance shouldn't be modified by the operations perfomed on it.
     */
    @Test
    public void testPutAll_empty() {
        instance.putAll(instance);
        assertEquals("la mappa deve rimanere inalterata", true, instance.isEmpty());
    }
    
    /**
     * @title Test #2 of putAll method, of class Map.
     * @description This test tests the behaviour of class Map when putAll() method is called using a map which contains only entries which are not contained in this map.
     * @expectedResults The map must add all the elements of the other map.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of methods size() and put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance has to be modified by the operations perfomed on it.
     */
    @Test
    public void testPutAll_notContained() {
        Map other = new Map();
        other.put("pippo", "asso");
        other.put("pluto", "topolino");
        other.put("paperino", "pluto");
        instance.putAll(other);
        assertEquals("la mappa deve aggiungere tutti gli elementi dell'altra mappa", 3, instance.size());
    }
    
    /**
     * @title Test #3 of putAll method, of class Map.
     * @description This test tests the behaviour of class Map when putAll() method is called using a map which contains a miscellaneous of elements that could be problematic.
     * @expectedResults The map must add just some of the entries of the other map.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of methods size() and put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance has to be modified by the operations perfomed on it.
     */
    @Test
    public void testPutAll_miscellaneous() {
        instance.put("AaAaAa", "asso");
        instance.put("pippo", "pluto");
        Map other = new Map();
        other.put("pippo", "asso");
        other.put("pluto", "topolino");
        other.put("paperino", "pluto");
        other.put("AaAaBB", "paperino");
        instance.putAll(other);
        assertEquals("la mappa deve aggiungere 3 soli elementi su 4", 5, instance.size());
    }
    
    /**
     * @title Test #4 of putAll method, of class Map.
     * @description This test tests the behaviour of class Map when putAll() method is called using a null reference as parameter. The result of an invocation of the method using as parameter a map which contains null elements cannot be tested, as we don't have Map implementations which support null elements.
     * @expectedResults The map must should throw a NullPointerException.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance has to be modified by the operations perfomed on it.
     */
    @Test(expected = NullPointerException.class)
    public void testPutAll_exceptions() {
        instance.putAll(null);
    }

    /**
     * @title Test #1 of remove method, of class Map.
     * @description This test tests the behaviour of class Map when remove() method is called on an empty map.
     * @expectedResults The map is expected to return a null reference, as the element wasn't contained in the map (which is empty).
     * @actualResult As expected result.
     * @dependencies  This test doesn't depend on the correctness of any other method of the class.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance is not modified as result of the test of the method.
     */
    @Test
    public void testRemove_empty() {
        Object result = instance.remove("pippo");
        assertEquals("rimozione su lista vuota", null, result);
    }
    
    /**
     * @title Test #2 of remove method, of class Map.
     * @description This test tests the behaviour of class Map when remove() method is called on a map which not contains the specified key.
     * @expectedResults The map is expected to return a null reference, as the element wasn't contained in the map.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of the methods put() and size().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance is not modified as direct result of the test of the method (but the map still has to contain the value inserted during the test).
     */
    @Test
    public void testRemove_notContained() {
        instance.put("pluto", "asso");
        Object result = instance.remove("pippo");
        assertEquals("rimozione di un elemento non presente", null, result);
        assertEquals("dimensione costante", 1, instance.size());
    }
    
    /**
     * @title Test #3 of remove method, of class Map.
     * @description This test tests the behaviour of class Map when remove() method is called on a map which contains the specified key.
     * @expectedResults The map is expected to return the previous value which was mapped with the key specified. The size of the map has to decrease.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of the methods put() and isEmpty().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance is modified as direct result of the test of the method.
     */
    @Test
    public void testRemove_contained() {
        instance.put("pluto", "asso");
        Object result = instance.remove("pluto");
        assertEquals("rimozione di un elemento presente", "asso", result);
        assertEquals("dimensione diminuita", true, instance.isEmpty());
    }
    
    /**
     * @title Test #4 of remove method, of class Map.
     * @description This test tests the behaviour of class Map when remove() method is called on a map using a key which has the same hash of a key contained, but is not the same object.
     * @expectedResults The map is expected to return a null reference, as the key is not really contained in the map.
     * @actualResult As expected result.
     * @dependencies  Depends on the correctness of the methods put() and size().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance is not modified as direct result of the test of the method.
     */
    @Test
    public void testRemove_hash() {
        instance.put("AaAaAa", "asso");
        Object result = instance.remove("AaAaBB");
        assertEquals("rimozione di una chiave con hash uguale ad una chiave presente", null, result);
    }
    
    /**
     * @title Test #5 of remove method, of class Map.
     * @description This test tests the behaviour of class Map when remove() method is called using a null reference as parameter.
     * @expectedResults The map is expected to throw a NullPointerException.
     * @actualResult As expected result.
     * @dependencies  The correctness of this method does not depends on the correctness of any other method of Map.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance is not modified as direct result of the test of the method.
     */
    @Test(expected = NullPointerException.class)
    public void testRemove_exceptions() {
        instance.remove(null);
    }

    /**
     * @title Test #1 of size method, of class Map.
     * @description This test tests the behaviour of size() method when called on an empty map.
     * @expectedResults The size is expected to be zero, as the map is empty.
     * @actualResult As expected result.
     * @dependencies This test has no dependencies on other class methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method.
     */
    @Test
    public void testSize_empty() {
        int result = instance.size();
        assertEquals("la dimensione di una mappa appena creata è 0", 0, result);
    }
    
    /**
     * @title Test #2 of size method, of class Map.
     * @description This test tests the behaviour of size() method when called on a non-empty map.
     * @expectedResults The size is expected to be equal to the number of elements that have been added to the map.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method put().
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method (but still have to contain the entry added while testing).
     */
    @Test
    public void testSize_notEmpty() {
        instance.put("pippo", "asso");
        int result = instance.size();
        assertEquals("la dimensione corrisponde al numero di entry aggiunte", 1, result);
    }    

    /**
     * @title Test #1 of values method, of class Map.
     * @description This test tests the behaviour of values() method when called on an empty map.
     * @expectedResults The collection is expected to be empty.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method isEmpty.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method.
     */
    @Test
    public void testValues_empty() {
        HCollection values = instance.values();
        assertEquals("una mappa vuota non ha valori", true, values.isEmpty());
    }
    
    /**
     * @title Test #2 of values method, of class Map.
     * @description This test tests the behaviour of values() method when called on a not-empty map.
     * @expectedResults The collection size is expected to equals to the number of entries previously putted in the map. The collection is also expected to contain all the values contained by the map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of methods put, isEmpty and contains.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method.
     */
    @Test
    public void testValues_notEmpty() {
        instance.put("pippo", "pluto");
        instance.put("masso", "asso");
        HCollection values = instance.values();
        assertEquals("la dimensione della collezione deve equivalere al numero di entry inserite", 2, values.size());
        assertEquals("la collezione deve contenere effettivamente i valori", true, values.contains("pluto")&&values.contains("asso"));
    }
    
    /**
     * @title Test #3 of values method, of class Map.
     * @description This test tests the behaviour of values() method when called on a not-empty map which contains duplicate values.
     * @expectedResults The collection size is expected to equals to the number of entries previously putted in the map (duplicates must be contained twice in the resulting collection).
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of methods size and put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method.
     */
    @Test
    public void testValues_duplicates() {
        instance.put("pippo", "pluto");
        instance.put("masso", "pluto");
        instance.put("basso", "pippo");
        HCollection values = instance.values();
        assertEquals("la dimensione della collezione deve equivalere al numero di entry inserite", 3, values.size());
    }
    
    /**
     * @title Test #4 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, this test that the new collection is backed to the map and that, if a structural modification is performed in the collection, it is reflected in the map too.
     * @expectedResults The iterator must be able to iterate all the map, and the changes performed by the iterator must be reflected to the main map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method size, isEmpty, containsValue and put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should be modified by the execution of the method.
     */
    @Test
    public void testValues_backed() {
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        instance.put("masso", "asso");
        HCollection values = instance.values();
        values.remove("pluto");
        assertEquals("la collezione riflette la rimozione sulla mappa", 2, instance.size());
        assertEquals("l'elemento rimosso non è più contenuto", false, instance.containsValue("pluto"));
        HCollection param = new List();
        param.add("topolino");
        param.add("pippo");
        values.removeAll(param);
        assertEquals("la collezione riflette la rimozione sulla mappa", 1, instance.size());
        assertEquals("l'elemento rimosso non è più contenuto", false, instance.containsValue("topolino"));
        values.retainAll(param);
        assertEquals("la collezione riflette la rimozione sulla mappa", true, instance.isEmpty());
        assertEquals("l'elemento rimosso non è più contenuto", false, instance.containsValue("asso"));
    }
    
    /**
     * @title Test #5 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests the iterator of the collection of values returned.
     * @expectedResults The iterator must be capable of iterating all map values and the modifications performed by the iterator should be reflected to the main list.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method size, containsValue and put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should be modified by the execution of the method.
     */
    @Test
    public void testValues_iterator() {
        HIterator it = instance.values().iterator();
        assertEquals("l'iteratore dei valori di una mappa vuota non deve avere next", false, it.hasNext());
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        instance.put("masso", "asso");
        it = instance.values().iterator();
        int i = 0;
        Object second = null;
        while(it.hasNext()){
            if(i==1) {
                second = it.next();
                assertEquals("l'elemento restituito dall'iteratore è contenuto nella mappa", true, instance.containsValue(second));
            } else {
                assertEquals("l'elemento restituito dall'iteratore è contenuto nella mappa", true, instance.containsValue(it.next()));
            }
            i++;
            
        }
        it = instance.values().iterator();
        Object removed = it.next();
        it.remove();
        assertEquals("la collezione riflette la rimozione sulla mappa", 2, instance.size());
        assertEquals("l'elemento rimosso non è più contenuto", false, instance.containsValue(removed));
        assertEquals("elemento successivo coerente", second, it.next()); 
    }
    
    /**
     * @title Test #6 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it test hashCode and equals method of the returned collection.
     * @expectedResults Two values set should equals when one of them contains all the elements of the other. hashcode and equals should be coherents.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test
    public void testValues_equalsHash() {
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        Map other = new Map();
        other.put("argo", "pluto");
        other.put("paperino", "topolino");
        assertEquals("dovrebbero equivalere", true, instance.values().equals(other.values()));
        assertEquals("stessi hash", true, instance.values().hashCode()==other.values().hashCode());
        instance.put("asso", "bello");
        assertEquals("non dovrebbero equivalere", false, instance.values().equals(other.values()));
        assertEquals("non dovrebbero avere gli stessi hash", false, instance.values().hashCode()==other.values().hashCode());
    }
    
    /**
     * @title Test #7 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests clear method of the returned collection.
     * @expectedResults The clear method should delete all the entries of the map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should be modified by the execution of the method tested.
     */
    @Test
    public void testValues_clear() {
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        instance.put("asso", "bello");
        HCollection values = instance.values();
        values.clear();
        assertEquals("la mappa dovrebbe essere vuota", true, instance.isEmpty());
        assertEquals("la collezione deve essere vuota", true, values.isEmpty());
    }
    
    /**
     * @title Test #8 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests containsAll method of the returned collection.
     * @expectedResults The method should correctly verify if a collection of values is contained or not in the map.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method put.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test
    public void testValues_containsAll() {
        instance.put("pippo", "pluto");
        instance.put("paperino", "topolino");
        instance.put("asso", "bello");
        HCollection param = new List();
        param.add("pluto");
        param.add("masso");
        HCollection values = instance.values();
        assertEquals("non tutti i valori sono contenuti", false, values.containsAll(param));
        param.remove("masso");
        param.add("topolino");
        assertEquals("tutti i valori sono contenuti", true, values.containsAll(param));
    }
    
    /**
     * @title Test #9 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testValues_exception1() {
        instance.values().add("pippo");
    }
    
    /**
     * @title Test #10 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testValues_exception2() {
        instance.values().addAll(new List());
    }
    
    /**
     * @title Test #11 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testValues_exception3() {
        instance.values().containsAll(null);
    }
    
    /**
     * @title Test #12 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testValues_exception4() {
        instance.values().contains(null);
    }
    
    /**
     * @title Test #13 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testValues_exception5() {
        instance.values().remove(null);
    }
    
    /**
     * @title Test #14 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testValues_exception6() {
        instance.values().removeAll(null);
    }
    
    /**
     * @title Test #15 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testValues_exception7() {
        instance.values().retainAll(null);
    }
    
    /**
     * @title Test #16 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testValues_exception8() {
        instance.values().toArray(null);
    }
    
    /**
     * @title Test #17 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = NoSuchElementException.class)
    public void testValues_exception9() {
        instance.values().iterator().next();
    }
    
    /**
     * @title Test #18 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = exceptions.IllegalStateException.class)
    public void testValues_exception10() {
        instance.values().iterator().remove();
    }
    
    /**
     * @title Test #19 of values method, of class Map.
     * @description This test tests the behaviour of values() method. More in details, it tests the returned set throws the right exceptions when expected.
     * @expectedResults The method should correctly throw all the exceptions documented.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The map instance must be a new istance of Map.
     * @postConditions The map instance should not be modified by the execution of the method tested.
     */
    @Test(expected = exceptions.IllegalStateException.class)
    public void testValues_exception11() {
        instance.put("pippo", "pluto");
        HIterator it = instance.values().iterator();
        it.next();
        it.remove();
        it.remove();
    }
    
}