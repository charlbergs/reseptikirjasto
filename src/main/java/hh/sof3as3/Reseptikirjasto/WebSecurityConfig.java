package hh.sof3as3.Reseptikirjasto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import hh.sof3as3.Reseptikirjasto.web.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
				.requestMatchers("/recipelist", "/recipe/**", "/css/**").permitAll() // kaikille rooleille: reseptilistaus, yksittäisen reseptin näkymä, css jotta muotoilu näkyy
				.requestMatchers("/signup").anonymous() // vain kirjautumattomille: rekisteröitymislomake
				.requestMatchers("/categorylist", "/api/**").hasAuthority("ADMIN") // vain adminille: kategorioiden hallinta, autogeneroidut rest-metodit
				.requestMatchers(toH2Console()).permitAll() // h2-console
				.anyRequest().authenticated()
				.and()
				.csrf().ignoringRequestMatchers(toH2Console()) // h2-console
				  .and()
				  .headers().frameOptions().disable()
				  .and()
			.formLogin()
				.defaultSuccessUrl("/recipelist", true) // sisäänkirjautumisen jälkeen ohjataan reseptilistaussivulle
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/recipelist") // myös uloskirjautumisen jälkeen ohjataan reseptilistaussivulle
				.permitAll()
				.and()
			.httpBasic();
		return http.build();
	}
	
	// käyttää UserDetailsServiceImpl:ssä paketoitua User-oliota
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
