package demo.com.testdemo.dynamicprices.view.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ludexiang on 2017/2/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IocDynamicView {
    /**
     * 组件定义的id
     * @return viewId
     */
    int id();

    /**
     * 组件定义的点击事件
     * @return
     */
    String click() default "";
}
