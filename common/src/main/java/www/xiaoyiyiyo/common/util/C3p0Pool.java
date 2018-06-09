package www.xiaoyiyiyo.common.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by xiaoyiyiyo on 2018/5/21.
 */
public class C3p0Pool {

    private static ComboPooledDataSource dataSource = null;

    static {
        try {
            Properties config = new Properties();
            config.load(C3p0Pool.class.getClassLoader().getResourceAsStream("c3p0.properties"));
            //声明C3P0数据源对象
            dataSource = new ComboPooledDataSource();
            //设置数据库连接驱动
            dataSource.setDriverClass(
                    config.getProperty("driver"));
            //设置数据连接URL
            dataSource.setJdbcUrl(
                    config.getProperty("url"));
            //设置数据库连接用户账号
            dataSource.setUser(
                    config.getProperty("user"));
            //设置数据库连接用户账号的密码
            dataSource.setPassword(
                    config.getProperty("password"));

            //设置数据库连接池中的初始化连接对象数量
            dataSource.setInitialPoolSize(30);
            //设置数据库连接池中的最小连接对象数量
            dataSource.setMinPoolSize(30);
            //设置数据库连接池中的最大连接对象数量
            dataSource.setMaxPoolSize(60);
            //当连接不够，每次新增连接数量
            dataSource.setAcquireIncrement(10);
        }catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ThreadLocal<Connection> connThreadLocal = new ThreadLocal<>();

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConn() throws SQLException {
        Connection conn = connThreadLocal.get();
        if (conn == null) {
            conn = dataSource.getConnection();
            connThreadLocal.set(conn);
        }
        return conn;
    }

    public static PreparedStatement getStatement(Connection conn, String sql, Object[] args) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
        }
        return ps;
    }

    public static ResultSet getResultSet(PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.executeQuery();
        return rs;
    }

    public static <T> List<T> selectMulti(String sql, Object[] args, Class<?> clazz) throws SQLException, IllegalAccessException, InstantiationException
    {
        Connection connection = getConn();
        PreparedStatement ps = getStatement(connection, sql, args);
        ResultSet rs = getResultSet(ps);
        ResultSetMetaData rsmd = rs.getMetaData();

        //获取结果集的元素个数
        int colCount = rsmd.getColumnCount();
        //返回结果的列表集合
        List<T> list = new ArrayList<>();
        //业务对象的属性数组
        Field[] fields = clazz.getDeclaredFields();
        while(rs.next()){//对每一条记录进行操作
            T obj = (T)clazz.newInstance();//构造业务对象实体
            //将每一个字段取出进行赋值
            for(int i = 1;i<=colCount;i++){
                Object value = rs.getObject(i);
                //寻找该列对应的对象属性
                for(int j=0;j<fields.length;j++){
                    Field f = fields[j];
                    //如果匹配进行赋值
                    if(f.getName().equalsIgnoreCase(rsmd.getColumnName(i))){
                        boolean flag = f.isAccessible();
                        f.setAccessible(true);
                        f.set(obj, value);
                        f.setAccessible(flag);
                    }
                }
            }
            list.add(obj);
        }

        release(connection, ps, rs);
        return list;
    }

    public static <T> T selectSingle(String sql, Object[] args, Class<?> clazz) throws SQLException, IllegalAccessException, InstantiationException
    {
        List<T> ts = selectMulti(sql, args, clazz);
        if (ts.size() > 0) {
            return ts.get(0);
        }
        return null;
    }

    public static void insert(String sql, Object[] args) throws SQLException {
        Connection connection = getConn();
        PreparedStatement ps = getStatement(connection, sql, args);
        System.out.println("======= " + sql);
        ps.execute();
        release(connection, ps, null);
    }

    public static void update(String sql, Object[] args) throws SQLException {
        Connection connection = getConn();
        PreparedStatement ps = getStatement(connection, sql, args);
        System.out.println("======= " + sql);
        ps.executeUpdate();
        release(connection, ps, null);
    }

    public static void delete(String sql, Object[] args) throws SQLException {
        update(sql, args);
    }

    public static void release(Connection connection, Statement statement, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }
}
