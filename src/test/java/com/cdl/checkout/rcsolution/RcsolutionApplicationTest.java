package com.cdl.checkout.rcsolution;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;


@ExtendWith(MockitoExtension.class)
public class RcsolutionApplicationTest {
    
    // Test cases for core functionality
    static HashMap<Character, ItemPrice> testItemPrices;
    static HashMap<Character, Integer> testBasket;
    static Scanner scanner;
    static ByteArrayOutputStream byteArrayOutputStream;
    static PrintStream systemOut = System.out;

    @BeforeClass
    public static void setup(){
        scanner = Mockito.mock(Scanner.class);

        testItemPrices = new HashMap<Character, ItemPrice>();
        ItemPrice itemPriceA = new ItemPrice(0.50, 3, 1.25);
        ItemPrice itemPriceB = new ItemPrice(1.0, 0, 0);

        testItemPrices.put('A', itemPriceA);
        testItemPrices.put('B', itemPriceB);

        testBasket = new HashMap<Character, Integer>();
        byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    @Test
    public void TestAddNewPrice(){

        Mockito.when(scanner.nextLine()).thenReturn("C", "0.75", "2", "1.0", "n");
        
        testItemPrices = RcsolutionApplication.priceInsertion(scanner, testItemPrices);

        assertEquals(Double.valueOf(0.75), testItemPrices.get('C').getPrice());
        assertEquals(2, testItemPrices.get('C').getSpecialAmount());
        assertEquals(Double.valueOf(1.0), testItemPrices.get('C').getSpecialPrice());
    }

    @Test
    public void testAddNewPriceInvalid(){
        Mockito.when(scanner.nextLine()).thenReturn("D", "A", "2", "1.0", "n");
        
        testItemPrices = RcsolutionApplication.priceInsertion(scanner, testItemPrices);

        assertThat(systemOut.toString(), containsString("Please enter a value for each"));

    }

    @Test
    public void testAddNewPriceEmpty(){
        Mockito.when(scanner.nextLine()).thenReturn("", "C", "0.75", "2", "1.0", "n");
        
        testItemPrices = RcsolutionApplication.priceInsertion(scanner, testItemPrices);

        assertThat(systemOut.toString(), containsString("Please enter a value for each"));
    }


    @Test
    public void testItemScan(){
        Mockito.when(scanner.nextLine()).thenReturn("A");

        testBasket = RcsolutionApplication.basketInsertion(scanner, testItemPrices, testBasket);

        assertEquals(1, testBasket.get('A'));
    
    }

    @Test
    public void testItemScanInvalid(){
        Mockito.when(scanner.nextLine()).thenReturn("Z", "A");

        testBasket = RcsolutionApplication.basketInsertion(scanner, testItemPrices, testBasket);

        assertThat(systemOut.toString(), containsString("Item Z is not a valid item, please try another item"));
        
    }

    @Test
    public void testItemScanEmpty(){

        Mockito.when(scanner.nextLine()).thenReturn("", "B");

        testBasket = RcsolutionApplication.basketInsertion(scanner, testItemPrices, testBasket);

        assertEquals(1, testBasket.get('B'));
    }


    @Test
    public void testFinalTotal(){
        assertEquals(Double.valueOf(2.25), RcsolutionApplication.calculateCurrentTotal(testBasket, testItemPrices));
    }

    @Test 
    public void testFinalTotalEmpty(){
        assertEquals(Double.valueOf(0), RcsolutionApplication.calculateCurrentTotal(new HashMap<>(), testItemPrices));
    }

    @AfterClass
    public static void tearDown() {
        System.setOut(systemOut);
    }
}