/*
    This class is a helper class to helper with the navigation view menu look and feel
 */

package com.example.ken.updish.Utility;

import android.app.Activity;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

/**
 * Method 'disableShiftMode' from kapsid in StackOverFlow
 * Source:
 * https://stackoverflow.com/questions/40183239/remove-bottomnavigationview-labels
 *
 * Method 'resizeItems' from Minkoo in StackOverFlow
 * Source:
 * https://stackoverflow.com/questions/43363568/how-to-increase-icon-size-in-android-bottom-navigation-layout
 *
 * Modified by Tan
 */

public class BottomNavigationViewHelper {

    public static void disableShiftMode(BottomNavigationView view, int paddingLeft, int paddingTop,
                                        int paddingRight, int paddingBottom) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                item.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    public static void resizeItems(Activity context, BottomNavigationView bottomNavigationView,
                                   int widthdp, int heightdp)
    {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView)
                bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView =
                    menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams =
                    iconView.getLayoutParams();
            final DisplayMetrics displayMetrics =
                    context.getResources().getDisplayMetrics();
            layoutParams.height = (int)
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthdp,
                            displayMetrics);
            layoutParams.width = (int)
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightdp,
                            displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }
    }
}
