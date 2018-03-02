package demo.com.testdemo.fragments;

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
import android.widget.Button;
import android.widget.EditText;
import demo.com.testdemo.R;

/**
 * Created by ludexiang on 2018/3/2.
 */

public class TextFragment extends Fragment {

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Log.e("ldx","----TextFragment-----onAttach");
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.e("ldx","----TextFragment-----onCreate");
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.e("ldx","----TextFragment-----onCreateView");
    View v = inflater.inflate(R.layout.text_fragment_layout, null);
    Button btn = (Button) v.findViewById(R.id.text_fragment_btn);
    btn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        //创建事务
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        //操作指令
        ft.add(R.id.fl_content, new ImageFragment());
        ft.addToBackStack("image");
        //提交事务
        ft.commit();
      }
    });
    return v;
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.e("ldx","----TextFragment-----onStart");
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.e("ldx","----TextFragment-----onActivityCreated");
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.e("ldx","----TextFragment-----onResume");
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.e("ldx","----TextFragment-----onPause");
  }

  @Override
  public void onStop() {
    super.onStop();
    Log.e("ldx","----TextFragment-----onStop");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    Log.e("ldx","----TextFragment-----onDestroyView");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.e("ldx","----TextFragment-----onDestroy");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    Log.e("ldx","----TextFragment-----onDetach");
  }
}
