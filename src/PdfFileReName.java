import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class PdfFileReName {
    public HashMap<Integer,String> rename;
    public File file;
    public LinkedList<File> pdfList;
    public ArrayList<Integer> index;
    public ArrayList<String> namelist;
    public PdfFileReName(HashMap<Integer,String> rename,File file, LinkedList<File> pdfList,ArrayList<Integer> index){
        this.rename=rename;
        this.file=file;
        this.pdfList=pdfList;
        this.index=index;
    }
    /**
      通过文件路径直接修改文件名
     */
    public void FixFileName() {
        namelist=new ArrayList<>();
        for(int i=0;i<index.size();i++){
            int renameindex=index.get(i);
            File f=pdfList.get(renameindex);
            String filePath=f.getAbsolutePath();
            if (!f.exists()) { // 判断原文件是否存在（防止文件名冲突）
                return ;
            }
            String newFileName=rename.get(renameindex)+(i+1);
            newFileName = newFileName.trim();
            if ("".equals(newFileName) || newFileName == null) // 文件名不能为空
                return ;
            String newFilePath = null;
            if (f.isDirectory()) { // 判断是否为文件夹
                newFilePath = filePath.substring(0, filePath.lastIndexOf("\\")) + "\\" + newFileName;
            } else {
                //newFilePath = filePath.substring(0, filePath.lastIndexOf("\\"));
                newFilePath = filePath.substring(0, filePath.lastIndexOf("\\")) + "\\" + newFileName
                        + filePath.substring(filePath.lastIndexOf("."));
            }
            namelist.add(newFilePath);
            File nf = new File(newFilePath);
            try {
                f.renameTo(nf); // 修改文件名
            } catch (Exception err) {
                err.printStackTrace();
                return ;
            }
        }
        return ;
    }
}
