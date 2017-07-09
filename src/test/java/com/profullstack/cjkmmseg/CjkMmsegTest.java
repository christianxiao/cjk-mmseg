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
        String s = "如果纪勤不是一个合格的主席，那肯定是因为有人忘了通知他。我的用户名是aa885,我的邮箱是9999@gmail.com.";
        Reader r = new StringReader(s);
        seg.setReader(r);
        Word w;
        while((w = seg.nextWord()) != null){
            System.out.println(w);
        }
        seg.close();
    }
}