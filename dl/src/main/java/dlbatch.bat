javac -d ..\..\..\classes -classpath ..\..\..\..\common\dist\hr_common.jar;. com\tm\hr\dl\exceptions\*.java 
javac -d ..\..\..\classes -classpath ..\..\..\..\common\dist\hr_common.jar;. com\tm\hr\dl\interfaces\dto\*.java
javac -d ..\..\..\classes -classpath ..\..\..\..\common\dist\hr_common.jar;. com\tm\hr\dl\dto\*.java
javac -d ..\..\..\classes -classpath ..\..\..\..\common\dist\hr_common.jar;. com\tm\hr\dl\interfaces\dao\*.java
javac -d ..\..\..\classes -classpath ..\..\..\..\common\dist\hr_common.jar;. com\tm\hr\dl\dao\*.java
cd..
cd..
cd..
cd classes 
jar -cvf ..\dist\dljar1.jar com
