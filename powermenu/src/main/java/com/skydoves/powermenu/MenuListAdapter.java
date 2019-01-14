
/*
 * Copyright (C) 2017 skydoves
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.powermenu;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class MenuListAdapter extends MenuBaseAdapter<PowerMenuItem> {

    private int textColor = -2;
    private int menuColor = -2;
    private int selectedTextColor = -2;
    private int selectedMenuColor = -2;

    private boolean selectedEffect = true;

    public MenuListAdapter(ListView listView) {
        super(listView);
    }

    @Override
    public View getView(final int index, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_power_menu, viewGroup, false);
        }

        PowerMenuItem powerMenuItem = (PowerMenuItem) getItem(index);

        final View background = view.findViewById(R.id.item_power_menu_layout);
        final TextView title = view.findViewById(R.id.item_power_menu_title);
        final ImageView icon = view.findViewById(R.id.item_power_menu_icon);

        title.setText(powerMenuItem.title);

        if (powerMenuItem.icon != 0) {
            icon.setImageResource(powerMenuItem.icon);
            icon.setVisibility(View.VISIBLE);
        } else {
            icon.setVisibility(View.GONE);
        }

        if (powerMenuItem.isSelected) {

            setSelectedPosition(index);

            if (selectedMenuColor == -2)
                background.setBackgroundColor(context.getResources().getColor(R.color.menu_background));
            else
                background.setBackgroundColor(selectedMenuColor);

            if (selectedTextColor == -2)
                title.setTextColor(context.getResources().getColor(R.color.menu_text_selected));
            else
                title.setTextColor(selectedTextColor);
        } else {
            if (menuColor == -2)
                background.setBackgroundColor(Color.WHITE);
            else
                background.setBackgroundColor(menuColor);

            if (textColor == -2)
                title.setTextColor(context.getResources().getColor(R.color.menu_text_no_selected));
            else
                title.setTextColor(textColor);
        }
        return super.getView(index, view, viewGroup);
    }

    @Override
    public void setSelectedPosition(int position) {
        super.setSelectedPosition(position);

        if (selectedEffect) {
            for (int i = 0; i < getItemList().size(); i++) {
                PowerMenuItem item = (PowerMenuItem) getItem(i);

                item.setIsSelected(false);
                if (i == position) {
                    item.setIsSelected(true);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void setTextColor(int color) {
        this.textColor = color;
    }

    public void setMenuColor(int color) {
        this.menuColor = color;
    }

    public void setSelectedTextColor(int color) {
        this.selectedTextColor = color;
    }

    public void setSelectedMenuColor(int color) {
        this.selectedMenuColor = color;
    }

    public void setSelectedEffect(boolean selectedEffect) {
        this.selectedEffect = selectedEffect;
    }
}
