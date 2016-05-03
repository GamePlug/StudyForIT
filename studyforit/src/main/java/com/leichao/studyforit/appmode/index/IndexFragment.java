package com.leichao.studyforit.appmode.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leichao.studyforit.R;
import com.leichao.studyforit.common.base.BaseListFragment;
import com.leichao.studyforit.common.widget.pullrecycler.BaseViewHolder;
import com.leichao.studyforit.common.widget.pullrecycler.GridItemDecoration;
import com.leichao.studyforit.common.widget.pullrecycler.ILayoutManager;
import com.leichao.studyforit.common.widget.pullrecycler.MyGridLayoutManager;
import com.leichao.studyforit.common.widget.pullrecycler.PullRecycler;
import com.leichao.studyforit.test.list.TestListActivity;
import com.leichao.studyforit.test.loading.TestLoadingActivity;
import com.leichao.studyforit.test.net.TestNetActivity;

/**
 *
 * Created by leichao on 2016/4/18.
 */
public class IndexFragment extends BaseListFragment<SubjectBean> {

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        recycler = (PullRecycler) view.findViewById(R.id.index_pullrecycler);
        return view;
    }

    @Override
    public void initData() {
        mDataList.add(new SubjectBean(TestNetActivity.class, R.drawable.tab_discover_normal, "Net"));
        mDataList.add(new SubjectBean(TestListActivity.class, R.drawable.tab_discover_pressed, "List"));
        mDataList.add(new SubjectBean(TestLoadingActivity.class, R.drawable.tab_discover_pressed, "Loading"));
        mDataList.add(new SubjectBean(TestNetActivity.class, R.drawable.tab_discover_normal, "Net"));
        mDataList.add(new SubjectBean(TestListActivity.class, R.drawable.tab_discover_pressed, "List"));
        mDataList.add(new SubjectBean(TestLoadingActivity.class, R.drawable.tab_discover_pressed, "Loading"));
        mDataList.add(new SubjectBean(null, 0, ""));
        mDataList.add(new SubjectBean(null, 0, ""));
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected ILayoutManager getLayoutManager() {
        return new MyGridLayoutManager(getActivity(), 4);
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new GridItemDecoration(getActivity(), R.drawable.list_divider);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_subject, parent, false);
        return new SampleViewHolder(view);
    }

    class SampleViewHolder extends BaseViewHolder {

        ImageView image;
        TextView desc;

        public SampleViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_index_subject_image);
            desc = (TextView) itemView.findViewById(R.id.item_index_subject_desc);
        }

        @Override
        public void onBindViewHolder(int position) {
            SubjectBean bean = mDataList.get(position);
            image.setImageResource(bean.getImageResource());
            desc.setText(bean.getDesc());
        }

        @Override
        public void onItemClick(View view, int position) {
            SubjectBean bean = mDataList.get(position);
            if (bean.getClazz() != null) {
                Intent intent = new Intent(getActivity(), bean.getClazz());
                getActivity().startActivity(intent);
            }
        }

    }
}
