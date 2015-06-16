package com.semars.util;

import com.semars.AppOutput;

public class ScreenOutput implements AppOutput{
    @Override
    public void print(String output) {
        System.out.println(output);
    }
}
