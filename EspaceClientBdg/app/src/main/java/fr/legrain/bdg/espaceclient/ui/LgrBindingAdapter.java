package fr.legrain.bdg.espaceclient.ui;

import android.graphics.Typeface;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.databinding.BindingAdapter;

public class LgrBindingAdapter {

    @BindingAdapter("android:timestamp")
    public static void setTextTimeStamp(TextView view, Date date) {
        if (date!=null) {
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String localizedDate = df.format(date);
            //
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            localizedDate = formatter.format(date);

            view.setText(localizedDate);
        }
    }

    @BindingAdapter("android:date")
    public static void setTextDate(TextView view, Date date) {
        if (date!=null) {
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String localizedDate = df.format(date);
            //
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            localizedDate = formatter.format(date);

            view.setText(localizedDate);
        }
    }

    @BindingAdapter("android:bigDecimalCurrency")
    public static void setTextDate(TextView view, BigDecimal date) {
        if (date!=null) {
            view.setText(date.toPlainString()+" €");
        }
    }

    @BindingAdapter("android:bigDecimalCurrencyHT")
    public static void setTextDateHT(TextView view, BigDecimal date) {
        if (date!=null) {
            view.setText("HT "+date.toPlainString()+" €");
        }
    }

    @BindingAdapter("android:bigDecimalCurrencyTTC")
    public static void setTextDateTTC(TextView view, BigDecimal date) {
        if (date!=null) {
            view.setText("TTC "+date.toPlainString()+" €");
        }
    }

    @BindingAdapter("android:bigDecimalCurrencyResteARegler")
    public static void setTextDateResteARegler(TextView view, BigDecimal date) {
        if (date!=null) {
            view.setText("Reste à régler "+date.toPlainString()+" €");
        }
    }

    @BindingAdapter("android:bigDecimal")
    public static void setTextDated(TextView view, BigDecimal date) {
        if (date!=null) {
            view.setText(date.toPlainString());
        }
    }

    @BindingAdapter("android:textStyle")
    public static void setTypeface(TextView v, String style) {
        switch (style) {
            case "bold":
                v.setTypeface(null, Typeface.BOLD);
                break;
            default:
                v.setTypeface(null, Typeface.NORMAL);
                break;
        }
    }
}
