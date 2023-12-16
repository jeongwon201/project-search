# project-search | 검색어 추천 서비스
> 코사인 유사도 기반 맞춤형 검색어 추천 서비스

<div>
  <img src="https://img.shields.io/badge/search-1.0.0 ver-blue"/>
  <img alt="GitHub License" src="https://img.shields.io/github/license/jeongwon201/project-search">
</div>
<br />
<br />

<div align="center">
  <image src="readme/images/search.gif" style="float: left; width: 500px; height: 300px;"/>
</div>
<br />

이 코사인 유사도 기반의 맞춤형 검색어 추천 서비스는 사용자들에게 최적화된 검색어를 제공하는 플랫폼으로 개발되었습니다. 
TF-IDF 기반의 코사인 유사도 알고리즘을 활용하여, 사용자가 입력한 검색어와 유사한 키워드를 추천하는 기능을 제공합니다. 
이를 통해 사용자들은 더 정확하고 관련성 높은 검색어를 받아들일 수 있으며, 원하는 정보를 빠르게 찾을 수 있게 되었습니다.  

또한, 서비스는 Scheduler를 활용하여 정기적으로 추천 검색어를 분석하고 업데이트합니다. 
이를 통해 시간이 지남에 따라 사용자들의 검색 패턴이나 관심사에 맞게 추천어가 업데이트되어 더욱 정확하고 신뢰할 수 있는 서비스를 제공합니다.  

더불어, Java와 Python의 유연한 통합 및 코드 상호 운용을 통해, 다양한 언어나 툴을 유연하게 활용할 수 있습니다. 
이는 향후 기능 추가나 서비스 확장에 있어서도 보다 확장성 높은 시스템을 제공할 수 있게 됩니다. 
이 웹 서비스는 사용자 중심의 효율적인 검색 경험을 제공하면서, 다양한 기술을 유연하게 결합하여 최상의 결과를 도출합니다.

## 시작하기

### 설치 방법

윈도우:
> Jdk 8 이상, Python 3.8(numpy, pandas, scikit-learn), Gradle, MySQL 이 필요합니다.
1. Git Repository 를 Clone 하세요.
> ```sh
> git clone https://github.com/jeongwon201/project-search.git
> ```
<br />

2. application.properties 파일을 자신의 환경에 맞게 수정하세요.
> 경로: ```src/main/resources/application.properties```
> ```yaml
> server.port=8000 # 8000 포트가 사용중이라면, 사용 중이지 않은 포트로 변경하세요.
> 
> #DB
> spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
> spring.datasource.url=jdbc:mysql://127.0.0.1:3306/search?useSSL=false
> spring.datasource.username={username} # 현재 사용중인 MySQL 사용자 username 으로 변경하세요.
> spring.datasource.password={password} # 현재 사용중인 MySQL 사용자 password 으로 변경하세요.
> 
> #JPA
> spring.jpa.database=mysql
> spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect
> spring.jpa.hibernate.ddl-auto=update
> spring.jpa.generate-ddl=true
> spring.jpa.show-sql=true
> spring.jpa.open-in-view=false
> ```
<br />

3. MySQL 에 데이터베이스를 추가하세요.
> sql 폴더의 ```search.sql``` 파일을 MySQL 에서 실행하여 데이터베이스를 준비합니다.
<br />

4. Gradle 을 이용하여 프로젝트를 빌드하세요.
> ```sh
> cd 경로/search
> gradle build
> ```
<br />

5. 서버를 오픈하세요.
> build/libs 폴더의 ```search-1.0.0.jar``` 파일을 원하는 디렉토리에 복사 후 다음 명령어를 이용하여 서버를 오픈하세요.
> 
> ```sh
> java -jar search-1.0.0.jar
> ```
> 
> ```CTRL + C``` 을 두 번 키다운하여 서버를 종료할 수 있습니다.  

### 사용 예제

#### 분석 주기 설정

```SearchApplication.java``` 의 38 라인 코드를 통해 추천 검색어 분석 주기를 설정할 수 있습니다.  
```java
@Scheduled(fixedRate = 100000) // fixedRate 를 변경하여 주기를 설정하세요.
public void initUpdate() throws JepException {
    this.update();
}
```

_더 많은 예제와 사용법은 다음 링크를 참고하세요._

- <a href="https://github.com/jeongwon201/project-search/blob/main/readme/usage.md" target="_blank">검색어 추천 서비스 사용 예제</a>

## 업데이트 내역

* 1.0.0
    * 첫 출시
* 0.0.1
    * 작업 진행 중

## 작성자
- 이정원 - jeongwon201@naver.com

## 라이센스

이 프로젝트는 MIT 라이센스를 준수하며 <a href="https://github.com/jeongwon201/project-search/blob/main/LICENSE" target="_blank">LICENSE</a>에서 자세한 정보를 확인할 수 있습니다.

## 기여 방법

1. (<https://github.com/jeongwon201/project-search/fork>)을 포크합니다.
2. (`git checkout -b feature/fooBar`) 명령어로 새 브랜치를 만드세요.
3. (`git commit -am 'Add some fooBar'`) 명령어로 커밋하세요.
4. (`git push origin feature/fooBar`) 명령어로 브랜치에 푸시하세요. 
5. 풀리퀘스트를 보내주세요.

<!-- Markdown link & img dfn's -->
[npm-image]: https://img.shields.io/npm/v/datadog-metrics.svg?style=flat-square
[npm-url]: https://npmjs.org/package/datadog-metrics
[npm-downloads]: https://img.shields.io/npm/dm/datadog-metrics.svg?style=flat-square
[travis-image]: https://img.shields.io/travis/dbader/node-datadog-metrics/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/dbader/node-datadog-metrics
[wiki]: https://github.com/yourname/yourproject/wiki
