package com.restws.restattachments;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.List;

@Path("/fileService")
@Service // Spring annotation. Spring will scan through classes & determine this is a Service class.
public class FileService {

	private static final String FILE_PATH = "C:\\Users\\Owner\\Downloads\\images\\upload.jpg";

    /**
     *  Upload files to a server.
     *  @param attachments the list of files to upload.
     */
	@Path("/upload")
    @POST
	public void upload(List<Attachment> attachments) throws FileNotFoundException, IOException {

		System.out.println("Inside upload method");
	    for (Attachment attachment: attachments) {

	        // Get the input stream and pass it to the copyFile method.
	        copyFile(attachment.getDataHandler().getInputStream());
        }
    }

    /**
     *  Download a file from the server.
     *  @return
     */
    @Path("/download")
    @GET
    public Response download() {

	    // the file we want the client to download.
	    File file = new File("C:\\Users\\Owner\\Downloads\\images\\upload.jpg");
	    Response.ResponseBuilder responseBuilder = Response.ok(file);
	    responseBuilder.header("Content-Disposition","attachment;filename=downloaded.jpg");

	    return responseBuilder.build();
    }

    /**
     *  Method to copy a file.
     *  @param inputStream the input stream.
     *  @throws FileNotFoundException a file not found exception.
     *  @throws IOException an IO exception.
     */
	private void copyFile(InputStream inputStream) throws FileNotFoundException, IOException {

		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];

		out = new FileOutputStream(new File(FILE_PATH));
		while ((read = inputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();	
	}
}