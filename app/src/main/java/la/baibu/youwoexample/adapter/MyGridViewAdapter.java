package la.baibu.youwoexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import la.baibu.youwoexample.R;
import la.baibu.youwoexample.bean.SelectImageBean;
import la.baibu.youwoexample.utils.FrescoUtil;

/**
 * Created by minna_Zhou on 2016/12/3 0003.
 * 水平照片墙的adapter
 */
public class MyGridViewAdapter extends BaseAdapter {
    public static int maxPhotoes = 9;
    private Context context;
    private List<SelectImageBean> mImages;


    public MyGridViewAdapter(Context context, List<SelectImageBean> mImages) {

        this.context = context;
        this.mImages = mImages;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object getItem(int position) {
        return mImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_image, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.defaultImage = (ImageView) convertView.findViewById(R.id.iv_default_type);
            viewHolder.addTypeImage = (SimpleDraweeView) convertView.findViewById(R.id.iv_add_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        SelectImageBean bean = mImages.get(position);
        if (bean.getImageType() == SelectImageBean.TYPE_DEFAULT_IMAGE) {
            viewHolder.defaultImage.setVisibility(View.VISIBLE);
            viewHolder.addTypeImage.setVisibility(View.GONE);
        } else if (bean.getImageType() == SelectImageBean.TYPE_IMAGE) {
            viewHolder.defaultImage.setVisibility(View.GONE);
            viewHolder.addTypeImage.setVisibility(View.VISIBLE);
            FrescoUtil.displayImageHttpOrFile(bean.getImagePath(), viewHolder.addTypeImage);
        }
        return convertView;
    }

    private static class MyViewHolder {
        ImageView defaultImage;
        SimpleDraweeView addTypeImage;
    }
}
