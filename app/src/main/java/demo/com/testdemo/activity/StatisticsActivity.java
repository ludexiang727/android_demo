package demo.com.testdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import demo.com.testdemo.R;
import demo.com.testdemo.model.HistogramModel;
import demo.com.testdemo.utils.AssetUtils;
import demo.com.testdemo.widget.BrokenLineView;
import demo.com.testdemo.widget.StatisticsGridView;
import demo.com.testdemo.widget.StatisticsView;

/**
 * Created by ludexiang on sliding_view16/sliding_view7/1.
 */
public class StatisticsActivity extends Activity {

    private StatisticsView mStatisticsView;
    private List<HistogramModel> mLists = new ArrayList<HistogramModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_layout);

        mStatisticsView = (StatisticsView) findViewById(R.id.statistics_view);

        String histogram = AssetUtils.readFile(this, "histogram.txt");
        parse(histogram);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mStatisticsView.setLists(mLists);
    }

    private void parse(String lists) {
        try {
            JSONObject obj = new JSONObject(lists);
            JSONArray array = obj.getJSONArray("histogram_lists");
            parseArray(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseArray(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            HistogramModel model = new HistogramModel();
            try {
                JSONObject obj = array.getJSONObject(i);
                int redPoint = obj.optInt("red_point");
                int greenPoint = obj.optInt("green_point");
                int blackPoint = obj.optInt("black_point");
                int whiteTop = obj.optInt("white_top");
                int blueTop = obj.optInt("blue_top");
                int redTop = obj.optInt("red_top");

                model.setBlackPoint(blackPoint);
                model.setBlueRectTop(blueTop);
                model.setRedRectTop(redTop);
                model.setWhiteRectTop(whiteTop);
                model.setRedPoint(redPoint);
                model.setGreenPoint(greenPoint);

                mLists.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
