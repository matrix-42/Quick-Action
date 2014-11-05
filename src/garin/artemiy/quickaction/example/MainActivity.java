package garin.artemiy.quickaction.example;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import garin.artemiy.quickaction.R;
import garin.artemiy.quickaction.library.QuickAction;
import garin.artemiy.quickaction.library.QuickActionItem;

import java.util.UUID;

public class MainActivity extends Activity {

    private QuickAction defaultQuickAction;
    private QuickAction customQuickAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        defaultQuickAction = new QuickAction(this, R.style.PopupAnimation, R.style.DefaultTextStyle,
                R.drawable.icon_arrow_up, R.drawable.popup_background);
        defaultQuickAction.setMaxHeightResource(R.dimen.popup_max_height);

        @SuppressLint("InflateParams") RelativeLayout customLayout =
                (RelativeLayout) getLayoutInflater().inflate(R.layout.popup_custom_layout, null);
        customQuickAction = new QuickAction(this, R.style.PopupAnimation, 0,
                R.drawable.popup_background, customLayout);

        insertExampleData();
    }

    private void insertExampleData() {
        defaultQuickAction.addActionItem(new QuickActionItem(1, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(2, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(3, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(4, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(5, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(6, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(7, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(8, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(9, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(10, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(11, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(12, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(13, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(14, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(15, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(16, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(17, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(18, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(19, UUID.randomUUID().toString()));
        defaultQuickAction.addActionItem(new QuickActionItem(20, UUID.randomUUID().toString()));
    }

    @SuppressWarnings("unused")
    public void onClickTopButton(View view) {
        defaultQuickAction.show(view);
    }

    @SuppressWarnings("unused")
    public void onClickMiddleButton(View view) {
        customQuickAction.show(view);
    }

    @SuppressWarnings("unused")
    public void onClickBottomButton(View view) {
        defaultQuickAction.show(view);
    }

}
