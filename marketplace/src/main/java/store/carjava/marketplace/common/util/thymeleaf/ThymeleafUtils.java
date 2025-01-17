package store.carjava.marketplace.common.util.thymeleaf;

import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Locale;

@Component
public class ThymeleafUtils {
    public String formatNumber(long number) {
        return NumberFormat.getInstance(Locale.KOREA).format(number);
    }
}