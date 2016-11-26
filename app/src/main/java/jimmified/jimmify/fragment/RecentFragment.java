package jimmified.jimmify.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jimmified.jimmify.request.model.QueryModel;

public class RecentFragment extends QueryListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle
                                     savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        for (int i = 0; i < 20; i++)
            queryList.add(new QueryModel(i, "search", "Test #" + String.valueOf(i)));

        return view;
    }

    public static RecentFragment newInstance() {
        RecentFragment recentFragment = new RecentFragment();
        Bundle bundle = new Bundle();

        recentFragment.setArguments(bundle);
        recentFragment.type = Type.RECENT;

        return recentFragment;
    }
}
