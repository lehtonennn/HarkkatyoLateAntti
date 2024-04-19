package com.example.harkkatyolateantti;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.harkkatyolateantti.fragments.BasicInformationFragment;
import com.example.harkkatyolateantti.fragments.ComparisonFragment;
import com.example.harkkatyolateantti.fragments.FragmentC;
import com.example.harkkatyolateantti.fragments.FragmentD;

public class TabPagerAdapter extends FragmentStateAdapter {

    public TabPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new BasicInformationFragment();
            case 1:
                return new ComparisonFragment();
            case 2:
                return new FragmentC();
            case 3:
                return new FragmentD();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
