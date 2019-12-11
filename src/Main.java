import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;


public class Main
{
    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args) throws ParseException {
        ArrayList<Employee> staff = loadStaffFromFile();
        Date firstDay = new SimpleDateFormat(dateFormat).parse("01.01.2017");
        Date lastDay = new SimpleDateFormat(dateFormat).parse("31.12.2017");
        staff.stream()
                .sorted(Comparator.comparing(Employee::getSalary)
                .thenComparing(Employee::getName))
                .forEach(System.out::println);

        System.out.println("\nМаксимальная з.п. среди тех кто пришел в 2017 году ");
        staff.stream().filter(employee -> employee.getWorkStart().after(firstDay) && employee.getWorkStart().before(lastDay)).max(Comparator.comparing(Employee::getSalary)).ifPresent(System.out::println);
    }

    private static ArrayList<Employee> loadStaffFromFile()
    {
        ArrayList<Employee> staff = new ArrayList<>();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for(String line : lines)
            {
                String[] fragments = line.split("\t");
                if(fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                    fragments[0],
                    Integer.parseInt(fragments[1]),
                    (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}