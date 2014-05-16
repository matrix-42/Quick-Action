<h3>Version — 1.0</h3>
<h3>Minimum SDK — 2.1+</h3>

<h2>Install</h2>
You may import src from project (need delete example folder) or <a href="https://github.com/kvirair/Quick-Action/releases">download jar</a> (recommended)

<h2>Quick start</h2>
```java
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...

        defaultQuickAction = new QuickAction(this, R.style.PopupAnimation, R.style.DefaultTextStyle,
                R.drawable.icon_arrow_up, R.drawable.popup_background); // popup resources

        defaultQuickAction.addActionItem(new QuickActionItem(1,"Text")); // id and element text
    }
```

**That's all!**, after that you need call **defaultQuickAction.show(view)**. View — anchor where popup will be showed. Example resources you can find <a href="https://github.com/kvirair/Quick-Action/tree/master/res">here</a>.

<h2>Custom layout</h2>
```java
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...

        RelativeLayout customLayout = (RelativeLayout) getLayoutInflater().
                inflate(R.layout.popup_custom_layout, null);

        customQuickAction = new QuickAction(this, R.style.PopupAnimation, R.drawable.icon_arrow_up,
                R.drawable.popup_background, customLayout);
    }
```