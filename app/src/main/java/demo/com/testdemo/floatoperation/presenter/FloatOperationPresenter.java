package demo.com.testdemo.floatoperation.presenter;

import android.content.Context;


import java.util.List;

import demo.com.testdemo.floatoperation.IFloatOperation;
import demo.com.testdemo.floatoperation.operation.Operation;

/**
 * Created by ludexiang on 2017/9/27.
 */

public class FloatOperationPresenter implements IFloatOperation.OnOperationItemClickListener {

    private IFloatOperation iFloatOperation;
    private Context mContext;

    public FloatOperationPresenter(Context context) {
        mContext = context;
    }

    public FloatOperationPresenter buildFloatOperation(IFloatOperation operation) {
        iFloatOperation = operation;
        iFloatOperation.setItemClickListener(this);
        return this;
    }

    public FloatOperationPresenter buildTitle(String txt) {
        if (iFloatOperation == null) {
            throw new IllegalArgumentException("unset FloatOperation");
        }
        iFloatOperation.setTitle(txt);
        return this;
    }

    public FloatOperationPresenter buildTitle(int txtId) {
        if (iFloatOperation == null) {
            throw new IllegalArgumentException("unset FloatOperation");
        }
        iFloatOperation.setTitle(txtId);
        return this;
    }

    public FloatOperationPresenter buildOperation(List<Operation> operations) {
        if (iFloatOperation == null) {
            throw new IllegalArgumentException("unset FloatOperation");
        }
        iFloatOperation.setOperation(operations);
        return this;
    }

//    public FloatOperationPresenter buildOperations(List<IFloatOperation.IOperation> operations) {
//        if (iFloatOperation == null) {
//            throw new IllegalArgumentException("unset FloatOperation");
//        }
//        iFloatOperation.setOperations(operations);
//        return this;
//    }

    public void show() {
        if (iFloatOperation == null) {
            throw new IllegalArgumentException("unset FloatOperation");
        }

        iFloatOperation.show();
    }

    public void hide() {
        if (iFloatOperation == null) {
            throw new IllegalArgumentException("unset FloatOperation");
        }

        iFloatOperation.close();
    }

    @Override
    public void onItemClick(Operation op) {

    }

}
