package com.king.chatdemo;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.king.chatdemo.Message.ChatMessage;

import java.util.List;

/**
 * User: king
 * Date: 2015/4/16
 */
public class ChatAdapter extends BaseAdapter {
    private List<ChatMessage> messages;
    private Context context;
    private LayoutInflater inflater;

    public ChatAdapter(List<ChatMessage> messages, Context context) {
        this.messages = messages;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (messages != null) {
            ret = messages.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        Object ret = null;
        if (messages != null) {
            ret = messages.get(position);
        }
        return ret;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 返回当前ListView显示的布局有几种类型
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * 这个方法，返回 0--类型个数-1
     * 必须是这样的方式
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messages.get(position);
        int ret = 0;
        String to = message.getTo();
        if (to.equals("me")) {
            ret = 0;
        } else {
            ret = 1;
        }
        return ret;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret = null;
        //TODO  需要展现界面，实现两个布局共存的ListView
        Log.i("ChatAdapter", "getView" + position + ",cv" + convertView);
        ChatMessage message = messages.get(position);
        String to = message.getTo();
        if (!to.equals("me")) {
            //右侧 RelativeLayout
            if (convertView != null) {
                ret = convertView;
            } else {
                ret = inflater.inflate(R.layout.item_chat_right, parent, false);
            }
            //TODO  处理右侧 Holder
            //右侧就用右侧  RightHolder
            RightHolder holder = (RightHolder) ret.getTag();
            if (holder == null) {
                holder = new RightHolder();
                holder.txtRight = (TextView) ret.findViewById(R.id.txt_chatright);
                holder.imgRight = (ImageView) ret.findViewById(R.id.img_iconright);
                ret.setTag(holder);
            }
            holder.txtRight.setText(exSb(messages.get(position).getContent()));
            holder.imgRight.setImageResource(messages.get(position).getImgId());
        } else {
            //左侧  LinearLayout
            if (convertView != null) {
                //如果复用布局有，就直接使用
                ret = convertView;
            } else {
                //没有复用，创建
                ret = inflater.inflate(R.layout.item_chat_left, parent, false);
            }
            //TODO 处理 左侧Holder
            //左侧就用左侧的 LeftHoler
            LeftHolder holder = (LeftHolder) ret.getTag();

            if (holder == null) {
                holder = new LeftHolder();
                holder.txtLeft = (TextView) ret.findViewById(R.id.txt_chatleft);
                holder.imgLeft = (ImageView) ret.findViewById(R.id.img_iconleft);
                ret.setTag(holder);
            }

            //TODO 显示布局，下载图片
            holder.txtLeft.setText(exSb(messages.get(position).getContent()));
            holder.imgLeft.setImageResource(messages.get(position).getImgId());

        }

        return ret;
    }

    private static class LeftHolder {
        private TextView txtLeft;
        private ImageView imgLeft;
    }

    private static class RightHolder {
        public TextView txtRight;
        private ImageView imgRight;
    }

    private SpannableStringBuilder exSb(String content){
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(content);
        if (content.contains("/ic-launcher")){
            int i = content.indexOf("/ic-launcher");
            ImageSpan imageSpan = new ImageSpan(context,R.drawable.ic_launcher);
            sb.setSpan(imageSpan,i,i+"/ic-launcher".length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return sb;
    }

}
