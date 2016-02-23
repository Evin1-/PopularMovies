package mx.evin.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mx.evin.udacity.popularmovies.R;
import mx.evin.udacity.popularmovies.entities.VideoResult;

/**
 * Created by evin on 2/22/16.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {
    private final ArrayList<VideoResult> mVideos;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.rvVideoTxt);
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
        VideoResult videoResult = mVideos.get(position);
        holder.textView.setText(videoResult.getKey());
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }
}
