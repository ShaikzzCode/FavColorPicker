package com.fav.coloryfy;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;

import static com.fav.coloryfy.ColorFormatHelper.assertColorValueInRange;
import static com.fav.coloryfy.ColorFormatHelper.formatColorValues;

/**
 * This is the only class of the project. It consists in a custom dialog that shows the GUI
 * used for choosing a color using three sliders or an input field.
 *
 * @author Simone Pessotto
 */
public class FavColors extends Dialog implements SeekBar.OnSeekBarChangeListener {

    private final Activity activity;

    private RelativeLayout colorView;
    private SeekBar alphaSeekBar, redSeekBar, greenSeekBar, blueSeekBar;
    private EditText hexCode;
    private TextView rgbTextView,copyrgbText;
    String alp = "1";
    private TextView hexTextView,copyhexText;
    private int alpha, red, green, blue;
    private ColorPickerCallback callback;

    private boolean withAlpha;
    private boolean autoclose;

    /**
     * Creator of the class. It will initialize the class with black color as default
     *
     * @param activity The reference to the activity where the color picker is called
     */
    public FavColors(Activity activity) {
        super(activity);

        this.activity = activity;

        if (activity instanceof ColorPickerCallback) {
            callback = (ColorPickerCallback) activity;
        }

        this.alpha = 255;
        this.red = 0;
        this.green = 0;
        this.blue = 0;

        this.withAlpha = false;
        this.autoclose = false;
    }

    /**
     * Creator of the class. It will initialize the class with the rgb color passed as default
     *
     * @param activity The reference to the activity where the color picker is called
     * @param red      Red color for RGB values (0 - 255)
     * @param green    Green color for RGB values (0 - 255)
     * @param blue     Blue color for RGB values (0 - 255)
     *
     *                 If the value of the colors it's not in the right range (0 - 255) it will
     *                 be place at 0.
     */
    public FavColors(Activity activity,
                     @IntRange(from = 0, to = 255) int red,
                     @IntRange(from = 0, to = 255) int green,
                     @IntRange(from = 0, to = 255) int blue) {
        this(activity);

        this.red = assertColorValueInRange(red);
        this.green = assertColorValueInRange(green);
        this.blue = assertColorValueInRange(blue);
    }
    
    /**
     * Creator of the class. It will initialize the class with the argb color passed as default
     *
     * @param activity The reference to the activity where the color picker is called
     * @param color ARGB color
     */
    public FavColors(Activity activity,
                     @ColorInt int color) {
        this(activity);

        this.alpha = Color.alpha(color);
        this.red = Color.red(color);
        this.green = Color.green(color);
        this.blue = Color.blue(color);
        
        this.withAlpha = this.alpha < 255;
    }

    /**
     * Creator of the class. It will initialize the class with the argb color passed as default
     *
     * @param activity The reference to the activity where the color picker is called
     * @param alpha    Alpha value (0 - 255)
     * @param red      Red color for RGB values (0 - 255)
     * @param green    Green color for RGB values (0 - 255)
     * @param blue     Blue color for RGB values (0 - 255)
     *
     *                 If the value of the colors it's not in the right range (0 - 255) it will
     *                 be place at 0.
     * @since v1.1.0
     */
//    public FavColors(Activity activity,
//                       @IntRange(from = 0, to = 255) int alpha,
//                       @IntRange(from = 0, to = 255) int red,
//                       @IntRange(from = 0, to = 255) int green,
//                       @IntRange(from = 0, to = 255) int blue) {
//        this(activity);
//
//        this.alpha = assertColorValueInRange(alpha);
//        this.red = assertColorValueInRange(red);
//        this.green = assertColorValueInRange(green);
//        this.blue = assertColorValueInRange(blue);
//
//        this.withAlpha = true;
//    }


    public FavColors(Activity activity,
                     @IntRange(from = 0, to = 255) int alpha,
                     @IntRange(from = 0, to = 255) int red,
                     @IntRange(from = 0, to = 255) int green,
                     @IntRange(from = 0, to = 255) int blue) {
        this(activity);

        this.alpha = assertColorValueInRange(alpha);
        this.red = assertColorValueInRange(red);
        this.green = assertColorValueInRange(green);
        this.blue = assertColorValueInRange(blue);

        this.withAlpha = true;
    }

    /**
     * Enable auto-dismiss for the dialog
     */
    public void enableAutoClose(){
        this.autoclose = true;
    }

    /**
     * Disable auto-dismiss for the dialog
     */
    public void disableAutoClose(){
        this.autoclose = false;
    }

    public void setCallback(ColorPickerCallback listener) {
        callback = listener;
    }

    /**
     * Simple onCreate function. Here there is the init of the GUI.
     *
     * @param savedInstanceState As usual ...
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        setContentView(R.layout.materialcolorpicker__layout_color_picker);

        colorView = findViewById(R.id.colorView);

        hexCode = (EditText) findViewById(R.id.hexCode);
        hexTextView =(TextView) findViewById(R.id.showHexText);
        rgbTextView =(TextView) findViewById(R.id.showRGBText);
        copyrgbText =(TextView) findViewById(R.id.copyRGB);
        copyhexText =(TextView) findViewById(R.id.copyHex);

        alphaSeekBar = (SeekBar) findViewById(R.id.alphaSeekBar);
        redSeekBar = (SeekBar) findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) findViewById(R.id.blueSeekBar);

        alphaSeekBar.setOnSeekBarChangeListener(this);
        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);

        hexCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(withAlpha ? 8 : 6)});

        hexCode.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            updateColorView(v.getText().toString());
                            InputMethodManager imm = (InputMethodManager) activity
                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(hexCode.getWindowToken(), 0);

                            return true;
                        }
                        return false;
                    }
                });

        final TextView okColor = (TextView) findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendColor();
            }
        });
    }

    private void initUi() {
        colorView.setBackgroundColor(getColor());

        alphaSeekBar.setProgress(alpha);
        redSeekBar.setProgress(red);
        greenSeekBar.setProgress(green);
        blueSeekBar.setProgress(blue);

        if (!withAlpha) {
            alphaSeekBar.setVisibility(View.GONE);
        }
        hexCode.setText(withAlpha
                ? formatColorValues(alpha, red, green, blue)
                : formatColorValues(red, green, blue)
        );
    }

    private void sendColor() {
        if (callback != null)
            callback.onColorChosen(getColor());
        if(autoclose){
            dismiss();
        }
    }

    public void setColor(@ColorInt int color) {
        alpha = Color.alpha(color);
        red = Color.red(color);
        green = Color.green(color);
        blue = Color.blue(color);
    }

    /**
     * Method that synchronizes the color between the bars, the view, and the HEX code text.
     *
     * @param input HEX Code of the color.
     */
    private void updateColorView(String input) {
        try {
            final int color = Color.parseColor('#' + input);
            alpha = Color.alpha(color);
            red = Color.red(color);
            green = Color.green(color);
            blue = Color.blue(color);

            colorView.setBackgroundColor(getColor());

            alphaSeekBar.setProgress(alpha);
            redSeekBar.setProgress(red);
            greenSeekBar.setProgress(green);
            blueSeekBar.setProgress(blue);
        } catch (IllegalArgumentException ignored) {
            hexCode.setError(activity.getResources().getText(R.string.materialcolorpicker__errHex));
        }
    }

    /**
     * Method called when the user change the value of the bars. This sync the colors.
     *
     * @param seekBar  SeekBar that has changed
     * @param progress The new progress value
     * @param fromUser Whether the user is the reason for the method call
     */

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (seekBar.getId() == R.id.alphaSeekBar) {

            alpha = progress;
            alp = String.valueOf(alpha);

            int opacity = alpha;
            if (opacity <=1){
                alp= "0";
            }
            else if (opacity >0 && opacity <=23){
                alp= "0.1";
            }
            else if (opacity >23 && opacity <=46){
                alp= "0.2";
            }
            else if (opacity >46 && opacity <=69){
                alp= "0.3";
            }
            else if (opacity >69 && opacity <=92){
                alp= "0.4";
            }
            else if (opacity >92 && opacity <=115){
                alp= "0.5";
            }
            else if (opacity >115 && opacity <=139){
                alp= "0.6";
            }
            else if (opacity >139 && opacity <=162){
                alp= "0.7";
            }
            else if (opacity >162 && opacity <=185){
                alp= "0.8";
            }
            else if (opacity >185 && opacity <=208){
                alp= "0.9";
            }
            else if (opacity >208 && opacity <=255){
                alp= "1";
            }

            String re = String.valueOf(red);
            String gr = String.valueOf(green);
            String bl = String.valueOf(blue);

            String rgbCode = "rgba("+re+"," +gr+"," +bl+"," +alp+")";
            rgbTextView.setText(rgbCode);

        } else if (seekBar.getId() == R.id.redSeekBar) {

            red = progress;
            String alp = String.valueOf(alpha);
            int opacity = alpha;
            if (opacity <=1){
                alp= "0";
            }
            else if (opacity >0 && opacity <=23){
                alp= "0.1";
            }
            else if (opacity >23 && opacity <=46){
                alp= "0.2";
            }
            else if (opacity >46 && opacity <=69){
                alp= "0.3";
            }
            else if (opacity >69 && opacity <=92){
                alp= "0.4";
            }
            else if (opacity >92 && opacity <=115){
                alp= "0.5";
            }
            else if (opacity >115 && opacity <=139){
                alp= "0.6";
            }
            else if (opacity >139 && opacity <=162){
                alp= "0.7";
            }
            else if (opacity >162 && opacity <=185){
                alp= "0.8";
            }
            else if (opacity >185 && opacity <=208){
                alp= "0.9";
            }
            else if (opacity >208 && opacity <=255){
                alp= "1";
            }
            String re = String.valueOf(red);
            String gr = String.valueOf(green);
            String bl = String.valueOf(blue);
            String rgbCode = "rgba("+re+"," +gr+"," +bl+"," +alp+")";
            rgbTextView.setText(rgbCode);

        } else if (seekBar.getId() == R.id.greenSeekBar) {

            green = progress;
            String alp = String.valueOf(alpha);
            int opacity = alpha;
            if (opacity <=1){
                alp= "0";
            }
            else if (opacity >0 && opacity <=23){
                alp= "0.1";
            }
            else if (opacity >23 && opacity <=46){
                alp= "0.2";
            }
            else if (opacity >46 && opacity <=69){
                alp= "0.3";
            }
            else if (opacity >69 && opacity <=92){
                alp= "0.4";
            }
            else if (opacity >92 && opacity <=115){
                alp= "0.5";
            }
            else if (opacity >115 && opacity <=139){
                alp= "0.6";
            }
            else if (opacity >139 && opacity <=162){
                alp= "0.7";
            }
            else if (opacity >162 && opacity <=185){
                alp= "0.8";
            }
            else if (opacity >185 && opacity <=208){
                alp= "0.9";
            }
            else if (opacity >208 && opacity <=255){
                alp= "1";
            }
            String re = String.valueOf(red);
            String gr = String.valueOf(green);
            String bl = String.valueOf(blue);
            String rgbCode = "rgba("+re+"," +gr+"," +bl+"," +alp+")";
            rgbTextView.setText(rgbCode);

        } else if (seekBar.getId() == R.id.blueSeekBar) {

            blue = progress;
            String alp = String.valueOf(alpha);
            int opacity = alpha;
            if (opacity <=1){
                alp= "0";
            }
            else if (opacity >0 && opacity <=23){
                alp= "0.1";
            }
            else if (opacity >23 && opacity <=46){
                alp= "0.2";
            }
            else if (opacity >46 && opacity <=69){
                alp= "0.3";
            }
            else if (opacity >69 && opacity <=92){
                alp= "0.4";
            }
            else if (opacity >92 && opacity <=115){
                alp= "0.5";
            }
            else if (opacity >115 && opacity <=139){
                alp= "0.6";
            }
            else if (opacity >139 && opacity <=162){
                alp= "0.7";
            }
            else if (opacity >162 && opacity <=185){
                alp= "0.8";
            }
            else if (opacity >185 && opacity <=208){
                alp= "0.9";
            }
            else if (opacity >208 && opacity <=255){
                alp= "1";
            }
            String re = String.valueOf(red);
            String gr = String.valueOf(green);
            String bl = String.valueOf(blue);
            String rgbCode = "rgba("+re+"," +gr+"," +bl+"," +alp+")";
            rgbTextView.setText(rgbCode);

        }

        colorView.setBackgroundColor(getColor());

        //Setting the inputText hex color
        hexCode.setText(withAlpha
                ? formatColorValues(alpha, red, green, blue)
                : formatColorValues(red, green, blue)
        );
        String hexCode = "#"+ formatColorValues(alpha, red, green, blue);

        hexTextView.setText(hexCode);

        if (alpha ==255){
            String alp = "1";
            String re = String.valueOf(red);
            String gr = String.valueOf(green);
            String bl = String.valueOf(blue);
            String rgbCode = "rgba("+re+"," +gr+"," +bl+"," +alp+")";
            rgbTextView.setText(rgbCode);
        }

        copyhexText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hexCode = "#"+ formatColorValues(alpha, red, green, blue);
//                Toast.makeText(getContext(), hexCode, Toast.LENGTH_SHORT).show();

                if (hexCode.trim().length() != 0) {
                    ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Label", hexCode);
                    cm.setPrimaryClip(mClipData);
                    dismiss();
                    Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }

            }
        });
        copyrgbText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rgbaCode = rgbTextView.getText().toString();
//                Toast.makeText(getContext(), rgbaCode, Toast.LENGTH_SHORT).show();

                if (rgbaCode.trim().length() != 0) {
                    ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Label", rgbaCode);
                    cm.setPrimaryClip(mClipData);
                    dismiss();
                    Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    /**
     * Getter for the ALPHA value of the ARGB selected color
     *
     * @return ALPHA Value Integer (0 - 255)
     * @since v1.1.0
     */
    public int getAlpha() {
        return alpha;
    }

    /**
     * Getter for the RED value of the RGB selected color
     *
     * @return RED Value Integer (0 - 255)
     */
    public int getRed() {
        return red;
    }

    /**
     * Getter for the GREEN value of the RGB selected color
     *
     * @return GREEN Value Integer (0 - 255)
     */
    public int getGreen() {
        return green;
    }


    /**
     * Getter for the BLUE value of the RGB selected color
     *
     * @return BLUE Value Integer (0 - 255)
     */
    public int getBlue() {
        return blue;
    }

    ////
    // Erlend: Added setters.
    ////

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public void setAll(int alpha, int red, int green, int blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void setColors(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    ////
    //
    ////

    /**
     * Getter for the color as Android Color class value.
     *
     * From Android Reference: The Color class defines methods for creating and converting color
     * ints.
     * Colors are represented as packed ints, made up of 4 bytes: alpha, red, green, blue.
     * The values are unpremultiplied, meaning any transparency is stored solely in the alpha
     * component, and not in the color components.
     *
     * @return Selected color as Android Color class value.
     */
    public int getColor() {
        return withAlpha ? Color.argb(alpha, red, green, blue) : Color.rgb(red, green, blue);
    }

    @Override
    public void show() {
        super.show();
        initUi();
    }
}
