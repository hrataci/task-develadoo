package com.develandoo.task.ui.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.develandoo.task.R;
import com.develandoo.task.helpers.NetworkHelper;
import com.develandoo.task.models.User;
import com.develandoo.task.ui.activities.BaseActivity;

import java.util.List;

import io.realm.Realm;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {
    private List<User> users;
    private BaseActivity activity;
    public UsersAdapter(BaseActivity activity,List<User> users) {
        this.activity = activity;
        this.users = users;
    }

    public void addUsers(List<User> users) {
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new UserHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder userHolder, int position) {
        userHolder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        NetworkImageView img;
        TextView name, details, phone;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgUserThumb);
            name = itemView.findViewById(R.id.userName);
            details = itemView.findViewById(R.id.userDetails);
            phone = itemView.findViewById(R.id.userPhone);
        }

        public void bindData(final int position) {
            final User user = users.get(position);
            img.setImageUrl(user.getPicture().getThumbnail(), NetworkHelper.getInst().getImageLoader());
            name.setText(user.getName().toString());
            details.setText(user.getLocation().toString());
            phone.setText(user.getPhone());
            if (!user.isOpened()) {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.newItemBackground));
            } else {
                itemView.setBackgroundColor(Color.WHITE);

            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            user.setOpened(true);
                            realm.insertOrUpdate(user);
                            notifyItemChanged(position);
                        }
                    });
                    activity.openUserPage(user.getRealmId());
                }
            });
        }
    }
}
