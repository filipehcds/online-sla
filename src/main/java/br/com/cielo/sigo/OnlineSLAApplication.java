package br.com.cielo.sigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Ponto de partida para subida da aplicação pelo Spring Boot
 * 
 * @author alexandre.oliveira
 * @since 28/03/2018
 *
 */
@EnableDiscoveryClient
@CrossOrigin(origins = "*")
@SpringBootApplication
public class OnlineSLAApplication {
	
	//TODO implementar log4j
	public static void main(String[] args) {
		SpringApplication.run(OnlineSLAApplication.class, args);
	}
	
}
