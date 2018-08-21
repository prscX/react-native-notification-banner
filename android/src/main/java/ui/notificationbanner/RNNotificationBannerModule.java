
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

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.views.text.ReactFontManager;
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
    String titleColor = props.getString("titleColor");

    String subTitle = props.getString("title");
    int subTitleSize = props.getInt("titleSize");
    String subTitleColor = props.getString("titleColor");

    int duration = props.getInt("duration");
    String tintColorValue = props.getString("tintColor");

    boolean dismissable = props.getBoolean("dismissable");

    boolean withIcon = props.getBoolean("withIcon");
    ReadableMap icon = props.hasKey("icon") ? props.getMap("icon") : null;

    Drawable iconDrawable = null;

    int tintColor = 0;

    onClickCallback = onClick;
    onHideCallback = onHide;

    if (withIcon) {
      if (icon != null && icon.toHashMap().size() > 0) {
        iconDrawable = this.generateVectorIcon(icon);
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

      final Alerter dismissAlerter = alerter;

      if (iconDrawable != null) {
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

      alerter.show();
  }


  @TargetApi(21) 
  private Drawable generateVectorIcon(ReadableMap icon) {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
 
    String family = icon.getString("family");
    String name = icon.getString("name");
    String glyph = icon.getString("glyph");
    String color = icon.getString("color");
    int size = icon.getInt("size");
 
    if (name != null && name.length() > 0 && name.contains(".")) {
      Resources resources = getReactApplicationContext().getResources();
      name = name.substring(0, name.lastIndexOf("."));
 
      final int resourceId = resources.getIdentifier(name, "drawable", getReactApplicationContext().getPackageName());
      return getReactApplicationContext().getDrawable(resourceId);
    } 
 
    float scale = getReactApplicationContext().getResources().getDisplayMetrics().density;
    String scaleSuffix = "@" + (scale == (int) scale ? Integer.toString((int) scale) : Float.toString(scale)) + "x";
    int fontSize = Math.round(size * scale);
 
    Typeface typeface = ReactFontManager.getInstance().getTypeface(family, 0, getReactApplicationContext().getAssets());
    Paint paint = new Paint();
    paint.setTypeface(typeface);
    paint.setColor(Color.parseColor(color));
    paint.setTextSize(fontSize);
    paint.setAntiAlias(true);
    Rect textBounds = new Rect();
    paint.getTextBounds(glyph, 0, glyph.length(), textBounds);
 
    Bitmap bitmap = Bitmap.createBitmap(textBounds.width(), textBounds.height(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    canvas.drawText(glyph, -textBounds.left, -textBounds.top, paint);
 
    return new BitmapDrawable(getReactApplicationContext().getResources(), bitmap);
  }
}