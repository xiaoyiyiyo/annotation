package www.xiaoyiyiyo.mybatis.proxy;

import www.xiaoyiyiyo.common.util.C3p0Pool;
import www.xiaoyiyiyo.mybatis.operate.Delete;
import www.xiaoyiyiyo.mybatis.operate.Insert;
import www.xiaoyiyiyo.mybatis.operate.Select;
import www.xiaoyiyiyo.mybatis.operate.Update;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by xiaoyiyiyo on 2018/5/17.
 * 无需传入目标接口，因为没有目标接口的实现类，无需调用具体方法
 */
public class AnnoMethodInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String sql = null;
        if (method.isAnnotationPresent(Insert.class)) {
            sql = checkSql(method.getAnnotation(Insert.class).value(), "Insert");
            C3p0Pool.insert(sql, args);
        } else if (method.isAnnotationPresent(Select.class)) {
            sql = checkSql(method.getAnnotation(Select.class).value(), "Select");
            Class clzz = method.getReturnType();
            //多条记录
            if (List.class.isAssignableFrom(clzz)) {
                //获取list中的对象类型
                Class<?> actualClzz =
                        (Class<?>) ((ParameterizedType)(method.getGenericReturnType())).getActualTypeArguments()[0];
                return C3p0Pool.selectMulti(sql, args, actualClzz);
            } else {
                return C3p0Pool.selectSingle(sql, args, clzz);
            }
        } else if (method.isAnnotationPresent(Update.class)) {
            sql = checkSql(method.getAnnotation(Update.class).value(), "Update");
            C3p0Pool.update(sql, args);
        } else if (method.isAnnotationPresent(Delete.class)) {
            sql = checkSql(method.getAnnotation(Delete.class).value(), "Delete");
            C3p0Pool.delete(sql, args);
        }
        return null;
    }

    private String checkSql(String sql, String operate) throws SQLException {
        // 获取关键字 e.g. select or update...
        String keyOperate = sql.split(" ")[0];
        if (null == keyOperate || !keyOperate.equalsIgnoreCase(operate)) {
            throw new SQLException("sql语句有错误");
        }
        return sql;
    }

}
