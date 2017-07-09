package com.profullstack.cjkmmseg;

import org.junit.Test;

/**
 * Created by christianxiao on 7/9/17.
 */
public class MmsegTest {

    @Test
    public void testNext() throws Exception {
        String filename = "/SogouLabDic.dic";
        Dictionary dic = new Dictionary(filename);
        Mmseg seg = new Mmseg(dic);
        String sen = "我是中国人";
        //
        seg.setSentence(sen);
        Word w;
        while(( w = seg.next()) != null){
            System.out.println(w);
        }
    }
}