import java.io.File;
import java.util.LinkedList;

public class Main {
    public static String path = "G:\\实习工作";    //自己的路径
    public static FindAllFiles findAllFiles;
    public static Analysis analysis;
    public static Calculator calculator;
    public static void main(String[] args) {
        findAllFiles=new FindAllFiles(path);
        findAllFiles.folderMethod();
        analysis=new Analysis(findAllFiles.file,findAllFiles.pdfList);
        analysis.getdata();
        calculator=new Calculator(analysis.id_key,analysis.moneys);
        calculator.calculator();

    }
}
