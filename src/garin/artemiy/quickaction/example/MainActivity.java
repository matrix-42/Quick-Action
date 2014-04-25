package garin.artemiy.quickaction.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import garin.artemiy.quickaction.R;
import garin.artemiy.quickaction.library.QuickAction;
import garin.artemiy.quickaction.library.QuickActionItem;

import java.util.UUID;

public class MainActivity extends Activity {

    private static final int ITEM_1 = 1;
    private static final int ITEM_2 = 2;
    private static final int ITEM_3 = 3;
    private QuickAction quickAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        quickAction = new QuickAction(this, R.style.PopupAnimation, R.style.DefaultTextStyle,
                R.drawable.icon_arrow_up, R.drawable.popup_background);

        quickAction.addActionItem(new QuickActionItem(ITEM_1, UUID.randomUUID().toString()));
        quickAction.addActionItem(new QuickActionItem(ITEM_2, UUID.randomUUID().toString()));
        quickAction.addActionItem(new QuickActionItem(ITEM_3, UUID.randomUUID().toString()));
    }

    @SuppressWarnings("unused")
    public void onClickTopButton(View view) {
        quickAction.show(view);
    }

    @SuppressWarnings("unused")
    public void onClickMiddleButton(View view) {
        quickAction.show(view);
    }

    @SuppressWarnings("unused")
    public void onClickBottomButton(View view) {
        quickAction.show(view);
    }

}
