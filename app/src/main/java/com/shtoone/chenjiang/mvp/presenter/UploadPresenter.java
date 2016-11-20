package com.shtoone.chenjiang.mvp.presenter;

import com.google.gson.Gson;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.contract.UploadContract;
import com.shtoone.chenjiang.mvp.model.HttpHelper;
import com.shtoone.chenjiang.mvp.model.bean.GongdianData;
import com.shtoone.chenjiang.mvp.model.bean.ShuizhunxianData;
import com.shtoone.chenjiang.mvp.presenter.base.BasePresenter;
import com.socks.library.KLog;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class UploadPresenter extends BasePresenter<UploadContract.View> implements UploadContract.Presenter {

    private static final String TAG = UploadPresenter.class.getSimpleName();
    private Gson mGson;

    public UploadPresenter(UploadContract.View mView) {
        super(mView);
    }

    @Override
    public void start() {
        requestGongdianData();
        requestShuizhunxianData(0);
    }

    public void requestGongdianData() {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<GongdianData>>() {
            @Override
            public void call(Subscriber<? super List<GongdianData>> subscriber) {
                try {
                    List<GongdianData> mGongdianData = DataSupport.findAll(GongdianData.class);
                    subscriber.onNext(mGongdianData);
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<GongdianData>>() {

                               @Override
                               public void _onNext(List<GongdianData> mGongdianData) {
                                   getView().responseGongdianData(mGongdianData);
                                   //下载完工点后就下载水准线
                               }
                           }
                ));
    }

    @Override
    public void requestShuizhunxianData(final int pagination) {
        mRxManager.add(Observable.create(new Observable.OnSubscribe<List<ShuizhunxianData>>() {

                    @Override
                    public void call(Subscriber<? super List<ShuizhunxianData>> subscriber) {
                        List<ShuizhunxianData> mShuizhunxianData = null;
                        try {
                            //分页查询每次查询PAGE_SIZE条，从0开始。
                            mShuizhunxianData = DataSupport.select("*")
                                    .order("id").limit(Constants.PAGE_SIZE)
                                    .offset(pagination * Constants.PAGE_SIZE)
                                    .find(ShuizhunxianData.class);
                            subscriber.onNext(mShuizhunxianData);
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                        //做此判断的目的是为了不让最后onCompleted()调用的showContent()遮住了showEmpty()的显示。
                        if (mShuizhunxianData != null && mShuizhunxianData.size() > 0) {
                            subscriber.onCompleted();
                        }
                    }
                }).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxSubscriber<List<ShuizhunxianData>>() {
                            @Override
                            public void _onNext(List<ShuizhunxianData> mShuizhunxianData) {
                                getView().responseShuizhunxianData(mShuizhunxianData, pagination);
                            }
                        })

        );
    }

    @Override
    public void upload(List<ShuizhunxianData> listShuizhunxian) {
        if (mGson == null) {
            mGson = new Gson();
        }
        String str = mGson.toJson(listShuizhunxian, ArrayList.class);
        KLog.e(str);

        HttpHelper.getInstance().initService().upload(str).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        KLog.e("responseBody:"+response.body().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
