package com.shtoone.chenjiang.mvp.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.socks.library.KLog;

public class MeasureRVPAdapter extends BaseQuickAdapter<CezhanData, BaseViewHolder> {
    private static final String TAG = MeasureRVPAdapter.class.getSimpleName();
    private int currentPosition = 0;
    private int measureIndex = 0;
    private int[] arrayOdd = {0, Constants.b1, Constants.f1, Constants.f2, Constants.b2};
    private int[] arrayEven = {0, Constants.f1, Constants.b1, Constants.b2, Constants.f2};
    int[] arraySequence = {0};

    public MeasureRVPAdapter() {
        super(R.layout.item_rvp_measure_right_fragment, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, CezhanData mCezhanData) {
        int position = holder.getLayoutPosition() - this.getHeaderLayoutCount();
        //设置数据
        holder.setText(R.id.tv_number_item_rvp_measure_right_fragment, mCezhanData.getNumber())
                .setText(R.id.tv_measure_direction_item_rvp_measure_right_fragment, mCezhanData.getMeasureDirection())
                .setText(R.id.tv_qianshi_item_rvp_measure_right_fragment, "前视:" + mCezhanData.getQianshi())
                .setText(R.id.tv_houshi_item_rvp_measure_right_fragment, "后视:" + mCezhanData.getHoushi())
                .setText(R.id.tv_observe_type_item_rvp_measure_right_fragment, mCezhanData.getObserveType())
                .setText(R.id.tv_b1hd, mCezhanData.getB1hd())
                .setText(R.id.tv_houshijuhe, mCezhanData.getHoushijuhe())
                .setText(R.id.tv_b2hd, mCezhanData.getB2hd())
                .setText(R.id.tv_qianshijuhe, mCezhanData.getQianshijuhe())
                .setText(R.id.tv_f1hd, mCezhanData.getF1hd())
                .setText(R.id.tv_gaocha1, mCezhanData.getGaocha1())
                .setText(R.id.tv_f2hd, mCezhanData.getF2hd())
                .setText(R.id.tv_gaocha2, mCezhanData.getGaocha2())
                .setText(R.id.tv_b1r, mCezhanData.getB1r())
                .setText(R.id.tv_cezhangaocha, mCezhanData.getCezhangaocha())
                .setText(R.id.tv_b2r, mCezhanData.getB2r())
                .setText(R.id.tv_gaochengzhi, mCezhanData.getGaochengzhi())
                .setText(R.id.tv_f1r, mCezhanData.getF1r())
                .setText(R.id.tv_frdushucha, mCezhanData.getFrdushucha())
                .setText(R.id.tv_f2r, mCezhanData.getF2r())
                .setText(R.id.tv_brdushucha, mCezhanData.getBrdushucha())
                //此处要还原样式，因为View会进行复用，会出现后面的item明明没有设置背景变色也跟着前面的变色了。
                .setBackgroundRes(R.id.tv_b1hd, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_b2hd, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_f1hd, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_f2hd, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_b1r, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_b2r, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_f1r, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_f2r, R.drawable.rect_bg_stroke_table);
        //设置样式
        if (currentPosition == position) {

            switch (arraySequence[measureIndex]) {
                case Constants.b1:
                    holder.setBackgroundRes(R.id.tv_b1hd, R.drawable.rect_bg_red_stroke_table)
                            .setBackgroundRes(R.id.tv_b1r, R.drawable.rect_bg_red_stroke_table);
                    break;
                case Constants.b2:
                    holder.setBackgroundRes(R.id.tv_b2hd, R.drawable.rect_bg_red_stroke_table)
                            .setBackgroundRes(R.id.tv_b2r, R.drawable.rect_bg_red_stroke_table);
                    break;
                case Constants.f1:
                    holder.setBackgroundRes(R.id.tv_f1hd, R.drawable.rect_bg_red_stroke_table)
                            .setBackgroundRes(R.id.tv_f1r, R.drawable.rect_bg_red_stroke_table);
                    break;
                case Constants.f2:
                    holder.setBackgroundRes(R.id.tv_f2hd, R.drawable.rect_bg_red_stroke_table)
                            .setBackgroundRes(R.id.tv_f2r, R.drawable.rect_bg_red_stroke_table);
                    break;
            }
        }
    }

    public void measure(int currentPosition, int measureIndex, String result) {
        KLog.e("currentPosition::" + currentPosition);
        KLog.e("measureIndex::" + measureIndex);
        this.currentPosition = currentPosition;
        this.measureIndex = measureIndex;
        CezhanData mCezhanData = mData.get(currentPosition);


        if ((currentPosition + 1) % 2 == 0) {
            //偶数站
            arraySequence = arrayEven;
            KLog.e("//偶数站//偶数站//偶数站//偶数站//偶数站//偶数站");
        } else {
            //奇数站
            arraySequence = arrayOdd;
            KLog.e("//奇数站//奇数站//奇数站//奇数站//奇数站//奇数站//奇数站//奇数站//奇数站//奇数站//奇数站");
        }

        switch (arraySequence[measureIndex]) {
            case Constants.b1:
                mCezhanData.setB1hd(result);
                mCezhanData.setB1r(result);
                KLog.e("b1b1b1b1b1b1b1b1b1b1b1b1");
                break;
            case Constants.b2:
                mCezhanData.setB2hd(result);
                mCezhanData.setB2r(result);
                KLog.e("b2b2b2b2b2b2b2b2b2b2b2b2");
                break;
            case Constants.f1:
                mCezhanData.setF1hd(result);
                mCezhanData.setF1r(result);
                KLog.e("f1f1f1f1f1f1f1f1f1f1");
                break;
            case Constants.f2:
                mCezhanData.setF2hd(result);
                mCezhanData.setF2r(result);
                KLog.e("f2f2f2f2f2f2f2f2f2");
                break;
        }


        notifyDataSetChanged();
    }
}
