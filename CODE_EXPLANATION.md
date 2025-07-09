# Spring Boot自動販売機 - コード解説（初学者向け）

## 概要

このドキュメントでは、TDDで実装したSpring Boot自動販売機アプリケーションのコードを初学者向けに詳しく解説します。

## 🏗️ アーキテクチャ概要

```
HTTP Request → Controller → Domain → DTO → HTTP Response
```

- **Controller**: HTTPリクエストを受け取り、レスポンスを返す
- **Domain**: ビジネスロジック（自動販売機の処理）
- **DTO**: データの受け渡し用オブジェクト

## 📝 クラス別コード解説

### 1. VendingMachineController.java（コントローラー層）

```java
package dev.training.vendingmachine.controller;

import dev.training.vendingmachine.domain.VendingMachine;
import dev.training.vendingmachine.dto.PurchaseRequest;
import dev.training.vendingmachine.dto.PurchaseResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class VendingMachineController {

    private final VendingMachine vendingMachine;

    public VendingMachineController() {
        this.vendingMachine = new VendingMachine();
    }

    @PostMapping("/purchase")
    public PurchaseResponse purchase(@RequestBody PurchaseRequest request) {
        String product = vendingMachine.purchase(request.getMoney());
        return new PurchaseResponse(product, 1);
    }
}
```

#### 🔍 詳細解説

##### パッケージとインポート
```java
package dev.training.vendingmachine.controller;
```
- このクラスが所属するパッケージを宣言
- パッケージはJavaでクラスを整理するための仕組み

```java
import dev.training.vendingmachine.domain.VendingMachine;
import dev.training.vendingmachine.dto.PurchaseRequest;
import dev.training.vendingmachine.dto.PurchaseResponse;
import org.springframework.web.bind.annotation.*;
```
- 他のクラスや外部ライブラリをインポート
- `import`により、フルパスを書かずにクラス名だけで使用可能

##### アノテーション

**@RestController**
- **用途**: このクラスがREST APIのコントローラーであることを示す
- **効果**: Spring Bootが自動的にHTTPリクエストをこのクラスにルーティング
- **公式ドキュメント**: [Spring Boot Reference - Web](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.servlet.spring-mvc)

**@RequestMapping("/api")**
- **用途**: このコントローラーのベースURLを設定
- **効果**: 全てのメソッドのURLの前に`/api`が付く
- **公式ドキュメント**: [Spring Framework Reference - Request Mapping](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-requestmapping)

**@PostMapping("/purchase")**
- **用途**: HTTPのPOSTメソッドで`/purchase`にアクセスした時の処理を定義
- **効果**: `POST /api/purchase`リクエストがこのメソッドで処理される
- **公式ドキュメント**: [Spring Framework Reference - HTTP Method](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-methods)

**@RequestBody**
- **用途**: HTTPリクエストのJSONボディをJavaオブジェクトに変換
- **効果**: 受信したJSONが自動的に`PurchaseRequest`オブジェクトになる
- **公式ドキュメント**: [Spring Framework Reference - Request Body](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-requestbody)

##### クラス設計
```java
private final VendingMachine vendingMachine;

public VendingMachineController() {
    this.vendingMachine = new VendingMachine();
}
```
- **`private final`**: このクラス内でのみ使用可能で、変更不可
- **コンストラクタ**: クラスのインスタンス作成時に実行される
- **依存関係**: ControllerがDomainクラスを使用する構造

##### メソッド処理
```java
public PurchaseResponse purchase(@RequestBody PurchaseRequest request) {
    String product = vendingMachine.purchase(request.getMoney());
    return new PurchaseResponse(product, 1);
}
```
1. リクエストから金額を取得
2. ドメインクラスで購入処理実行
3. 結果をレスポンスオブジェクトにして返却

### 2. VendingMachine.java（ドメイン層）

```java
package dev.training.vendingmachine.domain;

public class VendingMachine {
    
    public String purchase(int money) {
        // 100円でコーラを返す最小限の実装
        return "コーラ";
    }
}
```

#### 🔍 詳細解説

##### 役割
- **ビジネスロジック**: 自動販売機の動作ルールを実装
- **単純明快**: HTTPやデータベースなどの技術的詳細から分離
- **テスト容易**: 外部依存がないため単体テストが簡単

##### 実装方針（TDD）
- **現在**: 最小限の実装（常に"コーラ"を返す）
- **理由**: テストを通すための最小コード（TDDのGreenステップ）
- **今後**: 新しいテストケース追加に合わせて段階的に拡張

### 3. DTOクラス（データ転送層）

#### PurchaseRequest.java
```java
package dev.training.vendingmachine.dto;

public class PurchaseRequest {
    private int money;

    public PurchaseRequest() {
    }

    public PurchaseRequest(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
```

#### PurchaseResponse.java
```java
package dev.training.vendingmachine.dto;

public class PurchaseResponse {
    private String product;
    private int quantity;

    public PurchaseResponse() {
    }

    public PurchaseResponse(String product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // getter/setter省略
}
```

#### 🔍 詳細解説

##### DTO（Data Transfer Object）とは
- **目的**: データの受け渡し専用オブジェクト
- **特徴**: ロジックを持たず、データの入れ物として機能
- **利点**: APIの入出力形式を明確に定義

##### 設計パターン
- **デフォルトコンストラクタ**: JSONシリアライゼーションで必要
- **パラメータ付きコンストラクタ**: テストやコード内での作成用
- **getter/setter**: JSONとJavaオブジェクト間の変換で使用

## 🧪 テストクラス解説

### 1. VendingMachineTest.java（ドメインテスト）

```java
package dev.training.vendingmachine.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTest {

    private VendingMachine vendingMachine;

    @BeforeEach
    void setUp() {
        vendingMachine = new VendingMachine();
    }

    @Test
    void _100円を入れるとコーラが1本出てくる() {
        // When
        String result = vendingMachine.purchase(100);
        
        // Then
        assertEquals("コーラ", result);
    }
}
```

#### 🔍 詳細解説

##### テストアノテーション

**@BeforeEach**
- **用途**: 各テストメソッド実行前に必ず実行される
- **効果**: テストの前準備（セットアップ）を行う
- **公式ドキュメント**: [JUnit 5 User Guide - Annotations](https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations)

**@Test**
- **用途**: このメソッドがテストケースであることを示す
- **効果**: JUnitがテスト実行時にこのメソッドを自動的に実行
- **公式ドキュメント**: [JUnit 5 User Guide - Test Classes](https://junit.org/junit5/docs/current/user-guide/#writing-tests-classes-and-methods)

##### テスト構造（AAA パターン）
```java
// When (実行)
String result = vendingMachine.purchase(100);

// Then (検証)
assertEquals("コーラ", result);
```
- **Arrange**: セットアップ（@BeforeEachで実施）
- **Act**: 実際の処理実行
- **Assert**: 結果の検証

##### アサーション
**assertEquals(expected, actual)**
- **用途**: 期待値と実際の値が等しいかチェック
- **引数**: `assertEquals(期待値, 実際の値)`
- **公式ドキュメント**: [JUnit 5 User Guide - Assertions](https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions)

### 2. VendingMachineControllerTest.java（統合テスト）

```java
package dev.training.vendingmachine.controller;

import dev.training.vendingmachine.dto.PurchaseRequest;
import dev.training.vendingmachine.dto.PurchaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VendingMachineController.class)
class VendingMachineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void _100円でコーラを購入できる() throws Exception {
        PurchaseRequest request = new PurchaseRequest(100);
        
        mockMvc.perform(post("/api/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("コーラ"))
                .andExpect(jsonPath("$.quantity").value(1));
    }
}
```

#### 🔍 詳細解説

##### 統合テストアノテーション

**@WebMvcTest(VendingMachineController.class)**
- **用途**: Web層（Controller）の統合テスト用設定
- **効果**: MockMvcを使ったHTTPリクエスト/レスポンステストが可能
- **範囲**: 指定したControllerクラスのみテスト対象
- **公式ドキュメント**: [Spring Boot Reference - Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.spring-mvc-tests)

**@Autowired**
- **用途**: Springが管理するオブジェクトを自動注入
- **効果**: テストで使用するMockMvcやObjectMapperが自動で設定される
- **公式ドキュメント**: [Spring Framework Reference - Autowired](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-autowired-annotation)

##### テストツール

**MockMvc**
- **用途**: 実際のHTTPサーバーを起動せずにコントローラーをテスト
- **利点**: 高速で軽量なWebテスト
- **公式ドキュメント**: [Spring Framework Reference - MockMvc](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#spring-mvc-test-framework)

**ObjectMapper**
- **用途**: JavaオブジェクトとJSONの相互変換
- **使用例**: `objectMapper.writeValueAsString(request)` でオブジェクトをJSONに変換

##### テスト実行フロー
```java
mockMvc.perform(post("/api/purchase")                    // POSTリクエスト送信
        .contentType(MediaType.APPLICATION_JSON)         // Content-Type設定
        .content(objectMapper.writeValueAsString(request))) // リクエストボディ設定
        .andExpect(status().isOk())                      // HTTP 200 OKを期待
        .andExpect(jsonPath("$.product").value("コーラ"))  // レスポンスJSONの検証
        .andExpect(jsonPath("$.quantity").value(1));     // レスポンスJSONの検証
```

##### JSONPath
- **$.product**: JSONの"product"フィールドにアクセス
- **$.quantity**: JSONの"quantity"フィールドにアクセス
- **用途**: JSONレスポンスの特定フィールドを検証

## 🎯 TDD実装のポイント

### 1. テストファースト
```java
// 1. 最初にテストを書く（Red）
@Test
void _100円を入れるとコーラが1本出てくる() {
    String result = vendingMachine.purchase(100);
    assertEquals("コーラ", result);
}

// 2. テストを通す最小限のコードを書く（Green）
public String purchase(int money) {
    return "コーラ";  // 最小限の実装
}
```

### 2. テストケース名の工夫
- **日本語使用**: `_100円を入れるとコーラが1本出てくる`
- **アンダースコア**: メソッド名の先頭に数字が使えないため
- **説明的**: テストの内容が名前から理解できる

### 3. 段階的な実装
- **現在**: 常に"コーラ"を返す（1つのテストケースのみ）
- **次段階**: 新しいテストケース追加時に条件分岐を実装
- **最終形**: 複数商品、在庫管理、エラーハンドリング

## 📚 使用技術とリファレンス

### Spring Boot
- **公式サイト**: https://spring.io/projects/spring-boot
- **リファレンス**: https://docs.spring.io/spring-boot/docs/current/reference/html/

### Spring Framework
- **公式サイト**: https://spring.io/projects/spring-framework
- **リファレンス**: https://docs.spring.io/spring-framework/docs/current/reference/html/

### JUnit 5
- **公式サイト**: https://junit.org/junit5/
- **ユーザーガイド**: https://junit.org/junit5/docs/current/user-guide/

### Jackson（JSON処理）
- **公式サイト**: https://github.com/FasterXML/jackson
- **ドキュメント**: https://github.com/FasterXML/jackson-docs

## 🔄 次のステップ

### 新しいテストケース例
```java
@Test
void _50円を入れると何も出てこない() {
    String result = vendingMachine.purchase(50);
    assertNull(result);
}
```

### 対応する実装の変更
```java
public String purchase(int money) {
    if (money == 100) {
        return "コーラ";
    }
    return null;
}
```

このように、テストケースを追加するたびに実装を段階的に改善していくのがTDDの基本的な流れです。

## まとめ

このコードは以下の要素を学習できる構成になっています：

- **Spring Boot**: 現代的なJava Webアプリケーション開発
- **REST API**: HTTP通信によるAPIの設計と実装
- **TDD**: テスト駆動開発の実践
- **クリーンアーキテクチャ**: 層分離による保守しやすいコード設計
- **アノテーション**: Springフレームワークの活用方法

各クラスが明確な責務を持ち、テストしやすい構造になっているため、初学者がモダンなJava開発を学ぶのに適した教材となっています。
