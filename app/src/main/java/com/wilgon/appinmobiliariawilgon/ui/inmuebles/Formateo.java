package com.wilgon.appinmobiliariawilgon.ui.inmuebles;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Formateo {
    public static String formatPrice(double price) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        //DecimalFormat decimalFormat = new DecimalFormat("#,##.00", symbols);
        //DecimalFormat decimalFormat = new DecimalFormat("###,###,#.00", symbols);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00", symbols);

        double formattedPrice = price / 10.0;
        return decimalFormat.format(formattedPrice);
    }
}
