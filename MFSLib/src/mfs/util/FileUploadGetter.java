package mfs.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mfs.util.biller.ConstantUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Extract File list from Uploading. Need apache io and apache fileupload
 * library in classpath.
 * 
 * @author Apichart
 * 
 */
public class FileUploadGetter {

	public static List<File> getUploadedFile(HttpServletRequest request,
			HttpServletResponse response) {

		// Check that we have a file upload request
		final boolean isMultipart = ServletFileUpload
				.isMultipartContent(request);

		if (!isMultipart) {
			System.out.println("Is Not MULTIPART, quit getUploadedFile()");
			return new ArrayList<File>();
		}

		String filePath = ConstantUtil.TEMP_CONF_PATH + "\\";
		filePath = filePath.replace("/", "\\");
		System.out.println("FilePath=" + filePath);

		int maxFileSize = 50 * 1024;
		int maxMemSize = 4 * 1024;
		File file;

		System.out.println(" DiskFileItemFactory factory");

		final List<File> resultFileList = new ArrayList<File>();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File("c:\\temp"));

		System.out
				.println(" ServletFileUpload upload = new ServletFileUpload(factory)");

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);
		// Parse the request to get file items.
		List<FileItem> fileItems = null;
		try {
			fileItems = upload.parseRequest(request);
		} catch (FileUploadException e) {
			System.out.println("upload.parseRequest(request) throws exception");
			e.printStackTrace();
		}

		for (FileItem fi : fileItems) {
			if (!fi.isFormField()) {
				// Get the uploaded file parameters
				String fieldName = fi.getFieldName();
				String fileName = fi.getName();
				String contentType = fi.getContentType();
				boolean isInMemory = fi.isInMemory();
				long sizeInBytes = fi.getSize();
				// Write the file
				if (fileName.lastIndexOf("\\") >= 0) {
					file = new File(filePath
							+ fileName.substring(fileName.lastIndexOf("\\")));
				} else {
					file = new File(
							filePath
									+ fileName.substring(fileName
											.lastIndexOf("\\") + 1));
				}
				try {
					fi.write(file);
				} catch (Exception e) {
					System.out.println("Cannot write File");
					e.printStackTrace();
				}
				resultFileList.add(file);
			}
		}
		return resultFileList;
	}
}
