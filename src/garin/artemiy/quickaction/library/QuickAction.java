package garin.artemiy.quickaction.library;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.*;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 * Author: Artemiy Garin
 * Date: 21.04.14
 */
public class QuickAction implements PopupWindow.OnDismissListener {

    private static final int ARROW_DOWN = 1;
    private static final int ARROW_UP = 2;
    private static final int CONTENT_VIEW = android.R.id.content;
    private static final int DEGREES_180 = 180;

    private static final String PARAM_STATUS_BAR_HEIGHT = "status_bar_height";
    private static final String PARAM_DIMEN = "dimen";
    private static final String PARAM_ANDROID = "android";

    private Context context;
    private Bitmap arrowDown;
    private Bitmap arrowUp;
    private int screenWidth;
    private int screenHeight;

    private OnDismissListener onDismissListener;

    private PopupWindow popupWindow;
    private WindowManager windowManager;
    private RelativeLayout rootLayout;
    private ImageView arrowUpImageView;
    private ImageView arrowDownImageView;

    @SuppressWarnings("unused")
    public QuickAction(Context context, int animationStyle, int arrowUpResource, RelativeLayout rootLayout) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.context = context;
        this.rootLayout = rootLayout;
        if (arrowUpResource != 0) { // todo: fix up arrow
            this.arrowUp = ((BitmapDrawable) context.getResources().getDrawable(arrowUpResource)).getBitmap();
            this.arrowDown = rotateBitmap(DEGREES_180, arrowUp);
            initArrows();
            this.rootLayout.addView(arrowUpImageView);
            this.rootLayout.addView(arrowDownImageView);
        }

        initScreen();
        initPopupWindow(animationStyle);
    }

    @SuppressWarnings("ResourceType")
    private void initArrows() {
        arrowDownImageView = new ImageView(context);
        arrowDownImageView.setId(ARROW_DOWN);
        arrowDownImageView.setImageBitmap(arrowDown);
        RelativeLayout.LayoutParams arrowDownParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        arrowDownParams.addRule(RelativeLayout.BELOW, CONTENT_VIEW);
        arrowDownImageView.setLayoutParams(arrowDownParams);

        arrowUpImageView = new ImageView(context);
        arrowUpImageView.setId(ARROW_UP);
        arrowUpImageView.setImageBitmap(arrowUp);
        RelativeLayout.LayoutParams arrowUpParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        arrowUpImageView.setLayoutParams(arrowUpParams);
    }

    @SuppressWarnings("deprecation")
    private void initScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
            screenHeight = size.y;
        } else {
            screenWidth = windowManager.getDefaultDisplay().getWidth();
            screenHeight = windowManager.getDefaultDisplay().getHeight();
        }
    }

    private void initPopupWindow(int animationStyle) {
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(rootLayout);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(animationStyle);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                } else return false;
            }
        });
    }

    @SuppressWarnings("UnusedDeclaration")
    public void dismiss() {
        popupWindow.dismiss();
    }

    public void show(View anchor) {
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);

        Rect anchorRect = new Rect(location[0], location[1],
                location[0] + anchor.getWidth(), location[1] + anchor.getHeight());

        rootLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        int rootHeight = rootLayout.getMeasuredHeight();
        int rootWidth = rootLayout.getMeasuredWidth();
        int offsetTop = anchorRect.top;
        int offsetBottom = screenHeight - anchorRect.bottom;
        boolean onTop = offsetTop > offsetBottom;

        int x = calculateHorizontalPosition(anchor, anchorRect, rootWidth, screenWidth);
        int y = calculateVerticalPosition(anchorRect, rootHeight, onTop, offsetTop);

        if (arrowUpImageView != null) showArrow(((onTop) ? ARROW_DOWN : ARROW_UP), anchorRect.centerX() - x);
        popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, x, y);
    }

    @SuppressWarnings("ConstantConditions")
    private int calculateVerticalPosition(Rect anchorRect, int rootHeight, boolean onTop, int offsetTop) {
        int y;

        if (onTop) {
            if (rootHeight > offsetTop) y = getStatusBarHeight();
            else y = anchorRect.top - rootHeight;
        } else y = anchorRect.bottom;

        return y;
    }

    private int calculateHorizontalPosition(View anchor, Rect anchorRect, int rootWidth,
                                            int screenWidth) {
        int x;

        if ((anchorRect.left + rootWidth) > screenWidth) {
            x = anchorRect.left - (rootWidth - anchor.getWidth());
            if (x < 0) x = 0;
        } else {
            if (anchor.getWidth() > rootWidth) x = anchorRect.centerX() - (rootWidth / 2);
            else x = anchorRect.left;
        }

        return x;
    }

    @SuppressWarnings("ConstantConditions")
    private void showArrow(int arrowDirection, int horizontalPosition) {
        View showArrow = (arrowDirection == ARROW_UP) ? arrowUpImageView : arrowDownImageView;
        View hideArrow = (arrowDirection == ARROW_UP) ? arrowDownImageView : arrowUpImageView;
        int arrowWidth = arrowUpImageView.getMeasuredWidth();

        showArrow.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams arrowViewParams = (ViewGroup.MarginLayoutParams) showArrow.getLayoutParams();
        arrowViewParams.leftMargin = horizontalPosition - arrowWidth / 2;

        hideArrow.setVisibility(View.INVISIBLE);
    }

    public static Bitmap rotateBitmap(int rotate, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(PARAM_STATUS_BAR_HEIGHT, PARAM_DIMEN, PARAM_ANDROID);
        if (resourceId > 0) result = context.getResources().getDimensionPixelSize(resourceId);

        return result;
    }

    @Override
    public void onDismiss() {
        if (onDismissListener != null) onDismissListener.onDismiss();
    }

    /**
     * Listeners
     */
    @SuppressWarnings("unused")
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void setMaxHeightResource(int heightResource) {
        int maxHeight = context.getResources().getDimensionPixelSize(heightResource);
        popupWindow.setHeight(maxHeight);
    }

}