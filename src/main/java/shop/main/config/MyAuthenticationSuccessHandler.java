package shop.main.config;

import static shop.main.controller.admin.AdminController.ADMIN_PREFIX;
import static shop.main.controller.admin.AdminController.MANAGER_PREFIX;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import shop.main.utils.Constants;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	/**
	 * Builds the target URL according to the logic defined in the main class
	 * Javadoc.
	 */
	protected String determineTargetUrl(Authentication authentication) {
		boolean isUser = false;
		boolean isAdmin = false;
		boolean isManager = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			System.out.println("authority " + grantedAuthority.getAuthority());
			if (grantedAuthority.getAuthority().equals(Constants.RoleType.USER.name())) {
				isUser = true;
				break;
			} else if (grantedAuthority.getAuthority().equals(Constants.RoleType.MANAGER.name())) {
				isManager = true;
				break;
			} else if (grantedAuthority.getAuthority().equals(Constants.RoleType.ADMIN.name())) {
				isAdmin = true;
				break;
			}
		}

		if (isUser) {
			return "/user/cabinet";
		} else if (isAdmin) {
			return ADMIN_PREFIX + "welcome";
		} else if (isManager) {
			return MANAGER_PREFIX + "welcome";
		} else {
			return "/login";
		}
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

}