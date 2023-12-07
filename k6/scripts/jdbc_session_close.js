import http from 'k6/http';
import { check, group } from 'k6';
import { Trend } from 'k6/metrics';

// 独自メトリクスの定義
const setSessionDuration = new Trend('http_req_set_session_duration',true);
const getSessionDuration = new Trend('http_req_get_session_duration',true);

export const options = {
  scenarios: {
    closed_model: {
      executor: 'constant-vus',
      vus: 50,
      duration: '300s',
    },
  },
};

export default function () {

  group('Set Session Data', () => {
  const res_set = http.get('http://jdbc-http-session.example.com/session/set?data=XXXXX');

  // セッション登録のレスポンスタイムをメトリクスに記録
  setSessionDuration.add(res_set.timings.duration);
  
  });

  group('Get Session Data', () => {
    const res_get = http.get('http://jdbc-http-session.example.com/session/get');

    // セッション取得のレスポンスタイムをメトリクスに記録
    getSessionDuration.add(res_get.timings.duration);

    // レスポンスボディに含まれる文字列を検証する
    // https://k6.io/docs/using-k6/checks/
    // console.log(`Response data: ${res.body}`);
    check(res_get, {
      'verify Session Data': (r) =>
        r.body.includes('data: XXXXX'),
    });
  });
};
