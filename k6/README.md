# k6による負荷試験
## 事前準備
メトリクスデータを格納するための`InfluxDB`と`Grafana`をデプロイ

```
cd k6
docker compose up influxdb grafana -d
```

ダッシュボード
https://grafana.com/grafana/dashboards/14801-k6-dashboard/


## シナリオ実行

```
docker compose run --rm -T k6 run - < ./scripts/redis_session_close.js
```