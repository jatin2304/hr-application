javac -d ..\..\..\classes -classpath ..\..\..\..\dl\dist\dljar1.jar;..\..\..\..\common\dist\hr_common.jar;. com\tm\hr\bl\exceptions\*.java 
javac -d ..\..\..\classes -classpath ..\..\..\..\dl\dist\dljar1.jar;..\..\..\..\common\dist\hr_common.jar;. com\tm\hr\bl\interfaces\pojo\*.java
javac -d ..\..\..\classes -classpath ..\..\..\..\dl\dist\dljar1.jar;..\..\..\..\common\dist\hr_common.jar;. com\tm\hr\bl\pojo\*.java
javac -d ..\..\..\classes -classpath ..\..\..\..\dl\dist\dljar1.jar;..\..\..\..\common\dist\hr_common.jar;. com\tm\hr\bl\interfaces\managers\*.java
javac -d ..\..\..\classes -classpath ..\..\..\..\dl\dist\dljar1.jar;..\..\..\..\common\dist\hr_common.jar;. com\tm\hr\bl\managers\*.java
cd..
cd..
cd..
cd classes 
jar -cvf ..\dist\bljar1.jar com
