package test;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Map;


import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class IKSegmenterTest {
	public static void main(String[] args) throws IOException {
        String text = "我爱你中国，厉害了，我的国。我爱你！";
        int topWordsCount=4;
        Map<String,Integer> wordsFrenMaps=getTextDef(text);
        sortSegmentResult(wordsFrenMaps,topWordsCount);
    }
    
    public static Map getTextDef(String text) throws IOException {
        Map<String, Integer> wordsFren=new HashMap<String, Integer>();
        IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(text), true);
        Lexeme lexeme;
        while ((lexeme = ikSegmenter.next()) != null) {
            if(lexeme.getLexemeText().length()>1){
                if(wordsFren.containsKey(lexeme.getLexemeText())){
                    wordsFren.put(lexeme.getLexemeText(),wordsFren.get(lexeme.getLexemeText())+1);
                }else {
                    wordsFren.put(lexeme.getLexemeText(),1);
                }
            }
        }
        return wordsFren;
    }
    
    public static void sortSegmentResult(Map<String,Integer> wordsFrenMaps,int topWordsCount){
        Iterator<Map.Entry<String,Integer>> wordsFrenMapsIterator=wordsFrenMaps.entrySet().iterator();
        while (wordsFrenMapsIterator.hasNext()){
            Map.Entry<String,Integer> wordsFrenEntry=wordsFrenMapsIterator.next();
            System.out.println(wordsFrenEntry.getKey()+"       的次数为"+wordsFrenEntry.getValue());
        }
    }
	}
	


