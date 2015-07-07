package com.marshalchen.ultimaterecyclerview.demo.swipelist;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.demo.R;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {

    private List<String> data;
    private Context context;

    public PackageAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }


    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.package_row, viewGroup, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final String item = getItem(i);
        URLogs.d("" + viewHolder.ivImage);
        viewHolder.tvTitle.setText(data.get(i));


//
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        final PackageItem item = getItem(position);
//        ViewHolder holder;
//        if (convertView == null) {
//            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = li.inflate(R.layout.package_row, parent, false);
//            holder = new ViewHolder();
//            holder.ivImage = (ImageView) convertView.findViewById(R.id.example_row_iv_image);
//            holder.tvTitle = (TextView) convertView.findViewById(R.id.example_row_tv_title);
//            holder.tvDescription = (TextView) convertView.findViewById(R.id.example_row_tv_description);
//            holder.bAction1 = (Button) convertView.findViewById(R.id.example_row_b_action_1);
//            holder.bAction2 = (Button) convertView.findViewById(R.id.example_row_b_action_2);
//            holder.bAction3 = (Button) convertView.findViewById(R.id.example_row_b_action_3);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        ((SwipeListView)parent).recycle(convertView, position);
//
//        holder.ivImage.setImageDrawable(item.getIcon());
//        holder.tvTitle.setText(item.getName());
//        holder.tvDescription.setText(item.getPackageName());
//
//
//        holder.bAction1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = context.getPackageManager().getLaunchIntentForPackage(item.getPackageName());
//                if (intent != null) {
//                    context.startActivity(intent);
//                } else {
//                    Toast.makeText(context, R.string.cantOpen, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        holder.bAction2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isPlayStoreInstalled()) {
//                    context.startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("market://details?id=" + item.getPackageName())));
//                } else {
//                    context.startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("http://play.google.com/store/apps/details?id=" + item.getPackageName())));
//                }
//            }
//        });
//
//        holder.bAction3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri packageUri = Uri.parse("package:" + item.getPackageName());
//                Intent uninstallIntent;
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                    uninstallIntent = new Intent(Intent.ACTION_DELETE, packageUri);
//                } else {
//                    uninstallIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
//                }
//                context.startActivity(uninstallIntent);
//            }
//        });
//
//
//        return convertView;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        Button bAction1;
        Button bAction2;


        public ViewHolder(View itemView) {

            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.example_row_iv_image);
            tvTitle = (TextView) itemView.findViewById(R.id.example_row_tv_title);
            bAction1 = (Button) itemView.findViewById(R.id.example_row_b_action_1);
            bAction2 = (Button) itemView.findViewById(R.id.example_row_b_action_2);


        }
    }

    private boolean isPlayStoreInstalled() {
        Intent market = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=dummy"));
        PackageManager manager = context.getPackageManager();
        List<ResolveInfo> list = manager.queryIntentActivities(market, 0);

        return list.size() > 0;
    }

}
