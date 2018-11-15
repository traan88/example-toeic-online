package vn.toeiconline.core.common.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UploadUtil {
    private final int maxMemorySize = 1024 * 1024 * 3; // 3MB
    private final int maxRequestSize = 1024 * 1024 * 50; //50MB

    public Object[] writeOrUpdateFile(HttpServletRequest request, Set<String> titleValue, String path) throws Exception {
        ServletContext context = request.getServletContext();
        String address = context.getRealPath("image");
        boolean check = true;
        String fileLocation = null;
        String name = null;
        Map<String, String> mapReturnValue = new HashMap<String, String>();

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            System.out.println("Have not enctype multipart/form-data");
            check = false;
        }

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Set factory constraints
        factory.setSizeThreshold(maxMemorySize);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(maxRequestSize);

        List<FileItem> items = upload.parseRequest(request);
        for (FileItem item: items) {
            if (!item.isFormField()) {
                String fileName = item.getName();
                if (StringUtils.isNotBlank(fileName)) {
                    name = fileName;
                    File uploadedFile = new File(address + File.separator + path + File.separator + fileName);
                    fileLocation = address + File.separator + path + File.separator + fileName;
                    boolean isExist = uploadedFile.exists();
                    if (isExist) {
                        boolean isDelete = uploadedFile.delete();
                        if (isDelete) {
                            item.write(uploadedFile);
                        }
                    } else {
                        item.write(uploadedFile);
                    }
                }
            } else {
                String nameFiled = item.getFieldName();
                String valueField = item.getString();
                if (titleValue.contains(nameFiled)) {
                    mapReturnValue.put(nameFiled, valueField);
                }
            }
        }

        return new Object[]{check, fileLocation, name, mapReturnValue};
    }
}