package com.example.wms;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {
        switch(position)
        {
            case 1:
                return new ProductMngAddFragment();
            case 2:
                return new ProductMngEditFragment();
            case 3:
                return new ProductMngDeleteFragment();
        }
        return new ProductMngViewFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
