package com.chengfan.xiyou.utils;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class UserUtils {
    private Myinter mMyinter;
    private Attention attention;

    public void setiFun(Myinter mMyinter) {
        this.mMyinter = mMyinter;
    }

    public void setattention(Attention attention) {
        this.attention = attention;
    }
    public void login(boolean mboolean){
        if(mMyinter == null) return;
        mMyinter.myss(mboolean);
    }

    public void attention(){
        if(attention == null) return;
        attention.attention();
    }
}
