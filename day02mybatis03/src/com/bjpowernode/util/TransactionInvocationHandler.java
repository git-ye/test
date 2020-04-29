package com.bjpowernode.util;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 作者：北京动力节点学员叶某某
 */
public class TransactionInvocationHandler implements InvocationHandler {

    private Object target;

    public TransactionInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object obj = null;


        SqlSession session = null;
        try{
            session = sqlSessionUtil.getSession();
            obj = method.invoke(target,args);

            session.commit();
        }catch (Exception e){
            session.rollback();
            e.printStackTrace();
        }finally {
            sqlSessionUtil.myClose(session);
        }


        return null;
    }
}
