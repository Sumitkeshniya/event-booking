package com.eventbooking.user;

import com.eventbooking.exception.DuplicateEntryException;
import com.eventbooking.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public void registerUser(UserRegistrationDto userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new DuplicateEntryException("Email is already in use");
        }
        if (userRepository.findByContactNo(userRequest.getContactNo()).isPresent()) {
            throw new DuplicateEntryException("Mobile Number is already in use");
        }
        var user = modelMapper.map(userRequest,User.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void updateUser(Long id, User user){
        User user1 = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found."));
        user1.setContactNo(user.getContactNo());
        user1.setEmail(user.getEmail());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        userRepository.save(user1);
    }

    public User login(UserLoginRequest loginRequest) {
        String contactNo = loginRequest.getContactNo();
                contactNo = contactNo.startsWith("+") ? contactNo : "+"+contactNo;
        var user = userRepository.findByContactNo(contactNo)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return user;
    }
}
