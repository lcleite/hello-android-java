package lcleite.github.com.helloandroidjava.view.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lcleite.github.com.helloandroidjava.R;
import lcleite.github.com.helloandroidjava.async.SetDownloadedImageTask;
import lcleite.github.com.helloandroidjava.model.Tweet;
import lcleite.github.com.helloandroidjava.utils.DateUtils;
import lcleite.github.com.helloandroidjava.utils.StringUtils;

/**
 * Created by leandro on 23/09/17.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private List<Tweet> tweets;
    private OnItemClickListener onItemClickListener;

    public SearchResultAdapter(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Tweet tweet = tweets.get(position);

        downloadUserProfileImage(tweet, holder);
        setViews(tweet, holder);
        setMedias(tweet, holder);
    }

    private void downloadUserProfileImage(Tweet tweet, ViewHolder holder) {
        new SetDownloadedImageTask(tweet.getUser().getUserProfileImageUrl(), holder.ivUserProfile).execute();
    }

    private void setViews(Tweet tweet, ViewHolder holder){
        holder.tvUserName.setText(tweet.getUser().getUserName());
        holder.tvUserScreenName.setText(StringUtils.screenNameWithAt(tweet.getUser().getUserScreenName()));
        holder.tvText.setText(tweet.getContent().getText());
        holder.tvHashtags.setText(String.valueOf(tweet.getContent().getHashtags().size()));
        holder.tvMentions.setText(String.valueOf(tweet.getContent().getUserMentions().size()));
        holder.tvCreatedAt.setText(DateUtils.getStringFromDate(tweet.getCreatedAt()));
    }

    private void setMedias(Tweet tweet, ViewHolder holder) {
        switch (tweet.getMedia().getType()){
            case PHOTO:
                holder.ivMediaPhoto.setVisibility(View.VISIBLE);
                holder.ivMediaVideo.setVisibility(View.GONE);
                break;
            case VIDEO:
                holder.ivMediaPhoto.setVisibility(View.GONE);
                holder.ivMediaVideo.setVisibility(View.VISIBLE);
                break;
            case NONE:
                holder.ivMediaPhoto.setVisibility(View.GONE);
                holder.ivMediaVideo.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivUserProfile;
        public TextView tvUserName;
        public TextView tvUserScreenName;
        public TextView tvText;
        public ImageView ivMediaPhoto;
        public ImageView ivMediaVideo;
        public TextView tvHashtags;
        public TextView tvMentions;
        public TextView tvCreatedAt;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            ivUserProfile = (ImageView) itemView.findViewById(R.id.ivUserProfile);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvUserScreenName = (TextView) itemView.findViewById(R.id.tvUserScreenName);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            ivMediaPhoto = (ImageView) itemView.findViewById(R.id.ivMediaPhoto);
            ivMediaVideo = (ImageView) itemView.findViewById(R.id.ivMediaVideo);
            tvHashtags = (TextView) itemView.findViewById(R.id.tvHashtags);
            tvMentions = (TextView) itemView.findViewById(R.id.tvMentions);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvCreatedAt);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
