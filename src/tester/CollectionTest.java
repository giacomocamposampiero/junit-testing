package tester;

import adapters.List;
import adapters.Set;
import interfaces.HCollection;
import interfaces.HIterator;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

/**
 * Test suite for testing the behaviour of classes which, directly or not, implements the interface Collection.
 * This test suite is a parameterized suite which use reflection to test the common behaviour defined by Collection interface on instances of classes which implements that interface, directly or not.
 * More specific behaviours which are defined by subinterfaces of the interface Collection are tested by other specialized test suites.
 * @author Giacomo Camposampiero
 */
@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CollectionTest {

    private final String paramClass;
    private HCollection instance;
    
    public CollectionTest(String paramClass) {
        this.paramClass = paramClass;
    }

    @Parameterized.Parameters(name="{0}")
    public static Collection classesToTest() {
        return Arrays.asList(new Object[][]{
            {"List"},
            {"Set"},
            {"SubList"}
        });
    }
    
    @Before
    public void initialize() {
        if(paramClass.equals("SubList")) {
            List tmp = new List();
            tmp.add("pippo");
            tmp.add("pluto");
            tmp.add("paperino");
            tmp.add("topolino");
            tmp.add("asso");
            tmp.add("minnie");
            instance = tmp.subList(1, 1);
        } else {
            try {
                instance = (HCollection) Class.forName("adapters."+paramClass).getConstructor().newInstance();
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | SecurityException  ex) {
                System.exit(1);
            }
        }
    }
    
    /**
     * @title Test #1 of add method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method add() when called on an empty collection.
     * @expectedResults Adding an element to an empty list (as it has just been istantiated) must be always possible.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method size().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance is directly modified by the execution of the method tested.
     */
    @Test
    public void testAdd_empty() {
        boolean result = instance.add("pippo");
        assertEquals("primo elemento che inserisco", true, result);
        assertEquals("la dimensione deve essere incrementata di 1", 1, instance.size());
    }
    
    /**
     * @title Test #2 of add method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method add() when called on a not-empty collection.
     * @expectedResults Adding an element which is not contained in the collection should be always possible.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method size().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance is directly modified by the execution of the method tested.
     */
    @Test
    public void testAdd_notContained() {
        instance.add("pluto");
        boolean result = instance.add("pippo");
        assertEquals("aggiunta di un elemento non presente, possibile", true, result);
        assertEquals("la dimensione aumenta di 1", 2, instance.size());
    }
    
    /**
     * @title Test #3 of add method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method add() when called on a collection using a null parameter.
     * @expectedResults The class is expected to throw a NullPointerException when a null reference is used in the add method.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method of interface Collection.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance isn't directly modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testAdd_exceptions() {
        instance.add(null);
    }
    
    /**
     * @title Test of a miscellaneous of methods, on an instance of a class which implements Collection interface.
     * @description This test tests a complexe burst of add and remove operations. Coherence with methods size() and isEmpty() is tested too.
     * @expectedResults The sequence of operations succeed.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of add(), remove(), size() and isEmpty() 
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance isn't directly modified by the execution of the method tested.
     */
    @Test
    public void testAddRemoveSizeEmpty() {
        boolean result = instance.add("pippo");
        result &= instance.add("pluto");
        assertEquals("inserimenti riusciti", true, result);
        assertEquals("controllo validità metodo size()", 2, instance.size());
        assertEquals("controllo validità metodo isEmpty()", false, instance.isEmpty());
        result = instance.remove("pippo");
        result |= instance.remove("pluto");
        assertEquals("rimozioni riuscite", true, result);
        assertEquals("controllo validità metodo size()", 0, instance.size());
        assertEquals("controllo validità metodo isEmpty()", true, instance.isEmpty());
        result = instance.add("pippo");
        assertEquals("inserimento elemento precedentemente eliminato", true, (instance.size()==1) && result);
        result = instance.remove("pippo");
        assertEquals("inserimento elemento precedentemente eliminato", true, instance.isEmpty() && result);
    }
    
    /**
     * @title Test #1 of addAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method addAll() when called on an empty collection using an empty collection.
     * @expectedResults Nothing shoud be added to the collection, which should remain empty. 
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method isEmpty().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance has not to be modified by the execution of the method tested.
     */
    @Test
    public void testAddAll_bothEmpty() {
        HCollection param = new List();
        boolean result = instance.addAll(param);
        assertEquals("aggiunta una collezione vuota, che non modifica lo stato della collezione", false, result);
        assertEquals("la dimensione della collezione non deve variare", true, instance.isEmpty());
    }
    
    /**
     * @title Test #2 of addAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method addAll() when called on a not-empty collection using an empty collection.
     * @expectedResults Nothing shoud be added to the collection. 
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method size().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance has not to be modified by the execution of the method tested.
     */
    @Test
    public void testAddAll_emptyParam() {
        instance.add("pippo");
        HCollection param = new Set();
        boolean result = instance.addAll(param);
        assertEquals("aggiunta una collezione vuota, che non modifica lo stato della collezione", false, result);
        assertEquals("la dimensione della collezione non deve variare", 1, instance.size());
    }
    
    /**
     * @title Test #3 of addAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method addAll() when called using parameters which are not contained in the collection.
     * @expectedResults All the elements contained in the parametric collection should be added to the list. 
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of metohods add() and size().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance has to be modified by the execution of the method tested.
     */
    @Test
    public void testAddAll_notContained() {
        HCollection param = new List();
        instance.add("pippo");
        param.add("pluto");
        param.add("topolino");
        boolean result = instance.addAll(param);
        assertEquals("aggiunta di una collezione con soli nuovi elementi", true, result);
        assertEquals("controllo dimensione", 3, instance.size());
    }
    
    /**
     * @title Test #4 of addAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method addAll() when called using null reference. The behavioiur of the method when a collection which contains null elements is used as parameter cannot be tested, as we do not have collections which accept null elements.
     * @expectedResults The object is expected to throw a NullPointerException. 
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other class methods.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance has not to be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testAddAll_exceptions() {
        instance.containsAll(null);
    }
    
    /**
     * @title Test #1 of clear method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method clear() when called on an empty collection.
     * @expectedResults A just instancied collection is expected to be empty, should be still empty after the invocation of the method.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods add() and isEmpty.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testClear_empty() {
        instance.clear();
        boolean result = instance.isEmpty();
        assertEquals("pulizia di una collezione vuota, controllo che la dimensione rimanga 0", true, result);
    }    
     
    /**
     * @title Test #2 of clear method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method clear() when called on a not-empty collection.
     * @expectedResults The collection is expected to be empty after invocation of the method.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods add() and isEmpty().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance hase to be modified by the execution of the method tested.
     */
    @Test
    public void testClear_notEmpty() {
        instance.add("pippo");
        instance.add("pluto");
        instance.clear();
        boolean result = instance.isEmpty();
        assertEquals("pulizia di una lista piena, controllo che la dimensione sia 0", true, result);
    }
    
    /**
     * @title Test #1 of contains method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method contains() when called on an empty collection
     * @expectedResults A just instancied collection is expected not to contain any element.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test
    public void testContains_empty() {
        boolean result = instance.contains("pippo");
        assertEquals("metodo invocato su una collezione vuota", false, result);
    }
    
    /**
     * @title Test #2 of contains method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method contains() when called on a not-empty collection which not contains the parameter.
     * @expectedResults The collection does not contain the parameter, so the expected result is false.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test
    public void testContains_notContained() {
        instance.add("pippo");
        boolean result = instance.contains("pluto");
        assertEquals("elemento non contenuto", false, result);
    }
    
    /**
     * @title Test #3 of contains method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method contains() when called on a not-empty collection which contains the parameter.
     * @expectedResults The collection does not contain the parameter, so the expected result is true.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified directly by the execution of the method tested.
     */
    @Test
    public void testContains_contained() {
        instance.add("pippo");
        boolean result = instance.contains("pippo");
        assertEquals("elemento contenuto nella collection", true, result);
    }
    
    /**
     * @title Test #4 of contains method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method contains() when called on a not-empty collection where the parameter was removed right before the invocation of the method. Tests the coherence between remove and contains.
     * @expectedResults The collection no longer contains the parameter, so the expected result is false.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method add() and remove().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test
    public void testContains_removed() {
        instance.add("pippo");
        instance.remove("pippo");
        boolean result = instance.contains("pippo");
        assertEquals("metodo invocato con un parametro che è stato in precedenza cancellato", false, result);
    }
    
    /**
     * @title Test #5 of contains method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method contains() when called using a null reference.
     * @expectedResults The collection is expected to throw a NullPointerException as does not support null elements.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of other methods.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testContains_exceptions() {
        instance.contains(null);
    }
    
    /**
     * @title Test #1 of containsAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method containsAll() when called on an empty collection using an empty Set parameter.
     * @expectedResults The instance should contains all elements of the parameter, because it's empty.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testContainsAll_emptySet() {
        HCollection param = new Set();
        boolean result = instance.containsAll(param);
        assertEquals("metodo invocato su una collezione vuota", true, result);
    }
    
    /**
     * @title Test #2 of containsAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method containsAll() when called on an empty collection using an empty List parameter.
     * @expectedResults The instance should contains all elements of the parameter, because it's empty.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testContainsAll_emptyList() {
        HCollection param = new List();
        boolean result = instance.containsAll(param);
        assertEquals("metodo invocato su una collezione vuota", true, result);
    }
    
    /**
     * @title Test #3 of containsAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method containsAll() when called on a not-empty collection which not contains any of the elements contained in the Set param.
     * @expectedResults The collection should not contain any of the parameter elements, the result should be false.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testContainsAll_notContainedSet() {
        HCollection param = new Set();
        param.add("pippo");
        param.add("pluto");
        param.add("paperino");
        instance.add("asso");
        boolean result = instance.containsAll(param);
        assertEquals("elementi tutti non presenti", false, result);
    }
    /**
     * @title Test #4 of containsAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method containsAll() when called on a not-empty collection which not contains any of the elements contained in the List param.
     * @expectedResults The collection should not contain any of the parameter elements, the result should be false.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness ofadd().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testContainsAll_notContainedList() {
        HCollection param = new List();
        param.add("pippo");
        param.add("pluto");
        param.add("paperino");
        instance.add("asso");
        boolean result = instance.containsAll(param);
        assertEquals("elementi tutti non presenti", false, result);
    }
    
    /**
     * @title Test #5 of containsAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method containsAll() when called on a not-empty collection which contains any of the elements contained in the param.
     * @expectedResults The collection should contain all the parameter elements, the result should be true.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testContainsAll_containedParam() {
        HCollection param = new List();
        instance.add("pluto");
        instance.add("topolino");
        instance.add("asso");
        param.add("pluto");
        param.add("topolino");
        boolean result = instance.containsAll(param);
        assertEquals("collezione contiene tutti i parametri", true, result);
    }
    
    /**
     * @title Test #6 of containsAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method containsAll() when called on a not-empty collection which contains just few of the elements contained in the param.
     * @expectedResults The collection should not contain all the parameter elements, the result should be false.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testContainsAll_partiallyContainedParam() {
        HCollection param = new Set();
        param.add("topolino");
        param.add("pippo");
        param.add("pluto");
        instance.add("topolino");
        instance.add("pippo");
        boolean result = instance.containsAll(param);
        assertEquals("collezione contiene solo parte dei parametri", false, result);
    }
    
    /**
     * @title Test #7 of containsAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method containsAll() when called on a not-empty collection using an empty param.
     * @expectedResults The collection should contain all the parameter elements, as the param is empty.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testContainsAll_emptyParam() {
        HCollection param = new Set();
        instance.add("pluto");
        boolean result = instance.containsAll(param);
        assertEquals("metodo invocato con una collezione vuota come parametro", true, result);
    }
    
    /**
     * @title Test #8 of containsAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method containsAll() when called using a null reference. 
     * @expectedResults A NullPointerException is expected as result of the call of the method.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method of the class.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsAll_exceptions() {
        instance.containsAll(null);
    }
    
    /**
     * @title Test #1 of isEmpty method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method isEmpty() when called on an empty collection
     * @expectedResults A just instancied collection is expected to be empty.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testIsEmpty_empty() {
        boolean result = instance.isEmpty();
        assertEquals("controllo su collezione vuota", true, result); 
    }
    
    /**
     * @title Test #2 of isEmpty method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the method isEmpty() when called on an empty Collection.
     * @expectedResults An element was added to the collection, the collection should not be empty.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method put().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified by the execution of the method tested (but still have to contain the entry added while testing).
     */
    @Test
    public void testIsEmpty_notEmpty() {
        instance.add("pippo");
        boolean result = instance.isEmpty();
        assertEquals("controllo su collezione non vuota", false, result); 
    }
    
    /**
     * @title Test #3 of isEmpty method, of an istance of a class which implements Collection interface.
     * @description This test tests the coherence between methods size() and isEmpty().
     * @expectedResults A coherent behaviour is expected between size() and isEmpty() methods.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be modified directly by the execution of the method tested.
     */
    @Test
    public void testIsEmpty_size() {
        boolean size = (instance.size() == 0);
        boolean isEmpty = instance.isEmpty();
        assertEquals("i metodi devono essere coerenti quando la collezione è vuota", size, isEmpty);
        instance.add("pippo");
        size = (instance.size() == 0);
        isEmpty = instance.isEmpty();
        assertEquals("i metodi devono essere coerenti quando la collezione è piena", size, isEmpty);
    }
    
    /**
     * @title Test #1 of iterator method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the iterator returned by iterator() method. More in details, this test tests that an iterator of an empty collection has no next elements.
     * @expectedResults The iterator of an empty collection can't have a next element, as the collection is empty.
     * @actualResult As expected result.
     * @dependencies This test has no dependencies on other class methods.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified by the execution of the method.
     */
    @Test
    public void testIterator_empty() {
        HIterator it = instance.iterator();
        boolean result = it.hasNext();
        assertEquals("iteratore di una collezione vuota non deve avere un next", false, result);
    }
    
    /**
     * @title Test #2 of iterator method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the iterator returned by iterator() method. More in details, this test tests the behaviour of the iterator of a collection which has just an element.
     * @expectedResults The iterator of a collection which has just an element should have just an element, equal to the element of the list.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctness of method add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified by the execution of the method.
     */
    @Test
    public void testIterator_oneElement() {
        instance.add("pippo");
        HIterator it = instance.iterator();
        boolean result = it.hasNext();
        assertEquals("iteratore di una collezione piena deve avere un next", true, result);
        result = it.next().equals("pippo");
        assertEquals("l'oggetto restituito dall'iteratore corrisponde a quello presente nella lista", true, result);
        result = it.hasNext();
        assertEquals("iteratore al termine della collezione, non deve avere next", false, result);
    }
    
    /**
     * @title Test #3 of iterator method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the iterator returned by iterator() method. More in details, this test tests the behaviour of the iterator and the collection when the remove method is called. The test is performed on a collection which contains just one element, so the collection should be empty at the end of the test.
     * @expectedResults The iterator should remove the element and the collection should be empty.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctness of method add() and isEmpty().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should be modified by the execution of the method.
     */
    @Test
    public void testIterator_remove() {
        instance.add("pippo");
        HIterator it = instance.iterator();
        it.next();
        it.remove();
        assertEquals("la rimozione è stata fatta, la collezione dovrebbe essere vuota", true, instance.isEmpty());
    }
    
    /**
     * @title Test #4 of iterator method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the iterator returned by iterator() method. More in details, this test tests that all the elements of a collection are iterable by the iterator. 
     * @expectedResults The iterator should be able to iterate all the elements of the collection.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctness of methods add(), contains() and size().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified by the execution of the method.
     */
    @Test
    public void testIterator_iteration() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("topolino");
        HIterator it = instance.iterator();
        boolean result = true;
        int i = 0;
        while (it.hasNext()) {
            result = result && instance.contains(it.next());
            i++;
        }
        result = result && instance.size() == i;
        assertEquals("la lista contiene tutti gli elementi contenuti nell'iteratore", true, result);
    }
    
    /**
     * @title Test #5 of iterator method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the iterator returned by iterator() method. More in details, this test tests a complex sequence of operation with the iterator. 
     * @expectedResults The result of a sequence of operation performed by the iterator should be consistent and the changes should be correctly reflected to the map.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctness of method add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should be modified by the execution of the method.
     */
    @Test
    public void testIterator_complex() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("topolino");
        instance.add("paperino");
        instance.add("rame");
        instance.add("argon");
        HIterator it = instance.iterator();
        it.next();
        it.remove();
        assertEquals("dimensione diminuita", 5, instance.size());
        it.next();
        it.next();
        it.remove();
        assertEquals("dimensione diminuita", 4, instance.size());
        it.next();
        it.next();
        it.remove();
        assertEquals("dimensione diminuita", 3, instance.size());
        it.next();
        it.remove();
        assertEquals("dimensione diminuita", 2, instance.size());
        assertEquals("fine iteratore raggiunta", false, it.hasNext());
    }
    
    /**
     * @title Test #6 of iterator method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the iterator returned by iterator() method. More in details, the trowing of expected exceptions is tested: NoSuchElementException if next() is called but hasNext() is false; IllegalStateException if remove() is called before next() or if remove() is called another remove() without performing a next() before.
     * @expectedResults The expected exceptions should be thrown in the conditions listed above.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctness of method add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified by the execution of the method.
     */
    @Test(expected = NoSuchElementException.class)
    public void testIterator_exception1() {
        instance.iterator().next();
    }
    
    /**
     * @title Test #7 of iterator method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the iterator returned by iterator() method. More in details, the trowing of expected exceptions is tested: NoSuchElementException if next() is called but hasNext() is false; IllegalStateException if remove() is called before next() or if remove() is called another remove() without performing a next() before.
     * @expectedResults The expected exceptions should be thrown in the conditions listed above.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctness of method add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified by the execution of the method.
     */
    @Test(expected = exceptions.IllegalStateException.class)
    public void testIterator_exception2() {
        instance.add("pippo");
        instance.iterator().remove();
    }
    
    /**
     * @title Test #8 of iterator method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the iterator returned by iterator() method. More in details, the trowing of expected exceptions is tested: NoSuchElementException if next() is called but hasNext() is false; IllegalStateException if remove() is called before next() or if remove() is called another remove() without performing a next() before.
     * @expectedResults The expected exceptions should be thrown in the conditions listed above.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctness of method add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified by the execution of the method.
     */
    @Test(expected = exceptions.IllegalStateException.class)
    public void testIterator_exception3() {
        instance.add("pippo");
        HIterator iter = instance.iterator();
        iter.next();
        iter.remove();
        iter.remove();
    }
    
    /**
     * @title Test #1 of size method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of size() method when called on an empty collection.
     * @expectedResults The size is expected to be zero, as the collection is empty.
     * @actualResult As expected result.
     * @dependencies This test has no dependencies on other class methods.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified by the execution of the method.
     */
    @Test
    public void testSize_empty() {
        int result = instance.size();
        assertEquals("la dimensione di una collectionpa appena creata è 0", 0, result);
    }
    
    /**
     * @title Test #2 of size method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of size() method when called on a non-empty collection.
     * @expectedResults The size is expected to be equal to the number of elements that have been added to thecollection.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method put().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testSize_notEmpty() {
        instance.add("pippo");
        int result = instance.size();
        assertEquals("la dimensione corrisponde al numero di elementi aggiunti", 1, result);
    }    
    
    /**
     * @title Test #1 of remove method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of remove() method when called on an empty collection.
     * @expectedResults The collection is empty, the remove() must return false.
     * @actualResult As expected result.
     * @dependencies This test has no correctness dependencies on other class methods.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testRemove_empty() {
        boolean result = instance.remove("pippo");
        assertEquals("rimozione di un elemento in una collezione vuota", false, result);
    }
    
    /**
     * @title Test #2 of remove method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of remove() method when called on a not-empty collection and the specified element is not contained.
     * @expectedResults Specified element is not contained in the collection, the remove must return false.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods add() and isEmpty().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testRemove_notContained() {
        instance.add("pippo");
        boolean result = instance.remove("pluto");
        assertEquals("rimozione oggetto non presente", false, result && !instance.isEmpty());
    }
    
    /**
     * @title Test #3 of remove method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of remove() method when called on a not-empty collection and the specified element is contained.
     * @expectedResults Specified element is contained in the collection, the remove must return true and remove the element.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods add() and isEmpty().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should be modified directly by the execution of the method.
     */
    @Test
    public void testRemove_contained() {
        instance.add("pippo");
        boolean result = instance.remove("pippo");
        assertEquals("rimozione oggetto presente", true, result && instance.isEmpty());
        result = instance.remove("pippo");
        assertEquals("rimozione oggetto rimosso in precedenza", false, result);
    }
    
    /**
     * @title Test #4 of remove method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of remove() method when called using a null reference as parameter.
     * @expectedResults The method is expected to throw a NullPointerException as null elements are not supported by this collection.
     * @actualResult As expected result.
     * @dependencies This test does not depends on the correctness of any other method of the interface.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified directly by the execution of the method.
     */
    @Test(expected = NullPointerException.class)
    public void testRemove_exceptions() {
        instance.remove(null);
    }
    
    /**
     * @title Test #1 of removeAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of removeAll() method when called on an empty collection using an empty collection as parameter.
     * @expectedResults The method must return false, as the collection is not changed by the invocation of the method.
     * @actualResult As expected result.
     * @dependencies This test has no correctness dependencies on other class methods.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testRemoveAll_bothEmpty() {
        HCollection param = new Set();
        boolean result = instance.removeAll(param);
        assertEquals("removeAll invocato su collezione vuota", false, result);
    }
    
    /**
     * @title Test #2 of removeAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of removeAll() method when called on a not-empty collection using an empty collection as parameter.
     * @expectedResults The method must return false, as the collection is not changed by the invocation of the method.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctnes of method add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testRemoveAll_emptyParam() {
        HCollection param = new List();
        instance.add("pippo");
        boolean result = instance.removeAll(param);
        assertEquals("metodo invocato su collezione piena con parametro vuoto", false, result);
    }
    
    /**
     * @title Test #3 of removeAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of removeAll() method when called on a not-empty collection using a collection which contains elements which are both contained and not in the collection.
     * @expectedResults The method must return true, as some of the elements of the collection are removed and the collection state is changed.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctnes of method add() and size().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should be modified directly by the execution of the method.
     */
    @Test
    public void testRemoveAll_contained() {
        instance.add("pippo");
        instance.add("pippo");
        instance.add("pluto");
        HCollection param = new Set();
        param.add("pippo");
        param.add("paperino");
        boolean result = instance.removeAll(param);
        assertEquals("dalla collezione sono rimossi alcuni elementi", true, result);
        assertEquals("la dimensione della collezione è diminuita", 1, instance.size());
    }
    
    /**
     * @title Test #4 of removeAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of removeAll() method when called on a not-empty collection using a collection which contains all the elements of the collection.
     * @expectedResults All the collection elements are removed from the collection.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctnes of method add() and size().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should be modified directly by the execution of the method.
     */
    @Test
    public void testRemoveAll_all() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("topolino");
        HCollection param = new List();
        param.add("pippo");
        param.add("pluto");
        param.add("topolino");
        boolean res = instance.removeAll(param);
        assertEquals("è avvenuta una rimozione", true, res);
        assertEquals("la lista è vuota a seguito delle rimozioni", true, instance.isEmpty());
    }
    
    /**
     * @title Test #5 of removeAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of removeAll() method when called using a null reference as parameter.
     * @expectedResults The collection is expected to throw a NullPointerException.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctnes of other methods.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified directly by the execution of the method.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAll_exceptions() {
        instance.removeAll(null);
    }
    
    /**
     * @title Test #1 of retainAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of retainAll() method when called on an empty collection using an empty collection as parameter.
     * @expectedResults The collection should not be modified by the method, as is already empty.
     * @actualResult As expected result.
     * @dependencies This test has no correctness dependencies on other class methods.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testRetainAll_bothEmpty() {
        HCollection param = new Set();
        boolean result = instance.retainAll(param);
        assertEquals("metodo invocato su collezione vuota, non modifica la collezione", false, result);
    }
    
    /**
     * @title Test #2 of retainAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of retainAll() method when called on a not-empty collection using an empty collection as parameter.
     * @expectedResults All the elements of the collection must be removed from the collection.
     * @actualResult As expected result.
     * @dependencies This test has no correctness dependes on the correctness of methods add() and isEmpty.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should be directly modified by the execution of the method.
     */
    @Test
    public void testRetainAll_emptyParam() {
        HCollection param = new List();
        instance.add("pippo");
        instance.add("pluto");
        boolean result = instance.retainAll(param);
        assertEquals("metodo invocato su lista con parametro vuoto", true, result);
        assertEquals("nessun elemento rimasto", true, instance.isEmpty());
    }
    
    /**
     * @title Test #3 of retainAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of retainAll() method when called on a not-empty collection using a collection of elements which are both contained and not in the collection.
     * @expectedResults Only the elements of the parameter contained in the collection should be mantained inside it.
     * @actualResult As expected result.
     * @dependencies This test has no correctness dependes on the correctness of methods add() and size().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should be directly modified by the execution of the method.
     */
    @Test
    public void testRetainAll_variousParam() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("topolino");
        HCollection param = new Set();
        param.add("pluto");
        param.add("asso");
        boolean result = instance.retainAll(param);
        assertEquals("trattiene una parte degli elementi della lista", true, result);
        assertEquals("la dimensione è cambiata", 1, instance.size());
    }
    
    /**
     * @title Test #4 of retainAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of retainAll() method when called on a not-empty collection using a collection which contains all the elements of this collection.
     * @expectedResults All the elements of the collection should be mantained.
     * @actualResult As expected result.
     * @dependencies This test has no correctness dependes on the correctness of methods add() and size().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be directly modified by the execution of the method.
     */
    @Test
    public void testRetainAll_all() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("topolino");
        HCollection param = new Set();
        param.add("pippo");
        param.add("pluto");
        param.add("topolino");
        boolean result = instance.retainAll(param);
        assertEquals("trattiene tutti gli elementi della lista", false, result);
        assertEquals("la dimensione non è cambiata", 3, instance.size());
    }
    
    /**
     * @title Test #5 of retainAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of retainAll() method when called on a not-empty collection using a collection which has no element in common with the collection.
     * @expectedResults No elemenent should be mantained in the collection.
     * @actualResult As expected result.
     * @dependencies This test correctness dependes on the correctness of methods add() and isEmpty().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should be directly modified by the execution of the method.
     */
    @Test
    public void testRetainAll_notContained() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("topolino");
        HCollection param = new Set();
        param.add("paperino");
        param.add("asso");
        boolean result = instance.retainAll(param);
        assertEquals("non trattiene nessun elemento della lista", true, result);
        assertEquals("la collezione ora è vuota", true, instance.isEmpty());
    }
    
    /**
     * @title Test #6 of retainAll method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of retainAll() method when called using a null reference as parameter. 
     * @expectedResults The class is expected to throw a NullPointerException.
     * @actualResult As expected result.
     * @dependencies This test correctness does not depends on the correctness of any other method.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should be directly modified by the execution of the method.
     */
    @Test(expected = NullPointerException.class)
    public void testRetainAll_exceptions() {
        instance.retainAll(null);
    }
    
    /**
     * @title Test #1 of toArray method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of toArray() method when called on an empty collection.
     * @expectedResults The returned array must be an empty array, as the instance is empty.
     * @actualResult As expected result.
     * @dependencies This test has no correctness dependencies on other class methods.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testToArray_empty() {
        Object[] expected = new Object[0];
        Object[] result = instance.toArray();
        assertArrayEquals("toArray invocato su un set vuoto deve ritornare un array vuoto", expected, result);
    }    
        
    /**
     * @title Test #2 of toArray method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of toArray() method when called on a non-empty collection.
     * @expectedResults The returned array must be aa generic Object[] array, its size has to be equal to the instance size and it must contains all the elements of the original array.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods size(), add() and contains().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testToArray_notEmpty() {    
        instance.add("pippo");
        instance.add("pluto");
        Object[] result = instance.toArray();
        assertEquals("la lunghezza dell'array deve coincidere con quella dell'istanza", instance.size(), result.length);
        boolean check = true;
        for(int i=0; i<result.length; i++) {
            check = check && instance.contains(result[i]);
        }   
        assertEquals("tutti gli elementi dell'array devono essere contenuti nell'istanza", true, check);
        assertEquals("l'array restituito deve essere un array di Object", Object[].class, result.getClass());
    }
    
    /**
     * @title Test #3 of toArray method, of an istance of a class which implements Collection interface.
     * @description This test tests that the returned array is safe, no reference are mantained to the main collection.
     * @expectedResults The returned array can be modified without causing any structural modification to the collection.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods add() and size().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testToArray_safe() {    
        instance.add("pippo");
        instance.add("pluto");
        Object[] arr = instance.toArray();
        arr[0] = null;
        arr[1] = "paperino";
        boolean result = instance.contains("pippo") && instance.contains("pluto");
        assertEquals("la collection iniziale contiene ancora entrambi i valori iniziali", true, result);
    }
       
    /**
     * @title Test #1 of parametric toArray method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the parametric toArray() when an empty array is used as parameter and the collection is empty.
     * @expectedResults The array returned should be exactly the one which has been passed as parameter, not modified at all.
     * @actualResult As expected result.
     * @dependencies This test has no correctness dependencies on other class methods.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be directly modified by the call of the method tested.
     */
    @Test
    public void testParametricToArray_bothEmpty() {
        String[] param = new String[0];
        Object[] result;
        result = instance.toArray(param);
        assertArrayEquals("collection e array vuoti, dovrebbe coincidere", param, result);
    }
    
    /**
     * @title Test #2 of parametric toArray method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of the parametric toArray() when a not-empty array is used as parameter and thecollection is empty. This method test also wheter the method modify the only the firstcollection.size() cells of the param array.
     * @expectedResults The array returned should be exactly the one which has been passed as parameter, but the firstcollection.size() cells must contains the elements of the collection.
     * @actualResult As expected result.
     * @dependencies This test has no correctness dependencies on other class methods.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be directly modified by the call of the method tested.
     */
    @Test
    public void testParametricToArray_collectionEmpty() {
        String[] param = new String[10];
        for(int i=0; i<10; i++) param[i] = "pippo";
        Object[] result;
        result = instance.toArray(param);
        assertArrayEquals("collection vuoto e array riempito, dovrebbe coincidere", param, result);
    }
    
    /**
     * @title Test #3 of parametric toArray method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of parametric method toArray() when is called on a not-empty collection passing a not-empty array which length is greater than collection size.
     * @expectedResults The array returned should be exactly the one which has been passed as parameter, but the first size() must be modified, as they should contains the elements of this collection. The elements contained in the array in positions >= size() should not be modified.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be directly modified by the call of the method tested.
     */
    @Test
    public void testParametricToArray_notEmpty() {
        String[] param = new String[10];
        for(int i=0; i<10; i++) param[i] = "pippo";
        instance.add("pluto");
        instance.add("paperino");
        instance.add("topolino");
        
        Object[] result = instance.toArray(param);
        assertEquals("il tipo dell'array ritornato coincide con quello dell'array parametro", true, result.getClass().equals(param.getClass()));
        boolean check = true;
        for(int i=0; i<instance.size(); i++) {
            check = check && instance.contains(result[i]);
        }
        for(int i=instance.size(); i<result.length; i++) {
            check = check && result[i].equals(param[i]);
        }
        assertEquals("gli elementi del collection sono stati copiati nelle prime size()-1 posizioni, le successive non sono state modificate", true, check);
    }
    
    /**
     * @title Test #4 of parametric toArray method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of parametric method toArray() when is called on a not-empty collection using an array which is not big enough to contain the whole collection.
     * @expectedResults The array returned should be a new istance of a generic Object array (the parameter should not be modified), and it should contains all the elements contained in the collection.
     * @actualResult As expected result.
     * @dependencies Depends on the correctess of method add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be directly modified by the call of the method tested.
     */
    @Test
    public void testParametricToArray_notBigEnough() {
        String[] param = new String[1];
        param[0] = "pippo";
        String[] copyParam = Arrays.copyOf(param, param.length);
        instance.add("pluto");
        instance.add("paperino");
        instance.add("topolino");
        
        Object[] result = instance.toArray(param);
        assertEquals("il tipo dell'array ritornato non coincide con quello dell'array parametro", false, result.getClass().equals(param.getClass()));
        boolean check = instance.size() == result.length;
        for (Object tmp : result) {
            check = check && instance.contains(tmp);
        }
        assertEquals("gli elementi del collection sono tutti presenti e le dimensioni del risultato e dell'istanza di collection coincidono", true, check);
        assertArrayEquals("l'array parametro non è stato modificato", copyParam, param);
    }
    
    /**
     * @title Test #5 of parametric toArray method, of an istance of a class which implements Collection interface.
     * @description This test tests the behaviour of parametric toArray() method when a null reference is used as parameter.
     * @expectedResults A NullPointerException was thrown as result of the call.
     * @actualResult As expected result.
     * @dependencies This test has no correctness dependencies on other class methods.
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance should not be directly modified by the call of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testParametricToArray_exceptions() {
        instance.toArray(null);
    }

}