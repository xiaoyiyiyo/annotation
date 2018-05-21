package www.xiaoyiyiyo.mybatis.proxy;

import www.xiaoyiyiyo.mybatis.operate.Insert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by xiaoyiyiyo on 2018/5/17.
 * 无需传入目标接口，因为没有目标接口的实现类，无需调用具体方法
 */
public class AnnoMethodInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String sql = null;
        if (method.isAnnotationPresent(Insert.class)) {
            //TODO checksql

        }
        return null;
    }

    private static void insert(String sql, Object[] parameters) {

    }
}
