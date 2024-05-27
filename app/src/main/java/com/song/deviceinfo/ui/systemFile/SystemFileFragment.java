package com.song.deviceinfo.ui.systemFile;

import androidx.lifecycle.ViewModelProviders;

import com.song.deviceinfo.model.beans.PartitionsBean;
import com.song.deviceinfo.ui.base.BaseAdapter;
import com.song.deviceinfo.ui.base.BaseFragment;
import com.song.deviceinfo.ui.base.BaseViewModel;
import com.song.deviceinfo.utils.LogUtils;
import com.song.deviceinfo.utils.ThreadPoolUtils;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by chensongsong on 2020/5/29.
 */
public class SystemFileFragment extends BaseFragment<PartitionsBean> {

    @Override
    protected BaseAdapter crateAdapter() {
        return new SystemFileAdapter(getContext());
    }

    @Override
    protected BaseViewModel crateViewModel() {
        return ViewModelProviders.of(this).get(SystemFileViewModel.class);
    }

    @Override
    protected void refreshData() {
        ThreadPoolUtils.getInstance().execute(() -> {
            final List<PartitionsBean> list = getPartitionsInfo();
            JSONArray jsonArray = new JSONArray();
            for (PartitionsBean bean : list) {
                try {
                    jsonArray.put(bean.toJSON());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            LogUtils.printLongString(jsonArray.toString());
            mainHandler.post(() -> {
                viewModel.setValue(list);
            });
        });
    }

    private List<PartitionsBean> getPartitionsInfo() {
        return ((SystemFileViewModel) viewModel).getPartitionsInfo();
    }

}
