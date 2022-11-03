package com.midas.midashackathon.domain.wallet.presentation;

import com.midas.midashackathon.domain.wallet.service.AppleWalletService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/wallet")
@Controller
public class AppleWalletController {
    private final AppleWalletService appleWalletService;

    @RequestMapping(method = RequestMethod.GET)
    public void getPasskit(HttpServletRequest request, HttpServletResponse response) {
        byte[] pkpassFile = appleWalletService.getPasskit();

        // Prepare response to start download
        response.setStatus(200);
        response.setContentLength(pkpassFile.length);
        response.setContentType("application/vnd.apple.pkpass");
        response.setHeader("Content-Disposition","inline; filename=\"midas.pkpass\"");

        ByteArrayInputStream bais = new ByteArrayInputStream(pkpassFile);
        try {
            IOUtils.copy(bais, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
            // log the exception.
        }

    }
}
