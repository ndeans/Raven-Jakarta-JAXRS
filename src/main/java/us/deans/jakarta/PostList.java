package us.deans.jakarta;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.deans.opp.processor.OppPost;
import us.deans.opp.processor.OppProcessor_01;
import us.deans.raven.processor.Processor;
import java.util.List;

@Path("post-list")
public class PostList {
    Logger logger = LoggerFactory.getLogger(PostList.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setPostList(List<OppPost> postList) {
        String msg = "";
        try {
            Processor processor = new OppProcessor_01(postList);
            processor.log();
            processor.persist();
            msg = "inserting " + postList.size() + " post records...";
            logger.info(msg);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return Response.ok(msg).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getVersion() {
        return "Raven Jakarta version 1.1";
    }
}
