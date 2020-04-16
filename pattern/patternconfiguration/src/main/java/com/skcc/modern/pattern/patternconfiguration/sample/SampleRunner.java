package com.skcc.modern.pattern.patternconfiguration.sample;

// import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRunner implements ApplicationRunner {

    // @Value("${com.skcc.modern.pattern.environmentpostprocessor.gross.calculation.tax.rate}")
    // double taxRate = 1;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // System.out.println("Tax Rate : " + taxRate);
        // TODO : 설정 로딩된 값을 확인하고 싶은 경우 간다히 ApplicationRunner를 작성하여 확인 가능
    }

}