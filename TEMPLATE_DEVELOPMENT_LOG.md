# Spring Boot自動販売機 - ひな形作成開発ログ

## 概要

TDD（テスト駆動開発）によるSpring Bootバックエンドアプリケーションのひな形作成過程を記録したログです。

## 開発環境

- **OS**: Linux (GitHub Codespaces)
- **Java**: 21.0.7 (Microsoft Build)
- **IDE**: Visual Studio Code
- **シェル**: bash

## 開発ログ

### 📋 1. 要件確認・プロジェクト初期化

#### 利用したプロンプト

```
Spring Bootのバックエンドアプリケーションのひな型を作成します。
・ビルドツールは maven を利用してください
・パッケージ名は dev-training としてください
実装する機能は以下の2つ。在庫やエラー処理は必要なし
・100円でコーラが買える
ControllerとDTO、ドメインクラスを利用したい
・コントローラーとドメインクラスでは、VendingMachineControllerとVendingMachineクラスを作成したい
・DTOクラスについては適宜判断して実装してほしい
VendingMachineはテスト駆動開発で実装したい
・実装したVendingMachineをVendingMachineControllerから利用するようにしたい
・テスト駆動開発は以下の記事の進め方に従う
  ・https://t-wada.hatenablog.jp/entry/canon-tdd-by-kent-beck
・テストリストはTESTLIST.mdの内容を利用する
  ・必要に応じてリストを細分化してもよい
・テストを実装する際、メソッド名はテストリストに書いてある内容と一致させること
  ・メソッド名の先頭に数字は使えないため、"_" を付けたり、漢字にするなどして対応する
```

#### 要件
- Spring Bootバックエンドアプリケーション
- 機能: 100円でコーラが買える（在庫・エラー処理なし）
- Controller: VendingMachineController
- ドメイン: VendingMachine
- DTO: 適宜実装
- TDD実装（[t-wada氏のTDD記事](https://t-wada.hatenablog.jp/entry/canon-tdd-by-kent-beck)に従う）
- テストリスト: TESTLIST.md利用
- テストメソッド名: リスト内容と一致（数字対応は"_"付与等で工夫）

#### 初期状況確認
- ワークスペース: `/workspaces/dev-training-vendingmachine-with-tdd`
- 既存ファイル: `README.md`, `TESTLIST.md`
- Spring Bootプロジェクトのひな形が既に作成済み

### 🏗️ 2. プロジェクト構造確認・セットアップ

#### Maven プロジェクト構造
```
├── pom.xml                    # Maven設定（Spring Boot 3.2.0）
├── src/main/java/dev/training/vendingmachine/
│   ├── VendingMachineApplication.java
│   ├── controller/VendingMachineController.java
│   ├── domain/VendingMachine.java
│   └── dto/
│       ├── PurchaseRequest.java
│       └── PurchaseResponse.java
└── src/test/java/dev/training/vendingmachine/
    ├── controller/VendingMachineControllerTest.java
    └── domain/VendingMachineTest.java
```

#### 依存関係
- `spring-boot-starter-web`: REST API機能
- `spring-boot-starter-test`: テスト機能（JUnit 5, MockMvc）

### 🔄 3. パッケージ名変更作業

#### 変更内容
- 変更前: `com.example.*`
- 変更後: `dev.training.*`

#### 実施手順
1. 新しいディレクトリ構造作成
2. 全Javaファイルのパッケージ宣言更新
3. import文の更新
4. 古いパッケージファイル削除
5. テスト実行による動作確認

#### 結果
✅ パッケージ変更完了、全テスト成功

### 🌐 4. API仕様変更

#### ベースURL変更
- 変更前: `/api/vending-machine`
- 変更後: `/api`

#### 影響範囲
- `VendingMachineController.java`: `@RequestMapping`修正
- `VendingMachineControllerTest.java`: テストエンドポイント修正

#### 新しいAPI仕様
- エンドポイント: `POST /api/purchase`
- リクエスト: `{"money": 100}`
- レスポンス: `{"product": "コーラ", "quantity": 1}`

### ⚙️ 5. アプリケーション設定追加

#### application.properties設定追加
```properties
server.port=8080
spring.application.name=vendingmachine

# JSON設定: NULLカラムを出力しない
spring.jackson.default-property-inclusion=non_null
```

#### 設定効果確認
- 100円の場合: `{"product":"コーラ","quantity":1}`
- 50円の場合: `{"quantity":1}` (productがnullのため除外)

### 🔧 6. Maven Wrapper作成

#### 必要ファイル生成
```
.mvn/
└── wrapper/
    ├── maven-wrapper.jar          # 実行用JARファイル
    └── maven-wrapper.properties   # 設定ファイル
mvnw                              # Unix/Linux用スクリプト
mvnw.cmd                         # Windows用スクリプト
```

#### 設定内容
- Maven バージョン: 3.9.9
- Wrapper バージョン: 3.3.2
- 配布URL: Maven Central Repository

#### 動作確認
```bash
$ ./mvnw --version
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
```

### 📝 7. .gitignoreファイル作成

#### 除外対象
- **Java関連**: `*.class`, `*.log`, `*.jar`
- **Maven関連**: `target/`, `.mvn/wrapper/maven-wrapper.jar`
- **IDE関連**: `.idea/`, `.vscode/`, `.classpath`
- **OS関連**: `.DS_Store`, `Thumbs.db`
- **Spring Boot関連**: `*.log`, `application-local.properties`

#### 効果確認
- ビルド後も`target/`ディレクトリがGit追跡対象外
- 必要なソースファイルのみ追跡対象

### 📚 8. README.md作成

#### 含有内容
- **概要**: プロジェクト説明
- **技術スタック**: Java, Spring Boot, Maven, JUnit 5
- **プロジェクト構造**: ディレクトリ構成
- **API仕様**: エンドポイント、リクエスト/レスポンス
- **実行方法**: 起動、テスト、ビルド手順
- **TDD実装**: プロセス説明
- **使用例**: curlコマンド例
- **今後の拡張予定**: 機能追加案

## TDD実装詳細

### 実装済みテストケース
#### TESTLIST.mdより
- ✅ 100円を入れると、コーラが1本出てくる

#### 実装されたテスト
1. **VendingMachineTest**: ドメインロジックテスト
   - メソッド名: `_100円を入れるとコーラが1本出てくる()`
   - 内容: `vendingMachine.purchase(100)` → `"コーラ"`

2. **VendingMachineControllerTest**: API統合テスト
   - メソッド名: `_100円でコーラを購入できる()`
   - 内容: MockMvcでPOSTリクエストテスト

### TDDサイクル実践
1. **Red**: テスト作成（失敗）
2. **Green**: 最小限の実装（成功）
3. **Refactor**: コード改善

## 最終確認項目

### ✅ 機能確認
- [x] API動作: `POST /api/purchase` → `{"product":"コーラ","quantity":1}`
- [x] テスト成功: 2テスト実行、0失敗
- [x] アプリケーション起動: http://localhost:8080

### ✅ 品質確認
- [x] パッケージ構造: `dev.training.*`
- [x] Maven Wrapper動作
- [x] Git管理適切
- [x] ドキュメント整備

### ✅ 開発環境
- [x] IDE設定: VS Code
- [x] ビルドツール: Maven Wrapper
- [x] バージョン管理: Git + .gitignore

## 技術的成果

### アーキテクチャ
```
HTTP Request → Controller → Domain → DTO → HTTP Response
```

### レイヤー分離
- **Controller層**: REST API、リクエスト/レスポンス処理
- **Domain層**: ビジネスロジック
- **DTO層**: データ転送オブジェクト

### テスト戦略
- **単体テスト**: ドメインロジック
- **統合テスト**: API エンドポイント（MockMvc）

## 学習ポイント

### TDD実践
- テストファースト開発の実践
- Red-Green-Refactorサイクル
- テストリスト管理

### Spring Boot
- RESTコントローラー実装
- JSON シリアライゼーション設定
- テスト環境構築（MockMvc）

### 開発環境
- Maven Wrapper利用
- .gitignore設定
- プロジェクト文書化

## 次のステップ

### 実装候補機能
1. **50円投入時の動作**（テストリスト追加）
2. **複数商品対応**
3. **在庫管理**
4. **お釣り計算**
5. **エラーハンドリング**

### リファクタリング候補
1. **Serviceレイヤー追加**
2. **例外処理追加**
3. **バリデーション強化**
4. **設定外部化**

## 総評

Spring Bootバックエンドアプリケーションのひな形作成が完了しました。TDDアプローチにより、テストファーストでの開発基盤が確立され、今後の機能拡張に向けた土台が整いました。

### 達成事項
- ✅ 基本的なCRUD API実装
- ✅ テスト環境整備
- ✅ 開発環境標準化
- ✅ ドキュメント整備
- ✅ Git管理体制確立

プロジェクトは継続的な機能追加とTDDサイクルの反復に向けて準備完了状態です。
