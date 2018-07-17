package com.nestliveat500px.liveat500px.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.nestliveat500px.liveat500px.R;
import com.nestliveat500px.liveat500px.activity.MoreInfoActivity;
import com.nestliveat500px.liveat500px.adapter.PhotoListAdapter;
import com.nestliveat500px.liveat500px.dao.PhotoItemCollectionDao;
import com.nestliveat500px.liveat500px.dao.PhotoItemDao;
import com.nestliveat500px.liveat500px.manager.PhotoListManager;
import com.nestliveat500px.liveat500px.manager.http.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {

    /*
    Variable Zone
     */

    public interface FlagmentListener{
        void onPhotoItemClicked(PhotoItemDao dao);
    }

    private ListView listView;
    private PhotoListAdapter photoListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    /*
    Medthod Zone
     */

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView);
        photoListAdapter = new PhotoListAdapter();
        listView.setAdapter(photoListAdapter);
        listView.setOnItemClickListener(listViewItemClickListener);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(refreshLitener);
        listView.setOnScrollListener(listViewScrollListener);
        reloadData();


    }

    private void reloadData() {
        swipeRefreshLayout.setRefreshing(false); // stop refresh
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getApiService().loadPhotoList();
        call.enqueue(new Callback<PhotoItemCollectionDao>() {
            @Override
            public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
                if (response.isSuccessful()) {
                    PhotoItemCollectionDao dao = response.body();
                    PhotoListManager.getInstance().setDao(dao);
                    photoListAdapter.notifyDataSetChanged();
                    showToast(dao.getData().get(0).getCaption());

                } else {
                    //Handle
                    swipeRefreshLayout.setRefreshing(false); // stop refresh
                    String Error = response.errorBody().toString();
                    showToast(Error.toString());
                }
            }

            @Override
            public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
                //Handle
                showToast(t.toString());
                swipeRefreshLayout.setRefreshing(false); // stop refresh
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }


    /*
    Listener Zone
     */
    SwipeRefreshLayout.OnRefreshListener refreshLitener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            reloadData();
        }
    };
    AbsListView.OnScrollListener listViewScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {
        }
        @Override
        public void onScroll(AbsListView absListView,
                             int firstVisibleItem,
                             int visibleItemCount,
                             int totalItemCount) {
            swipeRefreshLayout.setEnabled(firstVisibleItem == 0);
        }
    };
    AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//            showToast("Position : "+position);
            PhotoItemDao dao = PhotoListManager.getInstance().getDao().getData().get(position);
            FlagmentListener listener = (FlagmentListener) getActivity();
            listener.onPhotoItemClicked(dao);
        }
    };

    /*
    Inner Class
     */
    private void showToast(String text) {
        Toast.makeText(Contextor.getInstance().getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
