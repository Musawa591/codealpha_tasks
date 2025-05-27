import java.util.*;
import java.text.DecimalFormat;

public class Task_2_CodeAlpha{
    private static Map<String, Stock> marketStocks = new HashMap<>();
    private static Map<String, Integer> portfolio = new HashMap<>();
    private static double balance = 10000.00; 
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeMarket();
        
        while (true) {
            System.out.println("\n=== STOCK TRADING PLATFORM ===");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stocks");
            System.out.println("3. Sell Stocks");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Account Balance");
            System.out.println("6. Exit");
            System.out.print("Select option: ");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    displayMarketData();
                    break;
                case 2:
                    buyStocks();
                    break;
                case 3:
                    sellStocks();
                    break;
                case 4:
                    displayPortfolio();
                    break;
                case 5:
                    System.out.println("\nCurrent Balance: $" + df.format(balance));
                    break;
                case 6:
                    System.out.println("Exiting platform...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private static void initializeMarket() {
        
        marketStocks.put("AAPL", new Stock("Apple Inc.", 182.63, 0.87));
        marketStocks.put("GOOGL", new Stock("Alphabet Inc.", 138.21, 1.23));
        marketStocks.put("MSFT", new Stock("Microsoft Corp.", 403.78, 0.56));
        marketStocks.put("AMZN", new Stock("Amazon.com Inc.", 177.58, -0.92));
        marketStocks.put("TSLA", new Stock("Tesla Inc.", 172.63, 2.45));
    }

    private static void displayMarketData() {
        System.out.println("\n=== MARKET DATA ===");
        System.out.printf("%-6s %-20s %-10s %-10s%n", "Symbol", "Company", "Price", "Change(%)");
        for (Map.Entry<String, Stock> entry : marketStocks.entrySet()) {
            Stock stock = entry.getValue();
            String changeColor = stock.getDailyChange() >= 0 ? "\u001B[32m" : "\u001B[31m";
            System.out.printf("%-6s %-20s $%-9s %s%.2f%%\u001B[0m%n", 
                entry.getKey(), 
                stock.getCompanyName(), 
                df.format(stock.getCurrentPrice()),
                changeColor,
                stock.getDailyChange());
        }
    }

    private static void buyStocks() {
        displayMarketData();
        System.out.print("\nEnter stock symbol to buy: ");
        String symbol = scanner.next().toUpperCase();
        
        if (!marketStocks.containsKey(symbol)) {
            System.out.println("Invalid stock symbol!");
            return;
        }
        
        System.out.print("Enter quantity to buy: ");
        int quantity = scanner.nextInt();
        
        Stock stock = marketStocks.get(symbol);
        double totalCost = stock.getCurrentPrice() * quantity;
        
        if (totalCost > balance) {
            System.out.println("Insufficient funds!");
            return;
        }
        
        balance -= totalCost;
        portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + quantity);
        System.out.printf("Successfully bought %d shares of %s for $%s%n", 
            quantity, symbol, df.format(totalCost));
    }

    private static void sellStocks() {
        if (portfolio.isEmpty()) {
            System.out.println("Your portfolio is empty!");
            return;
        }
        
        displayPortfolio();
        System.out.print("\nEnter stock symbol to sell: ");
        String symbol = scanner.next().toUpperCase();
        
        if (!portfolio.containsKey(symbol)) {
            System.out.println("You don't own this stock!");
            return;
        }
        
        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();
        
        int ownedQuantity = portfolio.get(symbol);
        if (quantity > ownedQuantity) {
            System.out.println("You don't have enough shares!");
            return;
        }
        
        Stock stock = marketStocks.get(symbol);
        double totalValue = stock.getCurrentPrice() * quantity;
        
        balance += totalValue;
        if (quantity == ownedQuantity) {
            portfolio.remove(symbol);
        } else {
            portfolio.put(symbol, ownedQuantity - quantity);
        }
        
        System.out.printf("Successfully sold %d shares of %s for $%s%n", 
            quantity, symbol, df.format(totalValue));
    }

    private static void displayPortfolio() {
        if (portfolio.isEmpty()) {
            System.out.println("\nYour portfolio is empty!");
            return;
        }
        
        System.out.println("\n=== YOUR PORTFOLIO ===");
        System.out.printf("%-6s %-20s %-10s %-10s %-12s%n", 
            "Symbol", "Company", "Shares", "Price", "Value");
        
        double totalValue = 0;
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            String symbol = entry.getKey();
            Stock stock = marketStocks.get(symbol);
            int shares = entry.getValue();
            double value = shares * stock.getCurrentPrice();
            totalValue += value;
            
            System.out.printf("%-6s %-20s %-10d $%-9s $%-11s%n", 
                symbol, 
                stock.getCompanyName(), 
                shares, 
                df.format(stock.getCurrentPrice()),
                df.format(value));
        }
        
        System.out.println("\nTotal Portfolio Value: $" + df.format(totalValue));
    }

    static class Stock {
        private String companyName;
        private double currentPrice;
        private double dailyChange;
        
        public Stock(String companyName, double currentPrice, double dailyChange) {
            this.companyName = companyName;
            this.currentPrice = currentPrice;
            this.dailyChange = dailyChange;
        }
        
        
        public String getCompanyName() { return companyName; }
        public double getCurrentPrice() { return currentPrice; }
        public double getDailyChange() { return dailyChange; }
    }
}
