package task.main;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import task.classes.ItemPrice;

public class Solution{
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<Character, ItemPrice> itemPrices = new HashMap<Character, ItemPrice>();
        HashMap<Character, Integer> basket = new HashMap<Character, Integer>();
        Double currentTotal = 0.0;

        mainLoop : while (true) {
            //read user input
            String userInput = scanner.nextLine().toLowerCase();

            switch (userInput) {
                case "price":
                    itemPrices = priceInsertion(scanner, itemPrices);
                    
                case "shop":

                    shoppingBasketLoop : while(true){
                        
                        System.out.println("\n" + "Please enter scanned item: ");
                        String line = scanner.nextLine();

                        if (line.length() > 1){
                            // Not a valid input, should only be one char long
                            System.out.println("Invalid entry, items are only one character. Please try again:");
                        } else {
                            Character itemName = line.charAt(0);
                            if (itemPrices.keySet().contains(itemName)) {
                                // Valid Item, add to basket
                                System.out.println(String.format("Adding 1 %s to the basket.", itemName));
                                basket.merge(itemName, 1, Integer::sum);

                                // Calculate subtotal
                                currentTotal = calculateCurrentTotal(basket, itemPrices);

                                System.out.println(String.format("Current basket total is £%d", currentTotal));

                            } else {
                                // Invalid Item
                                System.out.println(String.format("Item %s is not a valid item, please try another item:", itemName));
                            }
                        }
                    }
                    
                case "exit":
                    break mainLoop;
                
            }
        }

        scanner.close();
    }

    private static HashMap<Character, ItemPrice> priceInsertion(Scanner scanner, HashMap<Character, ItemPrice> itemPrices) {
        priceInputLoop : while (true) {
            System.out.println("Enter item name: ");
            Character newItemName = scanner.nextLine().charAt(0);

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
                System.out.print("Add another item? (y/n)");
                String userChoice = scanner.nextLine().toLowerCase();
                if (userChoice.equals("n") || userChoice.equals("no")) {
                    break priceInputLoop;
                } else if (userChoice.equals("y") && userChoice.equals("yes")){
                    break addAnotherItemChoiceLoop;
                } 
                System.out.println("Invalid entry");
            }
        }
        return itemPrices;
    }

    private static Double calculateCurrentTotal(HashMap<Character, Integer> basket, HashMap<Character, ItemPrice> itemPrices){
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
}