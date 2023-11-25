# Redis HttpSession Sample
`HttpSession`を使ってRedisにセッション情報を保存し操作するサンプル。
依存関係を追加して`HttpSession`を使うと勝手に`Redis`にセッション情報が保存されるようになる。
※検証しやすいように全部GETリクエストにしている

```
登録：/session/set?data=aaaaa
取得：/session/get
削除：/session/delete
```

## 参考
【Eclipse】SpringBootでsessionを操作する
https://qiita.com/EasyCoder/items/78aea71564987faee7f3
 
Spring BootでHTTPセッションをあつかう3つのパターン
https://www.memory-lovers.blog/entry/2018/02/04/171013

Spring Session - Spring Boot（公式）
https://spring.pleiades.io/spring-session/reference/guides/boot-redis.html

SpringBootでredisでセッション管理をする
https://springhack.com/springboot%E3%81%A7redis%E3%81%A7%E3%82%BB%E3%83%83%E3%82%B7%E3%83%A7%E3%83%B3%E7%AE%A1%E7%90%86%E3%82%92%E3%81%99%E3%82%8B/



## Redis
### Redisの起動

```
docker run --name redis -p 6379:6379 -d redis
```

### 登録されたセッション情報の確認

キー名は`spring:session:sessions:セッションID`となる。
ブラウザから初回リクエストを行うと`Spring Session`がセッションIDを生成し、セッションID(`SESSION`)を`Base64`エンコードしたものをレスポンスヘッダの`Set-Cookie`に乗せて返す。（ChromeだとDeveloper Toolで確認できる）
次回以降はリクエストヘッダの`Cookie`にセッションID(`SESSION`)を乗せてリクエストする。

```
# redis-cli keys '*'
1) "spring:session:sessions:46a4b0ae-98f3-428d-88f6-024e65065c6e"

# redis-cli hgetall "spring:session:sessions:46a4b0ae-98f3-428d-88f6-024e65065c6e"
1) "lastAccessedTime"
2) "\xac\xed\x00\x05sr\x00\x0ejava.lang.Long;\x8b\xe4\x90\xcc\x8f#\xdf\x02\x00\x01J\x00\x05valuexr\x00\x10java.lang.Number\x86\xac\x95\x1d\x0b\x94\xe0\x8b\x02\x00\x00xp\x00\x00\x01\x8b\xf0\x98\xf9\xaa"
3) "sessionAttr:data"
4) "\xac\xed\x00\x05t\x00\bXXXXXXXX"
5) "creationTime"
6) "\xac\xed\x00\x05sr\x00\x0ejava.lang.Long;\x8b\xe4\x90\xcc\x8f#\xdf\x02\x00\x01J\x00\x05valuexr\x00\x10java.lang.Number\x86\xac\x95\x1d\x0b\x94\xe0\x8b\x02\x00\x00xp\x00\x00\x01\x8b\xf0\x8a7]"
7) "maxInactiveInterval"
8) "\xac\xed\x00\x05sr\x00\x11java.lang.Integer\x12\xe2\xa0\xa4\xf7\x81\x878\x02\x00\x01I\x00\x05valuexr\x00\x10java.lang.Number\x86\xac\x95\x1d\x0b\x94\xe0\x8b\x02\x00\x00xp\x00\x00\a\b"
```

### Redisから全セッションを削除
```
redis-cli keys '*' | xargs redis-cli del
```