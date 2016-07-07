package com.mgodevelopment.friends;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
//import android.app.LoaderManager;
//import android.content.Loader;

import java.util.List;

/**
 * Created by moa21 on 7/7/2016.
 */
public class FriendsListFragment extends ListFragment
implements LoaderManager.LoaderCallbacks<List<Friend>> {

    private static final String LOG_TAG = FriendsListFragment.class.getSimpleName();
    private FriendsCustomAdapter mAdapter;
    private static final int LOADER_ID = 1;
    private ContentResolver mContentResolver;
    private List<Friend> mFriends;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mContentResolver = getActivity().getContentResolver();
        mAdapter = new FriendsCustomAdapter(getActivity(), getChildFragmentManager());
        setEmptyText("No friends");
        setListAdapter(mAdapter);
        setListShown(false);

        getLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int i, Bundle bundle) {

        mContentResolver = getActivity().getContentResolver();
        return new FriendsListLoader(getActivity(), FriendsContract.URI_TABLE, mContentResolver);

    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> friends) {

        mAdapter.setData(friends);
        mFriends = friends;

        if (isResumed()) {
            setListShown(true);
        }else{
            setListShownNoAnimation(true);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {
        mAdapter.setData(null);
    }
}
