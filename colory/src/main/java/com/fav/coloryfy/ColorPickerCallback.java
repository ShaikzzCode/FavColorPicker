package com.fav.coloryfy;


import androidx.annotation.ColorInt;

/**
 * Created by Patrick Geselbracht on 2017-03-04
 *
 * @author Patrick Geselbracht
 */
public interface ColorPickerCallback {
    /**
     * Gets called whenever a user chooses a color from the FavColors, i.e., presses the
     * "Choose" button.
     *
     * @param color Color chosen
     */

    void onColorChosen(@ColorInt int color);
}

