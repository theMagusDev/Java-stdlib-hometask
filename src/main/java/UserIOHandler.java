import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class UserIOHandler {

    static String readLineUTF8(InputStreamReader inputStreamReader) throws IOException {
        if (inputStreamReader == null) {
            throw new IOException("inputStreamReader can not be null");
        }
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader.readLine();
    }

    static void writeLineUTF8(String line, OutputStreamWriter outputStreamWriter, boolean newline) throws IOException {
        if (outputStreamWriter == null) {
            throw new IOException("outputStreamWriter can not be null");
        }

        if (newline) {
            outputStreamWriter.write(line + '\n');
        } else {
            outputStreamWriter.write(line);
        }
        outputStreamWriter.flush();
    }

    static List<String> getUserFullName(InputStreamReader inputStreamReader,
                                OutputStreamWriter outputStreamWriter
    ) throws IOException {
        if (inputStreamReader == null || outputStreamWriter == null) {
            throw new IOException("inputStreamReader and outputStreamWriter can not be null!");
        }

        String fullname = "";
        boolean fullnameCorrect = false;
        while (!fullnameCorrect) {
            UserIOHandler.writeLineUTF8("Enter your full name. E.g. Иванов Иван Иванович:", outputStreamWriter, true);
            fullname = UserIOHandler.readLineUTF8(inputStreamReader);
            if (fullname.split(" ").length == 3) {
                fullnameCorrect = true;
            } else {
                System.out.println("Incorrect full name input! Use 'Surname Name Patronymic' pattern.");
            }
        }

        return Arrays.asList(fullname.split(" "));
    }

    static GregorianCalendar getUserBirthDay(
            InputStreamReader inputStreamReader,
            OutputStreamWriter outputStreamWriter
    ) throws IOException {
        if (inputStreamReader == null || outputStreamWriter == null) {
            throw new IOException("inputStreamReader and outputStreamWriter can not be null!");
        }

        UserIOHandler.writeLineUTF8("Enter your birth date in format 'DAY.MOUNTH.YEAR'. E.g. 25.03.1998:", outputStreamWriter, true);
        UserIOHandler.writeLineUTF8("Note that 32.01.2005 is interpreted as 01.02.2005", outputStreamWriter, true);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        Date correctUserBirthDay = null;
        Date currentDate = new Date();
        String userBirthInput;
        boolean dateCorrect = false;
        Calendar birthCalendar = Calendar.getInstance();
        while (!dateCorrect) {
            userBirthInput = UserIOHandler.readLineUTF8(inputStreamReader);
            dateCorrect = true;

            try {
                correctUserBirthDay = dateFormatter.parse(userBirthInput);
            } catch (ParseException e) {
                dateCorrect = false;
                UserIOHandler.writeLineUTF8("Incorrect birth day input! Use format 'DAY.MOUNTH.YEAR'", outputStreamWriter, true);
                continue;
            }
            if (correctUserBirthDay == null) {
                UserIOHandler.writeLineUTF8("Incorrect birth day input! Use format 'DAY.MOUNTH.YEAR'", outputStreamWriter, true);
                continue;
            }
            if (correctUserBirthDay.after(currentDate)) {
                dateCorrect = false;
                UserIOHandler.writeLineUTF8("Incorrect birth day input, it can not be in future.", outputStreamWriter, true);
            }
            birthCalendar.setTime(correctUserBirthDay);
        }

        GregorianCalendar calendarBirthDay = new GregorianCalendar();
        if (correctUserBirthDay == null) {
            throw new IOException("Error reading user birth day, correctUserBirthDay is null.");
        }
        calendarBirthDay.setTime(correctUserBirthDay);
        return calendarBirthDay;
    }

}
