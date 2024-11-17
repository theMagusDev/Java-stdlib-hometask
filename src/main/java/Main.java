import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

        if (fullname.size() == 3) {
            String initials = fullname.get(0).substring(0, 1).toUpperCase()
                    + fullname.get(0).substring(1).toLowerCase() + " "
                    + fullname.get(1).substring(0, 1).toUpperCase() + "."
                    + fullname.get(2).substring(0, 1).toUpperCase() + ".";
            try {
                UserIOHandler.writeLineUTF8("Инициалы: " + initials, outputStreamWriter, true);
            } catch (IOException e) {
                System.err.println("IO exception while trying to print initials: " + e);
            }
        } else {
            System.out.println("[Failed to determine, unexpected full name]");
        }

        GregorianCalendar today = new GregorianCalendar();
        today.setTime(new Date());
        int calculatedAge = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        try {
            if (calculatedAge % 10 == 1 && calculatedAge != 11) {
                UserIOHandler.writeLineUTF8(calculatedAge + " год", outputStreamWriter, true);
            } else if (calculatedAge % 10 >= 2 && calculatedAge % 10 <= 4 && (calculatedAge % 100 < 10 || calculatedAge % 100 >= 20)) {
                UserIOHandler.writeLineUTF8(calculatedAge + " года", outputStreamWriter, true);
            } else {
                UserIOHandler.writeLineUTF8(calculatedAge + " лет", outputStreamWriter, true);
            }
        } catch (IOException e) {
            System.err.println("Failed to determine age, exception was caught while writing in console: " + e);
        }

        if (fullname.size() == 3) {
            String patronymic = fullname.get(2).toLowerCase();
            try {
                UserIOHandler.writeLineUTF8("Пол: ", outputStreamWriter, false);
                if (patronymic.endsWith("ч") || patronymic.endsWith("ин") || patronymic.endsWith("ев")) {
                    UserIOHandler.writeLineUTF8("мужской", outputStreamWriter, true);
                } else if (patronymic.endsWith("а")) {
                    UserIOHandler.writeLineUTF8("женский", outputStreamWriter, true);
                } else {
                    UserIOHandler.writeLineUTF8("Не удалось определить", outputStreamWriter, true);
                }
            } catch (IOException e) {
                System.out.println("Failed to determine sex, exception caught while printing in console: " + e);
            }
        } else {
            System.out.println("[Failed to determine, unexpected full name]");
        }
    }
}
