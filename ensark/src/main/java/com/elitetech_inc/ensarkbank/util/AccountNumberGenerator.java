package com.elitetech_inc.ensarkbank.util;


import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component

public class AccountNumberGenerator {

     public String generateAccountNumber(Long branchId, AccountType type) {

         String prefix = "683";

         String typeCode = switch (type) {
             case SAVINGS -> "01";
             case CURRENT -> "02";
             case FIXED_DEPOSIT -> "03";
             case STUDENT -> "04";
             case JOINT_ACCOUNT -> "05";
             case BUSINESS -> "06";
             default -> "00";
         };
         String branchPart = String.format("%03d", branchId);
         String random = String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
         return prefix + branchPart + typeCode + random;

     }

     public String generateBranchAccountNumber(String routing, String prefix){
         return prefix + routing;
     }
}
