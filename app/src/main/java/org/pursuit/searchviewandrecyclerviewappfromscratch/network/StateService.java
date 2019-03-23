package org.pursuit.searchviewandrecyclerviewappfromscratch.network;

import org.pursuit.searchviewandrecyclerviewappfromscratch.model.StateResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface StateService {
    String END_POINT = "jpriebe/d62a45e29f24e843c974/raw/b1d3066d245e742018bce56e41788ac7afa60e29/us_state_capitals.json";

    @GET(END_POINT)
    Single<StateResponse> getStateResponse();
}
//use Single for just one call bc they only return onSuccess and onError, observable returns
// 4 dif overridden methods and call new SingleObserver in the call(look in MainActivity)