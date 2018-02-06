package demo.com.testdemo.animators.item;

import android.view.View;
import demo.com.testdemo.animators.ViewAnimator;

public class MoveXAnimatorItem extends ViewAnimator.BaseAnimItem {

  private float mMoveX = 0;
  private float mStartTranslate = 0;

  public MoveXAnimatorItem(float move) {
    mMoveX = move;
  }

  @Override
  public void onViewAttached(View view) {
    mStartTranslate = view.getTranslationX();
  }

  @Override
  public void onUpdate(View view, float fraction) {
    view.setTranslationX(mStartTranslate + mMoveX * fraction);
  }
}
