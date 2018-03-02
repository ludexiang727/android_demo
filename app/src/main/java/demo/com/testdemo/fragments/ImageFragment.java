package demo.com.testdemo.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import demo.com.testdemo.R;

/**
 * Created by ludexiang on 2018/3/2.
 */

public class ImageFragment extends Fragment {

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    Log.e("ldx", "ImageFragment onAttach(Activity)");
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Log.e("ldx", "ImageFragment onAttach(Context)");
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.e("ldx", "ImageFragment onCreate(Bundle)");
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.e("ldx", "ImageFragment onCreateView()");
    View v = inflater.inflate(R.layout.image_fragment_layout, null);
    Button button = (Button) v.findViewById(R.id.image_add_fragment);
    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fl_content, new TextFragment());
        ft.addToBackStack("text");
        ft.commit();
      }
    });
    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Log.e("ldx", "ImageFragment onViewCreated()");
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.e("ldx", "ImageFragment onActivityCreated()");
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.e("ldx", "ImageFragment onStart()");
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.e("ldx", "ImageFragment onResume()");
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.e("ldx", "ImageFragment onPause()");
  }

  @Override
  public void onStop() {
    super.onStop();
    Log.e("ldx", "ImageFragment onStop()");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    Log.e("ldx", "ImageFragment onDestroyView()");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.e("ldx", "ImageFragment onDestroy()");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    Log.e("ldx", "ImageFragment onDetach()");
  }
}
