# 自動販売機 - Spring Bootバックエンド (TDD実装)

## 概要

テスト駆動開発（TDD）で実装したSpring Bootベースの自動販売機APIアプリケーションです。

## 機能

- ✅ **100円でコーラを購入** - 基本的な購入機能（在庫・エラー処理なし）

## 技術スタック

- **Java**: 17+
- **Spring Boot**: 3.2.0
- **Maven**: 3.9.9（Maven Wrapper使用）
- **テストフレームワーク**: JUnit 5
- **アーキテクチャ**: Controller → Domain → DTO

## プロジェクト構造

```
src/
├── main/java/dev/training/vendingmachine/
│   ├── VendingMachineApplication.java          # メインクラス
│   ├── controller/VendingMachineController.java # REST API
│   ├── domain/VendingMachine.java              # ドメインロジック
│   └── dto/                                    # データ転送オブジェクト
│       ├── PurchaseRequest.java
│       └── PurchaseResponse.java
└── test/java/dev/training/vendingmachine/
    ├── controller/VendingMachineControllerTest.java # API テスト
    └── domain/VendingMachineTest.java          # ドメインテスト
```

## API仕様

### 購入API

- **エンドポイント**: `POST /api/purchase`
- **リクエスト**: 
  ```json
  {
    "money": 100
  }
  ```
- **レスポンス**: 
  ```json
  {
    "product": "コーラ",
    "quantity": 1
  }
  ```

## 実行方法

### 前提条件

- Java 17以上がインストールされていること

### アプリケーション起動

```bash
# Maven Wrapperを使用（推奨）
./mvnw spring-boot:run

# または通常のMavenを使用
mvn spring-boot:run
```

アプリケーションは `http://localhost:8080` で起動します。

### テスト実行

```bash
# 全テスト実行
./mvnw test

# クリーンテスト
./mvnw clean test
```

### ビルド

```bash
# パッケージ作成
./mvnw clean package

# 成果物は target/vendingmachine-0.0.1-SNAPSHOT.jar に生成されます
```

## API使用例

```bash
# コーラ購入（100円）
curl -X POST http://localhost:8080/api/purchase \
  -H "Content-Type: application/json" \
  -d '{"money": 100}'

# レスポンス例
# {"product":"コーラ","quantity":1}
```

## TDD実装について

このプロジェクトは以下のTDDプロセスに従って実装されています：

1. **Red**: テストを書く（失敗する）
2. **Green**: テストを通す最小限のコードを書く
3. **Refactor**: コードをリファクタリングする

### テストリスト

進捗状況は [`TESTLIST.md`](./TESTLIST.md) で管理しています。

## 設定

### application.properties

- **ポート**: 8080
- **アプリケーション名**: vendingmachine
- **JSON設定**: NULL値は出力しない

## 開発環境

- **IDE**: Visual Studio Code推奨
- **パッケージ管理**: Maven Wrapper
- **バージョン管理**: Git

## 今後の拡張予定

- 複数商品対応
- 在庫管理
- お釣り計算
- エラーハンドリング
- データベース連携

## ライセンス

このプロジェクトは学習目的で作成されています。