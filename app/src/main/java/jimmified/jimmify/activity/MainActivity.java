package jimmified.jimmify.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jimmified.jimmify.R;
import jimmified.jimmify.application.SaveSharedPreference;
import jimmified.jimmify.fragment.QueryListFragment;
import jimmified.jimmify.fragment.QueueFragment;
import jimmified.jimmify.fragment.RecentFragment;
import jimmified.jimmify.fragment.SettingsFragment;

public class MainActivity
        extends AppCompatActivity
        implements SettingsFragment.OnUseTestQueriesListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mViewPager;

    PreferenceFragmentCompat mSettingsFragment;
    QueueFragment mQueueFragment;
    RecentFragment mRecentFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                mViewPager.setCurrentItem(2);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Subscribe to notifications
        FirebaseMessaging.getInstance().subscribeToTopic("search");

        setSupportActionBar(mToolbar);

        // Prepare view pager
        if (mQueueFragment == null)
            mQueueFragment = QueueFragment.newInstance();
        if (mRecentFragment == null)
            mRecentFragment = RecentFragment.newInstance();
        if (mSettingsFragment == null)
            mSettingsFragment = SettingsFragment.newInstance();

        final TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        tabsAdapter.addFragment(mQueueFragment, "Queue");
        tabsAdapter.addFragment(mRecentFragment, "Recent");
        tabsAdapter.addFragment(mSettingsFragment, "Settings");

        mViewPager.setAdapter(tabsAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                tabsAdapter.closeCards();
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);

        onUseTestQueries(SaveSharedPreference.getUseTestQueries());
    }

    @Override
    public void onUseTestQueries(boolean useTestQueries) {
        if (useTestQueries) {
            mQueueFragment.useTestQueries(5);
            mRecentFragment.useTestQueries(5);
        } else {
            mQueueFragment.clearTestQueries();
            mRecentFragment.clearTestQueries();
        }
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

        public void closeCards() {
            for (Fragment frag : mFragments) {
                try {
                    ((QueryListFragment) frag).closeCards();
                } catch (ClassCastException e) {

                }
            }
        }
    }
}
