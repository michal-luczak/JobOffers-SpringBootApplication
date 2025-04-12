package pl.luczak.michal.joboffersapp.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import pl.luczak.michal.joboffersapp.loginandsignup.UserIdDuplicationException;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOAdapterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDTOMapper userDTOMapper;

    @Mock
    private UserEntityMapper userEntityMapper;

    @InjectMocks
    private UserDAOAdapter userDAOAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_successfully_save_user_and_returns_user_id() {
        // GIVEN
        UserDTO userDTO = new UserDTO(1L, "username", "password");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userEntityMapper.map(userDTO))
                .thenReturn(userEntity);
        when(userRepository.save(userEntity))
                .thenReturn(userEntity);

        // WHEN
        Long userId = userDAOAdapter.save(userDTO);

        // THEN
        assertEquals(1L, userId);
        verify(userEntityMapper, times(1))
                .map(userDTO);
        verify(userRepository, times(1))
                .save(userEntity);
    }

    @Test
    void should_throw_UserIdDuplicationException() {
        // GIVEN
        UserDTO userDTO = new UserDTO(1L, "username", "password");
        UserEntity userEntity = new UserEntity();
        when(userEntityMapper.map(userDTO))
                .thenReturn(userEntity);
        when(userRepository.save(userEntity))
                .thenThrow(DataIntegrityViolationException.class);

        // WHEN && THEN
        assertThrows(
                UserIdDuplicationException.class,
                () -> userDAOAdapter.save(userDTO)
        );
        verify(userEntityMapper, times(1))
                .map(userDTO);
        verify(userRepository, times(1))
                .save(userEntity);
    }

    @Test
    void delete_SuccessfullyDeleted_ReturnsUserId() {
        // GIVEN
        UserDTO userDTO = new UserDTO(1L, "username", "password");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userEntityMapper.map(userDTO))
                .thenReturn(userEntity);

        // WHEN
        Long userId = userDAOAdapter.delete(userDTO);

        // THEN
        assertEquals(1L, userId);
        verify(userEntityMapper, times(1))
                .map(userDTO);
        verify(userRepository, times(1))
                .delete(userEntity);
    }

    @Test
    void delete_UserNotFound_ThrowsException() {
        // GIVEN
        UserDTO userDTO = new UserDTO(1L, "username", "password");
        UserEntity userEntity = new UserEntity();
        when(userEntityMapper.map(userDTO)).thenReturn(userEntity);

        // WHEN & THEN
        assertThrows(Exception.class, () -> userDAOAdapter.delete(userDTO));
        verify(userEntityMapper, times(1))
                .map(userDTO);
        verify(userRepository, times(1))
                .delete(userEntity);
    }

    @Test
    void findByUsername_UserExists_ReturnsOptionalUserDTO() {
        // GIVEN
        String username = "testuser";
        UserEntity userEntity = new UserEntity();
        UserDTO userDTO = new UserDTO(1L, "testuser", "password");
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(userEntity));
        when(userDTOMapper.apply(userEntity))
                .thenReturn(userDTO);

        // WHEN
        Optional<UserDTO> result = userDAOAdapter.findByUsername(username);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(userDTO, result.get());
        verify(userRepository, times(1))
                .findByUsername(username);
        verify(userDTOMapper, times(1))
                .apply(userEntity);
    }

    @Test
    void findByUsername_UserNotExists_ReturnsEmptyOptional() {
        // GIVEN
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        // WHEN
        Optional<UserDTO> result = userDAOAdapter.findByUsername(username);

        // THEN
        assertFalse(result.isPresent());
        verify(userRepository, times(1))
                .findByUsername(username);
        verify(userDTOMapper, never()).map(any());
    }
}