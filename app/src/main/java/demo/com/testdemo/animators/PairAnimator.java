package demo.com.testdemo.animators;

import android.view.View;
import demo.com.testdemo.animators.ViewAnimator.ViewPairAnimator;
import demo.com.testdemo.animators.item.MoveXAnimatorItem;
import java.util.Set;

/**
 * Created by ludexiang on 2018/2/6.
 */

public class PairAnimator extends ViewPairAnimator {
  private boolean isReverse;
  public PairAnimator(boolean reverse) {
    isReverse = reverse;
  }

  @Override
  protected void firstViewAnimators(Set<AnimatorItem> container) {
    if (isReverse) {
      container.add(new MoveXAnimatorItem(-15));
    } else {
      container.add(new MoveXAnimatorItem(10));
    }
  }

  @Override
  protected void secondViewAnimators(Set<AnimatorItem> container) {
    if (isReverse) {
      container.add(new MoveXAnimatorItem(-10));
    } else {
      container.add(new MoveXAnimatorItem(15));
    }
  }

}
