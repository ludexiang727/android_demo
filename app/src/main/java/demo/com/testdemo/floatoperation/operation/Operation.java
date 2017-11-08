package demo.com.testdemo.floatoperation.operation;

import android.support.annotation.StringRes;

import demo.com.testdemo.R;

/**
 * 暂时定义了几种默认ID,供业务线自定义操作台。
 * <br>
 * 业务线也可以自定义ID。
 * <br>
 * 优先使用{@link #string}字段，其次使用 {@link #text}
 *
 * @author Jin Liang
 * @since 16/9/6
 */
public class Operation {
    public static final int ID_HELP = 1;
    public static final int ID_EMERGENCY = 2;
    public static final int ID_CANCEL = 3;
    public static final int ID_SHARE = 4;
    public static final int ID_TIPS = 5;
    public static final int ID_TRANSLATE = 6;
    public static final int ID_COMPLAIN = 7;
    public static final int ID_CANCEL_ORDER = 8;
    public static final int ID_TRIP_PREFERENCE = 9;
    public static final int ID_ONE_KEY_REPORT = 10;
    public static final int ID_UNLOCK = 11;
    public static final int ID_REPAIR = 12;
    public static final int ID_FINISH = 13;
    public static final int ID_PAY = 14;
    public static final int ID_ONE_PRICE = 15;
    public static final int ID_CARPOOL_PRICE = 16;
    public static final int ID_CAN_NOT_END_SERVICE = 17;
    public static final int ID_MORE = 18;
    public static final int ID_IM = 19;
    public static final int ID_PHONE = 20;
    public static final int ID_MODIFY_STATION = 21;
    public static final int ID_NO_CARPOOL = 22;

    public static final Operation OP_HELP = new Operation(ID_HELP, R.string
            .oc_operation_panel_help);
    public static final Operation OP_EMERGENCY = new Operation(ID_EMERGENCY, R.string.oc_operation_panel_emergency);
    public static final Operation OP_CANCEL = new Operation(ID_CANCEL, R.string
            .oc_operation_panel_cancel);
    public static final Operation OP_SHARE = new Operation(ID_SHARE, R.string.oc_operation_panel_share);
    public static final Operation OP_TIPS = new Operation(ID_TIPS, R.string.oc_operation_panel_tips);
    public static final Operation OP_TRANSLATE = new Operation(ID_TRANSLATE, R.string
            .oc_operation_panel_translate);
    public static final Operation OP_COMPLAIN = new Operation(ID_COMPLAIN, R.string
            .oc_operation_panel_complain);
    public static final Operation OP_CANCEL_ORDER = new Operation(ID_CANCEL_ORDER, R.string
            .oc_operation_panel_cancel_trip);
    public static final Operation OP_TRIP_PREFERENCE = new Operation(ID_TRIP_PREFERENCE, R.string
            .oc_operation_panel_trip_preference);
    public static final Operation OP_ONE_KEY_REPORT = new Operation(ID_ONE_KEY_REPORT, R.string.oc_operation_panel_one_key_report);

    public static final Operation OP_TIPS_HORIZONTAL = new Operation(ID_TIPS, R.string
            .oc_operation_panel_tips,R.drawable.oc_drivercard_icon_tipping);

    public static final Operation OP_SHARE_HORIZONTAL = new Operation(ID_SHARE, R.string
            .oc_operation_panel_share,R.drawable.oc_drivercard_icon_share);

    public static final Operation OP_TAXI_SHARE_HORIZONTAL = new Operation(ID_SHARE, R.string
            .oc_operation_panel_share_taxi,R.drawable.oc_drivercard_icon_share);

    public static final Operation OP_HELP_HORIZONTAL = new Operation(ID_HELP, R.string
            .oc_operation_panel_help,R.drawable.oc_drivercard_icon_service);

    public static final Operation OP_EMERGENCY_HORIZONTAL = new Operation(ID_EMERGENCY, R.string
            .oc_operation_panel_emergency,R.drawable.oc_drivercard_icon_police);

    public static final Operation OP_ONE_KEY_REPORT_HORIZONTAL = new Operation(ID_ONE_KEY_REPORT, R.string
            .oc_operation_panel_one_key_report,R.drawable.oc_drivercard_icon_traffic_report);

    public static final Operation OP_FINISH_HORIZONTAL = new Operation(ID_FINISH, R.string
            .oc_operation_panel_finish,R.drawable.oc_drivercard_icon_done);

    public static final Operation OP_PAY_HORIZONTAL = new Operation(ID_PAY, R.string
            .oc_operation_panel_pay,R.drawable.oc_drivercard_icon_pay);

    public static final Operation OP_ONE_PRICE_HORIZONTAL = new Operation(ID_ONE_PRICE, R.string
            .oc_operation_panel_one_price,R.drawable.oc_drivercard_icon_yikoujia);

    public static final Operation OP_CARPOOL_PRICE_HORIZONTAL = new Operation(ID_CARPOOL_PRICE, R.string
            .oc_operation_panel_carpool_price,R.drawable.oc_drivercard_icon_carpool);

    public static final Operation OP_UNLOCK_HORIZONTAL = new Operation(ID_UNLOCK, R.string
            .oc_operation_panel_unloc,R.drawable.oc_drivercard_icon_howtounlock);

    public static final Operation OP_REPARI_HORIZONTAL = new Operation(ID_REPAIR, R.string
            .oc_operation_panel_repair,R.drawable.oc_drivercard_icon_repair);

    public static final Operation OP_MODIFY_STATION_HORIZONTAL = new Operation(ID_MODIFY_STATION, R.string
            .oc_operation_panel_modify_station, R.drawable.oc_drivercard_icon_modify_station);

    public static final Operation OP_NO_CAR_POOL = new Operation(ID_NO_CARPOOL, R.string
            .oc_operation_panel_no_carpool, R.drawable.oc_drivercard_icon_no_carpool);

    public static final Operation OP_CAN_NOT_END_SERVICE_HORIZONTAL = new Operation(ID_CAN_NOT_END_SERVICE, R.string
            .oc_operation_can_not_end_service,R.drawable.oc_drivercard_icon_can_not_end_serivce);

    public static final Operation OP_MORE_HORIZONTAL = new Operation(ID_MORE, R.string
            .oc_operation_pannel_more,R.drawable.oc_drivercard_icon_more);

    public static final Operation OP_IM_HORIZONTAL = new Operation(ID_IM, R.string.oc_driver_msg, R.drawable.oc_driverbar_icon_im);
    public static final Operation OP_PHONE_HORIZONTAL = new Operation(ID_PHONE, R.string.oc_driver_phone, R.drawable.oc_driverbar_icon_phone);
    public static final Operation OP_CANCEL_HORIZONTAL = new Operation(ID_CANCEL, R.string.oc_operation_panel_cancel_trip, R.drawable.oc_driverbar_icon_cancel);
    public int id;

    public int hintNum;
    public boolean isShowReddot;

    @StringRes
    public final int text;

    public final CharSequence string;

    public int imageResId;

    /**
     * 是否是checkbox style
     */
    public boolean checkable;

    /**
     * 如果{@link #checkable}为false,则此参数自动忽略
     */
    public boolean checked;

    public Operation(int id, @StringRes int text) {
        this.id = id;
        this.text = text;
        this.string = "";
    }

    public Operation(int id, CharSequence text) {
        this.id = id;
        this.string = text;
        this.text = 0;
    }

    public Operation(int id, CharSequence text, int imageResId) {
        this.id = id;
        this.string = text;
        this.imageResId = imageResId;
        this.text = 0;
    }

    public Operation(int id, int text, int imageResId) {
        this.id = id;
        this.text = text;
        this.imageResId = imageResId;
        this.string = "";
    }
}
