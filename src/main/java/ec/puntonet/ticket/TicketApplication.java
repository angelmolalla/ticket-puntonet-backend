package ec.puntonet.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TicketApplication  extends SpringBootServletInitializer implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(TicketApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(TicketApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		 logger.info("Servidor inciado");
	}

}
