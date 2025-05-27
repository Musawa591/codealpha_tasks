import java.util.*;

class Room {
    int roomNumber;
    String type; 
    double price;
    boolean isBooked;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isBooked = false;
    }

    public String toString() {
        return "Room " + roomNumber + " (" + type + ") - $" + price + " - " + (isBooked ? "Booked" : "Available");
    }
}

class Booking {
    String customerName;
    Room room;
    Date checkIn;
    Date checkOut;

    public Booking(String customerName, Room room, Date checkIn, Date checkOut) {
        this.customerName = customerName;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String toString() {
        return "Booking for " + customerName + " | Room: " + room.roomNumber +
               " | " + checkIn + " to " + checkOut;
    }
}

class Payment {
    public static boolean processPayment(String customerName, double amount) {
       
        System.out.println("Processing payment of $" + amount + " for " + customerName + "...");
        System.out.println("Payment successful!");
        return true;
    }
}

class Hotel {
    List<Room> rooms = new ArrayList<>();
    List<Booking> bookings = new ArrayList<>();

    public Hotel() {
       
        rooms.add(new Room(101, "Single", 100));
        rooms.add(new Room(102, "Double", 150));
        rooms.add(new Room(201, "Suite", 250));
        rooms.add(new Room(202, "Double", 150));
        rooms.add(new Room(301, "Single", 100));
    }

    public List<Room> searchAvailableRooms(String type) {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            if (room.type.equalsIgnoreCase(type) && !room.isBooked) {
                available.add(room);
            }
        }
        return available;
    }

    public Booking makeReservation(String customerName, String type, Date checkIn, Date checkOut) {
        List<Room> availableRooms = searchAvailableRooms(type);
        if (!availableRooms.isEmpty()) {
            Room room = availableRooms.get(0);
            room.isBooked = true;
            Booking booking = new Booking(customerName, room, checkIn, checkOut);
            bookings.add(booking);
            Payment.processPayment(customerName, room.price);
            return booking;
        } else {
            System.out.println("No available rooms of type " + type);
            return null;
        }
    }

    public void viewBookings() {
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }
}

public class Task_3_CodeApha {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        while (true) {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Bookings");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter room type (Single, Double, Suite): ");
                    String type = sc.nextLine();
                    List<Room> available = hotel.searchAvailableRooms(type);
                    if (available.isEmpty()) {
                        System.out.println("No available rooms.");
                    } else {
                        for (Room r : available) {
                            System.out.println(r);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter room type (Single, Double, Suite): ");
                    String roomType = sc.nextLine();
                    System.out.print("Enter check-in date (yyyy-mm-dd): ");
                    Date checkIn = java.sql.Date.valueOf(sc.nextLine());
                    System.out.print("Enter check-out date (yyyy-mm-dd): ");
                    Date checkOut = java.sql.Date.valueOf(sc.nextLine());
                    Booking booking = hotel.makeReservation(name, roomType, checkIn, checkOut);
                    if (booking != null) {
                        System.out.println("Reservation successful!");
                        System.out.println(booking);
                    }
                    break;
                case 3:
                    hotel.viewBookings();
                    break;
                case 4:
                    System.out.println("Thank you for using the Hotel Reservation System!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
