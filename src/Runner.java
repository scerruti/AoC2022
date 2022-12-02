import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.TimeZone;

public class Runner {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("EST"));
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        try {
            Class<?> clazz = Class.forName(String.format("Day%02d", dayOfMonth));
            Day day = (Day) clazz.getDeclaredConstructor().newInstance();
            day.solve();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
