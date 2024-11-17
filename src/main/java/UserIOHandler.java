import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
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
            UserIOHandler.writeLineUTF8("Введите ФИО. Например: Иванов Иван Иванович (допустимы только буквы):", outputStreamWriter, true);
            fullname = UserIOHandler.readLineUTF8(inputStreamReader);
            fullnameCorrect = true;
            if (fullname.split(" ").length == 3) {
                for (String str : fullname.split(" ")) {
                    for (char c : str.toCharArray()) {
                        if (!Character.isLetter(c)) {
                            fullnameCorrect = false;
                            UserIOHandler.writeLineUTF8("Неверный ввод! Допускаются только буквы.", outputStreamWriter, true);
                            break;
                        }
                    }
                }
            } else {
                UserIOHandler.writeLineUTF8("Неверный ввод ФИО! Используйте шаблон 'Фамилия Имя Отчество'.", outputStreamWriter, true);
                fullnameCorrect = false;
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

        UserIOHandler.writeLineUTF8("Введите дату рождения в формате 'ДЕНЬ.МЕСЯЦ.ГОД'. Например, 25.03.1998.", outputStreamWriter, true);
        UserIOHandler.writeLineUTF8("Замечание: ввод 32.01.2005 интерпретируется как 01.02.2005", outputStreamWriter, true);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        Date birthdayDateObject = null;
        Date currentDate = new Date();
        String userBirthInput;
        boolean dateCorrect = false;
        Calendar birthCalendar = Calendar.getInstance();

        String[] parsedInput;
        while (!dateCorrect) {
            userBirthInput = UserIOHandler.readLineUTF8(inputStreamReader);

            parsedInput = userBirthInput.split("\\.");
            if (parsedInput.length != 3) {
                UserIOHandler.writeLineUTF8("Неверный формат ввода даты! Используйте шаблон 'ДЕНЬ.МЕСЯЦ.ГОД'", outputStreamWriter, true);
                continue;
            }

            int month;
            try {
                month = Integer.parseInt(parsedInput[1]);
                if (month < 1 || month > 12) {
                    UserIOHandler.writeLineUTF8("Неверный формат ввода месяца даты! Значение должно быть числом от 1 до 12.", outputStreamWriter, true);
                    continue;
                }
            } catch (NumberFormatException e) {
                UserIOHandler.writeLineUTF8("Неверный формат ввода месяца даты! Значение должно быть числом от 1 до 12.", outputStreamWriter, true);
                continue;
            }

            int year;
            try {
                year = Integer.parseInt(parsedInput[2]);
                if (year < 0) {
                    UserIOHandler.writeLineUTF8("Неверный формат ввода года даты! Значение должно быть положительным числом.", outputStreamWriter, true);
                    continue;
                }
            } catch (NumberFormatException e) {
                UserIOHandler.writeLineUTF8("Неверный формат ввода года даты! Значение должно быть числом от 1 до 12.", outputStreamWriter, true);
                continue;
            }

            int day;
            try {
                day = Integer.parseInt(parsedInput[0]);
                if (day <= 0) {
                    UserIOHandler.writeLineUTF8("Неверный формат ввода дня даты! Значение должно быть положительным числом.", outputStreamWriter, true);
                    continue;
                }
                if (day > YearMonth.of(year, month).lengthOfMonth()) {
                    UserIOHandler.writeLineUTF8("Неверный формат ввода дня даты! В предоставленном месяце дней меньше, чем указанный день.", outputStreamWriter, true);
                    continue;
                }
            } catch (NumberFormatException e) {
                UserIOHandler.writeLineUTF8("Неверный формат ввода дня даты! Значение должно быть положительным числом.", outputStreamWriter, true);
                continue;
            }

            Calendar temp = Calendar.getInstance();
            temp.set(year, month - 1, day);
            birthdayDateObject = temp.getTime();
            if (birthdayDateObject.after(currentDate)) {
                UserIOHandler.writeLineUTF8("Неверный формат ввода даты: дата не может быть в будущем.", outputStreamWriter, true);
            } else {
                birthCalendar.setTime(birthdayDateObject);
                dateCorrect = true;
            }
        }

        GregorianCalendar calendarBirthDay = new GregorianCalendar();
        if (birthdayDateObject == null) {
            throw new IOException("Error reading user birth day, birthdayDateObject is null.");
        }
        calendarBirthDay.setTime(birthdayDateObject);
        return calendarBirthDay;
    }

}
