package com.zanar.hotelbooking.service.impl;

import com.zanar.hotelbooking.dto.LoginRequest;
import com.zanar.hotelbooking.dto.Response;
import com.zanar.hotelbooking.dto.UserDTO;
import com.zanar.hotelbooking.entity.User;
import com.zanar.hotelbooking.exception.OurException;
import com.zanar.hotelbooking.repo.UserRepository;
import com.zanar.hotelbooking.service.interf.IUserService;
import com.zanar.hotelbooking.utils.JWTUtils;
import com.zanar.hotelbooking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
    public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public Response register(User user) {
        Response response = new Response();
        try{
            if (user.getRole() == null || user.getRole().isBlank()){
                user.setRole("USER");
            }
            if (userRepository.existsByEmail(user.getEmail())){
               throw new OurException(user.getEmail() + "Already Exist");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);
            response.setStatusCode(200);
            response.setUser(userDTO);


        } catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){ response.setStatusCode(500);
            response.setMessage("Error Occurred" + e.getMessage());

        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("User Not Found"));

            var token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
            response.setMessage("Success");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error Occurred " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        return null;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        return null;
    }

    @Override
    public Response deleteUser(String userId) {
        return null;
    }

    @Override
    public Response getUserById(String userId) {
        return null;
    }

    @Override
    public Response getMyInfo(String email) {
        return null;
    }
}
