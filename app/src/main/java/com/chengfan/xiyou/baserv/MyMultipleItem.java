package com.chengfan.xiyou.baserv;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chengfan.xiyou.domain.model.entity.AccompanyEntity;

import java.util.List;


/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class MyMultipleItem implements MultiItemEntity {

    public static final int FIRST_TYPE = 1;//设置布局编号
    public static final int SECOND_TYPE = 2;
    public static final int NORMAL_TYPE = 3;

    private int itemType;
    AccompanyEntity.AccompanyPlayBean list;


    public MyMultipleItem(int itemType, AccompanyEntity.AccompanyPlayBean list) {
        this.itemType = itemType;
        this.list = list;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public AccompanyEntity.AccompanyPlayBean  getData(){
        return  list;
    }
}