package com.profullstack.cjkmmseg;

public class Word{
    private String text;//never null once inited, text.length() > 0
    private int startOffset;
    private int endOffset;
    //
    private int length;

    public Word(String text,int startOffset,int endOffset){
        this.text = text;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.length = endOffset - startOffset + 1;
    }
    
    public Word(int startOffset,int endOffset){
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.length = endOffset - startOffset + 1;
    }
    public int getLength(){
    	return this.length;
    }
    public String getText(){
        return this.text;
    }
    public void setTextIfNull(String sen){
    	if(this.text == null)
    		this.text = sen.substring(this.startOffset,this.endOffset+1);
    }
    public String getOrSetText(String sen){
    	this.setTextIfNull(sen);
    	return this.text;
    }
    public int getStartOffset(){
        return this.startOffset;
    }
    public int getEndOffset(){
        return this.endOffset;
    }
    public void addSenIndex(int index){
    	this.startOffset += index;
    	this.endOffset += index;
    }
    
    @Override
    public String toString(){
    	return "text: "+this.text+" startOffset: "+this.startOffset+" endOffset: "+this.endOffset;
    }

}