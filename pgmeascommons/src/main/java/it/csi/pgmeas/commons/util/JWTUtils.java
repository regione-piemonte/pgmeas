/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTUtils {
	private static final Logger LOG = LoggerFactory.getLogger(JWTUtils.class);

	private static final String PRIVATE_KEY_PATH = "/chiavi/private.key";
	private static final String PUBLIC_KEY_PATH = "/chiavi/public.key";
	private static final String ALGORITHM = "RSA";
	private static final Integer KEY_SIZE = 2048;

	private static ObjectMapper objectMapper = new ObjectMapper();
	private static KeyFactory keyFactory;

	static {
		try {
			keyFactory = KeyFactory.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Error creating keyFactory: {}", e);
		}
	}

	private static byte[] getKey(String keyPath) throws IOException {
		String key = IOUtils.toString(JWTUtils.class.getResource(keyPath), StandardCharsets.UTF_8);
//		k = k.replace("-----BEGIN RSA PRIVATE KEY-----", "");
//		k = k.replace("-----END RSA PRIVATE KEY-----", "");
//		k = k.replace("-----BEGIN PUBLIC KEY-----", "");
//		k = k.replace("-----END PUBLIC KEY-----", "");
//		k = k.replaceAll("\n", "");
		return Base64.getDecoder().decode(key);
	}

	private static PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(getKey(PRIVATE_KEY_PATH));
		return keyFactory.generatePrivate(keySpecPKCS8);
	}

	private static PublicKey getPublicKey()
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
		X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(getKey(PUBLIC_KEY_PATH));
		return keyFactory.generatePublic(keySpecX509);
	}

	public static String doGenerateToken(Map<String, Object> claims, String subject, long jwtTokenValidity)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		Key privateKey = getPrivateKey();
		long nowmilli = System.currentTimeMillis();
		return Jwts.builder().setClaims(claims).setSubject(subject)//
				.setIssuedAt(new Date(nowmilli)) //
				.setExpiration(new Date(nowmilli + jwtTokenValidity)) //
				.signWith(SignatureAlgorithm.RS256, privateKey).compact();
	}

	public static Claims decodeJWT(String jwt) throws ExpiredJwtException, UnsupportedJwtException,
			MalformedJwtException, SignatureException, IllegalArgumentException, NoSuchAlgorithmException,
			InvalidKeySpecException, IOException, ClassNotFoundException, InvalidKeyException {
		// This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt).getBody();
		return claims;
	}

	public static <T> String doGenerateToken(String subject, T object, long jwtTokenValidity)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		Map<String, Object> claims = new LinkedHashMap<String, Object>();
		claims.put(object.getClass().getSimpleName(), object);
		return doGenerateToken(claims, subject, jwtTokenValidity);
	}

	public static <T> T getDataFromJWT(String jwt, Class<T> classObject)
			throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException,
			InvalidKeyException, IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException,
			ClassNotFoundException, IOException {
		Claims claims = decodeJWT(jwt);
		return objectMapper.convertValue(claims.get(classObject.getSimpleName()), classObject);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
		keyPairGenerator.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		String privatekey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
		String publickey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

		FileUtils.writeStringToFile(new File("src/main/resources/chiavi/private.key"), privatekey,
				Charset.defaultCharset());
		FileUtils.writeStringToFile(new File("src/main/resources/chiavi/public.key"), publickey,
				Charset.defaultCharset());
	}

}
