package common;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YKL on 2018/4/19.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
public class IKSplit {

    public static String[] ikSplit(String str) throws IOException {

//        Analyzer anal = new IKAnalyzer();
//
//        StringReader reader = new StringReader(str);
//
//        TokenStream ts = anal.tokenStream("", reader);
//        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
//
//        List<String> splitStr = new ArrayList<String>();
//
//        while (ts.incrementToken()) {
//            splitStr.add(term.toString());
//        }
        StringReader reader=new StringReader(str);
        IKSegmenter ik=new IKSegmenter(reader, true);
        Lexeme lex=null;
        List<String> splitStr = new ArrayList<String>();
        while((lex=ik.next())!=null){
            splitStr.add(lex.getLexemeText());
        }

        String[] strs = new String[splitStr.size()];
        for (int i = 0; i < splitStr.size(); i++) {
            strs[i] = splitStr.get(i).toString();
        }

        reader.close();
        return strs;

    }

}
