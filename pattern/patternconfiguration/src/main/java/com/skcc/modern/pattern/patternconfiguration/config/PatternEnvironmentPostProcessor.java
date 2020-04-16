package com.skcc.modern.pattern.patternconfiguration.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;

// 패턴에서 제공하는 설정 정보의 우선순위가 낮아야 사용자가 변경할 설정값으로 적용이 가능하여 최하우 우선 순위로 조정
// 단, 패턴에서 제공하는 설정이 절대 불변이라면 Ordered.HIGHEST_PRECEDENCE 값으로 변경 전략 필요
@Order(Ordered.LOWEST_PRECEDENCE)
public class PatternEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PatternEnvironmentPostProcessor.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        // K8S로 배포한 설정을 먼저 확인하고 로컬에 있는 설정을 확인하는 방식으로 구성
        try {
            // K8S 환경에서 Mount 시켜줘야 함
            // 1. /config/overlay/pattern-{pattern name}.yml 등으로 정책에 맞춰 작성
            Resource k8sPath = new FileUrlResource("/config/overlay/pattern.yml");

            // 패키지된 기본 설정값 : resources/pattern.yml
            Resource localPath = new ClassPathResource("pattern.yml");

            if (k8sPath.exists()) {
                addLast(environment, k8sPath);
            } else if (localPath.exists()) {
                addLast(environment, localPath);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void addLast(ConfigurableEnvironment environment, Resource resource) throws IOException {
        environment.getPropertySources().addLast(new PropertiesPropertySourceLoader().load("patternConfig", resource).get(0));
    }

}