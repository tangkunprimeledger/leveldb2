package com.tank;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author tangkun
 * @date 2019-03-21
 */
public class HelloWorldTest {

    @Test
    public void testSayHello() throws Exception {

        HelloWorld helloWorld = new HelloWorld();
        String msg = "hello world";
        Assert.assertEquals(msg,helloWorld.sayHello(msg));
    }
}