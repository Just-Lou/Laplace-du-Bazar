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
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.UUID;


@Path("/laplace/scores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScoresService {

    @Inject
    ScoresMapper scoresMapper;

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("getScore/{id}")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public Score getScoreById(@PathParam("id") UUID id) {
        Score score = scoresMapper.getScoreById(id);
        return score;
    }

    @GET
    @Path("getScoreByUser/{id}")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public Score getScoreByUserId(@PathParam("id") UUID userId) {
        Score scoreUser = scoresMapper.getScoreByUserId(userId);
        return scoreUser;
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

    @PUT
    @Path("addScoreToUser/{sellerId}/{score}")
    @RolesAllowed({"StandardUser", "Administrator"})
    public Response addScoreToUser(@PathParam("sellerId") UUID sellerId, @PathParam("score") float score) {
        String clientId = jwt.getSubject();
        Score sellerScore = scoresMapper.getScoreByUserId(sellerId);
        if (score > 5) { score= 5; }
        if (score < 0) { score = 0; }
        float newScore = (sellerScore.getScore() * sellerScore.getScoreNumber() + score) / (sellerScore.getScoreNumber() + 1);
        scoresMapper.updateScore(sellerScore.getScoreId(), newScore, sellerScore.getScoreNumber() + 1);
        return Response.ok().build();
    }
}
