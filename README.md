# MSA Modern Platform  #

본 Repository는 MSA Modern Platform 작업을 위해 생성한 Repository로써 MSA Pattern 도출 및 Framework(Library)과정에서 발생하는 다양한 예제 및 개발 산출물 등을 형상 관리하기 위한 목적으로 관리한다.

## 사용 방법 ##

### 본 Repository의 상단의 Clone을 이용하여 로컬 환경으로 Clone을 하여 작업을 진행한다.

* Root 아래에 작업에 프로한 별도의 Project을 생성하여 추가하는 방식으로 Repository를 관리한다.
* 소스 코드 등에는 업무와 관련된 내용으로 git commit 시에는 comment을 추가(trello 정보 등)하면 해당 코드가 작성된 이유 등을 확인 및 관리하기에 유용하다.
* 소스 코드에 중요한 사항은 다른 사용자가 확인할 수 있도록 간략한 내용의 주석을 담으면 좋다.

## 코드 개발 규칙 ##

### 초기 소스 코드에 대한 작업은 기본 java을 기본으로 진행될 예정이다. Package는 대략 아래 방식으로 생성한다.

* Package 명(샘플 코드) : com.skcc.modern.example.pattern명
* Package 명(개발 코드) : com.skcc.modern.pattern명
* Dependency 도구 : Maven, Gradle 무관
* Spring Boot, Java 등은 최신 버젼을 기준으로 작성하며, 특별히 하위 버젼 기반으로 작성할 필요 없음
* Kubernetes 환경에 특징적으로 동작하는 경우, 해당 내용을 각 프로젝트의 README 등에 명시할 필요 있음
* README 파일은 초기에 작성이 어려운 경우, 나중에 추가로 작성해도 무관하나, 코드 또는 문서 어딘가에 대략적인 내용이 포함하는 것을 권장

## 디렉토리 구조 ##
```
/deploy-config                # 마이크로서비스 배포시에 사용되는 환경별 기본 base 설정 파일
/docs                         # 패턴별 설명 자료
/examples                     # 패턴별 프로젝트 단위의 예제 코드
/pattern                      # 패턴별 개발 코드
/references                   # 패턴 적용을 위한 추가 개발 요소(플랫폼 등) 및 참고 자료
/spring-modern-template       # 패턴 템플릿 코드(의존 관계 및 기본 설정 정보 적용)
```
