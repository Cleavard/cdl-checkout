package com.cdl.checkout.rcsolution;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;


public class RcsolutionApplication {

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<Character, ItemPrice> itemPrices = new HashMap<Character, ItemPrice>();
        HashMap<Character, Integer> basket = new HashMap<Character, Integer>();

        clearScreen();

        mainLoop : while (true) {
            System.out.println("What would you like to do?");
            //read user input
            String userInput = scanner.nextLine().toLowerCase();


            switch (userInput) {
                case "price":
                    itemPrices = priceInsertion(scanner, itemPrices);
                    continue;
                case "shop":
                    basket = basketInsertion(scanner, itemPrices, basket);
                    continue;
                case "exit":
                    break mainLoop;
                
            }
        }

        scanner.close();
    }

    /**
     * Allows the terminal user to enter an item name when promted to add it their basket.
     * This can continue as many times as the user desires to enter as many products as desired.
     * 
     * @param scanner
     * @param itemPrices        List of current item prices
     * @param basket            List of current user bucket
     * @return                  Updated basket
     */
    public static HashMap<Character,Integer> basketInsertion(Scanner scanner, HashMap<Character, ItemPrice> itemPrices,
            HashMap<Character, Integer> basket) {
        shoppingBasketLoop : while(true){

            System.out.println("\n" + "Please enter an item name to add to basket: ");
            String line = scanner.nextLine();

            if (line.isBlank()) {
                continue;
            }

            if (line.length() > 1){
                // Not a valid input, should only be one char long
                System.out.println("Invalid entry, items are only one character. Please try again:");
            } else {
                Character itemName = line.charAt(0);
                if (itemPrices.keySet().contains(itemName)) {
                    // Valid Item, add to basket
                    System.out.println(String.format("Adding 1 %s to the basket.", itemName));
                    basket.merge(itemName, 1, Integer::sum);

                    System.out.println(String.format("Current basket total is £%s", Double.toString(calculateCurrentTotal(basket, itemPrices))));

                } else {
                    // Invalid Item
                    System.out.println(String.format("Item %s is not a valid item, please try another item:", itemName));
                }
            }
            addAnotherItemToBaskerChoiceLoop : while (true) {
                System.out.println("Add another item? (y/n)");
                String userChoice = scanner.nextLine().toLowerCase();
                if (userChoice.equals("n") || userChoice.equals("no")) {
                    break shoppingBasketLoop;
                } else if (userChoice.equals("y") || userChoice.equals("yes")){
                    break addAnotherItemToBaskerChoiceLoop;
                } 
                System.out.println("Invalid entry");
            }
        }
        return basket;
    }

    
    public static HashMap<Character, ItemPrice> priceInsertion(Scanner scanner, HashMap<Character, ItemPrice> itemPrices) {
        priceInputLoop : while (true) {
            try {
                System.out.println("Enter item name: ");
                Character newItemName = scanner.nextLine().toUpperCase().charAt(0);
                if (!Character.isLetter(newItemName)) {
                    System.out.println("Please enter a valid letter");
                    continue;
                }

                System.out.println("Enter price: ");
                double price = Double.parseDouble(scanner.nextLine());

                System.out.println("Enter special amount: ");
                int specialAmount = Integer.parseInt(scanner.nextLine());

                System.out.println("Enter special price: ");
                double specialPrice = Double.parseDouble(scanner.nextLine());


                System.out.println(String.format("New item %s added. ", newItemName));
                ItemPrice newItemPrice = new ItemPrice(price, specialAmount, specialPrice);
                itemPrices.put(newItemName, newItemPrice);

                addAnotherItemChoiceLoop : while (true) {
                    System.out.println("Add another item? (y/n)");
                    String userChoice = scanner.nextLine().toLowerCase();
                    if (userChoice.equals("n") || userChoice.equals("no")) {
                        break priceInputLoop;
                    } else if (userChoice.equals("y") || userChoice.equals("yes")){
                        break addAnotherItemChoiceLoop;
                    } 
                    System.out.println("Invalid entry");
                }

            } catch (NullPointerException | StringIndexOutOfBoundsException e) {
                System.out.println("Please enter a value for each");
            } catch (NumberFormatException e){
                System.out.println("Please enter a valid number");
            }
        }
        
        return itemPrices;
    }

    public static Double calculateCurrentTotal(HashMap<Character, Integer> basket, HashMap<Character, ItemPrice> itemPrices){
        Double total = 0.0;

        for (Entry<Character, Integer> basketEntry : basket.entrySet()) {
            ItemPrice currentItemPrice = itemPrices.get(basketEntry.getKey());
            if (currentItemPrice.getSpecialAmount() < 1) {
              // No special deal
                total += currentItemPrice.getPrice() * basketEntry.getValue();
                continue;
            } 

            total += (basketEntry.getValue() / currentItemPrice.getSpecialAmount()) * currentItemPrice.getSpecialPrice();

            total += (basketEntry.getValue() % currentItemPrice.getSpecialAmount()) * currentItemPrice.getPrice();
        }

        return total;
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
}
