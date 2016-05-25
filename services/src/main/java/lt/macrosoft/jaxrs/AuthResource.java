package lt.macrosoft.jaxrs;

import java.io.IOException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;

import lt.macrosoft.core.Token;
import lt.macrosoft.daos.MemberDAO;
import lt.macrosoft.entities.Member;
import lt.macrosoft.utils.AuthUtils;
import lt.macrosoft.utils.PasswordService;

@Path("/auth")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
  
  private Client client = ClientBuilder.newClient();
 // private final UserDAO dao;
  @Inject
  MemberDAO dao;

  public static final String CLIENT_ID_KEY = "client_id", REDIRECT_URI_KEY = "redirect_uri",
      CLIENT_SECRET = "client_secret", CODE_KEY = "code", GRANT_TYPE_KEY = "grant_type",
      AUTH_CODE = "authorization_code";

  public static final String CONFLICT_MSG = "There is already a %s account that belongs to you",
      NOT_FOUND_MSG = "User not found", LOGING_ERROR_MSG = "Wrong email and/or password",
      UNLINK_ERROR_MSG = "Could not unlink %s account because it is your only sign-in method";

  public static final ObjectMapper MAPPER = new ObjectMapper();

  @GET
  @Path("{id}")
  public String getPerson(@PathParam("id") Long id, @Context final HttpServletRequest request) {
      Enumeration<String> x = request.getHeaderNames();
      String hedas = x.nextElement();
      String headeris = request.getHeader(hedas);
      System.out.println(headeris);
      Optional<Member> memberis = dao.getMemberByToken(headeris);
      if (memberis.isPresent()) {
          System.out.println(memberis.get().getEmail());
      }
      return headeris;
  }
  
  public AuthResource(final Client client, final MemberDAO dao) {
    this.client = client;
    this.dao = dao;
  }
  public AuthResource() {
	  }
  @POST
  @Path("login")
  public Response login(final Member member, @Context final HttpServletRequest request)
      throws JOSEException {
    final Optional<Member> foundUser = dao.findByEmail(member.getEmail());
    if (foundUser.isPresent()
        && PasswordService.checkPassword(member.getPassword(), foundUser.get().getPassword())) {
      final Token token = AuthUtils.createToken(request.getRemoteHost(), foundUser.get().getId());
      return Response.ok().entity(token).build();
    }
    return Response.status(Status.UNAUTHORIZED).entity(LOGING_ERROR_MSG).build();
  }

  @POST
  @Path("signup")
  public Response signup(final Member member, @Context final HttpServletRequest request)
      throws JOSEException {
	  member.setPassword(PasswordService.hashPassword(member.getPassword()));
    final Member savedUser = dao.save(member);
    final Token token = AuthUtils.createToken(request.getRemoteHost(), savedUser.getId());
    return Response.status(Status.CREATED).entity(token).build();
  }

  @POST
  @Path("facebook")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response loginFacebook(@Valid final Payload payload,
      @Context final HttpServletRequest request) throws JsonParseException, JsonMappingException,
      IOException, ParseException, JOSEException {
    final String accessTokenUrl = "https://graph.facebook.com/v2.3/oauth/access_token";
    final String graphApiUrl = "https://graph.facebook.com/v2.3/me";

    Response response;

    // Step 1. Exchange authorization code for access token.

    response =
        client.target(accessTokenUrl).queryParam(CLIENT_ID_KEY, payload.getClientId())
            .queryParam(REDIRECT_URI_KEY, payload.getRedirectUri())
            .queryParam(CLIENT_SECRET, "fd0c40e48c37f9aeee4b847acd0f4bb4")
            .queryParam(CODE_KEY, payload.getCode()).request("text/plain")
            .accept(MediaType.TEXT_PLAIN).get();

    Map<String, Object> responseEntity = getResponseEntity(response);
        
    response =
            client.target(graphApiUrl).queryParam("access_token", responseEntity.get("access_token"))
                .queryParam("expires_in", responseEntity.get("expires_in")).request("text/plain").get();
    
    final Map<String, Object> userInfo = getResponseEntity(response);

    // Step 3. Process the authenticated the user.
    return processUser(request, userInfo.get("id").toString(),
        userInfo.get("name").toString());
  }

  /*@POST
  @Path("unlink/")
  public Response unlink(@Valid final UnlinkRequest unlinkRequest,
      @Context final HttpServletRequest request) throws ParseException, IllegalArgumentException,
      IllegalAccessException, NoSuchFieldException, SecurityException, JOSEException {
    final String subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
    final Optional<Member> foundUser = dao. getMemberById(Long.parseLong(subject));

    String provider = unlinkRequest.provider;
    
    if (!foundUser.isPresent()) {
      return Response.status(Status.NOT_FOUND).entity(NOT_FOUND_MSG).build();
    }

    final Member userToUnlink = foundUser.get();

   // check that the user is not trying to unlink the only sign-in method
    if (userToUnlink.getSignInMethodCount() == 1) {
      return Response.status(Status.BAD_REQUEST)
          .entity(String.format(UNLINK_ERROR_MSG, provider)).build();
    }

    try {
      userToUnlink.setProviderId(Provider.valueOf(provider.toUpperCase()), null);
    } catch (final IllegalArgumentException e) {
      return Response.status(Status.BAD_REQUEST).build();
    }

    dao.save(userToUnlink);

    return Response.ok().build();
  }*/

  
  public static class UnlinkRequest {
	  @NotBlank
	  String provider;

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	  
  }

  /*
   * Inner classes for entity wrappers
   */
  public static class Payload {
    @NotBlank
    String clientId;

    @NotBlank
    String redirectUri;

    @NotBlank
    String code;

    public String getClientId() {
      return clientId;
    }

    public String getRedirectUri() {
      return redirectUri;
    }

    public String getCode() {
      return code;
    }
  }

  /*
   * Helper methods
   */
  private Map<String, Object> getResponseEntity(final Response response) throws JsonParseException,
      JsonMappingException, IOException {
    return MAPPER.readValue(response.readEntity(String.class),
        new TypeReference<Map<String, Object>>() {});
  }

  private Response processUser(final HttpServletRequest request, 
      final String id, final String displayName) throws JOSEException, ParseException {
    final Optional<Member> user = dao.findByFacebook(id);

    // Step 3a. If user is already signed in then link accounts.
    Member userToSave;
    final String authHeader = request.getHeader(AuthUtils.AUTH_HEADER_KEY);
    if (StringUtils.isNotBlank(authHeader)) {
      if (user.isPresent()) {
        return Response.status(Status.CONFLICT)
            .entity(String.format(CONFLICT_MSG, "FACEBOOK")).build();
      }

      final String subject = AuthUtils.getSubject(authHeader);
      final Optional<Member> foundUser = dao.getMemberById(Long.parseLong(subject));
      if (!foundUser.isPresent()) {
        return Response.status(Status.NOT_FOUND).entity((NOT_FOUND_MSG)).build();
      }

      userToSave = foundUser.get();
      if (userToSave.getName() == null) {
        userToSave.setName(displayName);
      }
      userToSave = dao.save(userToSave);
    } else {
      // Step 3b. Create a new user account or return an existing one.
      if (user.isPresent()) {
        userToSave = user.get();
      } else {
        userToSave = new Member();
        userToSave.setName(displayName);
        userToSave = dao.save(userToSave);
      }
    }

    final Token token = AuthUtils.createToken(request.getRemoteHost(), userToSave.getId());
    return Response.ok().entity(token).build();
  }
}
