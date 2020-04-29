package com.bjpowernode.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：北京动力节点学员叶某某
 */
public class sqlSessionUtil {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    private static ThreadLocal<SqlSession> t = new ThreadLocal<>();
    //取得sqlsession对象
    public static SqlSession getSession(){
        SqlSession session = t.get();
        if(session==null){
            session = sqlSessionFactory.openSession();
            t.set(session);
        }
        return session;
    }
    public static void myClose(SqlSession session){
        if(session!=null){
           session.close();
           t.remove();

        }
    }

}
