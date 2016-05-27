package lt.macrosoft.security;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import lt.macrosoft.utils.AuthUtils;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	
        // Get the HTTP Authorization header from the request
        String authorizationHeader = 
            requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly 
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
       
        try {
            // Validate the token
            if(!AuthUtils.validateToken(authorizationHeader))
            	throw new NotAuthorizedException("Token is not valid");
            requestContext.setSecurityContext(new SecurityContext() {

    	        @Override
    	        public Principal getUserPrincipal() {

    	            return new Principal() {

    	                @Override
    	                public String getName() {
    	                	try{
    	                		return AuthUtils.getSubject(authorizationHeader);
    	                	}catch(Exception e){
    	                		throw new NotAuthorizedException("Username not found for this token");
    	                	}
    	                }
    	            };
    	        }

    	        @Override
    	        public boolean isUserInRole(String role) {
    	            return true;
    	        }

    	        @Override
    	        public boolean isSecure() {
    	            return false;
    	        }

    	        @Override
    	        public String getAuthenticationScheme() {
    	            return null;
    	        }
    	    });
        } catch (Exception e) {
        	e.printStackTrace();
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

}