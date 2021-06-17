package server.restservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import server.restservice.repository.EmployeeRepository;

@Service
@AllArgsConstructor
public class AuctionScheduler {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 0 20 * * 6")
    public void scheduleFixedDelayTask() {
        mailService.notifyAboutAssignment(employeeRepository.execAuction());
    }

}
