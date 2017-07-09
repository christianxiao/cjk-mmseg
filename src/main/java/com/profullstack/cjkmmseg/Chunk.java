package com.profullstack.cjkmmseg;

import java.util.*;

/**
* a chunck may contain 1,2 or 3 words. Every word must have length()>0.
*/
public class Chunk{
    private ArrayList<Word> words;
    
    private int totalLength=-1;
    private float average=-1;
    private float variance=-1;
    private float sumOfMF=-1;  

    public Chunk(){
        words = new ArrayList<>(3);
    }

    public ArrayList<Word> getWords(){
        return this.words;
    }
    public int getTotalLength(){
        if(this.totalLength == -1){
            this.totalLength = 0;
            for(Word w : this.words){
                this.totalLength += w.getLength();
            }
        }
        return this.totalLength;
    }
    public float getAverage(){
        if(this.average == -1){
            this.average = 0;
            this.average = (float)this.getTotalLength() / (float)this.words.size();
        }
        return this.average;
    }
    public float getVariance(){
        if(this.variance == -1){
            this.variance = 0;
            for(Word w : this.words){
                this.variance += Math.pow((w.getLength() - this.getAverage()),2);
            }
            this.variance /= this.words.size(); 
        }
        return this.variance;
    }
    public float getSumOfMF(String sen,Dictionary dic){
        if(this.sumOfMF == -1){
            this.sumOfMF = 0.0f;
            for(Word w : this.words){
                if(w.getLength() == 1){
                    Double freq = dic.getWord(w.getOrSetText(sen));
                    if(freq == null)
                    	freq = Dictionary.MISSING_WORD_MF;
                    sumOfMF += (double)freq;                    
                }
            }
        }
        return this.sumOfMF;
    }

    public static LinkedList<Chunk> selectTops(List<Chunk> clist,String sen,Dictionary dic){
        LinkedList<Chunk> tops = new LinkedList<>();
        int maxLength = -1;
        if(clist.isEmpty())
        	return tops;
        for(Chunk c : clist){
            if(c.getTotalLength() > maxLength){
                maxLength = c.getTotalLength();
                tops.clear();
                tops.add(c);
            }else if(c.getTotalLength() == maxLength){
                tops.add(c);
            }
        }
        if(tops.size() == 1)
            return tops;
        //
        LinkedList<Chunk> tops1 = new LinkedList<>();
        float aver = -1;
        for(Chunk c : tops){
            if(c.getAverage() > aver){
                aver = c.getAverage();
                tops1.clear();
                tops1.add(c);
            }else if(c.getAverage() == aver){
                tops1.add(c);
            }
        }
        if(tops1.size() == 1)
            return tops1;
        //
        LinkedList<Chunk> tops2 = new LinkedList<>();
        float var = Float.MAX_VALUE;
        for(Chunk c : tops1){
            if(c.getVariance() < var){
                var = c.getVariance();
                tops2.clear();
                tops2.add(c);
            }else if(c.getVariance() == var){
                tops2.add(c);
            }
        }
        if(tops2.size() == 1)
            return tops2;
        //
        LinkedList<Chunk> tops3 = new LinkedList<>();
        float mf = -1;
        for(Chunk c : tops2){
            if(c.getSumOfMF(sen,dic) > mf){
                mf = c.getSumOfMF(sen,dic);
                tops3.clear();
                tops3.add(c);
            }else if(c.getSumOfMF(sen,dic) == mf){
                tops3.add(c);
            }
        }
        return tops3;
    }
}