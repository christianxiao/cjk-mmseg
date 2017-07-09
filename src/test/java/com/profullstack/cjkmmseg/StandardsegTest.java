package com.profullstack.cjkmmseg;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

/**
 * Created by christianxiao on 7/9/17.
 */
public class StandardsegTest {

    @Test
    public void testNext() throws Exception {
        String s = " w 我是中国人,90009aaaaa  ----====】】】】";
        Reader r = new StringReader(s);
        Standardseg ss = new Standardseg();
        ss.setReader(r);
        Word w;
        while((w = ss.next()) != null){
            System.out.println(w);
        }
    }
}