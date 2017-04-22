package com.zjianhao.universalcontroller;

import android.support.test.runner.AndroidJUnit4;

import com.zjianhao.module.electrical.model.Brand;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Brand brand = new Brand();
        brand.setName("小 米");
        System.out.println("========" + brand.getLetterIndex());
    }
}
