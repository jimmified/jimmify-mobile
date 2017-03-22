package com.jimmified.search.settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimmified.search.R;
import com.jimmified.search.JimmifyApplication;

public class SettingsFragment extends PreferenceFragmentCompat {

    Preference mLogoutButton;
    PreferenceCategory mPreferenceScreen;
    ListPreference mSwitchAnswerViews;
    SwitchPreferenceCompat mUseTestQueries;
    EditTextPreference mJimmifyAPI;

    private OnLogoutListener mOnLogoutListener;
    private OnUseTestQueriesListener mUseTestQueriesListener;

    public interface OnLogoutListener {
        void onLogout();
    }

    public interface OnUseTestQueriesListener {
        void onUseTestQueries(boolean useTestQueries);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle
                                     savedInstanceState) {

        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mPreferenceScreen = (PreferenceCategory) findPreference("jimmy_options_screen");

//        mSwitchAnswerViews = (ListPreference) mPreferenceScreen.findPreference(SaveSharedPreference.DB_ANSWER_VIEW_TYPE_FIELD);
//        if (mSwitchAnswerViews != null) {
//            mSwitchAnswerViews.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(Preference preference, Object newValue) {
//                    mSwitchAnswerViewsListener.onSwitchAnswerViews();
//                    return true;
//                }
//            });
//        }

        mLogoutButton = findPreference(getString(R.string.pref_logout));
        if (mLogoutButton != null) {
            mLogoutButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    mOnLogoutListener.onLogout();
                    return true;
                }
            });
        }

        mUseTestQueries = (SwitchPreferenceCompat) mPreferenceScreen.findPreference(SaveSharedPreference.DB_USE_TEST_QUERIES_FIELD);
        if (mUseTestQueries != null) {
            mUseTestQueries.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    mUseTestQueriesListener.onUseTestQueries((Boolean) newValue);
                    return true;
                }
            });
        }

        onAttachActivity(getActivity());

        mJimmifyAPI = (EditTextPreference) findPreference(getString(R.string.pref_api_url));
        mJimmifyAPI.setText(JimmifyApplication.getJimmifyURL());

        mJimmifyAPI.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newURL) {
                JimmifyApplication.setJimmifyURL(newURL.toString());
                return true;
            }
        });

        return view;
    }

    public void onAttachActivity(Activity activity) {
//        if (mSwitchAnswerViews != null) {
//            try {
//                mSwitchAnswerViewsListener = (OnSwitchAnswerViewsListener) activity;
//            } catch (ClassCastException e) {
//                throw new ClassCastException(
//                        activity.toString() + " must implement OnResetFeedsRequestListener");
//            }
//        }
        if (mLogoutButton != null) {
            try {
                mOnLogoutListener = (OnLogoutListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(
                        activity.toString() + " must implement OnPlayerSelectionSetListener");
            }
        }
        if (mUseTestQueries != null) {
            try {
                mUseTestQueriesListener = (OnUseTestQueriesListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(
                        activity.toString() + " must implement OnPlayerSelectionSetListener");
            }
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the default white background in the view so as to avoid transparency
//        view.setBackgroundColor(
//                ContextCompat.getColor(getContext(), R.color.background_material_light));
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String root) {
        setPreferencesFromResource(R.xml.pref_general, root);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public SettingsFragment() { }

    public static SettingsFragment newInstance() {
        SettingsFragment settingsFragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        settingsFragment.setArguments(bundle);
        return settingsFragment;
    }


}
