package src.test.java;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.*;

import src.main.java.com.solution.Solution;
import src.main.resources.classes.ItemPrice;

class testSolution {

    // Test cases for core functionality
    HashMap<Character, ItemPrice> testItemPrices;
    HashMap<Character, Integer> testBasket;
    Scanner scanner;
    ByteArrayOutputStream byteArrayOutputStream;
    private final PrintStream systemOut = System.out;


    @BeforeClass
    private void setup(){
        scanner = new Scanner(System.in);

        testItemPrices = new HashMap<Character, ItemPrice>();
        ItemPrice itemPriceA = new ItemPrice(0.50, 3, 1.25);
        ItemPrice itemPriceB = new ItemPrice(1.0, 0, 0);
        
        testItemPrices.put('A', itemPriceA);
        testItemPrices.put('B', itemPriceB);

        testBasket = new HashMap<Character, Integer>();
        byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    void simulateInput(String desiredSimulatedInput){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(desiredSimulatedInput.getBytes());
        System.setIn(byteArrayInputStream);
    }
    
    @Test
    public void testAddNewPrice(){
        testItemPrices = Solution.priceInsertion(scanner, testItemPrices);

        simulateInput("C");
        simulateInput("0.75");
        simulateInput("2");
        simulateInput("1.0");

        assertEquals("New item C added.", byteArrayOutputStream.toString().trim());


    }

    @Test
    public void testAddNewPriceInvalid(){}

    @Test
    public void testAddNewPriceEmpty(){}


    @Test
    public void testStartNewBucket(){}

    @Test
    public void testItemScan(){}

    @Test
    public void testItemScanInvalid(){}

    @Test
    public void testItemScanEmpty(){}

    @Test
    public void testItemScanDeal(){}


    @Test
    public void testFinalTotal(){}

    @Test 
    public void testFinalTotalEmpty(){}

    @AfterClass
    public void tearDown() {
        System.setOut(systemOut);
    }
}
