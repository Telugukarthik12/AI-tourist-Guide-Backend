package com.admin.service;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.admin.dto.AdminDto;
import com.admin.dto.CredentialsDto;
import com.admin.dto.SignUpDto;
import com.admin.entity.Admin;
import com.admin.exceptions.AppException;
import com.admin.mappers.AdminMapper;
import com.admin.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {

	private final AdminRepository adminRepo;

	private final PasswordEncoder passwordEncoder;

	private final AdminMapper adminMapper;

	public AdminDto login(CredentialsDto credentialsDto) {
		Admin admin = adminRepo.findByEmail(credentialsDto.email())
				.orElseThrow(() -> new AppException("Unknown admin", HttpStatus.NOT_FOUND));

		if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), admin.getPassword())) {
			return adminMapper.toAdminDto(admin);
		}
		throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
	}

	public AdminDto register(SignUpDto adminDto) {
		Optional<Admin> optionalAdmin = adminRepo.findByEmail(adminDto.email());

		if (optionalAdmin.isPresent()) {
			throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
		}

		Admin admin = adminMapper.signUpToAdmin(adminDto);
		admin.setPassword(passwordEncoder.encode(CharBuffer.wrap(adminDto.password())));

		Admin savedAdmin = adminRepo.save(admin);

		return adminMapper.toAdminDto(savedAdmin);
	}

	public AdminDto findByLoginEmail(String email) {
		Admin admin = adminRepo.findByEmail(email)
				.orElseThrow(() -> new AppException("Unknown Admin", HttpStatus.NOT_FOUND));
		return adminMapper.toAdminDto(admin);
	}

	// create user
	// view user by id
	// view users
	// update user

}
