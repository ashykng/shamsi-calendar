import java.util.Scanner;

public class Main {

    private static final int[] gDaysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] jDaysInMonth = {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Shamsi year: ");
        int shamsiYear = scanner.nextInt();

        System.out.println("Shamsi Calendar for Year " + shamsiYear + ":");
        displayShamsiCalendar(shamsiYear);
    }

    public static void displayShamsiCalendar(int shamsiYear) {
        String[] persianMonths = {"Farvardin", "Ordibehesht", "Khordad", "Tir", "Mordad", "Shahrivar",
                "Mehr", "Aban", "Azar", "Dey", "Bahman", "Esfand"};

        for (int i = 0; i < 12; i++) {
            int shamsiMonth = i + 1;
            int daysInMonth = jDaysInMonth[i];
            if (shamsiMonth == 12 && isLeap(shamsiYear)) {
                daysInMonth = 30;
            }

            System.out.println("\n" + persianMonths[i]);
            System.out.println("--------------");
            System.out.println("Sun Mon Tue Wed Thu Fri Sat");

            int startDayOfWeek = calculateStartDayOfWeek(shamsiYear, shamsiMonth, 1);

            for (int j = 0; j < startDayOfWeek; j++) {
                System.out.print("    ");
            }

            for (int day = 1; day <= daysInMonth; day++) {
                System.out.printf("%3d ", day);
                if ((day + startDayOfWeek) % 7 == 0 || day == daysInMonth) {
                    System.out.println();
                }
            }
        }
    }

    public static int calculateStartDayOfWeek(int shamsiYear, int shamsiMonth, int shamsiDay) {
        int gregorianYear = shamsiToGregorian(shamsiYear, shamsiMonth, shamsiDay)[0];
        int gregorianMonth = shamsiToGregorian(shamsiYear, shamsiMonth, shamsiDay)[1];
        int gregorianDay = shamsiToGregorian(shamsiYear, shamsiMonth, shamsiDay)[2];

        if (gregorianMonth < 3) {
            gregorianMonth += 12;
            gregorianYear -= 1;
        }
        int h = (gregorianDay + (13 * (gregorianMonth + 1)) / 5 + gregorianYear % 100 +
                (gregorianYear % 100) / 4 + (gregorianYear / 100) / 4 + 5 * (gregorianYear / 100)) % 7;
        return (h + 5) % 7;
    }

    public static boolean isLeap(int shamsiYear) {
        return (shamsiYear % 33) % 4 == 1;
    }

    public static int[] shamsiToGregorian(int jYear, int jMonth, int jDay) {
        int jDays = (jYear - 979) * 365 + (jYear - 978) / 33 * 8 + ((jYear - 978) % 33 + 3) / 4;
        for (int i = 0; i < jMonth - 1; ++i)
            jDays += jDaysInMonth[i];
        jDays += jDay - 1;

        int gDays = jDays + 79;
        int gy = 1600 + 400 * (gDays / 146097);
        gDays %= 146097;
        boolean leap = true;
        if (gDays >= 36525) {
            gDays--;
            gy += 100 * (gDays / 36524);
            gDays %= 36524;
            if (gDays >= 365)
                gDays++;
            else
                leap = false;
        }
        gy += 4 * (gDays / 1461);
        gDays %= 1461;
        if (gDays >= 366) {
            leap = false;
            gDays--;
            gy += gDays / 365;
            gDays %= 365;
        }
        for (int i = 0; i < 11; ++i) {
            if (gDays >= gDaysInMonth[i] + (i == 1 && leap ? 1 : 0))
                gDays -= gDaysInMonth[i] + (i == 1 && leap ? 1 : 0);
            else {
                int gm = i + 1;
                int gd = gDays + 1;
                return new int[]{gy, gm, gd};
            }
        }
        return new int[]{gy, 12, 29};
    }
}
