package tw.edu.fcu.recommendedfood.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import tw.edu.fcu.recommendedfood.Data.OnItemClickLitener;
import tw.edu.fcu.recommendedfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {
    public static final String KEY = "imageId";
    public static final String KEY_ITEM_CLICK = "KEYITEMCLICK";
    int imageId, pos;
    ImageView imageView;

    public ImageFragment() {
    }

    public static ImageFragment newInstance(int imageId, int position) {
        ImageFragment imageFragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, imageId);
        bundle.putInt(KEY_ITEM_CLICK, position);
        imageFragment.setArguments(bundle);

        return imageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_image, container, false);
        imageView = (ImageView) inflatedView.findViewById(R.id.imageView2);

        Bundle arguments = getArguments();
        imageId = arguments.getInt(KEY);
        pos = arguments.getInt(KEY_ITEM_CLICK);

        inflatedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("num", pos);
                intent.setAction("ACTION_GAME_LISTENER");
                getActivity().sendBroadcast(intent);
            }
        });
        imageView.setImageResource(imageId);


        return inflatedView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {

        }
    }

}
