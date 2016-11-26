package jimmified.jimmify.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jimmified.jimmify.R;
import jimmified.jimmify.fragment.QueueFragment;
import jimmified.jimmify.fragment.RecentFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        // Prepare view pager
        QueueFragment qf = QueueFragment.newInstance();
        RecentFragment rf = RecentFragment.newInstance();

        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        tabsAdapter.addFragment(qf, "Queue");
        tabsAdapter.addFragment(rf, "Recent");

        mViewPager.setAdapter(tabsAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class TabsAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mTitles = new ArrayList<>();

        public TabsAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        public void addFragment(Fragment frag, String title) {
            mFragments.add(frag);
            mTitles.add(title);
        }
    }
}
