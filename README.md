# cjk-mmseg
又一个基于mmseg的cjk中文分词器，首先按照Unicode text segmentation,uax 29的国际标准分词，即首先切分英文与数字等，再在切出的中文串句子上采用MMSEG分词，词典使用搜狗分词库。

Features:
1.字典采用patricia trie, 实现采用的是 [Patricia Trie](https://github.com/rkapsi/patricia-trie)，所以支持词典的动态增删查改，不需要重新load字典文件。
2.采用的分词方法是基于unicode uax #29 与mmseg，uax 29的实现是JDK的java.text包。所以既支持英文等欧洲的分词，又支持中文，CJK的分词，当然你要先准备日文的词典文件。

使用方法：
```java
	public static void main(String[] args) throws IOException{
		ECJKSeg seg = new ECJKSeg();
		String s = "如果纪勤不是一个合格的主席，那肯定是因为有人忘了通知他。我的用户名是aa885,我的邮箱是9999@gmail.com.";
		Reader r = new StringReader(s);
		seg.setReader(r);
		Word w;
		while((w = seg.nextWord()) != null){
			System.out.println(w);
		}
		seg.close();
	}
```
结果
```
text: 如果 startOffset: 0 endOffset: 1
text: 纪 startOffset: 2 endOffset: 2
text: 勤 startOffset: 3 endOffset: 3
text: 不是 startOffset: 4 endOffset: 5
text: 一个合格 startOffset: 6 endOffset: 9
text: 的 startOffset: 10 endOffset: 10
text: 主席 startOffset: 11 endOffset: 12
text: 那 startOffset: 14 endOffset: 14
text: 肯定 startOffset: 15 endOffset: 16
text: 是因为 startOffset: 17 endOffset: 19
text: 有人 startOffset: 20 endOffset: 21
text: 忘了 startOffset: 22 endOffset: 23
text: 通知 startOffset: 24 endOffset: 25
text: 他 startOffset: 26 endOffset: 26
text: 我的 startOffset: 28 endOffset: 29
text: 用户名 startOffset: 30 endOffset: 32
text: 是 startOffset: 33 endOffset: 33
text: aa885 startOffset: 34 endOffset: 38
text: 我的邮箱 startOffset: 40 endOffset: 43
text: 是 startOffset: 44 endOffset: 44
text: 9999 startOffset: 45 endOffset: 48
text: gmail.com startOffset: 50 endOffset: 58
```