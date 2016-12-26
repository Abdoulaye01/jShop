package shop.main.auth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.service.UserService;
import shop.main.data.objects.UserRole;

@Service("authenticationDetailsService")
public class AuthenticationDetailsService implements UserDetailsService{

		@Autowired
		private UserService userService;

		@Transactional(readOnly=true)
		@Override
		public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {

			shop.main.data.objects.User user = userService.findByUserName(username);
			if(user == null){
				throw new UsernameNotFoundException("Can not find user: "+username);
			}
			List<GrantedAuthority> authorities =
	                                      buildUserAuthority(user.getUserRole());

			return buildUserForAuthentication(user, authorities);

		}

		// Converts shop.main.data.objects.User user to
		// org.springframework.security.core.userdetails.User
		private User buildUserForAuthentication(shop.main.data.objects.User user,
			List<GrantedAuthority> authorities) {
			return new User(user.getUserName(), user.getPassword(),
				user.getEnabled(), true, true, true, authorities);
		}

		private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

			Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

			// Build user's authorities
			for (UserRole userRole : userRoles) {
				setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
			}

			List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

			return Result;
		}

}