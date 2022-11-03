package com.midas.midashackathon.domain.wallet.service;

import de.brendamour.jpasskit.PKBarcode;
import de.brendamour.jpasskit.PKField;
import de.brendamour.jpasskit.PKPass;
import de.brendamour.jpasskit.enums.PKBarcodeFormat;
import de.brendamour.jpasskit.enums.PKPassType;
import de.brendamour.jpasskit.passes.PKGenericPass;
import de.brendamour.jpasskit.signing.*;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

@Service
public class AppleWalletService {
    public byte[] getPasskit() {
        PKPass pass = PKPass.builder()
                .pass(
                        PKGenericPass.builder()
                                .passType(PKPassType.PKStoreCard)
                                .primaryFieldBuilder(
                                        PKField.builder()
                                                .key("balance")
                                                .label("balance")
                                                .value(20.0)
                                                .currencyCode("KRW")
                                )
                )
                .barcodeBuilder(
                        PKBarcode.builder()
                                .format(PKBarcodeFormat.PKBarcodeFormatQR)
                                .message("ABCDEFG")
                                .messageEncoding(Charset.forName("utf-8"))
                )
                .formatVersion(1)
                .passTypeIdentifier("pass.com.midas.it")
                .serialNumber("000000001")
                .teamIdentifier("MIDASIT")
                .organizationName("MIDASIT")
                .logoText("MyPass")
                .description("My PassBook")
                .backgroundColor(Color.BLACK)
                .foregroundColor("rgb(255,255,255)")
                .build();

        StringBuilder key = new StringBuilder();
        Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("certs/myKey.pem"));
        while(scanner.hasNextLine()) {
            key.append(scanner.nextLine());
        }
        System.out.println(key);

        try {
            PKSigningInformation pkSigningInformation = new PKSigningInformationUtil().loadSigningInformationFromPKCS12AndIntermediateCertificate(getClass().getClassLoader().getResourceAsStream("certs/keyStore.p12"),
                    "1234",
                    getClass().getClassLoader().getResourceAsStream("certs/AppleWWDRCA.cer"));
            new File("./pass").mkdirs();
            PKPassTemplateInMemory passTemplate = new PKPassTemplateInMemory();
            passTemplate.addFile(PKPassTemplateInMemory.PK_BACKGROUND, getClass().getClassLoader().getResourceAsStream("wallet_resources/icon.png"));
            passTemplate.addFile(PKPassTemplateInMemory.PK_BACKGROUND_RETINA, getClass().getClassLoader().getResourceAsStream("wallet_resources/icon.png"));
            passTemplate.addFile(PKPassTemplateInMemory.PK_ICON, getClass().getClassLoader().getResourceAsStream("wallet_resources/icon.png"));
            passTemplate.addFile(PKPassTemplateInMemory.PK_ICON_RETINA, getClass().getClassLoader().getResourceAsStream("wallet_resources/icon.png"));

            PKFileBasedSigningUtil pkSigningUtil = new PKFileBasedSigningUtil();

            byte[] signedAndZippedPkPassArchive = pkSigningUtil.createSignedAndZippedPkPassArchive(pass, passTemplate, pkSigningInformation);
            return signedAndZippedPkPassArchive;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException();
        }
    }
}
