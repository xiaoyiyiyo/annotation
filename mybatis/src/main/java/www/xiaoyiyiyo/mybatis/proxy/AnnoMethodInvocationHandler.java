package www.xiaoyiyiyo.mybatis.proxy;

import www.xiaoyiyiyo.common.util.C3p0Pool;
import www.xiaoyiyiyo.mybatis.operate.Insert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            C3p0Pool.insert(sql, args);
        }
        return null;
    }

}
