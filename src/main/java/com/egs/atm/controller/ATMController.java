//package com.egs.atm.controller;
//
//import com.egs.atm.model.EGSResponse;
//import com.egs.atm.model.JwtResponse;
//import com.egs.atm.model.LoginRequest;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestController
//@RequestMapping("/api/atm")
//public class ATMController {
//
//    private final RestTemplate restTemplate;
//
//    public ATMController(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @PostMapping(value = "/card/identify")
//    EGSResponse<JwtResponse> identifyCard(String cardNo, String fingerprint) {
//
//        Object response = restTemplate.exchange("http://localhost:8083/api/auth/signin",
//                HttpMethod.POST,
//                new HttpEntity(new LoginRequest(cardNo, fingerprint)),
//                EGSResponse.class).getBody().getResult();
//
//        return new EGSResponse(response, HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/check-pin/{pin}")
//    EGSResponse<JwtResponse> checkPinValidity(@PathVariable Long pin, HttpServletRequest request) {
//
//        Object response = restTemplate.exchange("http://localhost:8083/api/auth/check-pin/" + pin,
//                HttpMethod.GET,
//                new HttpEntity(getHeaders(request)),
//                EGSResponse.class).getBody().getResult();
//
//        return new EGSResponse(response, HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/logout")
//    EGSResponse<JwtResponse> identifyCard(HttpServletRequest request) {
//
//        Object response = restTemplate.exchange("http://localhost:8083/api/auth/logout",
//                HttpMethod.POST,
//                new HttpEntity(getHeaders(request)),
//                EGSResponse.class).getBody().getResult();
//
//        return new EGSResponse(response, HttpStatus.OK);
//    }
//
//    private HttpHeaders getHeaders(HttpServletRequest request) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", request.getHeader("Authorization"));
//
//        return headers;
//    }
//}
