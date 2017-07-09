package com.profullstack.cjkmmseg;

import javax.swing.text.Segment;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.text.BreakIterator;

public class Standardseg {
	
	public static final int READER_BUFFER_SIZE=1024;
	public static final int PUSHBACK_BUFFER_SIZE=1024;
		
	private PushbackReader pr;
	private char[] readerBuffer;
	private int readerBufferCurrentIndex;
	private int readerBufferNextIndex;
	private BreakIterator boundary;
	//used by next()
	private boolean needRead = false;
	private int start=0;
	private int rcount=0;
	//
	public Standardseg(){
        this.readerBuffer = new char[READER_BUFFER_SIZE];
		this.boundary = BreakIterator.getWordInstance();
	}
	public void setReader(Reader reader) throws IOException{
		this.close();
        this.pr = new PushbackReader(reader,PUSHBACK_BUFFER_SIZE);
        this.readerBufferCurrentIndex=0;
        this.readerBufferNextIndex = 0;
        this.needRead = true;
        //
        this.start = 0;
        this.rcount = 0;
	}
	
	public Word next() throws IOException{
		if(this.needRead){
			this.rcount = this.pr.read(readerBuffer);
	        if(this.rcount != -1){
	        	assert this.rcount >= 1 : "rcount = 0 !";
	        	this.readerBufferCurrentIndex = this.readerBufferNextIndex;
	        	this.readerBufferNextIndex = this.readerBufferCurrentIndex + rcount;
	        	this.boundary.setText(new Segment(readerBuffer, 0, this.rcount));
	        	this.needRead = false;
	        	this.start = 0;
	        }else{
	        	return null;
	        }
        }
		int end = boundary.following(this.start);
		assert end != BreakIterator.DONE;
		if(boundary.following(end) != BreakIterator.DONE){
			boundary.previous();
			Word w = new Word(new String(readerBuffer,this.start,end-this.start),this.readerBufferCurrentIndex+this.start,this.readerBufferCurrentIndex+end-1);
			this.start = end;
			return w;
		}
		//may roll back reader
		assert end == this.rcount;
		int rollCount = end-this.start;
		assert rollCount <= this.rcount;
		if(rollCount < this.rcount){
			this.pr.unread(readerBuffer,this.start,rollCount);
			this.readerBufferNextIndex = this.readerBufferCurrentIndex + this.rcount - rollCount;
			this.needRead = true;
			return this.next();	
		}else{
			//do not roll back, return immediately
			this.needRead = true;
			Word w = new Word(new String(readerBuffer,this.start,rollCount),this.readerBufferCurrentIndex+this.start,this.readerBufferCurrentIndex+rollCount-1);
			return w;
		}
	}
	public void close() throws IOException{
		if(this.pr != null)
			this.pr.close();
	}
}
