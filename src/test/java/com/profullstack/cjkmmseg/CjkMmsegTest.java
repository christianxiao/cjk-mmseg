package com.profullstack.cjkmmseg;

import java.io.Reader;
import java.io.StringReader;

/**
 * Created by christianxiao on 7/9/17.
 */
public class CjkMmsegTest {

    @org.junit.Test
    public void testNext() throws Exception {
        CjkMmseg seg = new CjkMmseg();
        String s = "My email address is christian.xiao@outlook.com。我的用户名是christian,我的邮箱是christian.xiao@outlook.com.";
        Reader r = new StringReader(s);
        seg.setReader(r);
        Word w;
        while((w = seg.nextWord()) != null){
            System.out.println(w);
        }
        seg.close();
    }
}