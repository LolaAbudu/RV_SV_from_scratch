package org.pursuit.searchviewandrecyclerviewappfromscratch;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import org.pursuit.searchviewandrecyclerviewappfromscratch.controller.StateAdapter;
import org.pursuit.searchviewandrecyclerviewappfromscratch.model.State;
import org.pursuit.searchviewandrecyclerviewappfromscratch.network.RetrofitSingleton;
import org.pursuit.searchviewandrecyclerviewappfromscratch.network.StateService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

//this code is using best practices, but not the architecture(According to Rusi)

//to clear your network call, add them into the CompositeDisposable, then in onStop or onPause,
// clear the disposable(prevent leaks and the call constantly running)
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private List<State> stateList;
    private RecyclerView recyclerView;
    private StateAdapter stateAdapter;

    private final CompositeDisposable disposable = new CompositeDisposable();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stateList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        final Retrofit retrofit = RetrofitSingleton.getInstance();
        StateService stateService = retrofit.create(StateService.class);

        disposable.add(
                stateService.getStateResponse()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                stateResponse -> {
                                    Log.d("TAG", "onResponse" + stateResponse.getAL().getName());

                                    stateList.addAll(stateResponse.getStateList());

                                    //always make an if check for list when making a call
                                    if (stateList != null) {
                                        stateAdapter = new StateAdapter(stateList);
                                        recyclerView.setAdapter(stateAdapter);
                                    }
                                }, throwable -> {
                                    Log.d("TAG", "onFailure" + throwable);
                                })
        );
    }

    //use either onPause or onStop to end/stop/clear the retrofit call that i saved
    // inside a disposable, so you don't leak info or waste memory
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        disposable.clear();
        super.onStop();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String userInput) {
        List<State> newStateList = new ArrayList<>();
        for (State state : stateList) {
            if (state.getName().toLowerCase().startsWith(userInput.toLowerCase())) {
                newStateList.add(state);
            }
        }
        stateAdapter.setData(newStateList);
        return false;
    }
}
