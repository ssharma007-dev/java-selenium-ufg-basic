# Applitools Java Classic Demo

### Setup API Key

Set your `APPLITOOLS_API_KEY` as an environment variable.
### Option 1: Using IntelliJ
Follow the steps shown below:
![Step 1](public/img.png)

![img_1.png](public/img_1.png)



Headless vs Headed
![headed_headless.png](public/headed_headless.png)

#### Headed (for demo)
```
java Main
```
#### Headless (for CI)
```
java -Dheadless=true Main
```

IntelliJ
- Right-click src/test/java/tests/LoginTest
- Run test
- Add VM option for headless:
-Dheadless=true