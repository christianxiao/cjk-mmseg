package com.profullstack.cjkmmseg;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

public class Mmseg {
	public static final int READER_BUFFER_SIZE=256;
	public static final int PUSHBACK_BUFFER_SIZE=256;
	//
	private Dictionary dic;
	//
	private String sentence;
	private int senIndex;
	//
	private int start=0;//for next()
		
	public Mmseg(Dictionary dic){
		this.dic = dic;
	}

	/**
	 * @param sen sen.length()>0
	 */
	public void setSentence(String sen,int senIndex){
		this.sentence = sen;
		this.senIndex = senIndex;
		this.start = 0;
	}
	public void setSentence(String sen){
		this.sentence = sen;
		this.senIndex = 0;
		this.start = 0;
	}
	public Word next(){		
		if(this.start == this.sentence.length())
			return null;
		Word w = getFirstWord(this.sentence,this.start,this.dic);
		this.start = w.getEndOffset()+1;
		w.addSenIndex(senIndex);
		return w;		
	}
	/**
	 * 
	 * @param sen sen.length() greater than 0
	 * @param start at first start = 0
	 * @param dic
	 */
	private static LinkedList<Integer> getPrefixWords(String sen,int start,Dictionary dic){
		LinkedList<Integer> wl = new LinkedList<>();
		if(start == sen.length())
			return wl;
		for(int i=start+1;i<=sen.length();i++){
			String w = sen.substring(start,i);
			SortedMap<String,Double> map = dic.prefixMap(w);
			if(map.isEmpty()){
				//we find a new word! Its length maybe larger than 1.
				if(i==start+1){
					wl.add(i); //this new word's length is 1.
				}else{ //this new word's length is larger than 1.
					if(wl.indexOf(i-1) == -1)
						wl.add(i-1);
				}
				return wl;
			}
			if(map.containsKey(w)){
				wl.add(i);
			}else{
				if(i==sen.length())
					wl.add(i); //we find a new word! Its length maybe larger than 1. 
			}
		}
		return wl;
	}
	private static LinkedList<Chunk> getFirstChunks(String sen,int start,Dictionary dic){
		LinkedList<Chunk> cl = new LinkedList<>();
		LinkedList<Integer> wl1 = getPrefixWords(sen,start,dic);
		if(wl1.isEmpty()){
			return cl;
		}
		for(Integer i : wl1){
			LinkedList<Integer> wl2 = getPrefixWords(sen,i,dic);
			if(wl2.isEmpty()){
				Chunk c = new Chunk();
				c.getWords().add(new Word(start,i-1));
				cl.add(c);
				continue;
			}
			for(Integer j : wl2){
				LinkedList<Integer> wl3 = getPrefixWords(sen,j,dic);
				if(wl3.isEmpty()){
					Chunk c = new Chunk();
					c.getWords().add(new Word(start,i-1));
					c.getWords().add(new Word(i,j-1));
					cl.add(c);
					continue;
				}
				for(Integer k : wl3){
					Chunk c = new Chunk();
					c.getWords().add(new Word(start,i-1));
					c.getWords().add(new Word(i,j-1));
					c.getWords().add(new Word(j,k-1));
					cl.add(c);
				}
			}
		}
		return cl;
	}
	private static Word getFirstWord(String sentence,int start,Dictionary dic){
		List<Chunk> cl = Chunk.selectTops(getFirstChunks(sentence,start,dic),sentence,dic);
		assert !cl.isEmpty();
		Word w = cl.get(0).getWords().get(0);
		w.setTextIfNull(sentence);
		return w;
	}

}
