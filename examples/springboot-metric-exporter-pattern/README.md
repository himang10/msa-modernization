# springboot-metric-exporter-pattern
springboot-metric-exporter-pattern

## sample 
GET /metrics 접근시 아래와 같은 샘플 Metric 출력을 볼 수 있다.
Metrics 유형(Counter, Gauge, Summary, Histogram, Labels)에 대한 예제는 추가할 예정이다.
```
# HELP my_gauge help
# TYPE my_gauge gauge
my_gauge 42.0
# HELP my_other_gauge help
# TYPE my_other_gauge gauge
my_other_gauge{labelname="foo",} 4.0
my_other_gauge{labelname="bar",} 5.0
```