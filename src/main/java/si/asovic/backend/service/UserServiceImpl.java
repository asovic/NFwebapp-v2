//package si.asovic.backend.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import si.asovic.backend.data.entity.UserEntity;
//import si.asovic.backend.data.repository.RoleRepository;
//import si.asovic.backend.data.repository.UserRepository;
//
//import java.util.HashSet;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//	@Override
//	public void save(UserEntity user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setRole(new HashSet<>(roleRepository.userRole()));
//        userRepository.save(user);
//	}
//
//	@Override
//	public UserEntity findByUsername(String username) {
//		return userRepository.findByUsername(username);
//	}
//
//}
