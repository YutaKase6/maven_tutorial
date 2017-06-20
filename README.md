# maven tutorial
参考  
[maven3 tutorial](http://sambatriste.github.io/maven3-tutorial/)  

## プロジェクトの作成  
アーキタイプによるプロジェクトの作成  

```
$ mvn archetype:generate
```

雛形がとんでもない数出てくる。  
とりあえずデフォルトで。  
Choose a numberと聞かれるので何も入力せずEnter。  

Choose versionと聞かれる。  
とりあえずデフォルトで。  
何も入力せずEnter。  

groupIdを入力する。  
プロジェクトを一意に識別する名前。  
今回はcom.exampleで作成。  

artifactIdを入力する。  
プロジェクトのディレクトリの名前やjarの名前になる。  
今回はhelloで作成。  

versionを入力する。  
最初なのでデフォルトでいいと思う。  
jarの名前に挿入される。  

package名を入力する。  
デフォルトではgroupIdとなっている。  
今回はデフォルトで作成。  

## pom.xml
最低限のpom.xmlがプロジェクトルートの直下に作成されているはず。  
先程設定した内容が確認できる。  

## コンパイル

```
$ mvn compile
```

私の環境(maven3.5.0, jdk1.9)ではコンパイルに失敗した。  
デフォルトのコンパイラの-target, -sourceのオプションが1.5なのが原因。  
jdk1.9から1.5は非サポート。  
pom.xmlに以下の内容を追記することでコンパイルに成功した。  

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <!-- ここから -->
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <!-- ここまで -->
  </properties>
```

おとなしくjdk1.8でやれば問題は無いはず。  

コンパイルに成功すると直下にtargetディレクトリが作成され、クラスファイルが生成される。  
このtarget以下に基本的にビルドの成果物が格納されていく。  
つまりgitignore。  

## テスト

```
mvn test
```

テストが実行される。  
*Test.javaって名前にしておけばとりあえず自動で実行される。  
メソッド名もtestっていれないとだめ?  
リフレクション使ってprivateメソッドを単体テストするコード書いておいた。  

## jarの作成

```
mvn package
```

target以下にjarを作成する。  
このjarをjavaコマンドで実行しても動かない。  
依存ライブラリやメインのエンドポイントを指定するためにmaven-assembly-pluginを利用した。  
pom.xmlへ追記。  

```xml
<build>
      <plugins>
          <plugin>
            <artifactId>maven-assembly-plugin</artifactId>
                <version>2.2</version>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                    <archive>
                        <manifest>
                            <!-- mainクラスを指定する -->
                            <mainClass>com.example.App</mainClass>
                        </manifest>
                    </archive>
                </configuration>
          </plugin>
      </plugins>
  </build>
```

## jarの実行

```
java -jar hello-1.0-SNAPSHOT-jar-with-dependencies.jar
```
