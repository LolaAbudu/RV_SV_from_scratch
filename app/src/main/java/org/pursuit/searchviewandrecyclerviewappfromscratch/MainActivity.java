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
import org.pursuit.searchviewandrecyclerviewappfromscratch.model.StateResponse;
import org.pursuit.searchviewandrecyclerviewappfromscratch.network.RetrofitSingleton;
import org.pursuit.searchviewandrecyclerviewappfromscratch.network.StateService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//this code is using best practices(According to Rusi)

//to clear your network call, add them into the CompositeDisposable, then in onStop or onPause,
// clear the disposable(prevent leaks and the call constantly running)
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private List<State> stateList;
    private RecyclerView recyclerView;
    private StateAdapter stateAdapter;
    private SearchView searchView;

    private final CompositeDisposable disposable = new CompositeDisposable();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stateList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);

        final Retrofit retrofit = RetrofitSingleton.getInstance();
        StateService stateService = retrofit.create(StateService.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

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

    //use either 2 below to end/stop the call
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
        for(State state : stateList){
            if(state.getName().toLowerCase().startsWith(userInput.toLowerCase())){
                newStateList.add(state);
            }
        }
        stateAdapter.setData(newStateList);
        return false;
    }
}


//        RetrofitSingleton.getInstance()
//                .create(StateService.class)
//                .getStateResponse()
//                .enqueue(new Callback<StateResponse>() {
//                    @Override
//                    public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
//                        Log.d("TAG", "onResponse" + response.body().getAR().getName());
//                    }
//
//                    @Override
//                    public void onFailure(Call<StateResponse> call, Throwable t) {
//                        Log.d("TAG", "onFailure" + t.getMessage());
//                    }
//                });

//                        state -> {
//                            stateList = new ArrayList<>();
//
//                            Log.d("TAG", "onResponse" + state.getAL().getName());
////                            stateList = new ArrayList<>();
////                            stateList.addAll(state.getAK().getName().)
//                        },
//                        throwable -> Log.d("TAG", "onFailure" + throwable)