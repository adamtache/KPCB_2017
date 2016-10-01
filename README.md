# KPCB 2017 Submission

By Adam Tache

---

# Gradle Setup

## 0. Install Gradle

1. Mac OSX
    
    ```
    brew install gradle
    ```
    
2. Ubuntu

    ```
    sudo add-apt-repository ppa:cwchien/gradle
    sudo apt-get update
    sudo apt-get install gradle
    ```

## 1. Establish project as Gradle project

```
$ cd path/to/project
$ echo ".gradle/" >> .gitignore
$ echo "build/" >> .gitignore
$ gradle init
```

## 2. Build project

```
$ gradle build
```

Outputs `KPCB.jar` file to `build/lib`

## 3. Run tests

```
$ gradle test
```

Runs JUnit tests and saves an HTML file with test results to `build/reports/tests/test/index.html`.