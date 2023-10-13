package com.cdl.checkout.rcsolution;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

public class RcsolutionApplication {

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<Character, ItemPrice> itemPrices = new HashMap<Character, ItemPrice>();
        HashMap<Character, Integer> basket = new HashMap<Character, Integer>();

        clearTerminal();

        mainLoop : while (true) {
            System.out.println("What would you like to do?");
            // read user input
            String userInput = scanner.nextLine().toLowerCase();
            System.out.println();

            switch (userInput) {
                case "price":
                    itemPrices = priceInsertion(scanner, itemPrices);
                    continue;
                case "menu":
                    for (Entry<Character, ItemPrice> entry : itemPrices.entrySet()) {
                        if (entry.getValue().getSpecialAmount() == 0) {
                            System.out.println(String.format("Name:%s Price:£%.2f ", entry.getKey(), entry.getValue().getPrice()));
                        } else {
                            ItemPrice itemPrice = entry.getValue();
                            System.out.println(String.format("Name:%s Price:£%.2f Deal:%d for £%.2f", entry.getKey(), itemPrice.getPrice(), itemPrice.getSpecialAmount(), itemPrice.getSpecialPrice()));
                        }
                        System.out.println();
                        continue;
                    }
                    continue;
                case "shop":
                    basket = basketInsertion(scanner, itemPrices, basket);
                    continue;
                case "total":
                    System.out.println(String.format("Basket total is £%.2f", calculateCurrentTotal(basket, itemPrices)));
                    continue;
                case "help":
                    System.out.println("price: Enters admin mode for creation of new items and their prices.");
                    System.out.println("shop: Enters shopping mode for creation of a basket and addition of items with running totals.");
                    System.out.println("menu: Prints info on all items currently present in the price system.");
                    System.out.println("total: Prints total cost of the current basket.");
                    System.out.println("clear: Removes all previous text from the terminal for a fresh start. Does not effect the items or basket.");
                    System.out.println("help: You should already know what this does.");
                    System.out.println("exit: End the program.\n");
                    continue;
                case "duck":
                    System.out.println("Quack\n");
                    continue;
                case "clear":
                    clearTerminal();
                    continue;
                case "exit":
                    break mainLoop;
                default:
                    System.out.println("Please use a valid command. use 'help' to view list of commands.\n");
                
            }
        }
        scanner.close();
    }

    /**
     * Allows the terminal user to enter an item name when promted to add it their basket.
     * This can continue as many times as the user desires to enter as many products as desired.
     * 
     * @param scanner           Scanner that reads user terminal input
     * @param itemPrices        List of current item prices
     * @param basket            List of current user bucket
     * @return                  Updated basket
     */
    public static HashMap<Character,Integer> basketInsertion(Scanner scanner, HashMap<Character, ItemPrice> itemPrices,
            HashMap<Character, Integer> basket) {
        // Loop for user shopping basket entry
        shoppingBasketLoop : while(true){

            System.out.println("\nPlease enter an item name to add to basket: ");
            String line = scanner.nextLine().toUpperCase();

            // Do nothing if entry is blank
            if (line.isBlank()) {
                continue;
            }

            if (line.length() > 1){
                // Not a valid input, item names should only be one char
                System.out.println("\nInvalid entry, items are only one character. Please try again:");
            } else {
                Character itemName = line.charAt(0);
                if (itemPrices.keySet().contains(itemName)) {
                    // Valid Item, add to basket
                    System.out.println(String.format("\nAdding 1 %s to the basket.", itemName));

                    // Increments value (count) of item by one, or creates if dosnt exist
                    basket.merge(itemName, 1, (a, b) -> a + b);

                    // Display subtotal
                    System.out.println(String.format("\nCurrent basket total is £%.2f", calculateCurrentTotal(basket, itemPrices)));

                } else {
                    // Invalid Item
                    System.out.println(String.format("\nItem %s is not a valid item, please try another item:", itemName));
                }
            }

            // Loop for user input to determine if another item is to be added
            addAnotherItemToBasketChoiceLoop : while (true) {
                System.out.println("\nAdd another item? (y/n)");
                String userChoice = scanner.nextLine().toLowerCase();

                if (userChoice.equals("n") || userChoice.equals("no")) {
                    break shoppingBasketLoop;
                } else if (userChoice.equals("y") || userChoice.equals("yes")){
                    break addAnotherItemToBasketChoiceLoop;
                } 
                System.out.println("\n Invalid entry");
            }
        }
        return basket;
    }

    /**
     * Allows the terminal user to enter new itemprices. Takes a prompt for each item name, price, special amount and special price.
     * Validates each entry before object creation and addition to the map. User can enter as many as required.
     * @param scanner           Scanner that reads user terminal input
     * @param itemPrices        List of current ItemPrices
     * @return                  Updated list of ItemPrices
     */
    public static HashMap<Character, ItemPrice> priceInsertion(Scanner scanner, HashMap<Character, ItemPrice> itemPrices) {
        priceInputLoop : while (true) {
            try {

                /*
                 * Read user input for each parameter of a new price
                 * Invalid entries are caught as exceptions later in the function
                 */
                System.out.println("Enter item name: ");
                Character newItemName = scanner.nextLine().toUpperCase().charAt(0);
                if (!Character.isLetter(newItemName)) {
                    System.out.println("Please enter a valid letter");
                    continue;
                } else if (itemPrices.keySet().contains(newItemName)){
                    System.out.println(String.format("Item %s has already been added, please try another item name.\n", newItemName));
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

                // Loop for user input to determine if another item is to be created
                createAnotherItemChoiceLoop : while (true) {
                    System.out.println("Add another item? (y/n)");
                    String userChoice = scanner.nextLine().toLowerCase();
                    if (userChoice.equals("n") || userChoice.equals("no")) {
                        break priceInputLoop;
                    } else if (userChoice.equals("y") || userChoice.equals("yes")){
                        break createAnotherItemChoiceLoop;
                    } 
                    System.out.println("Invalid entry");
                }
            } catch (NullPointerException | StringIndexOutOfBoundsException e) {
                System.out.println("Please enter a value for each part of the item, even if it is 0.");
            } catch (NumberFormatException e){
                System.out.println("Please enter a valid number");
            }
        }
        
        return itemPrices;
    }

    /**
     * Calculate the total cost of the basket, including special deals.
     * 
     * @param basket
     * @param itemPrices            List of current ItemPrices
     * @return                      Double of the calculated total cost
     */
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

    /**
     * Clears the terminal.
     */
    public static void clearTerminal() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
}
