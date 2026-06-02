package com.localbuddy.contact;

import com.localbuddy.ratelimit.ClientIpResolver;
import com.localbuddy.ratelimit.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/contact-us")
public class PublicContactUsController {

    private final ContactUsService contactUsService;
    private final RateLimitService rateLimitService;
    private final ClientIpResolver clientIpResolver;

    public PublicContactUsController(ContactUsService contactUsService,
                                     RateLimitService rateLimitService,
                                     ClientIpResolver clientIpResolver) {
        this.contactUsService = contactUsService;
        this.rateLimitService = rateLimitService;
        this.clientIpResolver = clientIpResolver;
    }

    @PostMapping
    public ResponseEntity<ContactUsResponse> submitContactUs(
            HttpServletRequest servletRequest,
            @Valid @RequestBody ContactUsRequest request
    ) {
        String clientIp = clientIpResolver.resolveClientIp(servletRequest);

        rateLimitService.checkPublicApiLimit("contact-us:" + clientIp);

        ContactUsResponse response = contactUsService.submitContactUs(
                request,
                clientIp,
                servletRequest.getHeader("User-Agent")
        );

        return ResponseEntity.ok(response);
    }
}