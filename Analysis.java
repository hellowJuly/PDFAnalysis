import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Analysis {
    public File file;
    public LinkedList<File> pdfList;
    public HashMap<Integer,String> id_key;
    public ArrayList<Double> moneys;
    Analysis(File file, LinkedList<File> pdfList){
        this.file=file;
        this.pdfList=pdfList;
    }
    //解析和存储数据
    public void getdata(){
        id_key=new HashMap<>();
        moneys=new ArrayList<>();
        int index=0;
        for(File f : pdfList){
            //System.out.println(f.getAbsolutePath());
            File pdfFile = new File(f.getAbsolutePath());
            PDDocument document = null;
            try
            {
                // 方式一：

                InputStream input = null;
                input = new FileInputStream( pdfFile );
                //加载 pdf 文档
                PDFParser parser = new PDFParser(new RandomAccessBuffer(input));
                parser.parse();
                document = parser.getPDDocument();


                // 获取页码
                int pages = document.getNumberOfPages();

                // 读文本内容
                PDFTextStripper stripper=new PDFTextStripper();
                // 设置按顺序输出
                stripper.setSortByPosition(true);
                stripper.setStartPage(1);
                stripper.setEndPage(pages);
                String content = stripper.getText(document);

                // 提取pdf文件中的数据并保存
                String code="";
                String num="";
                String valid="";
                double money=0;
                int cnt=0;
                int i=0;
                while (i<content.length()) {
                    if (content.charAt(i) == '代') {
                        int len = i + 18;
                        while (i < len) {
                            if (content.charAt(i) >= '0' && content.charAt(i) <= '9') {
                                code += content.charAt(i);
                            }
                            i++;
                        }
                    }
                    if (content.charAt(i) == '号' && content.charAt(i + 1) == '码') {
                        int len = i + 15;
                        while (i < len) {
                            if (content.charAt(i) >= '0' && content.charAt(i) <= '9') {
                                num += content.charAt(i);
                            }
                            i++;
                        }
                    }
                    if (content.charAt(i) == '校' && content.charAt(i + 2) == '验') {
                        int len = i + 35;
                        while (i < len) {
                            if (content.charAt(i) >= '0' && content.charAt(i) <= '9') {
                                valid += content.charAt(i);
                            }
                            i++;
                        }
                        valid = valid.substring(valid.length() - 6, valid.length());
                    }
                    if (content.charAt(i) == '￥') {
                        cnt++;
                        if (cnt == 3) {
                            i++;
                            int len = i + 10;
                            double m1 = 0, m2 = 0;
                            while (i < len) {
                                if (content.charAt(i) == '.') break;
                                m1 = m1 * 10 + content.charAt(i) - '0';
                                i++;
                            }
                            i++;
                            int count = 0;
                            while (i < len) {
                                if (content.charAt(i) < '0' || content.charAt(i) > '9') break;
                                m2 += Math.pow(0.1, ++count) * (content.charAt(i) - '0');
                                i++;
                            }
                            money = m1 + m2;
                            //System.out.println(money+"|||||||");
                        }
                    }
                    i++;

                }
                moneys.add(money);
                String key=code+num+valid+index;
                id_key.put(index,key);
                index++;
                //System.out.println(content);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }

}
