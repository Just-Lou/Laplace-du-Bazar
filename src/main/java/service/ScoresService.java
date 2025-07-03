package service;

import business.Score;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mapper.ScoresMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;


@Path("/laplace/scores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScoresService {

    @Inject
    ScoresMapper scoresMapper;

    @GET
    @Path("getScore/{id}")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public Score getScoreById(@PathParam("id") UUID id) {
        Score score = scoresMapper.getScoreById(id);
        return score;
    }

    @GET
    @Path("getAllScores")
    @RolesAllowed("Administrator")
    public List<Score> getAllScores() {
        List<Score> scores = scoresMapper.getAllScores();
        return scores;
    }

    @PUT
    @Path("updateScore/{id}")
    @RolesAllowed("Administrator")
    public boolean updateScore(@PathParam("id") UUID id, @QueryParam("score") float score, @QueryParam("number") int number) {
        scoresMapper.updateScore(id, score, number);
        return true;
    }

    @PUT
    @Path("createScore/{id}")
    @RolesAllowed({"StandardUser", "Administrator"})
    public Response createScore(@PathParam("id") UUID id, float score) {
        scoresMapper.createScore(id, score);
        return Response.ok().build();
    }
}
