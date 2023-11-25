# JDBC HttpSession Sample
`HttpSession`を使って`PostgreSQL`にセッション情報を保存し操作するサンプル。
依存関係を追加して`HttpSession`を使うと勝手に`PostgreSQL`にセッション情報が保存されるようになる。
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
https://spring.pleiades.io/spring-session/reference/guides/boot-jdbc.html

Spring BootアプリケーションのセッションをDBで管理する
https://iikanji.hatenablog.jp/entry/2021/10/31/201621

How to initialize schema in spring-session with JDBC
https://stackoverflow.com/questions/53823174/how-to-initialize-schema-in-spring-session-with-jdbc


## PostgreSQL
### PostgreSQLの起動

```
docker run --name postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
```

### PostgreSQLに接続

```
psql -d postgres -U postgres
```

### 登録されたセッション情報の確認
ブラウザから初回リクエストを行うと`Spring Session`がセッションIDを生成し、セッションID(`SESSION`)を`Base64`エンコードしたものをレスポンスヘッダの`Set-Cookie`に乗せて返す。（ChromeだとDeveloper Toolで確認できる）
次回以降はリクエストヘッダの`Cookie`にセッションID(`SESSION`)を乗せてリクエストする。
`session_id`がセッションIDに該当する。


セッション情報が格納されたテーブル一覧を表示
```
postgres=# \dt
                   List of relations
 Schema |           Name            | Type  |  Owner   
--------+---------------------------+-------+----------
 public | spring_session            | table | postgres
 public | spring_session_attributes | table | postgres
(2 rows)
```

セッション情報を確認
```
postgres=# select * from spring_session;
              primary_id              |              session_id              | creation_time | last_access_time | max_inactive_interval |  expiry_time  | principal_name 
--------------------------------------+--------------------------------------+---------------+------------------+-----------------------+---------------+----------------
 516baf94-c68a-473c-bf8d-2ecb228cb175 | 37d24e9a-0dc4-4c4f-93e6-e7a2ff7bdafa | 1700553170446 |    1700553404759 |                  1800 | 1700555204759 | 
(1 row)

postgres=# select * from spring_session_attributes;
          session_primary_id          | attribute_name |         attribute_bytes          
--------------------------------------+----------------+----------------------------------
 516baf94-c68a-473c-bf8d-2ecb228cb175 | data           | \xaced00057400085858585858585858
(1 row)
```

```
echo \xaced00057400085858585858585858 | xxd -r -p                                                                    (kind-kind-multi-ingress-1.28.0/default)
??XXXXXXXX
```

### DBから全セッションを削除

```
postgres=# TRUNCATE TABLE spring_session,spring_session_attributes;
TRUNCATE TABLE
```