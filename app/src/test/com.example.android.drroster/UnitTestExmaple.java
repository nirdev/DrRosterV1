package com.example.android.drroster;

import com.example.android.drroster.utils.DateUtils;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        int actual = DateUtils.getYearFromDate(new Date());
        assertEquals(2016, actual);

    }
}