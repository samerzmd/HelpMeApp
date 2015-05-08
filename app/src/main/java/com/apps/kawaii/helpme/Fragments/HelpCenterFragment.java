package com.apps.kawaii.helpme.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.kawaii.helpme.R;
import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpCenterFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";
    private Fragment[] homeFragments;

    private static Class<?>[] homeFragmentClasses = {
            HelpRequesterFragment.class, BlankFragment.class};

    public static HelpCenterFragment newInstance(int sectionNumber) {
        HelpCenterFragment fragment = new HelpCenterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public HelpCenterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        homeFragments = new Fragment[homeFragmentClasses.length];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_help_center, container, false);
        ButterKnife.inject(this, view);
        ViewPager viewPager = (ViewPager) view.findViewById(
                R.id.home_tabs_pager);
        viewPager.setAdapter(new HomePagerAdapter(
                getChildFragmentManager()));
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);


        return view;
    }
    public class HomePagerAdapter extends FragmentPagerAdapter {

        public HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return homeFragments.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fg = null;

            switch (position){
                case 0:
                    fg=new HelpRequesterFragment();
                    break;
                case 1:
                    fg=new BlankFragment();
                    break;
            }
            return fg;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Help Center";
        }
    }

}
