package com.profullstack.cjkmmseg;

import org.ardverk.collection.PatriciaTrie;
import org.ardverk.collection.StringKeyAnalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.SortedMap;

public class Dictionary{
	public static final String DEFAULT_DIC_NAME = "/SogouLabDic.dic";
	public static final Double MISSING_WORD_MF = 1.0;
	
    private PatriciaTrie<String, Double> trie;
    
    private int wordsCount = 0;
    private int maxLength = 0;//call this.remove() will not change this maxLength!

    public Dictionary(boolean loadDefault){
        trie = new PatriciaTrie<String, Double>(StringKeyAnalyzer.CHAR);
        if(loadDefault)
        	loadDicFile(DEFAULT_DIC_NAME);
    }

    public Dictionary(String filename){
        trie = new PatriciaTrie<String, Double>(StringKeyAnalyzer.CHAR);
        loadDicFile(filename);
    }

    public void loadDicFile(String filename){
        try(BufferedReader br =  new BufferedReader(new InputStreamReader(Dictionary.class.getResourceAsStream(filename),"UTF-8"))){
            String line = null;
            while((line = br.readLine()) != null) {
                String[] la = line.split("\\s+");
                String word = la[0];
                String freqs = la[1];
                this.putWord(word,Double.parseDouble(freqs));
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public boolean containsWord(String w){
    	return this.trie.containsKey(w);
    }

    public Double getWord(String w){
        return this.trie.get(w);
    }
    public Double putWord(String w,Double freq){
    	wordsCount++;
    	if(w.length() > this.maxLength)
    		this.maxLength = w.length();
        return this.trie.put(w,freq);
    }
    public Double remove(String w){
    	wordsCount--;
        return this.trie.remove(w);
    }
    public SortedMap<String,Double> prefixMap(String word){
        return this.trie.prefixMap(word);
    }
    public int getMaxLength(){
    	return this.maxLength;
    }
    public int getWordsCount(){
    	return this.wordsCount;
    }
}