package com.lolucode.morphine;

import com.lolucode.morphine.appuser.AppUser;
import com.lolucode.morphine.appuser.AppUserRepository;
import com.lolucode.morphine.reservation.model.AmenityType;
import com.lolucode.morphine.reservation.repos.CapacityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MorphineApplication {

	private Map<AmenityType, Integer> initialCapacities =
			new HashMap<>() {
				{
					put(AmenityType.GYM, 20);
					put(AmenityType.POOL, 4);
					put(AmenityType.SAUNA, 1);
				}
			};

	public static void main(String[] args) {
		SpringApplication.run(MorphineApplication.class, args);
	}

}
