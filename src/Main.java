import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static String path = "G:\\实习工作\\数据";    //自己的路径
    public static FindAllFiles findAllFiles;
    public static Analysis analysis;
    public static Calculator calculator;
    public static ArrayList<Integer> index;
    public static PdfFileReName pdfFileReName;
    public static ZipFile zipFile;
    public static void main(String[] args) {
        findAllFiles=new FindAllFiles(path);
        findAllFiles.folderMethod();
        analysis=new Analysis(findAllFiles.file,findAllFiles.pdfList);
        analysis.getdata();
        System.out.print("请输入要报销的金额：");
        Scanner input=new Scanner(System.in);
        int target=input.nextInt();
        calculator=new Calculator(analysis.id_key,analysis.moneys,target);
        index=calculator.calculator();
        System.out.println("总报销金额为："+calculator.min_val);
        System.out.println("准备修改文件名！");
        pdfFileReName=new PdfFileReName(analysis.id_key,findAllFiles.file,findAllFiles.pdfList,index);
        pdfFileReName.FixFileName();
        System.out.println("文件名修改完成，准备压缩！");
        zipFile=new ZipFile(pdfFileReName.namelist);
        zipFile.FileToZip();
        System.out.println("压缩完成！");
    }
}
