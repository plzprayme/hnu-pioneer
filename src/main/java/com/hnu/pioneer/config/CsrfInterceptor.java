package com.hnu.pioneer.config;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@Component
public class CsrfInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res,
                           Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("_csrf", new Mustache.Lambda(){
                public void execute(Template.Fragment frag, Writer out) throws IOException {
                    if ("token".equals(frag.execute())) {
                        out.write( ((CsrfToken) req.getAttribute(CsrfToken.class.getName()))
                                .getToken() );
                    }
                }
            });
        }

        super.postHandle(req, res, handler, modelAndView);
    }
}
