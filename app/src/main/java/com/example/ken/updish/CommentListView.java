/*
    This class is used to adjust the height of the List view in comment
 */

package com.example.ken.updish;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * Created by mjlee on 2018-04-07.
 */

//https://stackoverflow.com/questions/18997729/listview-same-height-as-content/24186596#24186596
public class CommentListView extends ListView {

    public CommentListView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentListView  (Context context) {
        super(context);
    }

    public CommentListView  (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}