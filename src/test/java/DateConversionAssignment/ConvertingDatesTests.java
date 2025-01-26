package DateConversionAssignment;

import org.json.JSONObject;
import org.testng.annotations.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConvertingDatesTests {

    JSONObject possibleFormatsAvailable = new JSONObject("" +
            "{" +
            "'DayMonthYear_Slashes': '1'," +
            "'MonthDayYear_FullText': '2'," +
            "'DayNameMonthDayYear_FullText': '3'," +
            "}");

    String[] supportedFormats = {
            "dd/MM/yyyy",
            "MMMM d'th' yyyy",
            "EEEE MMMM d'th' yyyy"
    };

    @Test
    public void Test01_ConvertDateSuccessfully() {
        ConvertDateToARequiredFormat("21/12/2024",
                Integer.parseInt(possibleFormatsAvailable.getString("DayNameMonthDayYear_FullText")));
    }

    @Test
    public void Test02_NotSupportedDateFormat() {
        ConvertDateToARequiredFormat("2024/12/23",
                Integer.parseInt(possibleFormatsAvailable.getString("DayNameMonthDayYear_FullText")));
    }

    @Test
    public void Test03_AskingToConvertToTheSameFormat() {
        ConvertDateToARequiredFormat("21/12/2024",
                Integer.parseInt(possibleFormatsAvailable.getString("DayMonthYear_Slashes")));
    }


    public void ConvertDateToARequiredFormat(String inputDate, int formatIndex) {
        if(formatIndex < 1 || formatIndex > supportedFormats.length) {
            System.out.println("Error: Invalid date index. Please choose between 1 and " + supportedFormats.length);
            return;
        }

        Date parsedDate = null;
        Integer foundIndex = -1;
        for (int i=0; i<supportedFormats.length; i++) {
            try {
                SimpleDateFormat inputFormatter = new SimpleDateFormat(supportedFormats[i], Locale.ENGLISH);
                inputFormatter.setLenient(false);
                parsedDate = inputFormatter.parse(inputDate);
                foundIndex = i;
                break;
            } catch (Exception e) {
                System.out.println("Failed to convert format " + inputDate + " - " + e.getMessage());
            }
        }

        if (parsedDate == null) {
            System.out.println("Error: Input date does not match any supported format. Please try another one");
            PrintToConfirmTestEnding();
            return;
        }
        else if(foundIndex == formatIndex - 1) {
            System.out.println("Your date is already within the requested format. No changes needed. The date is " + inputDate);
            PrintToConfirmTestEnding();
            return;
        }

        String desiredFormat = supportedFormats[formatIndex - 1];
        SimpleDateFormat outputFormatter = new SimpleDateFormat(desiredFormat, Locale.ENGLISH);
        String formattedDate = outputFormatter.format(parsedDate);
        System.out.println("Converted Successfully from " + inputDate +  ". New date is: " + formattedDate);
        PrintToConfirmTestEnding();
    }

    public void PrintToConfirmTestEnding() {
        System.out.println("----------------------------------------------------------");
        System.out.println("----------------------------------------------------------");
        System.out.println("----------------------------------------------------------");
    }
}
