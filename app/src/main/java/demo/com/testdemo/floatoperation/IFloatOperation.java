package demo.com.testdemo.floatoperation;

import java.util.List;

import demo.com.testdemo.floatoperation.operation.Operation;

/**
 * Created by ludexiang on 2017/sliding_view9/27.
 */

public interface IFloatOperation {
    void setTitle(int titleRes);
    void setTitle(String titleTxt);
    void show();
    void close();
    /** 类专车业务线可以直接使用 Operation **/
    void setOperation(List<Operation> operations);

    void setItemClickListener(OnOperationItemClickListener listener);

//    interface IOperation {
//        /**
//         * operation item的唯一标识
//         * @return
//         */
//        int getOperationId();
//
//        /**
//         * operation item的图标
//         * @return
//         */
//        int getOperationImg();
//
//        /**
//         * operation item的文案
//         * @return
//         */
//        int getOperationTxt();
//    }

    interface OnOperationItemClickListener {

        void onItemClick(Operation op);

//        void onItemClick(IOperation op);
    }
}
