package springlol.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
	/**
	 * 创建一个文件，如果目录不存在，将自动创建目录
	 *
	 * @param pathName 文件绝对路径名
	 * @return
	 * @throws IOException
	 */
	public static File creatNewFile(String pathName) throws IOException {
		File file = new File(pathName);
		File dir = file.getParentFile();
		if(!dir.exists()) {
			dir.mkdirs();
		}
		file.createNewFile();
		return file;
	}

	/**
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] fileToBytes(File file) throws IOException, FileTooBigException {
		long fileSize = file.length();
		if(fileSize > Integer.MAX_VALUE) {
			throw new FileTooBigException("文件超出限制大小");
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		// 确保所有数据均被读取
		if(offset != buffer.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		fi.close();
		return buffer;
	}

	/**
	 * 将字节数组写入文件
	 * @param filePath
	 * @param fileData
	 * @throws IOException
	 */
	public static void writeBytesToFile(String filePath, byte[] fileData) throws IOException {
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);
		out.write(fileData);
		out.flush();
		out.close();
	}
}
