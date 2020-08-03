

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

public class FindAllFiles {
	public String path;   //自己的路径
	public File file;
	public LinkedList<File> pdfList;
	public FindAllFiles(String path){
		this.path=path;
	}

	public void folderMethod() {

        file = new File(path);
        LinkedList<File> list = new LinkedList<>();

        //保存所有pdf文件的对象
        pdfList = new LinkedList<File>();

        //该路径对应的文件或文件夹是否存在
        if (file.exists()) {

        	//如果该路径为---文件或空文件夹
            if (null == file.listFiles()) {
//            	System.out.println(file.getAbsolutePath());
            	if(file.getAbsolutePath().endsWith(".pdf"))
            		pdfList.add(file);
            }

            //如果该路径为非空文件夹
            else {
            	//将该路径下的所有文件（文件或文件夹）对象加入队列
                list.addAll(Arrays.asList(file.listFiles()));
                //遍历该队列
                while (!list.isEmpty()) {

                	File firstF = list.removeFirst();

                	//这里不论是文件夹还是文件，只需判断是否以“.pdf”结尾
            		if(firstF.getAbsolutePath().endsWith(".pdf"))
            			pdfList.add(firstF);

                	File[] files = firstF.listFiles();

                	if (null == files) {
                		//System.out.println(firstF.getAbsolutePath());
                		continue;
                	}
                	for (File f : files) {
                		if (f.isDirectory()) {
                			list.add(f);
                		} else {
                			if(f.getAbsolutePath().endsWith(".pdf"))
                    			pdfList.add(f);
                		}
                	}
              }
            }

        } else {
            System.out.println("文件不存在!");
        }
    }


}