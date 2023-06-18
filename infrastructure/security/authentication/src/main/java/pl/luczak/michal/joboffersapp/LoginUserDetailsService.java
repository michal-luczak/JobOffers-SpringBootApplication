package pl.luczak.michal.joboffersapp;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.luczak.michal.joboffersapp.ports.output.UserService;

@AllArgsConstructor
class LoginUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserDTOMapper userDTOMapper;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDTOMapper.fromUserDTO(
                userService.findByUsername(username)
        );
    }
}
