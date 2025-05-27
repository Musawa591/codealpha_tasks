import java.util.ArrayList;
import java.util.Scanner;

public class Task_1_CodeAlpha {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Double> grades = new ArrayList<>();
        
        System.out.println("STUDENT GRADE CALCULATOR");
        
       
        System.out.println("Enter grades (type 'done' to finish):");
        while (true) {
            System.out.print("Grade #" + (grades.size() + 1) + ": ");
            if (input.hasNextDouble()) {
                double grade = input.nextDouble();
                grades.add(grade);
            } else {
                break;
            }
        }
        input.close();
        
        
        if (grades.isEmpty()) {
            System.out.println("No grades entered.");
            return;
        }
        
        
        double sum = 0;
        double high = grades.get(0);
        double low = grades.get(0);
        
        for (double grade : grades) {
            sum += grade;
            if (grade > high) high = grade;
            if (grade < low) low = grade;
        }
        
        double average = sum / grades.size();
        
        
        System.out.println("\nRESULTS");
        System.out.println("Students: " + grades.size());
        System.out.println("Average: " + average);
        System.out.println("Highest: " + high);
        System.out.println("Lowest: " + low);
    }
}
