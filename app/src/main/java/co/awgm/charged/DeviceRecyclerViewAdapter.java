package co.awgm.charged;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.awgm.charged.DevicesFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ChargedDevice} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 *
 */
public class DeviceRecyclerViewAdapter extends RecyclerView.Adapter<DeviceRecyclerViewAdapter.ViewHolder> {

    private final List<ChargedDevice> mValues;
    private final OnListFragmentInteractionListener mListener;

    public DeviceRecyclerViewAdapter(List<ChargedDevice> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_devices, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        //Log.d("DEVICE_VIEW_ADAPTER",mValues.get(position).getImageType().toString());

        switch (mValues.get(position).getImageType().toString()){

            case ("phone"):
                holder.mImageView.setImageResource(R.drawable.ic_phone_android_black_24dp);
                break;
            case("laptop"):
                holder.mImageView.setImageResource(R.drawable.ic_computer_black_24dp);
                break;
            default:
                holder.mImageView.setImageResource(R.drawable.ic_devices_other_black_24dp);
        }


        holder.mNickNameView.setText(mValues.get(position).getNickName());
        String content = mValues.get(position).getMake() + " " + mValues.get(position).getModel();
        holder.mContentView.setText(content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mNickNameView;
        public final TextView mContentView;
        public ChargedDevice mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.deviceImage);
            mNickNameView = (TextView) view.findViewById(R.id.nickName);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
