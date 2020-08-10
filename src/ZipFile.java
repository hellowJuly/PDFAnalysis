import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFile {
    public ArrayList<String> namelist;
    public ZipFile(ArrayList<String> namelist){
        this.namelist=namelist;
    }
    /**
     * 文件压缩成zip
     * @param zipFilePath zip压缩文件存放路径
     *        fileName 文件名
     *        filePath 文件路径
     */
    public static void zipFileChannel(String zipFilePath, String fileName, String filePath) {
        long beginTime = System.currentTimeMillis();
        File zipFile = new File(zipFilePath);
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            File file = new File(filePath + fileName);
            if (!file.exists()) {
                return;
            }
            FileChannel fileChannel = new FileInputStream(file).getChannel();
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            fileChannel.transferTo(0, file.length(), writableByteChannel);
            long endTime = System.currentTimeMillis();
            System.out.println("压缩时间："+(endTime - beginTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void FileToZip(){
        String zipFilePath;
        String fileName;
        String filePath;
        for(int i=0;i<namelist.size();i++){
            zipFilePath=namelist.get(i);
            filePath=zipFilePath.substring(0, zipFilePath.lastIndexOf("\\")+1);
            fileName=zipFilePath.substring(zipFilePath.lastIndexOf("\\")+1,zipFilePath.length());
            zipFilePath=zipFilePath.substring(0, zipFilePath.lastIndexOf(".")+1)+"zip";
            zipFileChannel(zipFilePath,fileName,filePath);
        }
    }
}
