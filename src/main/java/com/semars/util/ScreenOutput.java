package com.semars.util;

import com.semars.AppOutput;
import org.springframework.stereotype.Service;

@Service
public class ScreenOutput implements AppOutput{
    @Override
    public void print(String output) {
        System.out.println(output);
    }
}
