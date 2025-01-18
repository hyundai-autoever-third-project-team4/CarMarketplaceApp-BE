package store.carjava.marketplace.common.util.thymeleaf;

import java.text.NumberFormat;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class ThymeleafUtils {

    public String formatNumber(long number) {
        return NumberFormat.getInstance(Locale.KOREA).format(number);
    }

    public String formatPhone(String phone) {
        if (phone == null || phone.length() != 10 && phone.length() != 11) {
            return "미설정"; // Invalid or null phone number
        }
        return phone.length() == 10
                ? phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-" + phone.substring(6)
                : phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
    }
}
