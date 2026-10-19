/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manager;

import Entity.Fruit;
import Entity.Order;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Manager {

    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Fruit> fruits = new ArrayList<>();
    private final Hashtable<String, ArrayList<Order>> customerOrders = new Hashtable<>();

    public void run() {
        while (true) {
            System.out.println("\nFRUIT SHOP SYSTEM");
            System.out.println("1. Create Fruit");
            System.out.println("2. View orders");
            System.out.println("3. Shopping (for buyer)");
            System.out.println("4. Exit");

            int choice = Validation.readInt(scanner, "Please choose 1 to 4: ", 1, 4);
            switch (choice) {
                case 1:
                    createFruits();
                    break;
                case 2:
                    viewOrders();
                    break;
                case 3:
                    shopping();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    return;
                default:
                    break;
            }
        }
    }

    private void createFruits() {
        boolean addMore;
        do {
            int id = Validation.readInt(scanner, "Enter fruit ID: ", 1, Integer.MAX_VALUE);
            while (findFruitById(id) != null) {
                System.out.println("This fruit ID already exists.");
                id = Validation.readInt(scanner, "Enter another fruit ID: ", 1, Integer.MAX_VALUE);
            }

            String name = Validation.readNonEmpty(scanner, "Enter fruit name: ");
            double price = Validation.readPositiveDouble(scanner, "Enter price: ");
            int quantity = Validation.readInt(scanner, "Enter quantity: ", 1, Integer.MAX_VALUE);
            String origin = Validation.readNonEmpty(scanner, "Enter origin: ");
            fruits.add(new Fruit(id, name, price, quantity, origin));
            System.out.println("Fruit created successfully.");

            addMore = Validation.readYesNo(scanner, "Do you want to continue (Y/N)? ");
        } while (addMore);

        displayAllFruits();
    }

    private void displayAllFruits() {
        if (fruits.isEmpty()) {
            System.out.println("There are no fruits.");
            return;
        }

        System.out.println("\nList of Fruits:");
        System.out.printf("%-8s %-20s %-15s %-12s %-10s%n",
                "ID", "Fruit Name", "Origin", "Price", "Quantity");
        for (Fruit fruit : fruits) {
            System.out.printf("%-8d %-20s %-15s %-12s %-10d%n",
                    fruit.getId(), fruit.getName(), fruit.getOrigin(),
                    formatMoney(fruit.getPrice()), fruit.getQuantity());
        }
    }

    private void displayShoppingFruits() {
        System.out.println("\nList of Fruit:");
        System.out.printf("%-12s %-20s %-15s %-12s%n",
                "Item", "Fruit Name", "Origin", "Price");
        for (int i = 0; i < fruits.size(); i++) {
            Fruit fruit = fruits.get(i);
            System.out.printf("%-12d %-20s %-15s %-12s%n",
                    i + 1, fruit.getName(), fruit.getOrigin(), formatMoney(fruit.getPrice()));
        }
    }

    private void shopping() {
        if (fruits.isEmpty()) {
            System.out.println("There are no fruits available.");
            return;
        }

        ArrayList<Order> cart = new ArrayList<>();
        boolean checkout;
        do {
            if (!hasAvailableFruit(cart)) {
                if (cart.isEmpty()) {
                    System.out.println("There are no fruits in stock.");
                    return;
                }
                System.out.println("No more fruits are available. Completing your order.");
                checkout = true;
                break;
            }

            displayShoppingFruits();
            int item = Validation.readInt(scanner, "Please select an item: ", 1, fruits.size());
            Fruit selectedFruit = fruits.get(item - 1);

            int availableQuantity = selectedFruit.getQuantity() - getCartQuantity(cart, selectedFruit);
            if (availableQuantity == 0) {
                System.out.println("There is no more stock for this fruit.");
                checkout = false;
                continue;
            }

            int quantity = Validation.readInt(scanner, "Please input quantity: ", 1, availableQuantity);
            cart.add(new Order(selectedFruit.getId(), selectedFruit.getName(),
                    quantity, selectedFruit.getPrice()));
            checkout = Validation.readYesNo(scanner, "Do you want to order now (Y/N)? ");
            if (!checkout && !hasAvailableFruit(cart)) {
                System.out.println("No more fruits are available. Completing your order.");
                checkout = true;
            }
        } while (!checkout);

        printReceipt(cart);
        String customerName = Validation.readNonEmpty(scanner, "Input your name: ");

        for (Order order : cart) {
            Fruit fruit = findFruitById(order.getFruitId());
            fruit.setQuantity(fruit.getQuantity() - order.getQuantity());
        }

        ArrayList<Order> customerOrder = customerOrders.get(customerName);
        if (customerOrder == null) {
            customerOrder = new ArrayList<>();
        }
        customerOrder.addAll(cart);
        customerOrders.put(customerName, customerOrder);
    }

    private void printReceipt(ArrayList<Order> orders) {
        System.out.println("\nProduct | Quantity | Price | Amount");
        double total = 0;
        for (Order order : orders) {
            System.out.printf("%-20s %-10d %-10s %-10s%n",
                    order.getFruitName(), order.getQuantity(),
                    formatMoney(order.getPrice()), formatMoney(order.getAmount()));
            total += order.getAmount();
        }
        System.out.println("Total: " + formatMoney(total));
    }

    private void viewOrders() {
        if (customerOrders.isEmpty()) {
            System.out.println("There are no orders.");
            return;
        }

        for (String customerName : customerOrders.keySet()) {
            System.out.println("\nCustomer: " + customerName);
            System.out.println("Product | Quantity | Price | Amount");
            ArrayList<Order> orders = customerOrders.get(customerName);
            double total = 0;
            for (int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                System.out.printf("%d. %-20s %-10d %-10s %-10s%n",
                        i + 1, order.getFruitName(), order.getQuantity(),
                        formatMoney(order.getPrice()), formatMoney(order.getAmount()));
                total += order.getAmount();
            }
            System.out.println("Total: " + formatMoney(total));
        }
    }

    private Fruit findFruitById(int id) {
        for (Fruit fruit : fruits) {
            if (fruit.getId() == id) {
                return fruit;
            }
        }
        return null;
    }

    private int getCartQuantity(ArrayList<Order> cart, Fruit fruit) {
        int quantity = 0;
        for (Order order : cart) {
            if (order.getFruitId() == fruit.getId()) {
                quantity += order.getQuantity();
            }
        }
        return quantity;
    }

    private boolean hasAvailableFruit(ArrayList<Order> cart) {
        for (Fruit fruit : fruits) {
            if (fruit.getQuantity() > getCartQuantity(cart, fruit)) {
                return true;
            }
        }
        return false;
    }

    private String formatMoney(double amount) {
        if (amount == Math.floor(amount)) {
            return (long) amount + "$";
        }
        return String.format("%.2f$", amount);
    }
}
