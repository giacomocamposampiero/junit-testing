package tester;

import adapters.List;
import adapters.Set;
import interfaces.HCollection;
import interfaces.HSet;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

/**
 * Test suite for Set class.
 * This suite contains a sequence of tests which check the specific behaviuour of a Set. 
 * More general behaviours (inherited by Collection interface) are tested in Collection test suite.
 * @author Giacomo Camposampiero
 */
@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SetTest {

    private final String paramClass;
    private HSet instance;

    public SetTest(String paramClass) {
        this.paramClass = paramClass;
    }
    
    @Parameterized.Parameters(name="{0}")
    public static Collection classesToTest() {
        return Arrays.asList(new Object[][]{
            {"Set"}
        });
    }
    
    @Before
    public void initialize() {
        try {
            instance = (HSet) Class.forName("adapters."+paramClass).getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | SecurityException  ex) {
            System.exit(1);
        }
    }  

    /**
     * @title Test #1 of add method, of class Set.
     * @description This test tests the behaviour of the method add() when called on a not-empty set. More in details, it only tests that an element which is already contained in the list is not added again.
     * @expectedResults The add method is expected to add only elements which aren't contained in the set yet.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method size().
     * @preConditions The set instance must be a new istance of Set.
     * @postConditions The set instance is directly modified by the execution of the method tested.
     */
    @Test
    public void testAdd_duplicates() {
        boolean result = instance.add("pippo");
        assertEquals("elemento non presente precedentemente, inserito", true, result);
        assertEquals("dimensione aumentata", 1, instance.size());
        result = instance.add("pippo");
        assertEquals("elemento già contenuto nel set, non inserito", false, result);
        assertEquals("dimensione inalterata", 1, instance.size());
    }
    
    /**
     * @title Test #1 of addAll method, of class Set.
     * @description This test tests the behaviour of the method addAll() when called using a collection which contains duplicates.
     * @expectedResults The duplicates elementes should be added just once in the collections, as duplicates are not allowed in the set.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method size() and add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance has to be modified by the execution of the method tested.
     */
    @Test
    public void testAddAll_duplicates() {
        HCollection param = new List();
        param.add("pippo");
        param.add("pippo");
        param.add("pluto");
        boolean result = instance.addAll(param);
        assertEquals("ci sono elementi nuovi, vengono aggiunti e lo stato della lista cambia", true, result);
        assertEquals("la dimensione della collezione deve variare, ma i doppioni sono aggiunti una volta sola", 2, instance.size());
    }
    
    /**
     * @title Test #2 of addAll method, of class Set.
     * @description This test tests the behaviour of the method addAll() when called using a collection which contains elements which are already contained in the set.
     * @expectedResults The elementes which are already contained should be added just once in the collections, as duplicates are not allowed in the set.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of methods size() and add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance has to be modified by the execution of the method tested.
     */
    @Test
    public void testAddAll_contained() {
        instance.add("pippo");
        HCollection param = new List();
        param.add("pippo");
        param.add("pluto");
        boolean result = instance.addAll(param);
        assertEquals("ci sono elementi nuovi, vengono aggiunti e lo stato della lista cambia", true, result);
        assertEquals("la dimensione della collezione deve variare, ma gli elementi già presenti non sono aggiunti di nuovo", 2, instance.size());
    }

    /**
     * @title Test #1 of contains method, of class Set.
     * @description This test tests the behaviour of the method contains() when called using a parameter which is not contained in the set, but has the same hash of an element contained.
     * @expectedResults The comparison is not performed only on the hash of the objects, return value should be false.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method add().
     * @preConditions The set instance must be a new istance of Set.
     * @postConditions The set instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test
    public void testContains_hash() {
        instance.add("AaAaBB");
        boolean result = instance.contains("AaAaAa");
        assertEquals("parametro non contenuto ma con stesso hash di uno contenuto", false, result);
    }
    
    /**
     * @title Test #1 of containsAll method, of class Set.
     * @description This test tests the behaviour of the method containsAll() when is called using as parameter a set which contains elements whith same hash of an element of this set, but which is not contained.
     * @expectedResults The result should be false, as this set doesn't contains all param elements (comparison by equals, not hash).
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of add().
     * @preConditions The set instance must be a new istance of Set.
     * @postConditions The set instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testContainsAll_emptySet() {
        instance.add("AaAaBB");
        instance.add("pippo");
        Set param = new Set();
        param.add("AaAaAa");
        param.add("pippo");
        boolean result = instance.containsAll(param);
        assertEquals("test confronto hash o equals", false, result);
    }
    
    /**
     * @title Test #1 of equals method, of class Set.
     * @description This test tests the behaviour of the method equals() when two empty sets are compared
     * @expectedResults Two empty sets should equals.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method.
     * @preConditions The set instance must be a new istance of Set.
     * @postConditions The set instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test
    public void testEquals_empty() {
        Set instance2 = new Set();
        boolean result = instance.equals(instance2);
        assertEquals("confronto di due set vuoti", true, result);
    }
    
    /**
     * @title Test #2 of equals method, of class Set.
     * @description This test tests the behaviour of the method equals() when the two sets don't contains the same elements.
     * @expectedResults Two empty sets should not equals.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method add.
     * @preConditions The set instance must be a new istance of Set.
     * @postConditions The set instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test
    public void testEquals_differents() {
        instance.add("pippo");
        Set instance2 = new Set();
        boolean result = instance.equals(instance2);
        assertEquals("confronto di due set diversi", false, result);
    }
    
    /**
     * @title Test #3 of equals method, of class Set.
     * @description This test tests the behaviour of the method equals() when the two sets contains the same elements. It's also tested that the method is simmetric.
     * @expectedResults Two empty sets should equals.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method add().
     * @preConditions The set instance must be a new istance of Set.
     * @postConditions The set instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test
    public void testEquals_equals() {
        instance.add("pippo");
        Set other = new Set();
        other.add("pippo");
        boolean result = instance.equals(other);
        assertEquals("confronto di due set uguali", true, result);
        result = other.equals(instance);
        assertEquals("il confronto deve essere simmetrico", true, result);
    }

    /**
     * @title Test #1 of hashCode method, of class Set.
     * @description This test tests the behaviour of the method hashCode() when called on an empty Set. The hash of a Set is defined as the sum of the hashes of set elements.
     * @expectedResults The hashcode of an empty Set must be 0.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method.
     * @preConditions The set instance must be a new istance of Set.
     * @postConditions The set instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testHashCode_empty() {
        int result = instance.hashCode();
        assertEquals("per definizione, l'hashcode di un set vuoto deve essere 0", 0, result);
    }
    
    /**
     * @title Test #2 of hashCode method, of class Set.
     * @description This test tests the behaviour of the method hashCode() when called on a not-empty Set. The hash of a Set is defined as the sum of the hashes of set elements.
     * @expectedResults The hashcode of a set must equals to the sum of its element hash.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of add().
     * @preConditions The set instance must be a new istance of Set.
     * @postConditions The set instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testHashCode_notEmpty() {
        String elem1 = "pippo", elem2 = "pluto";
        instance.add(elem1);
        instance.add(elem2);
        int result = instance.hashCode();
        int expected = elem1.hashCode() + elem2.hashCode();
        assertEquals("hash di un set non vuoto", expected, result);
    }

    /**
     * @title Test of hashCode and equals methods, of class Set.
     * @description This test tests the coherence between the hashCode and equals methods. More in details, if two objects equals each other they must have the same hash. If two object has the same hash, not necessarly they equals each other.
     * @expectedResults The equals method is expected to be coherent with the hashcode. 
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method add().
     * @preConditions The set instance must be a new istance of Set.
     * @postConditions The set instance is not directly modified by the execution of the method tested.
     */
    @Test
    public void testHashEquals() {
        instance.add("pippo");
        int hash = instance.hashCode();
        Set instance2 = new Set();
        instance2.add("pippo");
        boolean result = (hash == instance2.hashCode()) && instance.equals(instance2);
        assertEquals("controllo coerenza hashCode ed equals", true, result);
    }

    /**
     * @title Test #1 of removeAll method, of class Set.
     * @description This test tests the behaviour of removeAl() method when called using as parameter a collection which has duplicates elements contained.
     * @expectedResults The method has to remove the element the first time, and then not modify the set again the others times.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctness of methods add() and size().
     * @preConditions The set instance must be a new istance of Set.
     * @postConditions The set instance should be modified directly by the execution of the method.
     */
    @Test
    public void testRemoveAll_duplicates() {
        instance.add("pippo");
        instance.add("pluto");
        HCollection param = new List();
        param.add("pippo");
        param.add("pippo");
        boolean result = instance.removeAll(param);
        assertEquals("la collezione è modificata, un elemento è rimosso", true, result);
        assertEquals("la dimensione è diminuita", 1, instance.size());
    }
    
    /**
     * @title Test #2 of removeAll method, of class Set.
     * @description This test tests the behaviour of removeAll() method when called using as parameter a set which contains elements that have the same hash of elements contained in this set but are not contained.
     * @expectedResults The method should not remove elements with the same hash in this set, the comparison is based on equals.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctness of method add().
     * @preConditions The set instance must be a new istance of Set.
     * @postConditions The set instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testRemoveAll_hash() {
        instance.add("AaAaBB");
        instance.add("pluto");
        HCollection param = new List();
        param.add("AaAaAa");
        param.add("pippo");
        boolean result = instance.removeAll(param);
        assertEquals("elementi con hash uguali non dovrebbero essere rimossi", false, result);
    }
    
    /**
     * @title Test #1 of retainAll method, of class Set.
     * @description This test tests the behaviour of retainAll() method when called using as parameter a set which contains elements that have the same hash of elements contained in this set but are not contained.
     * @expectedResults The method should not mantain elements with the same hash in this set, the comparison is based on equals.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctness of methods add() and size().
     * @preConditions The set instance must be a new istance of Set.
     * @postConditions The set instance should be modified directly by the execution of the method.
     */
    @Test
    public void testRetainAll_hash() {
        instance.add("AaAaBB");
        instance.add("pluto");
        HCollection param = new List();
        param.add("AaAaAa");
        param.add("pluto");
        boolean result = instance.removeAll(param);
        assertEquals("elementi con hash uguali ma non equivalenti dovrebbero essere rimossi", true, result);
        assertEquals("la dimensione dovrebbe diminuire", 1, instance.size());
    }

}