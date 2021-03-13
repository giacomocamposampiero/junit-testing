package tester;

import adapters.List;
import interfaces.HCollection;
import interfaces.HIterator;
import interfaces.HList;
import interfaces.HListIterator;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

/**
 * Test suite for List class.
 * This suite contains a sequence of tests which check the specific behaviuour of a List. 
 * More general behaviours (inherited by Collection interface) are tested in Collection test suite.
 * @author Giacomo Camposampiero
 */
@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListTest {

    private final String paramClass;
    private HList instance;

    public ListTest(String paramClass) {
        this.paramClass = paramClass;
    }
    
    @Parameterized.Parameters(name="{0}")
    public static Collection classesToTest() {
        return Arrays.asList(new Object[][]{
            {"List"},
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
                instance = (HList) Class.forName("adapters."+paramClass).getConstructor().newInstance();
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | SecurityException  ex) {
                System.exit(1);
            }
        }
    }  
    
    /**
     * @title Test #1 of add method, of class List.
     * @description This test tests the behaviour of the method add() when called on a not-empty list. More in details, it only tests that the element is appended at the end of the list.
     * @expectedResults The add method is expected to append the new element at the end of the list.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method size().
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is directly modified by the execution of the method tested.
     */
    @Test
    public void testAdd_append() {
        instance.add("pippo");
        assertEquals("stringa inserita a fine lista", "pippo", instance.get(instance.size()-1));
        instance.add("pluto");
        assertEquals("stringa inserita a fine lista", "pluto", instance.get(instance.size()-1));
    }
    
    /**
     * @title Test #2 of add method, of class List.
     * @description This test tests the behaviour of the method add() when called on a not-empty list using a parameter which is already contained in the list.
     * @expectedResults List allows duplicates, the new entry should be accepted.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method size().
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is directly modified by the execution of the method tested.
     */
    @Test
    public void testAdd_duplicates() {
        instance.add("pippo");
        boolean result = instance.add("pippo");
        assertEquals("inserimento duplicato", true, result);
        assertEquals("controllo aumento dimensione", 2, instance.size());
    }

    /**
     * @title Test #1 of parametric add method, of class List.
     * @description This test tests the behaviour of the method add() when called on an empty list.
     * @expectedResults The add method is expected to add the element in the first positision (index 0) of the list.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods size() and get().
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is directly modified by the execution of the method tested.
     */
    @Test
    public void testParametricAdd_empty() {
        instance.add(0, "pippo");
        int result = instance.size();
        assertEquals("inserimento di un oggetto in una lista vuota", 1, result);
        assertEquals("l'oggetto è stato inserito in posizione 0", "pippo", instance.get(0));
    }
    
    /**
     * @title Test #2 of parametric add method, of class List.
     * @description This test tests the behaviour of the method add() when called on a not-empty list, with an index = size().
     * @expectedResults The add method is expected to append the element at the end of the list.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods size() and get().
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is directly modified by the execution of the method tested.
     */
    @Test
    public void testParametricAdd_append() {
        instance.add(instance.size(), "pippo");
        instance.add(instance.size(), "pluto");
        int result = instance.size();
        assertEquals("inserisco in coda ad una lista piena", 2, result);
        assertEquals("il secondo elemento è inserito al termine della lista", "pluto", instance.get(instance.size()-1));
    }
    
    /**
     * @title Test #3 of parametric add method, of class List.
     * @description This test tests the behaviour of the method add() when an element is added at the beginning of the list.
     * @expectedResults The add method is expected to append the element at the beginning of the list.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods size() and get().
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is directly modified by the execution of the method tested.
     */
    @Test
    public void testParametricAdd_front() {
        instance.add(0, "topolino");
        instance.add(0, "pippo");
        int result = instance.size();
        assertEquals("inserisco in testa ad una lista piena", 2, result);
        assertEquals("il secondo elemento è inserito in testa", "pippo", instance.get(0));
        instance.add(0, "pluto");
        result = instance.size();
        assertEquals("inserisco in testa ad una lista piena", 3, result);
        assertEquals("il terzo elemento è inserito in testa", "pluto", instance.get(0));
    }
    
    /**
     * @title Test #4 of parametric add method, of class List.
     * @description This test tests the behaviour of the method add() when an element which is already contained is added in the middle of the list.
     * @expectedResults The add method is expected to add the element and to shif right all the elements which was in positions >= index.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods size() and get().
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is directly modified by the execution of the method tested.
     */
    @Test
    public void testParametricAdd_middle() {
        instance.add(instance.size(), "pippo");
        instance.add(instance.size(), "pluto");
        instance.add(instance.size(), "paperino");
        instance.add(instance.size(), "topolino");
        instance.add(2, "pippo");
        int result = instance.size();
        assertEquals("inserisco in mezzo ad una lista piena, con duplicato", 5, result);
        assertEquals("elemento inserito nella posizione specificata", "pippo", instance.get(2));
        boolean shift = instance.get(3).equals("paperino") && instance.get(4).equals("topolino");
        assertEquals("gli elementi a destra sono shiftati di una posizione", true, shift);
    }
    
    /**
     * @title Test #5 of parametric add method, of class List.
     * @description This test tests the behaviour of the method add() when an invalid parameter is used. More in details, the method is expected to throw a NullPointerException when the object to add is a null reference and a IndexOutOfBoundsException when the specified index is <0 or >size().
     * @expectedResults The add method is expected to throw the specified exceptions in the situations described above..
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method).
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is not directly modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testParametricAdd_exception1() {
        instance.add(0, null);
    }
    
    /**
     * @title Test #6 of parametric add method, of class List.
     * @description This test tests the behaviour of the method add() when an invalid parameter is used. More in details, the method is expected to throw a NullPointerException when the object to add is a null reference and a IndexOutOfBoundsException when the specified index is <0 or >size().
     * @expectedResults The add method is expected to throw the specified exceptions in the situations described above..
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method).
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is not directly modified by the execution of the method tested.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testParametricAdd_exception2() {
                    instance.add(100000, "pippo");
    }
    
    /**
     * @title Test #7 of parametric add method, of class List.
     * @description This test tests the behaviour of the method add() when an invalid parameter is used. More in details, the method is expected to throw a NullPointerException when the object to add is a null reference and a IndexOutOfBoundsException when the specified index is <0 or >size().
     * @expectedResults The add method is expected to throw the specified exceptions in the situations described above..
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method).
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is not directly modified by the execution of the method tested.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testParametricAdd_exception3() {
        instance.add(-20, "pippo");
    }
    
    /**
     * @title Test #1 of addAll method, of class List.
     * @description This test tests the behaviour of the method addAll() when called on a not-empty list. More in details, it only tests that the element is appended at the end of the list in the precise order defined by the iterator.
     * @expectedResults The add method is expected to append the new elements at the end of the list.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method size().
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is directly modified by the execution of the method tested.
     */
    @Test
    public void testAddAll_append() {
        HCollection param = new List();
        param.add("pippo");
        param.add("pluto");
        instance.add("paperino");
        instance.addAll(param);
        boolean res = true;
        HIterator instIt = instance.iterator();
        instIt.next();
        HIterator it = param.iterator();
        while(it.hasNext()) res &= it.next().equals(instIt.next());
        assertEquals("elementi aggiunti in fondo nell'ordine corretto", true, res);
    }
    
    /**
     * @title Test #2 of addAll method, of class List.
     * @description This test tests the behaviour of the method addAll() when called using a collection which contains elements which are already contained in the list.
     * @expectedResults The elementes which are already contained should be added without problems in the list, duplicates are allowed.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of methods size() and add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance has to be modified by the execution of the method tested.
     */
    @Test
    public void testAddAll_contained() {
        instance.add("pippo");
        HCollection param = new List();
        param.add("pippo");
        param.add("pluto");
        boolean result = instance.addAll(param);
        assertEquals("ci sono elementi nuovi, vengono aggiunti e lo stato della lista cambia", true, result);
        assertEquals("tutti gli elementi sono aggiunti", 3, instance.size());
    }

    /**
     * @title Test #1 of parametric addAll method, of class List.
     * @description This test tests the behaviour of the method add() when called on an empty list using an empty collection.
     * @expectedResults The list should not change.
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correctness of method isEmpty().
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is not directly modified by the execution of the method tested.
     */
    @Test
    public void testParametricAddAll_bothEmpty() {
        List list = new List();
        boolean result = instance.addAll(0, list);
        assertEquals("aggiunta una collezione vuota, che non modifica lo stato della lista", false, result);
        assertEquals("controllo sulla dimensione", true, instance.isEmpty());
    }
    
    /**
     * @title Test #2 of parametric addAll method, of class List.
     * @description This test tests the behaviour of the method add() when called on a not-empty list using an empty collection.
     * @expectedResults The list should not change.
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correctness of methods add() and size().
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is not directly modified by the execution of the method tested.
     */
    @Test
    public void testParametricAddAll_emptyParam() {
        instance.add("pippo");
        instance.add("pluto");
        List list = new List();
        boolean result = instance.addAll(1, list);
        assertEquals("aggiunta una collezione vuota, che non modifica lo stato della lista", false, result);
        assertEquals("controllo sulla dimensione", 2, instance.size());
    }
    
    /**
     * @title Test #3 of parametric addAll method, of class List.
     * @description This test tests the behaviour of the method add() when the param elements are appended at the end of the list.
     * @expectedResults All the new elements should be appended at the end of the list.
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correctness of methods add(), size(), iterator() and iterator(int).
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is directly modified by the execution of the method tested.
     */
    @Test
    public void testParametricAddAll_append() {
        instance.add("pippo");
        instance.add("pluto");
        HCollection param = new List();
        param.add("pippo");
        param.add("pluto");
        boolean result = instance.addAll(instance.size(), param);
        assertEquals("elementi della collezione parametro appesi alla fine della lista", true, result);
        assertEquals("controllo sulla dimensione", 4, instance.size());
 
        HListIterator it = instance.listIterator(2);
        HIterator parIt = param.iterator();
        while(parIt.hasNext()) result &= parIt.next().equals(it.next());
        assertEquals("elementi aggiunti nell'ordine in cui l'iteratore della collection li restituisce", true, result);
    }
    
    /**
     * @title Test #4 of parametric addAll method, of class List.
     * @description This test tests the behaviour of the method add() when the param elements are placed in the of the list.
     * @expectedResults All the new elements should be added from specified index; elements which index was >index should be shifted to the right of param.size() positions.
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correctness of methods add(), size(), get() and iterator().
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is directly modified by the execution of the method tested.
     */
    @Test
    public void testParametricAddAll_middle() {
        instance.add("topolino");
        instance.add("paperino");
        HCollection param = new List();
        param.add("pippo");
        param.add("pluto");
        param.add("asso");
        boolean result = instance.addAll(1, param);
        assertEquals("aggiunta di una collezione piena nel mezzo", true, result);
        assertEquals("controllo dimensione", 5, instance.size());
        result = instance.get(0).equals("topolino");
        HIterator it = param.iterator();
        for(int i=1; it.hasNext(); i++)
            result &= it.next().equals(instance.get(i));
        result &= instance.get(4).equals("paperino");
        assertEquals("elementi aggiunti secondo l'ordine definito dall'iteratore del parametro", true, result);
    }
    
    /**
     * @title Test #5 of parametric addAll method, of class List.
     * @description This test tests the behaviour of the method addAll() when an invalid parameter is used. More in details, the method is expected to throw a NullPointerException when the object to add is a null reference and a IndexOutOfBoundsException when the specified index is <0 or >size().
     * @expectedResults The add method is expected to throw the specified exceptions in the situations described above.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method).
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is not directly modified by the execution of the method tested.
     */
    @Test(expected = NullPointerException.class)
    public void testParametricAddAll_exception1() {
        instance.addAll(null);         
    }
    
    /**
     * @title Test #6 of parametric addAll method, of class List.
     * @description This test tests the behaviour of the method addAll() when an invalid parameter is used. More in details, the method is expected to throw a NullPointerException when the object to add is a null reference and a IndexOutOfBoundsException when the specified index is <0 or >size().
     * @expectedResults The add method is expected to throw the specified exceptions in the situations described above.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method).
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is not directly modified by the execution of the method tested.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testParametricAddAll_exception2() {
        instance.addAll(100000, new List());
    }
    
    /**
     * @title Test #7 of parametric addAll method, of class List.
     * @description This test tests the behaviour of the method addAll() when an invalid parameter is used. More in details, the method is expected to throw a NullPointerException when the object to add is a null reference and a IndexOutOfBoundsException when the specified index is <0 or >size().
     * @expectedResults The add method is expected to throw the specified exceptions in the situations described above.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method).
     * @preConditions The collection instance must be a new istance of List.
     * @postConditions The collection instance is not directly modified by the execution of the method tested.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testParametricAddAll_exception3() {
        instance.addAll(-20, new List());
    }
  
    /**
     * @title Test #1 of contains method, of class List.
     * @description This test tests the behaviour of the method contains() when called on a list which contains multiple elements which equals to the parameter.
     * @expectedResults The parameter should be contained.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test
    public void testContains_multiple() {
        instance.add("pippo");
        instance.add("pippo");
        instance.add("pippo");
        boolean result = instance.contains("pippo");
        assertEquals("collezione che contiene più istanze del parametro", true, result);
    }
    
    /**
     * @title Test #1 of containsAll method, of class List.
     * @description This test tests the behaviour of the method containsAll() when called on a list which contains multiple elements which equals to the parameter.
     * @expectedResults The parameter should be contained.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method add().
     * @preConditions The collection instance must be a new istance of Collection.
     * @postConditions The collection instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test
    public void testContainsAll_multiple() {
        instance.add("pippo");
        instance.add("pippo");
        instance.add("pluto");
        instance.add("paperino");
        List param = new List();
        param.add("pippo");
        param.add("pippo");
        param.add("pluto");
        boolean result = instance.containsAll(param);
        assertEquals("collezione che contiene più istanze del parametro", true, result);
    }
    
    /**
     * @title Test #1 of equals method, of class List.
     * @description This test tests the behaviour of the method equals() when two empty lists are compared
     * @expectedResults Two empty lists should equals.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test
    public void testEquals_empty() {
        List instance2 = new List();
        boolean result = instance.equals(instance2);
        assertEquals("confronto di due list vuoti", true, result);
    }
    
    /**
     * @title Test #2 of equals method, of class List.
     * @description This test tests the behaviour of the method equals() when the two lists don't contains the same elements.
     * @expectedResults Two empty lists should not equals.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method add.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test
    public void testEquals_differents() {
        instance.add("pippo");
        List instance2 = new List();
        boolean result = instance.equals(instance2);
        assertEquals("confronto di due list diversi", false, result);
    }
    
    /**
     * @title Test #3 of equals method, of class List.
     * @description This test tests the behaviour of the method equals() when the two lists contains the same elements. It's also tested that the method is simmetric.
     * @expectedResults Two empty lists should equals.
     * @actualResult As expected result.
     * @dependencies The correctness of this method depends on the correctness of method add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance doesn't have to be directly modified by the execution of the method tested.
     */
    @Test
    public void testEquals_equals() {
        instance.add("pippo");
        List other = new List();
        other.add("pippo");
        boolean result = instance.equals(other);
        assertEquals("confronto di due list uguali", true, result);
        result = other.equals(instance);
        assertEquals("il confronto deve essere simmetrico", true, result);
    }

    /**
     * @title Test #1 of hashCode method, of class List.
     * @description This test tests the behaviour of the method hashCode() when called on an empty List. The hash of a List is defined with a specific formula in the documentation.
     * @expectedResults The hashcode of an empty List must be 1.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of any other method.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testHashCode_empty() {
        int result = instance.hashCode();
        assertEquals("per definizione, l'hashcode di un list vuoto deve essere 1", 1, result);
    }
    
    /**
     * @title Test #2 of hashCode method, of class List.
     * @description This test tests the behaviour of the method hashCode() when called on a not-empty List. The hash of a List is defined with a specific formula in the documentation.
     * @expectedResults The hashcode of a list must equals to the result of the specified formula.
     * @actualResult As expected result.
     * @dependencies The correctness of this method does not depends on the correctness of add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance doesn't have to be modified by the execution of the method tested.
     */
    @Test
    public void testHashCode_notEmpty() {
        String elem1 = "pippo", elem2 = "pluto";
        instance.add(elem1);
        instance.add(elem2);
        int result = instance.hashCode();
        int expected = 31 + elem1.hashCode();
        expected = expected * 31 + elem2.hashCode();
        assertEquals("hash di un list non vuoto", expected, result);
    }

    /**
     * @title Test of hashCode and equals methods, of class List.
     * @description This test tests the coherence between the hashCode and equals methods. More in details, if two objects equals each other they must have the same hash. If two object has the same hash, not necessarly they equals each other.
     * @expectedResults The equals method is expected to be coherent with the hashcode. 
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance is not directly modified by the execution of the method tested.
     */
    @Test
    public void testHashEquals() {
        instance.add("pippo");
        int hash = instance.hashCode();
        List instance2 = new List();
        instance2.add("pippo");
        boolean result = (hash == instance2.hashCode()) && instance.equals(instance2);
        assertEquals("controllo coerenza hashCode ed equals", true, result);
    }

    /**
     * @title Test #1 of get method, of class List.
     * @description This test tests the behaviour of get() method when called on an list which contains many objects.
     * @expectedResults The method should return the element which correspond to the specified index.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testGet() {
        instance.add("pippo");
        instance.add("paperino");
        instance.add("pluto");
        Object result = instance.get(0);
        assertEquals("controllo posizione 0", "pippo", result);
        result = instance.get(1);
        assertEquals("controllo posizione 1", "paperino", result);
        result = instance.get(2);
        assertEquals("controllo posizione 2", "pluto", result);
    }
    
    /**
     * @title Test #2 of get method, of class List.
     * @description This test tests the behaviour of get() method when param are invalid. The method should throw an IndexOutOfBoundsException if the specified index is less than 0 or >= size().
     * @expectedResults The method should throw the expected exception.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet_exception1() {
        instance.get(instance.size());
    }
    
    /**
     * @title Test #2 of get method, of class List.
     * @description This test tests the behaviour of get() method when param are invalid. The method should throw an IndexOutOfBoundsException if the specified index is less than 0 or >= size().
     * @expectedResults The method should throw the expected exception.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet_exception2() {
        instance.get(-20);
    }

    /**
     * @title Test #1 of indexOf method, of class List.
     * @description This test tests the behaviour of indexOf() method when called on an empty list.
     * @expectedResults The method should always return -1, as the list is empty.
     * @actualResult As expected result.
     * @dependencies The correctness of the test does not depends on the correctness of any other method.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testIndexOf_empty() {
        int result = instance.indexOf("pippo");
        assertEquals("metodo invocato su lista vuota", -1, result);
    }
    
    /**
     * @title Test #2 of indexOf method, of class List.
     * @description This test tests the behaviour of indexOf() method when called on a list where the param is not contained.
     * @expectedResults The method should always return -1, as the element is not contained in the list.
     * @actualResult As expected result.
     * @dependencies The correctness of the test depends on the correctness of method add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testIndexOf_notContained() {
        instance.add("pluto");
        int result = instance.indexOf("pippo");
        assertEquals("metodo invocato su lista popolata, elemento non presente", -1, result);
    }
    
    /**
     * @title Test #3 of indexOf method, of class List.
     * @description This test tests the behaviour of indexOf() method when called on a list where the param is contained.
     * @expectedResults The method should return the index corresponding to the specified object. If duplicates are contained in the list, the lower index should be returned.
     * @actualResult As expected result.
     * @dependencies The correctness of the test depends on the correctness of method add() and get().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testIndexOf_contained() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("pippo");
        instance.add("pippo");
        int result = instance.indexOf("pippo");
        assertEquals("l'oggetto nella posizione trovata corrisponde al parametro", "pippo", instance.get(result));
        assertEquals("l'indice restituito è il minore tra quelli degli elementi uguali", 0, result);
    }
    
    /**
     * @title Test #4 of indexOf method, of class List.
     * @description This test tests the behaviour of indexOf() method when called using an invalid parameter. The method should throw a NullPointerException if the param is a null reference.
     * @expectedResults The method should throw the exception in the situation described above.
     * @actualResult As expected result.
     * @dependencies The correctness of the test does not depends on the correctness of any other method.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test(expected = NullPointerException.class)
    public void testIndexOf_exceptions() {
        instance.indexOf(null);
    }
    
    /**
     * @title Test #1 of lastIndexOf method, of class List.
     * @description This test tests the behaviour of lastIndexOf() method when called on an empty list.
     * @expectedResults The method should always return -1, as the list is empty.
     * @actualResult As expected result.
     * @dependencies The correctness of the test does not depends on the correctness of any other method.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testLastIndexOf_empty() {
        int result = instance.lastIndexOf("pippo");
        assertEquals("metodo invocato su lista vuota", -1, result);
    }
    
    /**
     * @title Test #2 of lastIndexOf method, of class List.
     * @description This test tests the behaviour of lastIndexOf() method when called on a list where the param is not contained.
     * @expectedResults The method should always return -1, as the element is not contained in the list.
     * @actualResult As expected result.
     * @dependencies The correctness of the test depends on the correctness of method add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testLastIndexOf_notContained() {
        instance.add("pluto");
        int result = instance.lastIndexOf("pippo");
        assertEquals("metodo invocato su lista popolata, elemento non presente", -1, result);
    }
    
    /**
     * @title Test #3 of lastIndexOf method, of class List.
     * @description This test tests the behaviour of lastIndexOf() method when called on a list where the param is contained.
     * @expectedResults The method should return the index corresponding to the specified object. If duplicates are contained in the list, the greater index should be returned.
     * @actualResult As expected result.
     * @dependencies The correctness of the test depends on the correctness of method add() and get().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testLastIndexOf_contained() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("pippo");
        instance.add("pippo");
        int result = instance.lastIndexOf("pippo");
        assertEquals("l'oggetto nella posizione trovata corrisponde al parametro", "pippo", instance.get(result));
        assertEquals("l'indice restituito è il minore tra quelli degli elementi uguali", 3, result);
    }
    
    /**
     * @title Test #4 of lastIndexOf method, of class List.
     * @description This test tests the behaviour of lastIndexOf() method when called using an invalid parameter. The method should throw a NullPointerException if the param is a null reference.
     * @expectedResults The method should throw the exception in the situation described above.
     * @actualResult As expected result.
     * @dependencies The correctness of the test does not depends on the correctness of any other method.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test(expected = NullPointerException.class)
    public void testLastIndexOf_exceptions() {
        instance.lastIndexOf(null);
    }

    //ONLY ITERATOR FUCNTIONS THAT ARE NOT TESTED BY COLLECTION TEST SUITE ARE TESTED HERE
    
    /**
     * @title Test #1 of listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by listIterator() method. More in details, it's tested the scenario of an iterator returned by an empty list.
     * @expectedResults The iterator of an empty list can't have a previous and a next element, as the list is empty.
     * @actualResult As expected result.
     * @dependencies This test has no dependencies on other class methods.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified by the execution of the method.
     */
    @Test
    public void testListIterator_empty() {
        HListIterator it = instance.listIterator();
        assertEquals("l'iteratore di una lista vuota non ha previous", false, it.hasPrevious());
        assertEquals("nextIndex deve ritornare size(), in quanto l'iteratore è al termine della lista", instance.size(), it.nextIndex());
        assertEquals("previousIndex deve ritornare -1, in quanto l'iteratore è all'inizio della lista", -1, it.previousIndex());
    }
    
    /**
     * @title Test #2 of listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by listIterator() method. More in details, this test tests the behaviour of a listiterator which is returned by a list which has only one element.
     * @expectedResults The iterator of should have next() element but not previous, as the iterator is initialized at the beginning of the list.
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correcntess of method add() and get().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified by the direct execution of the tested method.
     */
    @Test
    public void testListIterator_notEmpty() {
        instance.add("pippo");
        HListIterator it = instance.listIterator();
        assertEquals("inizializzato all'inizio della lista, non ha previous", false, it.hasPrevious());
        assertEquals("nextIndex deve ritornare l'indice del prossimo elemento", 0, it.nextIndex());
        assertEquals("previousIndex deve ritornare -1, in quanto l'iteratore è all'inizio della lista", -1, it.previousIndex());
        Object expected = it.next();
        assertEquals("l'iteratore ora ha un elemento precedente", true, it.hasPrevious());
        assertEquals("nextIndex deve ritornare size() in quanto non ci sono altri elementi", instance.size(), it.nextIndex());
        assertEquals("previousIndex deve ritornare l'indice dell'elemento appena attraversato", 0, it.previousIndex());
        Object result = it.previous();
        assertEquals("controllo su previous", expected, result);   
    }
    
    /**
     * @title Test #3 of listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by listIterator() method. More in details, it tests the correctness of modification method.
     * @expectedResults The iterator should modify elements of the list.
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correcntess of method add() and get().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be modified by the direct execution of the tested method.
     */
    @Test
    public void testListIterator_set() {
        instance.add("pluto");
        instance.add("pippo");
        HListIterator it = instance.listIterator();
        it.next();
        it.set("asso");
        it.next();
        it.set("paperino");
        assertEquals("controllo modifica su lista associata", "asso", instance.get(0));
        assertEquals("controllo modifica su lista associata", "paperino", instance.get(1));
        assertEquals("controllo coerenza iterator", "paperino", it.previous());
        assertEquals("controllo coerenza iterator", "asso", it.previous());
    }
    
    /**
     * @title Test #4 of listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by listIterator() method. More in details, it tests the correctness of add method.
     * @expectedResults The iterator should add elements to the main list.
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correcntess of method add(), get() and size().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be modified by the direct execution of the tested method.
     */
    @Test
    public void testListIterator_add() {
        instance.add("pluto");
        HListIterator it = instance.listIterator();
        int prev = it.previousIndex(), next = it.nextIndex();
        it.add("asso");
        boolean in = (it.nextIndex()==next+1) && (it.previousIndex()==prev+1);
        assertEquals("i risultati di nextIndex e previousIndex devono essere incrementati di 1 dall'add", true, in);
        assertEquals("nuovo elemento aggiunto all'inizio della lista", "asso", instance.get(0));
        assertEquals("dimensione aumentata", 2, instance.size());
        it.next();
        it.add("paperino");
        assertEquals("nuovo elemento aggiunto alla fine della lista", "paperino", instance.get(2));
        assertEquals("dimensione aumentata", 3, instance.size());
    }
    
    /**
     * @title Test #5 of listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by listIterator() method. More in details, it tests a full iteration of the list.
     * @expectedResults The iterator should iterate all the elements of the list, in both directions (ascending and descending).
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correcntess of methods add() and get().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be modified by the direct execution of the tested method.
     */
    @Test
    public void testListIterator_iteration() {
        instance.add("pluto");
        instance.add("pippo");
        instance.add("topolino");
        instance.add("pluto");
        instance.add("pippo");
        instance.add("paperino");
        int i = 0;
        HListIterator it = instance.listIterator();
        while(it.hasNext()) {
            assertEquals("ascending", instance.get(i++), it.next());
        }
        while(it.hasPrevious()) {
            assertEquals("descending", instance.get(--i), it.previous());
        }
    }
    
    /**
     * @title Test #6 of listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by listIterator() method. More in details, it tests a complex sequence of operations performed with the iterator.
     * @expectedResults The iterator should successfully conclude the sequence of operations.
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correcntess of method add(), get(), size().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be modified by the direct execution of the tested method.
     */
    @Test
    public void testListIterator_complex() {
        instance.add("pluto");
        instance.add("pippo");
        instance.add("topolino");
        HListIterator it = instance.listIterator();
        it.add("asso");
        it.add("sasso");
        assertEquals("elementi aggiunti in testa", true, instance.get(0).equals("asso") && instance.get(1).equals("sasso"));
        assertEquals("dimensione aggiornata", 5, instance.size());
        it.next();
        it.remove();
        assertEquals("controllo remove", "pippo", instance.get(2));
        it.next();
        it.next();
        assertEquals("fine collezione", false, it.hasNext());
        it.previous();
        it.remove();
        assertEquals("fine collezione", false, it.hasNext());
        assertEquals("controllo ultimo elemento", "pippo", instance.get(2));
        assertEquals("controllo indici prev e next", true, it.previousIndex()==2 && it.nextIndex()==instance.size());
    }
    
    /**
     * @title Test #7 of listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by listIterator() method. More in details, it tests exceptions thrown by the object. IllegalArgumentException is thrown by the method add when is called with a null reference as parameter. NoSuchElementException is thrown by next or before if there is not a next or befor element. IllegalStateException is thrown by set method if the current element of the iterator has been removed or it wasn't called next() or previous() before, IllegalArgumentException thrown if the parameter is a null reference. IllegalStateException is thrown by the method if the current element was already removed or if it wasn't called next() method yet.
     * @expectedResults A listiterator should throw exceptions when used in specific ways, defined above.
     * @actualResult As expected result.
     * @dependencies The correctness of this test does not depends on the correctness of other methods
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified by the direct execution of the tested method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testListIterator_exception1() {
        //add
        HListIterator it = instance.listIterator();
        it.add(null);
    }
    
    /**
     * @title Test #8 of listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by listIterator() method. More in details, it tests exceptions thrown by the object. IllegalArgumentException is thrown by the method add when is called with a null reference as parameter. NoSuchElementException is thrown by next or before if there is not a next or befor element. IllegalStateException is thrown by set method if the current element of the iterator has been removed or it wasn't called next() or previous() before, IllegalArgumentException thrown if the parameter is a null reference. IllegalStateException is thrown by the method if the current element was already removed or if it wasn't called next() method yet.
     * @expectedResults A listiterator should throw exceptions when used in specific ways, defined above.
     * @actualResult As expected result.
     * @dependencies The correctness of this test does not depends on the correctness of other methods
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified by the direct execution of the tested method.
     */
    @Test(expected = NoSuchElementException.class)
    public void testListIterator_exception2() {
        //previous
        HListIterator it = instance.listIterator();
        it.previous();
    }
    
    /**
     * @title Test #9 of listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by listIterator() method. More in details, it tests exceptions thrown by the object. IllegalArgumentException is thrown by the method add when is called with a null reference as parameter. NoSuchElementException is thrown by next or before if there is not a next or befor element. IllegalStateException is thrown by set method if the current element of the iterator has been removed or it wasn't called next() or previous() before, IllegalArgumentException thrown if the parameter is a null reference. IllegalStateException is thrown by the method if the current element was already removed or if it wasn't called next() method yet.
     * @expectedResults A listiterator should throw exceptions when used in specific ways, defined above.
     * @actualResult As expected result.
     * @dependencies The correctness of this test does not depends on the correctness of other methods
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified by the direct execution of the tested method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testListIterator_exception3() {
        //set
        instance.add("pippo");
        HListIterator it = instance.listIterator();
        it.next();
        it.set(null);
    }
    
    /**
     * @title Test #10 of listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by listIterator() method. More in details, it tests exceptions thrown by the object. IllegalArgumentException is thrown by the method add when is called with a null reference as parameter. NoSuchElementException is thrown by next or before if there is not a next or befor element. IllegalStateException is thrown by set method if the current element of the iterator has been removed or it wasn't called next() or previous() before, IllegalArgumentException thrown if the parameter is a null reference. IllegalStateException is thrown by the method if the current element was already removed or if it wasn't called next() method yet.
     * @expectedResults A listiterator should throw exceptions when used in specific ways, defined above.
     * @actualResult As expected result.
     * @dependencies The correctness of this test does not depends on the correctness of other methods
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified by the direct execution of the tested method.
     */
    @Test(expected = exceptions.IllegalStateException.class)
    public void testListIterator_exception4() {
        HListIterator it = instance.listIterator();
        it.set("pippo");
    }
    
    /**
     * @title Test #11 of listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by listIterator() method. More in details, it tests exceptions thrown by the object. IllegalArgumentException is thrown by the method add when is called with a null reference as parameter. NoSuchElementException is thrown by next or before if there is not a next or befor element. IllegalStateException is thrown by set method if the current element of the iterator has been removed or it wasn't called next() or previous() before, IllegalArgumentException thrown if the parameter is a null reference. IllegalStateException is thrown by the method if the current element was already removed or if it wasn't called next() method yet.
     * @expectedResults A listiterator should throw exceptions when used in specific ways, defined above.
     * @actualResult As expected result.
     * @dependencies The correctness of this test does not depends on the correctness of other methods
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified by the direct execution of the tested method.
     */
    @Test(expected = exceptions.IllegalStateException.class)
    public void testListIterator_exception5() {
        instance.add("pippo");
        HListIterator it = instance.listIterator();
        it.next();
        it.remove();
        it.set("pluto");
    }
    
    /**
     * @title Test #1 of parametric listIterator method, of class List.
     * @description This test tests the only behaviour of ListIterator which has not been tested yet: the listIterator starts in the right point of the list, with correct next() and previous() elements. All the other behaviours has already been tested by normal listIterator() method tests.
     * @expectedResults A ListIterator istantiated with an index should have as first next element the corresponding index element, as previous it should have the element at index (index-1).
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correctness of methods add(), size(), get()
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be modified by the direct execution of the tested method.
     */
    @Test
    public void testParametricListIterator() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("topolino");
        instance.add("paperino");
        HListIterator it = instance.listIterator(2);
        Object result = it.next();
        assertEquals("controllo su elemento successivo", "topolino", result);
        it.previous();
        result = it.previous();
        assertEquals("controllo su elemento precedente", "pluto", result);
    }
    
    /**
     * @title Test #1 of parametric listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by the parametric listIterator method when a bad type of parameter is used (tests exceptions)
     * @expectedResults A listiterator should throw exceptions when the index specified does not respect the boundary of the list (less than 0 or >= size())
     * @actualResult As expected result.
     * @dependencies The correctness of this test does not depends on the correctness of other methods.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified by the direct execution of the tested method.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testParametricListIterator_exception1() {
        instance.listIterator(-1);
    }
    
    /**
     * @title Test #2 of parametric listIterator method, of class List.
     * @description This test tests the behaviour of the ListIterator returned by the parametric listIterator method when a bad type of parameter is used (tests exceptions)
     * @expectedResults A listiterator should throw exceptions when the index specified does not respect the boundary of the list (less than 0 or >= size())
     * @actualResult As expected result.
     * @dependencies The correctness of this test does not depends on the correctness of other methods.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified by the direct execution of the tested method.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testParametricListIterator_exception2() {
        instance.listIterator(instance.size()+1);
    }

    /**
     * @title Test #1 of remove(int) method, of Class list.
     * @description This test tests the behaviour of remove(int) method when called using a valid index.
     * @expectedResults The element at the specified index has been removed from the list and the returned element correspond to the value of removed index.
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correctness of methods add(), get(), contains() and size().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be directly modified  by the execution of the method.
     */
    @Test
    public void testRemoveInt_valid() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("topolino");
        Object expected = instance.get(0);
        Object result = instance.remove(0);
        assertEquals("l'elemento è stato rimosso dalla lista", expected, result);
        assertEquals("controllo sulla dimensione", 2, instance.size());
        assertEquals("controllo che l'elemento non sia più contenuto", false, instance.contains(result));
    }
    
    /**
     * @title Test #2 of remove(int) method, of Class list.
     * @description This test tests the behaviour of remove(int) method when called using a valid index. More in details, the aim of this test is to check that all the elements on the right of the removed element have been shifted by one postion.
     * @expectedResults All the elements on the right of the specified index should be shifted of one position to the left.
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correctness of methods add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be directly modified  by the execution of the method.
     */
    @Test
    public void testRemoveInt_shift() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("topolino");
        Object result = instance.remove(0);
        boolean check = instance.get(0).equals("pluto") && instance.get(1).equals("topolino");
        assertEquals("gli elementi a destra di quello rimosso sono stati scalati di uno", true, check);
    }

    /**
     * @title Test #3 of remove(int) method, of Class list.
     * @description This test tests the behaviour of remove(int) method when called using an invalid parameter.
     * @expectedResults The method should throw an IndexOutOfBoundsException if the specified index is less than 0 or >= size().
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correctness of methods add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be directly modified  by the execution of the method.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveInt_exception1() {
        instance.remove(instance.size());
    }
    
    /**
     * @title Test #4 of remove(int) method, of Class list.
     * @description This test tests the behaviour of remove(int) method when called using an invalid parameter.
     * @expectedResults The method should throw an IndexOutOfBoundsException if the specified index is less than 0 or >= size().
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correctness of methods add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be directly modified  by the execution of the method.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveInt_exception12() {
        instance.remove(-20);
    }

    /**
     * @title Test #1 of remove method, of class List.
     * @description This test tests the behaviour of remove() method when called on a not-empty collection and the specified element is contained. More in details, it tests wheter the removed element is that one with the lowest index or not. It also tests that the method removes only one element from the list.
     * @expectedResults The element remove is the one which has the lowest index between the equals object of the list.
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of methods add(), isEmpty() and get().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be modified directly by the execution of the method.
     */
    @Test
    public void testRemove() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("pippo");
        boolean result = instance.remove("pippo");
        String[] expected = {"pluto", "pippo"};
        for(int i=0; i<2; i++) result &= instance.get(i).equals(expected[i]);
        assertEquals("verifica che, in caso di duplicati, si rimuova l'istanza con indice minore", true, result);
        assertEquals("l'elemento rimosso deve essere solo uno", 2, instance.size());
    }
    
    /**
     * @title Test #1 of retainAll method, of Class list.
     * @description This test tests the behaviour of retainAll() method when called on a collection which has multiple istances of an element contained in the param.
     * @expectedResults All the instances of the object should be mantained in the collection.
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correctness of methods add() and size().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be directly modified  by the execution of the method.
     */
    @Test
    public void testRetainAll_duplicates() {    
        instance.add("asso");
        instance.add("pluto");
        instance.add("pluto");
        instance.add("pluto");
        HCollection param = new List();
        param.add("pluto");
        param.add("asso");
        boolean result = instance.retainAll(param);
        assertEquals("tutti gli elementi devono essere mantenuti", false, result);
        assertEquals("la dimensione non deve variare", 4, instance.size());
    }
    
    /**
     * @title Test #1 of set method, of Class list.
     * @description This test tests the behaviour of set() method when called using a valid index.
     * @expectedResults The object returned by the method must equals to the old value of the object at the specified index. The object value must be updated, whereas the rest of the list should not be modified.
     * @actualResult As expected result.
     * @dependencies The correctness of this test depends on the correctness of methods add(), get() and toArray().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be directly modified  by the execution of the method.
     */
    @Test
    public void testSet_valid() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("topolino");
        Object[] arr = instance.toArray();
        Object result = instance.set(0, "pluto");
        assertEquals("controllo che l'oggetto restituito dal metodo corrisponda a quello che è stato sostituito ", "pippo", result);
        assertEquals("controllo che l'oggetto all'indice specificato sia stato effettivamente sostituito", "pluto", instance.get(0));
        boolean check = true;
        for(int i=1; i<arr.length; i++) check = check && arr[i].equals(instance.get(i));
        assertEquals("controllo che il resto della lista non sia stato alterato dalla modifica", true, check);
    }
    
    /**
     * @title Test #2 of set method, of Class list.
     * @description This test tests the behaviour of set() method when called using invalid parameters.
     * @expectedResults The method is expected to throw a NullPointerException if the new value is a null reference, IndexOutOfBoundsException if the specified index is less than 0 or >=size().
     * @actualResult As expected result.
     * @dependencies The correctness of this test does not depends on the correctness of other methods.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be directly modified  by the execution of the method.
     */
    @Test(expected = NullPointerException.class)
    public void testSet_exception1() {
        instance.add("pippo");
        instance.set(0, null);
    }
    
    /**
     * @title Test #3 of set method, of Class list.
     * @description This test tests the behaviour of set() method when called using invalid parameters.
     * @expectedResults The method is expected to throw a NullPointerException if the new value is a null reference, IndexOutOfBoundsException if the specified index is less than 0 or >=size().
     * @actualResult As expected result.
     * @dependencies The correctness of this test does not depends on the correctness of other methods.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be directly modified  by the execution of the method.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSet_exception2() {
        instance.add("pippo");
        instance.set(-1, "pippo");
    }
    
    /**
     * @title Test #4 of set method, of Class list.
     * @description This test tests the behaviour of set() method when called using invalid parameters.
     * @expectedResults The method is expected to throw a NullPointerException if the new value is a null reference, IndexOutOfBoundsException if the specified index is less than 0 or >=size().
     * @actualResult As expected result.
     * @dependencies The correctness of this test does not depends on the correctness of other methods.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be directly modified  by the execution of the method.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSet_exception3() {
        instance.add("pippo");
        instance.set(instance.size(), "pippo");
    }

    /**
     * @title Test #1 of subList method, of Class list.
     * @description This test tests the behaviour of subList() method when called on an empty list. When modify, the main list will always equal to the sublist.
     * @expectedResults The returned list must be empty and should always equals to the upper list.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctnes of methods isEmpty(), add(), size(), remove(), equals().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be modified directly by the execution of the method.
     */
    @Test
    public void testSubList_empty() {
        HList sub = instance.subList(0, 0);
        assertEquals("la sottolista è vuota", true, sub.isEmpty());
        sub.add("pippo");
        sub.add("pluto");
        assertEquals("aggiunte apportate alla sottolista", 2, sub.size());
        assertEquals("aggiunte apportate alla lista madre", 2, instance.size());
        assertEquals("le due liste si equivalgono", true, sub.equals(instance));
        sub.remove(0);
        assertEquals("rimozioni apportate alla lista madre", 1, instance.size());
        assertEquals("le due liste si equivalgono", true, sub.equals(instance));
    }
    
    /**
     * @title Test #2 of subList method, of Class list.
     * @description This test tests the behaviour of subList() method when called on a not-empty list, using as parameters (0, size()) (the sublist is a view of all the main list).
     * @expectedResults The returned list must contains all the elements of the main list and must be alway equals to it.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctnes of methods add, size(), equals() and remove().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be modified directly by the execution of the method.
     */
    @Test
    public void testSubList_all() {
        instance.add("pippo");
        instance.add("pluto");
        HList sub = instance.subList(0, instance.size());
        assertEquals("la sottolista ha la stessa dimensione della main", 2, sub.size());
        assertEquals("le due liste sono equivalenti", true, sub.equals(instance));
        sub.add("pippo");
        sub.add("pluto");
        assertEquals("aggiunte apportate alla lista madre", 4, instance.size());
        assertEquals("le due liste si equivalgono", true, sub.equals(instance));
        sub.remove(0);
        assertEquals("rimozioni apportate alla lista madre", 3, instance.size());
        assertEquals("le due liste si equivalgono", true, sub.equals(instance));
    }
    
    /**
     * @title Test #3 of subList method, of Class list.
     * @description This test tests the behaviour of subList() when the sublist is a a view of just a portion of the list.
     * @expectedResults The returned list must reflect all the changes to the main list.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctnes of methods add(), size(), get() and remove().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be modified directly by the execution of the method.
     */
    @Test
    public void testSubList_portion() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("asso");
        instance.add("pluto");
        HList sub = instance.subList(1, 3);
        assertEquals("controllo dimensione", 2, sub.size());
        boolean result = sub.get(0).equals("pluto") && sub.get(1).equals("asso");
        assertEquals("la sottolista deve essere una vista della sola porzione specificata", true, result);
        sub.add("pippo");
        sub.add("pluto");
        assertEquals("aggiunte apportate alla lista madre", 6, instance.size());
        assertEquals("controllo di uno degli elementi aggiunti nella lista principale", "pippo", instance.get(3));
        sub.remove("pippo");
        sub.remove("pluto");
        assertEquals("rimozioni apportate alla lista madre", 4, instance.size());
    }
    
    /**
     * @title Test #4 of subList method, of Class list.
     * @description This test tests the behaviour of subList() when the sublist return is used to perform a complex sequence of operation.
     * @expectedResults The returned list must reflect all the changes to the main list and must be coherent with the operations specified.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctnes of methods add, remove, size, get, addAll, removeAll.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be modified directly by the execution of the method.
     */
    @Test
    public void testSubList_complex() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("asso");
        instance.add("pluto");
        HList sub = instance.subList(0, 3);
        assertEquals("controllo dimensione", 3, sub.size());
        boolean result = sub.get(0).equals("pippo") && sub.get(1).equals("pluto") && sub.get(2).equals("asso");
        assertEquals("la sottolista deve essere una vista della sola porzione specificata", true, result);
        sub.add("pippo");
        assertEquals("aggiunte apportate alla lista madre", 5, instance.size());
        assertEquals("controllo di uno degli elementi aggiunti nella lista principale", "pippo", instance.get(3));
        sub.remove(0);
        assertEquals("rimozioni apportate alla lista madre", 4, instance.size());
        assertEquals("controllo di uno degli elementi aggiunti nella lista principale", "pluto", instance.get(0));
        HCollection param = new List();
        param.add("pietra");
        param.add("asso");
        sub.addAll(0, param);
        assertEquals("rimozioni apportate alla lista madre", 6, instance.size());
        assertEquals("controllo elementi aggiunti", true, instance.get(0).equals("pietra") && instance.get(1).equals("asso"));
        param.remove("pietra");
        sub.removeAll(param);
        assertEquals("rimozioni apportate alla lista madre", 4, instance.size());
        assertEquals("controllo get", "pietra", sub.get(0));
    }
    
    /**
     * @title Test #5 of subList method, of Class list.
     * @description This test tests the behaviour of subList() when the sublist is used to clear a part of the main list.
     * @expectedResults All the elements contained in the sublist must be removed from the main list.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctnes of methods add, size and get.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be modified directly by the execution of the method.
     */
    @Test
    public void testSubList_clear() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("asso");
        instance.add("pietra");
        HList sub = instance.subList(0, 3);
        sub.clear();
        assertEquals("rimozioni apportate alla lista madre", 1, instance.size());
        assertEquals("unico elemento rimasto era fuori dalla sottolista", "pietra", instance.get(0));
    }
    
    /**
     * @title Test #6 of subList method, of Class list.
     * @description This test tests the behaviour of subList() when the sublist is used to find the indexOf or lastIndexOf a parameter in a view of the main list.
     * @expectedResults The minIndex and maxIndex of an object in a specified view of the list.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctnes of method add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testSubList_minMaxIndex() {
        instance.add("pippo");
        instance.add("pippo");
        instance.add("pippo");
        instance.add("pippo");
        HList sub = instance.subList(1, 4);
        int min = sub.indexOf("pippo"), max = sub.lastIndexOf("pippo");
        assertEquals("minimo indice nella vista", 0, min);
        assertEquals("massimo indice nella vista", 2, max);
    }
    
    /**
     * @title Test #7 of subList method, of Class list.
     * @description This test tests the behaviour of subList() when the sublist is used to find the set a new value for an element contained in the list.
     * @expectedResults The modifications should be reflected to the main list.
     * @actualResult As expected result.
     * @dependencies This test correctness depends on the correctnes of methods .
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be modified directly by the execution of the method.
     */
    @Test
    public void testSubList_set() {
        instance.add("pippo");
        instance.add("pluto");
        instance.add("paperino");
        HList sub = instance.subList(1, 2);
        Object expected = instance.get(1);
        Object result = sub.set(0, "asso");
        assertEquals("vecchio valore oggetto", expected, result);
        assertEquals("nuovo valore settato correttamente", "asso", instance.get(1));
    }
    
    /**
     * @title Test #1 of toArray method, of Class list.
     * @description This test tests the behaviour of toArray() method when called on a non-empty list. More in details, it tests that the returned array elements are placed in the right order defined by the iterator.
     * @expectedResults The returned array must contains the list elements placed in the order defined by the iterator.
     * @actualResult As expected result.
     * @dependencies This test has no correctness dependencies on other class methods.
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be modified directly by the execution of the method.
     */
    @Test
    public void testToArray_notEmpty() {    
        instance.add("pippo");
        instance.add("pluto");
        
        Object[] result = instance.toArray();
        HIterator it = instance.iterator();
        boolean check = true;
        for(int i=0; it.hasNext() && i<result.length; i++) {
            check = check && it.next().equals(result[i]);
        }
        assertEquals("gli elementi sono copiati nell'array nell'ordine definito dall'iteratore", true, check);
    }
    
    /**
     * @title Test #1 of parametric toArray method, of class List.
     * @description This test tests the behaviour of parametric method toArray() when is called on a not-empty list passing an array which length is greater than list size. More in detail, it tests that the elements are returned in the right order.
     * @expectedResults The array returned should be exactly the one which has been passed as parameter, but the first size() must be modified, as they should contains the elements of this list in the right order. 
     * @actualResult As expected result.
     * @dependencies Depends on the correctness of method add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should not be directly modified by the call of the method tested.
     */
    @Test
    public void testParametricToArray_notEmpty() {
        String[] param = new String[10];
        for(int i=0; i<10; i++) param[i] = "pippo";
        instance.add("pluto");
        instance.add("paperino");
        instance.add("topolino");
        
        Object[] result = instance.toArray(param);
        HIterator it = instance.iterator();
        boolean check = true;
        for(int i=0; it.hasNext(); i++) {
            check = check && it.next().equals(result[i]);
        }
        assertEquals("gli elementi della lista sono stati copiati nell'ordine definito dall'iteratore", true, check);
    }
    
    /**
     * @title Test #2 of parametric toArray method, of class List.
     * @description This test tests the behaviour of parametric method toArray() when is called on a not-empty collection using an array which is not big enough to contain the whole list. More in details, the it tests the order of the returned array.
     * @expectedResults The array returned should be a new istance of a generic Object array (the parameter should not be modified), and it should contains all the elements contained in the list in the proper order.
     * @actualResult As expected result.
     * @dependencies Depends on the correctess of method add().
     * @preConditions The list instance must be a new istance of List.
     * @postConditions The list instance should be directly modified by the call of this method.
     */
    @Test
    public void testParametricToArray_notBigEnough() {
        String[] param = new String[1];
        param[0] = "pippo";
        instance.add("pluto");
        instance.add("paperino");
        instance.add("topolino");
        
        Object[] result = instance.toArray(param);
        HIterator it = instance.iterator();
        boolean check = true;
        for(int i=0; it.hasNext(); i++) {
            check = check && it.next().equals(result[i]);
        }
        assertEquals("gli elementi della lista sono stati copiati nell'ordine definito dall'iteratore", true, check);
    }
    
}