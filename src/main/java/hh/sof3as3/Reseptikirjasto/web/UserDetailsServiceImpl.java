package hh.sof3as3.Reseptikirjasto.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hh.sof3as3.Reseptikirjasto.domain.User;
import hh.sof3as3.Reseptikirjasto.domain.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository repository;
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.repository = userRepository;
	}
	
	// paketoidaan projektin oman User-luokan oliot Springin User-luokan olioiksi
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User currentUser = repository.findByUsername(username);
		UserDetails user = 
				new org.springframework.security.core.userdetails.User(
						username, 
						currentUser.getPasswordHash(),
						AuthorityUtils.createAuthorityList(currentUser.getRole())
						);
		return user;
	}
	
}
