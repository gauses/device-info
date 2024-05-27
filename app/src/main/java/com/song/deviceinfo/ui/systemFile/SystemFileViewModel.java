package com.song.deviceinfo.ui.systemFile;

import com.song.deviceinfo.info.PartitionsInfo;
import com.song.deviceinfo.model.beans.PartitionsBean;
import com.song.deviceinfo.ui.base.BaseViewModel;

import java.util.List;

/**
 * Created by chensongsong on 2020/5/29.
 */
public class SystemFileViewModel extends BaseViewModel {

    protected List<PartitionsBean> getPartitionsInfo(){

        return PartitionsInfo.getSysTemFilePartitionsInfo();
    }

}
