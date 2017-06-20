package com.example;

import junit.framework.TestCase;

import java.lang.reflect.Method;

/**
 * Created by yutakase on 2017/06/20.
 */
public class App2Test extends TestCase {
    public void test() throws Exception {
        Method method = App.class.getDeclaredMethod("hello");
        method.setAccessible(true);
        assertEquals((String) method.invoke(new App()), "Hello World!");
    }
}
