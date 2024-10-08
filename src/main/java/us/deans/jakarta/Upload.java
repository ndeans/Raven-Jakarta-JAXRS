package us.deans.jakarta;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.deans.raven.processor.OppProcessor_02;
import us.deans.raven.processor.RvnImport;
import us.deans.raven.processor.Processor;

import java.util.Arrays;

@Path("upload")
public class Upload {
    Logger logger = LoggerFactory.getLogger(Upload.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setPostList(RvnImport upload) {
        String msg = "Receiving import object... { " + upload.printJob() + " post list: " + upload.getPostCount() + " records }";
        logger.info(msg);  // logger.debug(msg);

        try {
            Processor processor = new OppProcessor_02(upload);
            processor.log();
            processor.persist();  // activating separately affords a chance to manipulate first)
        } catch (Exception ex) {
            logger.error(ex.toString());
            logger.error(Arrays.toString(ex.getStackTrace()));
        }
        return Response.ok(msg).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getVersion() {
        return "Raven Jakarta version 1.2";
    }
}
