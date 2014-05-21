package com.sinosoft.test;

/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-9-6
 * Time: 下午3:36
 * To change this template use File | Settings | File Templates.
 */
public class ByteArrayTest {

    public static void main(String[] args) {
        byte[] bytes = new byte[100];
        for(int i = 0; i< 100; i++) {
            bytes[i] = (byte) (i+30);
        }
        String data = new String(bytes);
        System.out.println(data);

        //......
        byte [] bytes1 = data.getBytes();

        for(byte b : bytes1){
            System.out.println(b);
        }




    }

}
