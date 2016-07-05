package demo.com.testdemo.model;

/**
 * Created by ludexiang on 16/7/1.
 */
public class HistogramModel {
    private int mRedPoint;
    private int mGreenPoint;
    private int mBlackPoint;

    // white top 值越小则高度越高
    private int mWhiteRectTop;
    private int mBlueRectTop;
    private int mRedRectTop;

    public int getRedPoint() {
        return mRedPoint;
    }

    public void setRedPoint(int mRedPoint) {
        this.mRedPoint = mRedPoint;
    }

    public int getGreenPoint() {
        return mGreenPoint;
    }

    public void setGreenPoint(int mGreenPoint) {
        this.mGreenPoint = mGreenPoint;
    }

    public int getBlackPoint() {
        return mBlackPoint;
    }

    public void setBlackPoint(int mBlackPoint) {
        this.mBlackPoint = mBlackPoint;
    }

    public int getWhiteRectTop() {
        return mWhiteRectTop;
    }

    public void setWhiteRectTop(int mWhiteRectTop) {
        this.mWhiteRectTop = mWhiteRectTop;
    }

    public int getBlueRectTop() {
        return mBlueRectTop;
    }

    public void setBlueRectTop(int mBlueRectTop) {
        this.mBlueRectTop = mBlueRectTop;
    }

    public int getRedRectTop() {
        return mRedRectTop;
    }

    public void setRedRectTop(int mRedRectTop) {
        this.mRedRectTop = mRedRectTop;
    }
}
