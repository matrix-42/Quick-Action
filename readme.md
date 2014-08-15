<h3>Version — 1.1</h3>
<h3>Minimum SDK — 2.2+</h3>

<h2>Screenshot</h2>
![alt tag](http://i62.tinypic.com/2pr7ewm.png)

<h2>Install</h2>
You may import src from project (need delete example folder) or <a href="https://github.com/kvirair/Quick-Action/releases">download jar</a> (recommended)

<h2>Quick start</h2>
```java
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...

        defaultQuickAction = new QuickAction(this,
                R.style.PopupAnimation, R.style.DefaultTextStyle,
                R.drawable.icon_arrow_up, R.drawable.popup_background); // popup resources

        defaultQuickAction.addActionItem(new QuickActionItem(1, "Text")); // id and element title
    }
```

**That's all!**, after that you need call **defaultQuickAction.show(view)**.

View — anchor where popup will be showed. Example resources you can find <a href="https://github.com/kvirair/Quick-Action/tree/master/res">here</a>.

<h2>Custom layout</h2>
```java
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...

        RelativeLayout customLayout = (RelativeLayout) getLayoutInflater().
                inflate(R.layout.popup_custom_layout, null);

        customQuickAction = new QuickAction(this,
                R.style.PopupAnimation, R.drawable.icon_arrow_up,
                R.drawable.popup_background, customLayout);
    }
```

In this case you also call **customQuickAction.show(view)**. XML example:

```java
<?xml version="1.0" encoding="utf-8"?>

<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:tools="http://schemas.android.com/tools"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content">

    <LinearLayout
            android:id="@android:id/content"
            android:background="@drawable/popup_background"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            tools:ignore="UselessParent">

        <TextView
                style="@style/DefaultTextStyle"
                android:gravity="center"
                android:drawableTop="@drawable/icon"
                android:text="@string/app_name"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>

    </LinearLayout>

</RelativeLayout>
```

**Important** part is set id, **android:id="@android:id/content"**, in this easy case, you also can set this id to TextView. And remove relative layout.

<h2>Popup animation</h2>
```java
    <style name="PopupAnimation">
        <item name="android:windowEnterAnimation">@android:anim/fade_in</item>
        <item name="android:windowExitAnimation">@android:anim/fade_out</item>
    </style>
```

<h2>Listeners</h2>
Use OnItemClickListener and OnDismissListener on QuickAction class. For example:

```java
quickAction.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                // your code
            }
        });
```

<h2>Additional methods</h2>
**setMaxHeightResource** — example: setMaxHeightResource(R.dimen.popup_max_height);