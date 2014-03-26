package edu.harding.android.eatsmart;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class OrganizePendingFoodFragment extends Fragment{
	
	public static final String EXTRA_IMAGE_PATH =
	"edu.harding.android.eatsmart.image_path";
	private ImageView mImageView;
	public static OrganizePendingFoodFragment newInstance(String imagePath){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_IMAGE_PATH, imagePath);
		
		OrganizePendingFoodFragment fragment = new OrganizePendingFoodFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.pending_food);
	}
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		View v = inflater.inflate(R.layout.fragment_organize_pending_food, parent, false);
		
		mImageView = (ImageView)v.findViewById(R.id.pendingFoodImageView);
		String path = (String)getArguments().getSerializable(EXTRA_IMAGE_PATH);
		BitmapDrawable image = PictureUtils.getScaledDrawable(getActivity(), path);
		
		mImageView.setImageDrawable(image);
		
		return v;
	}

	@Override
	public void onDestroyView(){
		super.onDestroy();
		PictureUtils.cleanImageView(mImageView);
	}
	
}
