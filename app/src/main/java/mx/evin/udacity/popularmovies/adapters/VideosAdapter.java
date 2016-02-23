package mx.evin.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mx.evin.udacity.popularmovies.R;
import mx.evin.udacity.popularmovies.entities.VideoResult;
import mx.evin.udacity.popularmovies.utils.YoutubeMagic;

/**
 * Created by evin on 2/22/16.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {
    private final ArrayList<VideoResult> mVideos;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        String videoId;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.rvVideoImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YoutubeMagic.watchYoutubeVideo(videoId, mContext);
                }
            });
        }
    }

    public VideosAdapter(Context context, ArrayList<VideoResult> videos) {
        mContext = context;
        mVideos = videos;
    }

    @Override
    public VideosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View termView = inflater.inflate(R.layout.recycler_item_video, parent, false);

        return new ViewHolder(termView);
    }

    @Override
    public void onBindViewHolder(VideosAdapter.ViewHolder holder, int position) {
        final String key = mVideos.get(position).getKey();

        ImageView imageView = holder.imageView;
        Picasso.with(mContext)
                .load("http://img.youtube.com/vi/" + key + "/0.jpg")
                .into(imageView);

        holder.videoId = key;
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }
}
