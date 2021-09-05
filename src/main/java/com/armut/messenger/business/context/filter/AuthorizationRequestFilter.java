package com.armut.messenger.business.context.filter;

import com.armut.messenger.business.constant.MappingConstants;
import com.armut.messenger.business.constant.ProjectConstants;
import com.armut.messenger.business.constant.SecurityConstants;
import com.armut.messenger.business.exception.APIException;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.repository.UserJPARepository;
import com.armut.messenger.presentation.api.dto.APIErrorResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class AuthorizationRequestFilter implements Filter {

    private final UserJPARepository userJPARepository;


    private static final List<String> GLOBAL_PERMIT_PATTERNS = Arrays.asList(
            MappingConstants.USER_CONTROLLER_PATH + "/**");

    public AuthorizationRequestFilter(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        log.info("doFilter is running...");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        try {
            if (shouldFilter(httpServletRequest)) {
                final String authorization = httpServletRequest.getHeader(SecurityConstants.DEFAULT_HEADER_TOKEN_KEY);
                if(authorization != null && authorization.startsWith(SecurityConstants.DEFAULT_HEADER_TOKEN_VALUE_PREFIX)){
                    String[] arrOfStr = authorization.split(" ");
                    String userToken = arrOfStr[1];
                    User user = userJPARepository.findByToken(userToken);
                    if (!Objects.isNull(user) && !user.getToken().isEmpty()){
                        if (user.getTokenExpiryDate().isAfter(LocalDateTime.now())){
                            httpServletRequest.setAttribute(ProjectConstants.HEADER_ATTRIBUTE_AUTH_USER,user);
                            filterChain.doFilter(servletRequest, servletResponse);
                            log.info("doFilter: Requested URI: " + ((HttpServletRequest) servletRequest).getRequestURI());
                            log.info("doFilter: User is auth. UserID: " + user.getId());
                            log.info("doFilter is ending...");
                            return;
                        }
                        else{
                            log.error("doFilter: Auth Error - Token Expiry: " + authorization);
                            APIErrorResponseDTO apiErrorResponse = new APIErrorResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_ERROR,
                                    HttpStatus.UNAUTHORIZED, "Token Expiry");
                            responseError(httpServletResponse,apiErrorResponse);
                        }
                    }
                    else{
                        log.error("doFilter: Auth Error - Token Not Found: " + authorization);
                        APIErrorResponseDTO apiErrorResponse = new APIErrorResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_ERROR,
                                HttpStatus.UNAUTHORIZED, "Token Not Found");
                        responseError(httpServletResponse,apiErrorResponse);
                    }
                }
                else{
                    log.error("doFilter: Auth Error - Invalid Token: " + authorization);
                    APIErrorResponseDTO apiErrorResponse = new APIErrorResponseDTO<>(ProjectConstants.API_RESPONSE_STATUS_ERROR,
                            HttpStatus.UNAUTHORIZED, "Invalid Token");
                    responseError(httpServletResponse,apiErrorResponse);
                }
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
                log.info("doFilter: Requested URI: " + ((HttpServletRequest) servletRequest).getRequestURI());
                log.info("doFilter is ending...");
                return;
            }
        } catch (Exception e) {
            log.error("doFilter: Auth Error " + e.getMessage());
            throw new APIException("GO LOGIN PAGE", HttpStatus.PERMANENT_REDIRECT,"");
        }
    }

    @Override
    public void destroy() {

    }

    public boolean shouldFilter(HttpServletRequest httpServletRequest) {
        return GLOBAL_PERMIT_PATTERNS.stream()
                .noneMatch(e -> new AntPathMatcher().match(e, httpServletRequest.getRequestURI()));
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    private void responseError(HttpServletResponse httpServletResponse, APIErrorResponseDTO apiErrorResponse ) throws IOException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(apiErrorResponse.getHttpStatus());
        httpServletResponse.getWriter().write(convertObjectToJson(apiErrorResponse));
    }
}
