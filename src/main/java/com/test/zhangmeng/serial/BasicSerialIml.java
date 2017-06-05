package com.test.zhangmeng.serial;

import java.io.*;

/**
 * Created by zhangmeng on 2016/9/18.
 */
public class BasicSerialIml {

    public static  byte[] objectToByte(Object object) throws IOException {
        byte[] b = null;
        ByteArrayOutputStream bo=new ByteArrayOutputStream();
        ObjectOutputStream oo = null;
        try {
        	 System.out.println(b==null);
            oo=new ObjectOutputStream(bo);
            oo.writeObject(object);
            b=bo.toByteArray();
        }catch (IOException e) {
        	e.printStackTrace();
                oo.close();
        }finally {
                oo.close();
        }
        return  b;
    }

    public static Object ByteToObject(byte[] bytes) throws IOException {
        ByteArrayInputStream bi=new ByteArrayInputStream(bytes);
        ObjectInputStream oi = null;
        Object object=null;
        try {
            oi=new ObjectInputStream(bi);
            object=oi.readObject();
        }  catch (Exception e) {
            e.printStackTrace();
        }finally {
            oi.close();
        }
        return  object;
    }
}
