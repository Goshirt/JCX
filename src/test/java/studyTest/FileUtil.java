package studyTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;

public class FileUtil {
	
	private FTPClient ftClient = new FTPClient();
	/**
	 * Handling single file download.
	 * 
	 * @exception		any IOException occurred in streams
	 * @exception		any FTP exception occurred
	 * @exception		any other exceptions 
	 */
	public void onSingleFileDownload(HttpServletResponse response) {
		
		try {
			
			ftClient.connect("");
			// to throw exception when file not found on ftp server
			File ftpFile = File.createTempFile("ftp", ".zip");
			long start = System.currentTimeMillis();
			System.out.println("start download file at "+System.currentTimeMillis());
			System.out.println( ">> END download file from FTP used time: " + (System.currentTimeMillis() - start));
			//File unzipFile = null;
			ZipFile zipFile = new ZipFile(ftpFile);
			Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();
			ZipEntry zipEntry = (ZipEntry) zipEntrys.nextElement(); // zip file should contains one and only one file
			String fileSuffix = "";
			if (zipEntry.getName().indexOf(".") != -1) {
				fileSuffix = zipEntry.getName().substring(zipEntry.getName().lastIndexOf("."));
			}
			StringBuffer sb = new StringBuffer(64);
			String unzipFileName = sb.toString();
			
			
				// http://wiki.apache.org/myfaces/Sending_Files
			
				start = System.currentTimeMillis();
				System.out.println(">> START directly unzipped file to HTTP response: " + unzipFileName);
				InputStream is = null;
				OutputStream fos = null;
				
				int zipEntryStreamLength = 0;
				
				try {
					is = new BufferedInputStream(zipFile.getInputStream(zipEntry));
					fos = new BufferedOutputStream(response.getOutputStream());
					zipEntryStreamLength = is.available();
					
					int c;
					while ((c = is.read()) != -1) {
						fos.write(c);
					}
					fos.flush();
					
				} catch (IOException ioe) {
					System.out.println(ioe);
				} finally {
					if (fos != null) {
						fos.close();
						fos = null;
					}
					if (is != null) {
						is.close();
						is = null;
					}
				}
				zipFile.close();
				ftpFile.delete();
				System.out.println("end directly unzipped file to HTTP response: " + (System.currentTimeMillis() - start));
				response.setContentType("application/data");
				response.setHeader("Content-Length", String.valueOf(zipEntryStreamLength));
				response.setHeader("Content-Disposition", "attachment;filename=\"" + unzipFileName + "\"");
			
				
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (ftClient.isConnected()) {
				try {
					ftClient.disconnect();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
}

}
