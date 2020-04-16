# 패턴별 예제 코드 #

## 패턴 목록 ##

## Kustomize 적용 규칙 ##

해당 규칙에 적용된 정책으로 Pattern 예제 코드에 적용합니다.
* https://github.com/cloudsvcdev/awesome-shopping-finished


```
/base                         # Deployment, Service, 앱 properties ConfigMap
/pattern                      # 마이크로서비스 패턴 적용 위한 공통 ConfigMap
       +/bin                  # .jar 같은 바이너리 리소스
       +/config               # 패턴 위한 properties 포함 ConfigMap 생성용
              +/dev           # dev 배포환경을 위한 ConfigMap 생성 kustomize
              +/prod          # prod 배포환경을 위한 ConfigMap 생성 kustomize
       +/{patternname}        # 마이크로서비스 패턴
/overlay                      # 마이크로서비스의 개별 형상정보
       +/awesome-sa           # 서비스어카운트와 Role과 같은 사전 배포 정보
                  +/dev       # dev 배포환경용
                  +/prod      # prod 배포 환경용
       +/awesome-{name}       # 마이크로서비스 kustomize 보관
                      +/dev   
                      +/prod  
       +/awesome-all          # 배포 편의성을 위한 전체 배포용 kustomize
                   +/dev
                   +/prod
```
