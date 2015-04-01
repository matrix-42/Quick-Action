<h3>Version — 1.2</h3>
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
         setContentView(R.layout.activity_main_layout);

      RelativeLayout customLayout = (RelativeLayout)
          getLayoutInflater().inflate(R.layout.popup_custom_layout, null);

     quickAction = new QuickAction
         (this, R.style.PopupAnimation, customLayout, customLayout);
     }
```

**That's all!**, after that you need call **quickAction.show(yourView)**.

<h2>Popup animation</h2>
```java
    <style name="PopupAnimation">
        <item name="android:windowEnterAnimation">@android:anim/fade_in</item>
        <item name="android:windowExitAnimation">@android:anim/fade_out</item>
    </style>
```

<h2>Additional methods</h2>
**setMaxHeightResource** — example: setMaxHeightResource(R.dimen.popup_max_height);

<h2>License</h2>
```
Copyright (C) 2013 Artemiy Garin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```