package ait.cohort55.accounting.service;

import ait.cohort55.accounting.dao.UserAccountRepository;
import ait.cohort55.accounting.dto.RolesDto;
import ait.cohort55.accounting.dto.UserDto;
import ait.cohort55.accounting.dto.UserEditDto;
import ait.cohort55.accounting.dto.UserRegisterDto;
import ait.cohort55.accounting.dto.exception.UserExistsException;
import ait.cohort55.accounting.dto.exception.UserNotFoundException;
import ait.cohort55.accounting.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        if (userAccountRepository.existsById(userRegisterDto.getLogin())) {
            throw new UserExistsException();
        }
        UserAccount userAccount = modelMapper.map(userRegisterDto, UserAccount.class);
        String password = BCrypt.hashpw(userRegisterDto.getPassword(), BCrypt.gensalt());
        userAccount.setPassword(password);
        userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto getUser(String login) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto removeUser(String login) {
        // TODO HomeWork
        return null;
    }

    @Override
    public UserDto updateUser(String login, UserEditDto userEditDto) {
        // TODO HomeWork
        return null;
    }

    @Override
    public RolesDto changeRolesList(String login, String role, boolean isAddRole) {
        // TODO HomeWork
        return null;
    }

    @Override
    public void changePassword(String login, String newPassword) {

    }
}
