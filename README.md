# PowerMenu
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9f2c560683f648f68db4e7f493fb6d5e)](https://app.codacy.com/app/skydoves/PowerMenu?utm_source=github.com&utm_medium=referral&utm_content=skydoves/PowerMenu&utm_campaign=Badge_Grade_Dashboard)
[![travis](https://travis-ci.org/skydoves/PowerMenu.svg?branch=master)](https://travis-ci.org/skydoves/PowerMenu) 
[![Android Weekly](https://img.shields.io/badge/Android%20weekly-326-orange.svg)](https://androidweekly.net/issues/issue-326) <br>
A library that lets you implement popup so easily. <br>

![gif1](https://user-images.githubusercontent.com/24237865/32500436-c52b02ec-c418-11e7-8027-9aceb5cbe368.gif)
![screenshot0](https://user-images.githubusercontent.com/24237865/32500435-c4f81594-c418-11e7-98e5-d1ddbbb6c2ad.jpg)

## Download
### Gradle
```gradle
dependencies {
    implementation "com.github.skydoves:powermenu:2.0.5"
}
```

## Usage

### Basic example
This is a basic example on a screenshot. You can create `PowerMenu` using `PowerMenu.Builder`.
```java
PowerMenu powerMenu = new PowerMenu.Builder(context)
                .addItemList(list) // list has "Novel", "Poerty", "Art"
                .addItem(new PowerMenuItem("Journals", false))
                .addItem(new PowerMenuItem("Travel", false))
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setTextColor(context.getResources().getColor(R.color.md_grey_800))
                .setSelectedTextColor(Color.WHITE)
                .setMenuColor(Color.WHITE)
                .setSelectedMenuColor(context.getResources().getColor(R.color.colorPrimary))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();
```

You can add items or an item list using `PowerMenuItem` class. This is how to initialize `PowerMenuItem`.
```java
PowerMenuItem powerMenuItem = new PowerMenuItem("Travel", true);
```

The first argument is an item **title**, and the other is **selected status**. <br>
If **selected status** is true, the item's text and background color be changed by your settings like below.<br>

```java
.setSelectedTextColor(Color.WHITE)
.setSelectedMenuColor(context.getResources().getColor(R.color.colorPrimary))
```

`OnMenuItemClickListener` is an interface definition for a callback to be invoked when a menu item is clicked.
```java
private OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
    @Override
    public void onItemClick(int position, PowerMenuItem item) {
        Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        powerMenu.setSelectedPosition(position); // change selected item
        powerMenu.dismiss();
    }
};
```

After declaring the listener, you should set about `PowerMenu` using below method.
```java
.setOnMenuItemClickListener(onMenuItemClickListener)
```

The last, just show your popup! [Various show & dismiss methods](https://github.com/skydoves/PowerMenu#show--dismiss).
```java
powerMenu.showAsDropDown(view); // view is an anchor
```

## Customizing Popup
You can customizing item styles using `CustomPowerMenu` and your customized adapter. <br>
Below is how to customizing popup item that has an icon. <br>

![custom0](https://user-images.githubusercontent.com/24237865/45586080-5a07e800-b92b-11e8-8c7e-5aa0e524598c.png)
![gif0](https://user-images.githubusercontent.com/24237865/45586081-5a07e800-b92b-11e8-9cba-303eb013b7c9.gif) <br>

At first, you should create your item model.
```java
public class IconPowerMenuItem {
    private Drawable icon;
    private String title;

    public IconPowerMenuItem(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
    }
 // --- skipped setter and getter methods
}
```

And next, you should create your own customized XML layout and adapter. <br>
Custom Adapter should extend `MenuBaseAdapter<YOUR_ITEM_MODEL>`.

```java
public class IconMenuAdapter extends MenuBaseAdapter<IconPowerMenuItem> {

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_icon_menu, viewGroup, false);
        }

        IconPowerMenuItem item = (IconPowerMenuItem) getItem(index);
        final ImageView icon = view.findViewById(R.id.item_icon);
        icon.setImageDrawable(item.getIcon());
        final TextView title = view.findViewById(R.id.item_title);
        title.setText(item.getTitle());
        return super.getView(index, view, viewGroup);
    }
}
```

The last, create CustomPowerMenu and attach `onMenuItemClickListener`.
```java
CustomPowerMenu customPowerMenu = new CustomPowerMenu.Builder<>(context, new IconMenuAdapter())
                .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_wechat), "WeChat"))
                .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_facebook), "Facebook"))
                .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_twitter), "Twitter"))
                .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_line), "Line"))
                .setOnMenuItemClickListener(onIconMenuItemClickListener)
                .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .build();
```
```java
private OnMenuItemClickListener<IconPowerMenuItem> onIconMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
    @Override
    public void onItemClick(int position, IconPowerMenuItem item) {
        Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        iconMenu.dismiss();
    }
};
```

## Dialogs
You can create seems like dialogs using `PowerMenu`.<br><br>
![screenshot_2017-12-18-23-39-00](https://user-images.githubusercontent.com/24237865/34111113-1de9bfce-e44c-11e7-9b60-44b8d440b910.png)
![screenshot_2017-12-18-23-39-05](https://user-images.githubusercontent.com/24237865/34111114-1e19ddf8-e44c-11e7-9747-17713d2932bd.png)
<br>

Below example is normal dialog. Dialogs are composed of a header, footer and body. <br>
```java
PowerMenu powerMenu = new PowerMenu.Builder(context)
                .setHeaderView(R.layout.layout_dialog_header) // header used for title
                .setFooterView(R.layout.layout_dialog_footer) // footer used for yes and no buttons
                .addItem(new PowerMenuItem("This is DialogPowerMenu", false)) // this is body
                .setLifecycleOwner(lifecycleOwner)
                .setAnimation(MenuAnimation.SHOW_UP_CENTER)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setWith(600)
                .setSelectedEffect(false)
                .build();
```

And you can create a customized dialog like below.
```java
CustomPowerMenu customPowerMenu = new CustomPowerMenu.Builder<>(context, new CustomDialogMenuAdapter())
                .setHeaderView(R.layout.layout_custom_dialog_header) // header used for title
                .setFooterView(R.layout.layout_custom_dialog_footer) // footer used for Read More and Close buttons
                 // this is body
                .addItem(new NameCardMenuItem(context.getResources().getDrawable(R.drawable.face3), "Sophie", context.getString(R.string.board3)))
                .setLifecycleOwner(lifecycleOwner)
                .setAnimation(MenuAnimation.SHOW_UP_CENTER)
                .setWith(800)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .build();
```

## Anchor
You can show a popup by anchors. <br><br>

<img src="https://user-images.githubusercontent.com/24237865/38161125-da1ac0aa-3503-11e8-9a91-7dc4f8f4d9c3.gif" align="left" width="30%">

```java
.showAsAnchorLeftTop(view)
.showAsAnchorLeftBottom(view)
.showAsAnchorRightTop(view) // with .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT) looks better
.showAsAnchorRightBottom(view) // with .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT) looks better
.showAsAnchorCenter(view) // with .setAnimation(MenuAnimation.SHOW_UP_CENTER) looks better
```

or you can customize your own position using the below methods.
```java
.getContentViewWidth() // return popup's measured width
.getContentViewHeight() // return popup's measured height
```

like this :
```java
// show popup at the center of an anchor. it's same using .showAsAnchorCenter
hamburgerMenu.showAsDropDown(view, 
             view.getMeasuredWidth()/2 - hamburgerMenu.getContentViewWidth(), 
             -view.getMeasuredHeight()/2 - hamburgerMenu.getContentViewHeight());
```
<br>

## Background
This is options about the background.
```java
.setShowBackground(false) // do not showing background
.setTouchInterceptor(onTouchListener) // set touch listener what outside of popup.
.setFocusable(true) // do not allow touch outside of popup
```

## Functions
### Popup & Item Attributes
```java
.addItemList(list)
.addItem(new PowerMenuItem("Journals", false)) // add a PowerMenuItem
.addItem(3, new PowerMenuItem("Travel", false)) // add a PowerMenuItem at position 3
.setLifecycleOwner(lifecycleOwner) // set LifecycleOwner about activity or fragment. This helps avoid a memory leak.
.setWith(300) // set popup width size
.setHeight(400) // set popup height size
.setMenuRadius(10f) // set popup corner radius
.setMenuShadow(10f) // set popup shadow
.setDivider(new ColorDrawable(context.getResources().getColor(R.color.md_blue_grey_300))) // set a divider
.setDividerHeight(1) // set divider's height
.setAnimation(MenuAnimation.FADE) // set Animation
.setTextColor(context.getResources().getColor(R.color.md_grey_800)) // set normal item text color
.setSelectedTextColor(Color.WHITE) // set selected item text color
.setMenuColor(Color.WHITE) // set normal item background color
.setSelectedMenuColor(context.getResources().getColor(R.color.colorPrimary)) // set selected item background color
.setSelectedEffect(false) // if false, no apply selected colors(text, background)
.setOnMenuItemClickListener(onMenuItemClickListener) // add an item click listener
.setOnDismissListener(OnDismissedListener onDismissListener) // add a menu dismiss listener
.setHeaderView(int layout) // set a fixed view to appear at the top of the list
.setHeaderView(View view) // set a fixed view to appear at the top of the list
.setFooterView(View v) // set a fixed view to appear at the bottom of the list
.setFooterView(View view) // set a fixed view to appear at the bottom of the list
.setSelection(int position) // scroll to position
.getSelectedPosition() // get the last selected item's position. if not selected before, returns -1 .
.getHeaderView() // get header view
.getFooterView() // get footer view
.getMenuListView() // return menu ListView on Powermenu
```

### Background Attributes
```java
.setBackgroundAlpha(0.7f) // set background's alpha
.setBackgroundColor(Color.GRAY) // set background's color
.setShowBackground(false) // set showing background
.setOnBackgroundClickListener(onClickListener) // set a background click listener. the default is dismissing the popup.
```

### Show & Dismiss
```java
.showAsDropDown(View anchor); // shows a popup as drop-down below an anchor view
.showAsDropDown(View anchor, -370, 0); // showAsDropDown with offset (xoff, yoff)
.showAtCenter(View layout); // shows a popup at anchor view's center
.showAtCenter(View layout, 0, 0); // showAtCenter with offset (xoff, yoff)
.showAtLocation(View anchor, int xOff, int yOff)
.showAtLocation(View anchor, int gravity, int xOff, int yOff)
.isShowing(); return true or false
.dismiss(); // dismiss popup
```

## Avoid Memory leak
Dialog, PopupWindow and etc.. have memory leak issue if not dismissed before activity or fragment are destroyed.<br>
But Lifecycles are now also integrated with the Support Library since Architecture Components 1.0 Stable released.<br>
So you can solve memory leak issue so easily.<br>

Just use `setLifecycleOwner` method. Then `dismiss` method will be called automatically before activity or fragment are destroyed.
```java
.setLifecycleOwner(lifecycleOwner)
```

# License
```xml
Copyright 2017 skydoves

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
