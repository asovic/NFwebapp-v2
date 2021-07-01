package si.asovic.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import si.asovic.backend.data.entity.OrderEntity;
import si.asovic.backend.mail.EmailServiceImpl;

import javax.persistence.PostPersist;

public class NewOrderListener {
	@Autowired
	EmailServiceImpl mailService;
	
	@PostPersist
	public void methodInvokedAfterPersist(OrderEntity oe) {
		String text = "Prispelo je novo naročilo od uporabnika: " + oe.getUsername() + ". Naročena količina: " + oe.getBottle().size();
		Thread mailSendingThread = new Thread("Mail Thread") {
			@Override
			public void run() {
				mailService.sendMessage("andrej.sovic@gmail.com", "NF: Novo naročilo", text);
			}
		};
		mailSendingThread.start();
	}
}
