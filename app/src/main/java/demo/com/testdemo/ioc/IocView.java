package demo.com.testdemo.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ludexiang on 2017/2/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IocView {
    /**
     * view 组件id
     * @return id
     */
    int id();

    /**
     * view 组件点击事件
     * @return view的方法名称
     */
    String click() default "";
}
