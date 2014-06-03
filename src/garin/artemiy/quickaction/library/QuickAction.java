package garin.artemiy.quickaction.library;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.*;
import android.widget.*;
import garin.artemiy.quickaction.library.listeners.OnDismissListener;
import garin.artemiy.quickaction.library.listeners.OnItemClickListener;

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
    private static final float PADDING = 8;

    private Context context;
    private Bitmap arrowDown;
    private Bitmap arrowUp;
    private int popupBackgroundResource;
    private int textAppearanceStyle;

    private OnDismissListener onDismissListener;
    private OnItemClickListener onItemClickListener;

    private PopupWindow popupWindow;
    private WindowManager windowManager;
    private RelativeLayout rootLayout;
    private ImageView arrowUpImageView;
    private ImageView arrowDownImageView;
    private LinearLayout contentLayout;
    private ScrollView scrollView;
    private boolean isUseDefaultView;
    private int padding;

    @SuppressWarnings("unused")
    public QuickAction(Context context, int animationStyle, int textAppearanceStyle, int arrowUpResource,
                       int popupBackgroundResource) {
        init(context, animationStyle, textAppearanceStyle, popupBackgroundResource, null, arrowUpResource);
    }

    @SuppressWarnings("unused")
    public QuickAction(Context context, int animationStyle, int arrowUpResource,
                       int popupBackgroundResource, RelativeLayout rootLayout) {
        init(context, animationStyle, 0, popupBackgroundResource, rootLayout, arrowUpResource);
    }

    @SuppressWarnings("ConstantConditions")
    private void init(Context context, int animationStyle, int textAppearanceStyle,
                      int popupBackgroundResource, RelativeLayout rootLayout, int arrowUpResource) {
        this.context = context;
        this.arrowUp = ((BitmapDrawable) context.getResources().getDrawable(arrowUpResource)).getBitmap();
        this.arrowDown = rotateBitmap(DEGREES_180, arrowUp);
        this.popupBackgroundResource = popupBackgroundResource;
        this.textAppearanceStyle = textAppearanceStyle;

        initArrows();

        if (rootLayout == null) {
            this.padding = (int) (PADDING / context.getResources().getDisplayMetrics().density);
            this.rootLayout = configureDefaultPopupView();
        } else {
            this.rootLayout = rootLayout;
            this.rootLayout.addView(arrowUpImageView);
            this.rootLayout.addView(arrowDownImageView);
            rootLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        initPopupWindow(animationStyle);

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

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
        arrowUpImageView.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
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
                }
                return false;
            }
        });
    }

    private RelativeLayout configureDefaultPopupView() {
        isUseDefaultView = true;

        RelativeLayout rootLayout = new RelativeLayout(context);
        rootLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        rootLayout.setPadding(padding / 2, padding, padding / 2, padding);

        contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        scrollView = new ScrollView(context);
        scrollView.setId(CONTENT_VIEW);
        RelativeLayout.LayoutParams scrollParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        scrollParams.addRule(RelativeLayout.BELOW, ARROW_UP);
        scrollView.setLayoutParams(scrollParams);
        scrollView.setBackgroundResource(popupBackgroundResource);

        scrollView.addView(contentLayout);

        rootLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        rootLayout.addView(arrowUpImageView);
        rootLayout.addView(scrollView);
        rootLayout.addView(arrowDownImageView);

        return rootLayout;
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public void addActionItem(final QuickActionItem action) {
        if (isUseDefaultView) {
            TextView titleView = new TextView(context);
            titleView.setText(action.getTitle());
            titleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            titleView.setClickable(true);
            titleView.setTextAppearance(context, textAppearanceStyle);
            titleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(action.getId());
                }
            });

            contentLayout.addView(titleView);
        } else {
            throw new RuntimeException("You can't use custom view and default action item!");
        }
    }

    @SuppressWarnings("deprecation")
    public void show(View anchor) {
        int arrowHorizontalPosition;
        int[] location = new int[2];

        anchor.getLocationOnScreen(location);

        Rect anchorRect = new Rect(location[0], location[1],
                location[0] + anchor.getWidth(), location[1] + anchor.getHeight());

        rootLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        int rootHeight = rootLayout.getMeasuredHeight();
        int rootWidth = rootLayout.getMeasuredWidth();

        int screenWidth;
        int screenHeight;
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

        int offsetTop = anchorRect.top;
        int offsetBottom = screenHeight - anchorRect.bottom;
        boolean onTop = offsetTop > offsetBottom;

        int x = calculateHorizontalPosition(anchor, anchorRect, rootWidth, screenWidth);
        int y = calculateVerticalPosition(anchor, anchorRect, rootHeight, onTop, offsetTop, offsetBottom);

        arrowHorizontalPosition = anchorRect.centerX() - x;

        showArrow(((onTop) ? ARROW_DOWN : ARROW_UP), arrowHorizontalPosition);
        popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, x, y);
    }

    @SuppressWarnings("ConstantConditions")
    private int calculateVerticalPosition(View anchor, Rect anchorRect, int rootHeight,
                                          boolean onTop, int offsetTop, int offsetBottom) {
        int y;

        if (onTop) {

            if (rootHeight > offsetTop) {
                scrollView.getLayoutParams().height = offsetTop - anchor.getHeight();
                y = getStatusBarHeight();
            } else y = anchorRect.top - rootHeight;

        } else {
            y = anchorRect.bottom;

            if (rootHeight > offsetBottom) scrollView.getLayoutParams().height = offsetBottom;
        }

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
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
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

    @SuppressWarnings("unused")
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}