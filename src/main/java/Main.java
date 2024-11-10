import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);

        List<String> fullname = List.of();
        try {
            fullname = UserIOHandler.getUserFullName(inputStreamReader, outputStreamWriter);
        } catch (IOException e) {
            System.err.println("I/O error while getting full name from user. Message: " + e.getMessage());
        }

        GregorianCalendar birthDay;
        try {
             birthDay = UserIOHandler.getUserBirthDay(inputStreamReader, outputStreamWriter);
        } catch (IOException e) {
            System.err.println("I/O error while getting birth date from user. Message: " + e.getMessage());
            return; // end program
        }

        System.out.println("< User info >");

        System.out.print("Initials: ");
        if (fullname.size() == 3) {
            String initials = fullname.get(0).substring(0, 1).toUpperCase()
                    + fullname.get(0).substring(1).toLowerCase() + " "
                    + fullname.get(1).substring(0, 1).toUpperCase() + "."
                    + fullname.get(2).substring(0, 1).toUpperCase() + ".";
            try {
                UserIOHandler.writeLineUTF8(initials, outputStreamWriter, true);
            } catch (IOException e) {
                System.err.println("IO exception while trying to print initials.");
                return; // end program
            }
        } else {
            System.out.println("[Failed to determine, unexpected full name]");
        }

        GregorianCalendar today = new GregorianCalendar();
        today.setTime(new Date());
        System.out.println(today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR) + " years old");

        System.out.print("Sex: ");
        if (fullname.size() == 3) {
            String patronymic = fullname.get(2).toLowerCase();
            if (patronymic.endsWith("ич") || patronymic.endsWith("евич")) {
                System.out.println("male");
            } else if (patronymic.endsWith("овна") || patronymic.endsWith("евна")) {
                System.out.println("female");
            } else {
                System.out.println("[Failed to determine]");
            }
        } else {
            System.out.println("[Failed to determine, unexpected full name]");
        }
    }
}
