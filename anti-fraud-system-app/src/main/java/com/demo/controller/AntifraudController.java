package com.demo.controller;

import com.demo.entity.Ip;
import com.demo.entity.StolenCard;
import com.demo.entity.UserAccount;
import com.demo.pojo.CreateIpRequest;
import com.demo.pojo.CreateIpResponse;
import com.demo.pojo.CreateStolenCardRequest;
import com.demo.pojo.CreateStolenCardResponse;
import com.demo.pojo.DeleteIpResponse;
import com.demo.pojo.Transaction;
import com.demo.pojo.TransactionStatusResponse;
import com.demo.pojo.TransactionType;
import com.demo.service.IpService;
import com.demo.service.StolenCardService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AntifraudController {
    private final IpService ipService;
    private final StolenCardService stolenCardService;

    AntifraudController(IpService ipService, StolenCardService stolenCardService) {
        this.ipService = ipService;
        this.stolenCardService = stolenCardService;
    }

    @PostMapping("/api/antifraud/transaction")
    public ResponseEntity<TransactionStatusResponse> verifyTransaction(@RequestBody Transaction transaction) {
        TransactionStatusResponse resp = new TransactionStatusResponse(TransactionType.PROHIBITED);

        if (transaction.getAmount() < 0) {
            return ResponseEntity.badRequest().build();
        }

        if (transaction.getAmount() <= 200) {
            resp = new TransactionStatusResponse(TransactionType.ALLOWED);
        } else if (transaction.getAmount() > 200 && transaction.getAmount() <= 1500) {
            resp = new TransactionStatusResponse(TransactionType.MANUAL_PROCESSING);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resp);
    }

    @PostMapping("/api/antifraud/stolen-card")
    public ResponseEntity<CreateStolenCardResponse> saveStolenCard(@RequestBody CreateStolenCardRequest stolenCardRequest) {
        StolenCard stolenCard = this.stolenCardService.saveNumber(stolenCardRequest.number());
        if (stolenCard == null) {
            return ResponseEntity.status(409).build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateStolenCardResponse(stolenCard.getId(), stolenCard.getNumber()));
    }

    @GetMapping(path = "/api/antifraud/stolen-card")
    public ResponseEntity<List<StolenCard>> readAllCards() {
        List<StolenCard> stolenCardList = this.stolenCardService.readAll();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stolenCardList);
    }

    @GetMapping(path = "/api/antifraud/suspicious-ip")
    public ResponseEntity<List<Ip>> readAllIps() {
        List<Ip> ipsList = this.ipService.getAllIps();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ipsList);
    }

    @PostMapping("/api/antifraud/suspicious-ip")
    public ResponseEntity<CreateIpResponse> saveSuspiciousIp(@RequestBody CreateIpRequest ipRequest) {
        Ip ip = this.ipService.createNewIp(ipRequest.ip());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateIpResponse(ip.getId(), ip.getValue()));
    }

    @DeleteMapping("/api/antifraud/suspicious-ip/{ip}")
    public ResponseEntity<DeleteIpResponse> deleteSuspiciousIp(@PathVariable String ip) {
        long id = this.ipService.deleteIp(ip);
        if (id == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new DeleteIpResponse("IP " + ip + " successfully removed!"));
    }




}
