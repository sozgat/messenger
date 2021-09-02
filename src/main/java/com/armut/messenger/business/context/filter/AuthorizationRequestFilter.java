package com.armut.messenger.business.context.filter;

import com.armut.messenger.business.constant.MappingConstants;
import com.armut.messenger.business.constant.SecurityConstants;
import com.armut.messenger.business.exception.APIException;
import com.armut.messenger.business.model.User;
import com.armut.messenger.business.repository.UserJPARepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        try {
            if (shouldFilter(httpServletRequest)) {
                final String authorization = httpServletRequest.getHeader(SecurityConstants.DEFAULT_HEADER_TOKEN_KEY);
                if(authorization != null && authorization.startsWith(SecurityConstants.DEFAULT_HEADER_TOKEN_VALUE_PREFIX)){
                    String[] arrOfStr = authorization.split(" ");
                    String userToken = arrOfStr[1];
                    User user = userJPARepository.findByToken(userToken);
                    if (!user.getToken().isEmpty()){
                        if (user.getTokenExpiryDate().isAfter(LocalDateTime.now())){
                            filterChain.doFilter(servletRequest, servletResponse);
                            return;
                        }
                        else{
                            throw new APIException("Token Expiry", HttpStatus.UNAUTHORIZED);
                        }
                    }
                    else{
                        throw new APIException("Token Not Found", HttpStatus.UNAUTHORIZED);
                    }
                }
                else{
                    throw new APIException("Invalid Token", HttpStatus.UNAUTHORIZED);
                }
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        } catch (Exception e) {
            throw new APIException("GO LOGIN PAGE", HttpStatus.PERMANENT_REDIRECT);
        }
    }

    @Override
    public void destroy() {

    }

    public boolean shouldFilter(HttpServletRequest httpServletRequest) {
        return GLOBAL_PERMIT_PATTERNS.stream()
                .noneMatch(e -> new AntPathMatcher().match(e, httpServletRequest.getRequestURI()));
    }
}
