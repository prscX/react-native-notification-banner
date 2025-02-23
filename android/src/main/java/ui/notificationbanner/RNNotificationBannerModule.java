
package ui.notificationbanner;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import java.lang.reflect.Method;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.views.text.ReactFontManager;
import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;

public class RNNotificationBannerModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  private Callback onClickCallback = null;
  private Callback onHideCallback = null;

  public RNNotificationBannerModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNNotificationBanner";
  }

  @ReactMethod
  public void Show(final ReadableMap props, final Callback onClick, final Callback onHide) {

    int type = props.getInt("type");

    String title = props.getString("title");
    int titleSize = props.getInt("titleSize");
    String titleColorValue = props.getString("titleColor");

    String subTitle = props.getString("subTitle");
    int subTitleSize = props.getInt("subTitleSize");
    String subTitleColorValue = props.getString("subTitleColor");

    int duration = props.getInt("duration");
    boolean enableProgress = props.getBoolean("enableProgress");
    String tintColorValue = props.getString("tintColor");

    boolean dismissable = props.getBoolean("dismissable");

    boolean isSwipeToDismissEnabled = props.getBoolean("isSwipeToDismissEnabled");

    boolean withIcon = props.getBoolean("withIcon");
    ReadableMap icon = props.hasKey("icon") ? props.getMap("icon") : null;

    Drawable iconDrawable = null;

    int tintColor = 0;

    onClickCallback = onClick;
    onHideCallback = onHide;

    if (withIcon) {
      if (icon != null && icon.toHashMap().size() > 0) {
        try {
          Class<?> clazz = Class.forName("prscx.imagehelper.RNImageHelperModule"); //Controller A or B
          Class params[] = {ReadableMap.class};
          Method method = clazz.getDeclaredMethod("GenerateImage", params);

          iconDrawable = (Drawable) method.invoke(null, icon);
        } catch (Exception e) {
        }
      }
    }

    if (titleColor != null && titleColor.length() > 0) {
//      config.setTextColor(Color.parseColor(titleColor));
    }
    if (titleSize != 0) {
//      config.setTextSize(titleSize);
    }


    if (tintColorValue != null && tintColorValue.length() > 0) {
      tintColor = Color.parseColor(tintColorValue);
    }

    Alerter alerter = Alerter.create(getCurrentActivity());
      alerter = alerter.setTitle(title);
      alerter = alerter.setText(subTitle);

      if (iconDrawable != null && enableProgress == false) {
        alerter = alerter.setIcon(iconDrawable);
      } else {
        alerter = alerter.hideIcon();
      }

      if (tintColor != 0) {
        alerter = alerter.setBackgroundColorInt(tintColor);
      }

      if (!dismissable) {
        alerter = alerter.setDismissable(dismissable);
      }

      if (isSwipeToDismissEnabled) {
        alerter.enableSwipeToDismiss();
      }

      alerter = alerter.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (onClickCallback != null) {
            onClickCallback.invoke();

            onClickCallback = null;
            onHideCallback = null;
          }
        }
      });

      alerter = alerter.setOnHideListener(new OnHideAlertListener() {
        @Override
        public void onHide() {
          if (onHideCallback != null) {
            onHideCallback.invoke();

            onHideCallback = null;
            onClickCallback = null;
          }
        }
      });

      if (enableProgress) {
        alerter.enableProgress(true);
        alerter.setProgressColorInt(Color.WHITE);
      }

      if (duration != 0) {
        alerter.setDuration(duration);
      }

      Alert activeAlert = alerter.show();

      if (activeAlert == null) {
        return;
      }

      String regexHexColor = "^#(?:[0-9a-fA-F]{3}){1,2}$";
      if (titleColorValue != null && titleColorValue.matches(regexHexColor)) {
        activeAlert.getTitle().setTextColor(Color.parseColor(titleColorValue));
      }
      if(subTitleColorValue != null && subTitleColorValue.matches(regexHexColor)){
        activeAlert.getText().setTextColor(Color.parseColor(subTitleColorValue));
      }
  }

  @ReactMethod
  public void Dismiss() {
    Alerter.clearCurrent(getCurrentActivity());
  }
}