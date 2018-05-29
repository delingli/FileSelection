package com.stkj.freeshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stkj.freeshare.R;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

public final class MainActivity extends TitleBarActivity implements
        FlexibleAdapter.OnItemClickListener {
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTVTitle.setText(R.string.freeshare);
        setContentView(R.layout.activity_main);
        RecyclerView list = findViewById(R.id.main_content_rv);
        list.setLayoutManager(new LinearLayoutManager(this));
        List<AbstractFlexibleItem> items = new ArrayList<>();
        items.add(new MainImageItem());
        items.add(new MainSectionItem(R.string.main_section_send,
                R.drawable.ic_main_section_send));
        items.add(new MainSectionItem(R.string.main_section_receive,
                R.drawable.ic_main_section_receive));
        FlexibleAdapter<AbstractFlexibleItem> adapter = new FlexibleAdapter<>(items);
        adapter.addListener(this);
        list.setAdapter(adapter);
    }

    @Override
    public final boolean onItemClick(final View view, final int position) {
        switch (position) {
            case 1:
                startActivity(new Intent(this, ResourceActivity.class));
                return true;
            case 2:
                startActivity(new Intent(this, ReceiveActivity.class));
                return true;
        }
        return false;
    }

    private static final class MainImageItem extends AbstractFlexibleItem<MainImageItem
            .MainImageViewHolder> {

        @Override
        public final boolean equals(final Object o) {
            return this == o;
        }

        @Override
        public final int getLayoutRes() {
            return R.layout.main_item_image;
        }

        @Override
        public final MainImageViewHolder createViewHolder(
                final View view, final FlexibleAdapter<IFlexible> adapter) {
            return new MainImageViewHolder(view, adapter);
        }

        @Override
        public final void bindViewHolder(final FlexibleAdapter<IFlexible> adapter,
                                         final MainImageViewHolder holder,
                                         final int position, final List<Object> payloads) {
        }

        static final class MainImageViewHolder extends FlexibleViewHolder {
            private MainImageViewHolder(final View view, final FlexibleAdapter adapter) {
                super(view, adapter);
            }
        }
    }

    private static final class MainSectionItem extends AbstractFlexibleItem<MainSectionItem
            .MainSectionViewHolder> {
        private final int RES_TITLE, RES_ICON;

        private MainSectionItem(int title, int icon) {
            RES_TITLE = title;
            RES_ICON = icon;
        }

        @Override
        public final boolean equals(final Object o) {
            return this == o;
        }

        @Override
        public final int getLayoutRes() {
            return R.layout.main_item_section;
        }

        @Override
        public final MainSectionViewHolder createViewHolder(
                final View view, final FlexibleAdapter<IFlexible> adapter) {
            return new MainSectionViewHolder(view, adapter);
        }

        @Override
        public final void bindViewHolder(final FlexibleAdapter<IFlexible> adapter,
                                         final MainSectionViewHolder holder, final int position,
                                         final List<Object> payloads) {
            holder.TV_ITEM.setText(RES_TITLE);
            holder.TV_ITEM.setCompoundDrawablesWithIntrinsicBounds(RES_ICON, 0, 0, 0);
        }

        static final class MainSectionViewHolder extends FlexibleViewHolder {
            private final TextView TV_ITEM;

            private MainSectionViewHolder(final View view, final FlexibleAdapter adapter) {
                super(view, adapter);
                TV_ITEM = (TextView) view;
            }
        }
    }
}