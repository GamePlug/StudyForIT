package com.leichao.studyforit.common.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.leichao.studyforit.R;
import com.leichao.studyforit.common.widget.pullrecycler.BaseListAdapter;
import com.leichao.studyforit.common.widget.pullrecycler.BaseViewHolder;
import com.leichao.studyforit.common.widget.pullrecycler.DividerItemDecoration;
import com.leichao.studyforit.common.widget.pullrecycler.PullRecycler;
import com.leichao.studyforit.common.widget.pullrecycler.ILayoutManager;
import com.leichao.studyforit.common.widget.pullrecycler.MyLinearLayoutManager;

import java.util.ArrayList;


/**
 * Created by Stay on 25/2/16.
 * Powered by www.stay4it.com
 */
public abstract class BaseListActivity<T> extends BaseLoadingActivity implements PullRecycler.OnRecyclerRefreshListener {
    protected BaseListAdapter adapter;
    protected ArrayList<T> mDataList;
    protected PullRecycler recycler;

    @Override
    public void initView() {
        setContentView(R.layout.activity_base_list);
        recycler = (PullRecycler) findViewById(R.id.pullRecycler);
    }

    @Override
    public void initData() {
        setUpAdapter();
        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        recycler.setAdapter(adapter);
    }

    @Override
    public void initEvent() {

    }

    protected void setUpAdapter() {
        adapter = new ListAdapter();
    }

    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getApplicationContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getApplicationContext(), R.drawable.list_divider);
    }

    public class ListAdapter extends BaseListAdapter {

        @Override
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        protected int getDataCount() {
            return mDataList != null ? mDataList.size() : 0;
        }

        @Override
        protected int getDataViewType(int position) {
            return getItemType(position);
        }

        @Override
        public boolean isSectionHeader(int position) {
            return BaseListActivity.this.isSectionHeader(position);
        }
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }
    protected int getItemType(int position) {
        return 0;
    }
    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);

}
