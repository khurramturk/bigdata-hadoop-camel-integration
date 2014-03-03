package com.ews.web.phaselisteners;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class controls the caching of web pages so that for each request a new
 * page is pulled and rendered as opposed to just fetching a stored page.
 * @author rana.ijaz
 */
public class CacheControlPhaseListener implements PhaseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8038589842136213763L;

	/**
     * Not in use.
     * @param event
     *            The phase event that triggered this listener
     */
    @Override
    public final void afterPhase(final PhaseEvent event) {
    }

    /**
     * Set all of the cache-controls to not store web pages and ensure that a
     * web page will expire.
     * @param event
     *            The phase event that triggered this listener
     */
     // CHECKSTYLE:OFF
    @Override
    public final void beforePhase(final PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        ExternalContext eContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        Map<String, Object> requestCookieMap = facesContext.getExternalContext().getRequestCookieMap();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (request.getMethod().equalsIgnoreCase("GET") && !request.getRequestURI().contains("login.xhtml")
            && !request.getRequestURI().contains("logout.xhtml")) {
            Cookie cookie;
            if (eContext.getSessionMap().get("TIME_DIFFERENCE") == null
                && requestCookieMap.get("CLIENT_CURRENT_TIME") != null) {
                cookie = (Cookie) requestCookieMap.get("CLIENT_CURRENT_TIME");
                String str_serverTime = String.valueOf(Calendar.getInstance().getTimeInMillis());
                String s1 = str_serverTime.substring(0, str_serverTime.length() - 3);
                String s2 = cookie.getValue().substring(str_serverTime.length() - 3);
                long clientTime = Long.parseLong(cookie.getValue());
                long serverTime = Long.parseLong(s1 + s2);
                long timeDifference = serverTime - clientTime;
                eContext.getSessionMap().put("TIME_DIFFERENCE", timeDifference);
            }
            long delayInSeconds = 0;
            if (requestCookieMap.get("PAGE_NAVIGATION_DELAY") != null) {
                cookie = (Cookie) requestCookieMap.get("PAGE_NAVIGATION_DELAY");
                long timeDifference = (Long) eContext.getSessionMap().get("TIME_DIFFERENCE");
                long cookieTime = Long.parseLong(cookie.getValue());
                long currentTime = Calendar.getInstance().getTimeInMillis();
                delayInSeconds = (currentTime - cookieTime - timeDifference) / 1000;
                if (delayInSeconds > 3) {
                    try {
                        response.sendRedirect(request.getContextPath() + "/logout.xhtml");
                    } catch (IOException e) {
                        System.out.println("Couldn't navigate to logout page");
                        e.printStackTrace();
                    }
                }
            }
        }
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "must-revalidate");
        response.addDateHeader("Expires", 0);
    }

    /**
     * Tells the listener that we want it to get triggered during the Render
     * Response part of the life-cycle.
     * @return The phaseId associated with this listener, which is
     *         RENDER_RESPONSE.
     */
    @Override
    public final PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
